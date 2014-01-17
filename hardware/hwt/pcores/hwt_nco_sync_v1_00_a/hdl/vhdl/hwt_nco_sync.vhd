--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - hwt_nco_sync_sync
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  Hardware thread for a synchronized numeric controlled 
--                 oscillator
--
--                 Synchronization of two oscillators
--                 Whenever the master's phase ends, reset slave's phase.
--                 Slave's frequency usually higher and not dividable by
--                 master's frequency
--
-- ======================================================================

library ieee;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

--library proc_common_v3_00_a;
--use proc_common_v3_00_a.proc_common_pkg.all;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;
use soundgates_v1_00_a.soundgates_reconos_pkg.all;

entity hwt_nco_sync is
    generic(
		SND_COMP_CLK_FREQ : integer := 100_000_000;
		SND_COMP_NCO_SYNC_TPYE : integer := 2
	);
   port (
		-- OSIF FIFO ports
		OSIF_FIFO_Sw2Hw_Data    : in  std_logic_vector(31 downto 0);
		OSIF_FIFO_Sw2Hw_Fill    : in  std_logic_vector(15 downto 0);
		OSIF_FIFO_Sw2Hw_Empty   : in  std_logic;
		OSIF_FIFO_Sw2Hw_RE      : out std_logic;

		OSIF_FIFO_Hw2Sw_Data    : out std_logic_vector(31 downto 0);
		OSIF_FIFO_Hw2Sw_Rem     : in  std_logic_vector(15 downto 0);
		OSIF_FIFO_Hw2Sw_Full    : in  std_logic;
		OSIF_FIFO_Hw2Sw_WE      : out std_logic;

		-- MEMIF FIFO ports
		MEMIF_FIFO_Hwt2Mem_Data    : out std_logic_vector(31 downto 0);
		MEMIF_FIFO_Hwt2Mem_Rem     : in  std_logic_vector(15 downto 0);
		MEMIF_FIFO_Hwt2Mem_Full    : in  std_logic;
		MEMIF_FIFO_Hwt2Mem_WE      : out std_logic;

		MEMIF_FIFO_Mem2Hwt_Data    : in  std_logic_vector(31 downto 0);
		MEMIF_FIFO_Mem2Hwt_Fill    : in  std_logic_vector(15 downto 0);
		MEMIF_FIFO_Mem2Hwt_Empty   : in  std_logic;
		MEMIF_FIFO_Mem2Hwt_RE      : out std_logic;

		HWT_Clk   : in  std_logic;
		HWT_Rst   : in  std_logic
    );
end hwt_nco_sync;

architecture Behavioral of hwt_nco_sync is

    ----------------------------------------------------------------
    -- Subcomponent declarations
    ----------------------------------------------------------------
    
    component nco_sync is
	generic(
		FPGA_FREQUENCY  : integer       := 100_000_000;
		WAVEFORM        : WAVEFORM_TYPE := SAW    -- sync nco eignet sich eigentlich nur für square oder saw
	);
    Port ( 
            clk          : in  std_logic;           
            rst          : in  std_logic;
            ce           : in  std_logic;
            master_phase_offset : in signed(31 downto 0);
            master_phase_incr   : in signed(31 downto 0);
            slave_phase_offset : in signed(31 downto 0);
            slave_phase_incr   : in signed(31 downto 0);
            soundout         : out signed(31 downto 0)
           );
    end component nco_sync;
 
    signal clk   : std_logic;
	signal rst   : std_logic;

-- ReconOS Stuff
    signal i_osif   : i_osif_t;
    signal o_osif   : o_osif_t;
    signal i_memif  : i_memif_t;
    signal o_memif  : o_memif_t;
    
    signal i_ram    : i_ram_t;
    signal o_ram    : o_ram_t;
    
    constant MBOX_START   : std_logic_vector(31 downto 0) := x"00000000";
    constant MBOX_FINISH  : std_logic_vector(31 downto 0) := x"00000001";
-- /ReconOS Stuff

    type STATE_TYPE is (STATE_INIT, STATE_WAITING, STATE_REFRESH_INPUT_PHASE_OFFSET, STATE_REFRESH_INPUT_PHASE_INCR, STATE_PROCESS, STATE_WRITE_MEM, STATE_NOTIFY, STATE_EXIT);
    signal state    : STATE_TYPE;
    
    ----------------------------------------------------------------
    -- Common sound component signals, constants and types
    ----------------------------------------------------------------
    
    constant C_MAX_SAMPLE_COUNT : integer := 1024;
    
   	-- define size of local RAM here
	constant C_LOCAL_RAM_SIZE          : integer := C_MAX_SAMPLE_COUNT;
	constant C_LOCAL_RAM_ADDRESS_WIDTH : integer := 10;--clog2(C_LOCAL_RAM_SIZE);
	constant C_LOCAL_RAM_SIZE_IN_BYTES : integer := 4*C_LOCAL_RAM_SIZE;

    type LOCAL_MEMORY_T is array (0 to C_LOCAL_RAM_SIZE-1) of std_logic_vector(31 downto 0);
        
    signal o_RAMAddr_nco_sync : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMData_nco_sync : std_logic_vector(0 to 31);   -- nco_sync to local ram
	signal i_RAMData_nco_sync : std_logic_vector(0 to 31);   -- local ram to nco_sync
    signal o_RAMWE_nco_sync   : std_logic;
	
  	signal o_RAMAddr_reconos   : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMAddr_reconos_2 : std_logic_vector(0 to 31);
	signal o_RAMData_reconos   : std_logic_vector(0 to 31);
	signal o_RAMWE_reconos     : std_logic;
	signal i_RAMData_reconos   : std_logic_vector(0 to 31);
    
    signal osif_ctrl_signal : std_logic_vector(31 downto 0);
    signal ignore : std_logic_vector(31 downto 0);
    
    
    constant o_RAMAddr_max : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1) := (others=>'1');

	shared variable local_ram : LOCAL_MEMORY_T;
    
    signal snd_comp_header : snd_comp_header_msg_t;  -- common sound component header
       
    signal sample_count            : unsigned(15 downto 0) := to_unsigned(C_MAX_SAMPLE_COUNT, 16);
    
    ----------------------------------------------------------------
    -- Component dependent signals
    ----------------------------------------------------------------
    signal nco_sync_ce            : std_logic;           -- nco_sync clock enable (like a start/stop signal)

    signal master_offset_addr : std_logic_vector(31 downto 0);
    signal master_incr_addr   : std_logic_vector(31 downto 0);
	 
	 signal slave_offset_addr : std_logic_vector(31 downto 0);
    signal slave_incr_addr   : std_logic_vector(31 downto 0);
   
    signal master_phase_offset  : std_logic_vector(31 downto 0);
    signal master_phase_incr    : std_logic_vector(31 downto 0);
	 
	 signal slave_phase_offset  : std_logic_vector(31 downto 0);
    signal slave_phase_incr    : std_logic_vector(31 downto 0);
    
    signal nco_sync_data      : signed(31 downto 0);
    
    signal state_inner_process : std_logic;
    
    ----------------------------------------------------------------
    -- OS Communication
    ----------------------------------------------------------------
    
    constant nco_sync_START : std_logic_vector(31 downto 0) := x"0000000F";
    constant nco_sync_EXIT  : std_logic_vector(31 downto 0) := x"000000F0";

begin
    -----------------------------------
    -- Hard wirings
    -----------------------------------
    clk <= HWT_Clk;
	rst <= HWT_Rst;
    o_RAMData_nco_sync <= std_logic_vector(nco_sync_data);
    
    o_RAMAddr_reconos(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1) <= o_RAMAddr_reconos_2((32-C_LOCAL_RAM_ADDRESS_WIDTH) to 31);
    
        
    -- ReconOS Stufff
    osif_setup (
            i_osif,
            o_osif,
            OSIF_FIFO_Sw2Hw_Data,
            OSIF_FIFO_Sw2Hw_Fill,
            OSIF_FIFO_Sw2Hw_Empty,
            OSIF_FIFO_Hw2Sw_Rem,
            OSIF_FIFO_Hw2Sw_Full,
            OSIF_FIFO_Sw2Hw_RE,
            OSIF_FIFO_Hw2Sw_Data,
            OSIF_FIFO_Hw2Sw_WE
        );
                    
    memif_setup (
            i_memif,
            o_memif,
            MEMIF_FIFO_Mem2Hwt_Data,
            MEMIF_FIFO_Mem2Hwt_Fill,
            MEMIF_FIFO_Mem2Hwt_Empty,
            MEMIF_FIFO_Hwt2Mem_Rem,
            MEMIF_FIFO_Hwt2Mem_Full,
            MEMIF_FIFO_Mem2Hwt_RE,
            MEMIF_FIFO_Hwt2Mem_Data,
            MEMIF_FIFO_Hwt2Mem_WE
        );

    ram_setup (
		i_ram,
		o_ram,
		o_RAMAddr_reconos_2,
		o_RAMWE_reconos,
		o_RAMData_reconos,
		i_RAMData_reconos
	);
            
    -- /ReconOS Stuff
    nco_sync_inst : nco_sync
    generic map(
		FPGA_FREQUENCY  => SND_COMP_CLK_FREQ,
		WAVEFORM        => WAVEFORM_TYPE'val(SND_COMP_NCO_TPYE)
	 )
    port map( 
            clk          => clk,
            rst          => rst,
            ce           => nco_sync_ce,
            master_phase_offset => signed(master_phase_offset),
            master_phase_incr   => signed(master_phase_incr),
            slave_phase_offset => signed(slave_phase_offset),
            slave_phase_incr   => signed(slave_phase_incr),  
            soundout         => nco_sync_data
            );
            
    local_ram_ctrl_1 : process (clk) is
	begin
		if (rising_edge(clk)) then
			if (o_RAMWE_reconos = '1') then
				local_ram(to_integer(unsigned(o_RAMAddr_reconos))) := o_RAMData_reconos;
			else
				i_RAMData_reconos <= local_ram(to_integer(unsigned(o_RAMAddr_reconos)));
			end if;
		end if;
	end process;
    
    local_ram_ctrl_2 : process (clk) is
	begin
		if (rising_edge(clk)) then		
			if (o_RAMWE_nco_sync = '1') then
				local_ram(to_integer(unsigned(o_RAMAddr_nco_sync))) := o_RAMData_nco_sync;
            --else      -- else not needed, because nco_sync is not consuming any samples
			--	i_RAMData_nco_sync <= local_ram(conv_integer(unsigned(o_RAMAddr_nco_sync)));
			end if;
		end if;
	end process;
    
    
    NCO_SYNC_CTRL_FSM_PROC : process (clk, rst, o_osif, o_memif) is
        variable done : boolean;            
    begin
        if rst = '1' then
                    
            osif_reset(o_osif);
			memif_reset(o_memif);           
            ram_reset(o_ram);
            
            state           <= STATE_INIT;
            sample_count    <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);
            osif_ctrl_signal <= (others => '0');
            nco_sync_ce       <= '0';
            o_RAMWE_nco_sync  <= '0';
            state_inner_process <= '0';
            
            done := False;
              
        elsif rising_edge(clk) then
            
            nco_sync_ce      <= '0';
            o_RAMWE_nco_sync <= '0';
            osif_ctrl_signal <= ( others => '0');
            
            case state is            
            -- INIT State gets the address of the header struct
            when STATE_INIT =>               

                snd_comp_get_header(i_osif, o_osif, i_memif, o_memif, snd_comp_header, done);         
                if done then
                    -- Initialize your signals
                    master_offset_addr <= snd_comp_header.opt_arg_addr;
                    master_incr_addr   <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 4);
						  master_offset_addr   <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 8);	
						  slave_incr_addr   <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 12);
                    state <= STATE_WAITING;
                end if;            
            
            when STATE_WAITING =>

                -- Software process "Synthesizer" sends the start signal via mbox_start
                osif_mbox_get(i_osif, o_osif, MBOX_START, osif_ctrl_signal, done);
                if done then
                    if osif_ctrl_signal = nco_sync_START then
                        
                        sample_count <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);

                        state        <= STATE_REFRESH_INPUT_PHASE_OFFSET;

                    elsif osif_ctrl_signal = nco_sync_EXIT then
                        
                        state   <= STATE_EXIT;

                    end if;    
                end if;
                 
            when STATE_REFRESH_MASTER_INPUT_PHASE_OFFSET =>
                
                memif_read_word(i_memif, o_memif, master_offset_addr, master_phase_offset, done);
                if done then
                    state <= STATE_REFRESH_INPUT_PHASE_INCR;
                end if;
                
            when STATE_REFRESH_INPUT_MASTER_PHASE_INCR =>
            
                memif_read_word(i_memif, o_memif, master_incr_addr, master_phase_incr, done);
                if done then
                    state <= STATE_REFRESH_MASTER_INPUT_PHASE_OFFSET;
                end if;
                 
            when STATE_REFRESH_MASTER_INPUT_PHASE_OFFSET =>
                
                memif_read_word(i_memif, o_memif, slave_offset_addr, slave_phase_offset, done);
                if done then
                    state <= STATE_REFRESH_INPUT_SLAVE_PHASE_INCR;
                end if;
                
            when STATE_REFRESH_INPUT_SLAVE_PHASE_INCR =>
            
                memif_read_word(i_memif, o_memif, slave_incr_addr, slave_phase_incr, done);
                if done then
                    state <= STATE_PROCESS;
                end if;
                
            when STATE_PROCESS =>
                if sample_count > 0 then
                    case state_inner_process is
                        when '0' =>
                            o_RAMWE_nco_sync   <= '1';
                            nco_sync_ce        <= '1'; -- ein takt früher
                            state_inner_process          <= '1';
                        when '1' =>
                            o_RAMAddr_nco_sync       <= std_logic_vector(unsigned(o_RAMAddr_nco_sync) + 1);
                            sample_count        <= sample_count - 1;
                            state_inner_process <= '0';                    
                    end case;
                else
                    -- Samples have been generated
                    o_RAMAddr_nco_sync <= (others => '0');
                    state <= STATE_WRITE_MEM;
                end if;

             when STATE_WRITE_MEM =>
        
                memif_write(i_ram, o_ram, i_memif, o_memif, X"00000000", snd_comp_header.dest_addr, std_logic_vector(to_unsigned(C_LOCAL_RAM_SIZE_IN_BYTES,24)), done);
                if done then
                    state <= STATE_NOTIFY;
				end if;
				
		    when STATE_NOTIFY =>

                osif_mbox_put(i_osif, o_osif, MBOX_FINISH, snd_comp_header.dest_addr, ignore, done);
                if done then
                    state <= STATE_WAITING;
				end if;
                        
            when STATE_EXIT =>

                   osif_thread_exit(i_osif,o_osif);            
            end case;
        end if;
    end process;

end Behavioral;

-- ====================================
-- = RECONOS Function Library - Copy and Paste!
-- ====================================        
-- osif_mbox_put(i_osif, o_osif, MBOX_NAME, SOURCESIGNAL, ignore, done);
-- osif_mbox_get(i_osif, o_osif, MBOX_NAME, TARGETSIGNAL, done);

-- Read from shared memory:

-- Speicherzugriffe:
-- Wortzugriff:
-- memif_read_word(i_memif, o_memif, addr, TARGETSIGNAL, done);
-- memif_write_word(i_memif, o_memif, addr, SOURCESIGNAL, done);

-- Die Laenge ist bei Speicherzugriffen Byte adressiert!
-- memif_read(i_ram, o_ram, i_memif, o_memif, SRC_ADDR std_logic_vector(31 downto 0);
--            dst_addr std_logic_vector(31 downto 0);
--            BYTES std_logic_vector(23 downto 0);
--            done);
-- memif_write(i_ram, o_ram, i_memif, o_memif,
--             src_addr : in std_logic_vector(31 downto 0),
--             dst_addr : in std_logic_vector(31 downto 0);
--             len      : in std_logic_vector(23 downto 0);
--             done);

