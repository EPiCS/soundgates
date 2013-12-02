--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - hwt_noise
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  Hardware thread for generating noise
--
-- ======================================================================

library ieee;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

library proc_common_v3_00_a;
use proc_common_v3_00_a.proc_common_pkg.all;

library reconos_v3_00_c;
use reconos_v3_00_c.reconos_pkg.all;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;
use soundgates_v1_00_a.soundgates_reconos_pkg.all;

entity hwt_noise is
    generic(
    	SND_COMP_CLK_FREQ   : integer := 100_000_000;
		SND_COMP_NOISE_TPYE : integer := 0
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
end hwt_noise;

architecture Behavioral of hwt_noise is

    ----------------------------------------------------------------
    -- Subcomponent declarations
    ----------------------------------------------------------------
    
    component noise is
	generic(
		FPGA_FREQUENCY  : integer       := 100_000_000;
		NOISE           : NOISE_TYPE    := WHITE
	);
    Port ( 
            clk          : in  std_logic;           
            rst          : in  std_logic;
            ce           : in  std_logic;
            data         : out signed(31 downto 0)
           );
    end component noise;
 
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

    type STATE_TYPE is (STATE_INIT, STATE_WAITING, STATE_PROCESS, STATE_WRITE_MEM, STATE_NOTIFY, STATE_EXIT);
    signal state    : STATE_TYPE;
    
    ----------------------------------------------------------------
    -- Common sound component signals, constants and types
    ----------------------------------------------------------------
    
    constant C_MAX_SAMPLE_COUNT : integer := 1024;
    
   	-- define size of local RAM here
	constant C_LOCAL_RAM_SIZE          : integer := C_MAX_SAMPLE_COUNT;
	constant C_LOCAL_RAM_ADDRESS_WIDTH : integer := clog2(C_LOCAL_RAM_SIZE);
	constant C_LOCAL_RAM_SIZE_IN_BYTES : integer := 4*C_LOCAL_RAM_SIZE;

    type LOCAL_MEMORY_T is array (0 to C_LOCAL_RAM_SIZE-1) of std_logic_vector(31 downto 0);
        
    signal o_RAMAddr_noise : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMData_noise : std_logic_vector(0 to 31);   -- noise to local ram
	signal i_RAMData_noise : std_logic_vector(0 to 31);   -- local ram to noise
    signal o_RAMWE_noise   : std_logic;
	
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
    signal noise_ce            : std_logic;           -- noise clock enable (like a start/stop signal)
    
    signal noise_data      : signed(31 downto 0);
    
    ----------------------------------------------------------------
    -- OS Communication
    ----------------------------------------------------------------
    
    constant NOISE_START : std_logic_vector(31 downto 0) := x"0000000F";
    constant NOISE_EXIT  : std_logic_vector(31 downto 0) := x"000000F0";

begin
    -----------------------------------
    -- Hard wirings
    -----------------------------------
    clk <= HWT_Clk;
	rst <= HWT_Rst;
    o_RAMData_noise <= std_logic_vector(noise_data);
    
    o_RAMAddr_reconos(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1) <= o_RAMAddr_reconos_2((32-C_LOCAL_RAM_ADDRESS_WIDTH) to 31);
    
        
    -- ReconOS Stuff
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
    NOISE_INST : noise
    generic map(
		FPGA_FREQUENCY  => SND_COMP_CLK_FREQ,
		NOISE        => WAVEFORM_TYPE'val(SND_COMP_NOISE_TPYE)
	 )
    port map( 
            clk          => clk,
            rst          => rst,
            ce           => noise_ce,
            data         => noise_data
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
			if (o_RAMWE_noise = '1') then
				local_ram(to_integer(unsigned(o_RAMAddr_noise))) := o_RAMData_noise;
            --else      -- else not needed, because noise is not consuming any samples
			--	i_RAMData_noise <= local_ram(conv_integer(unsigned(o_RAMAddr_noise)));
			end if;
		end if;
	end process;
    
    
    NOISE_CTRL_FSM_PROC : process (clk, rst, o_osif, o_memif) is
        variable done : boolean;            
    begin
        if rst = '1' then
                    
            osif_reset(o_osif);
			memif_reset(o_memif);           
            ram_reset(o_ram);
            
            state           <= STATE_INIT;
            sample_count    <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);
            osif_ctrl_signal <= (others => '0');
            noise_ce       <= '0';
            o_RAMWE_noise  <= '0';
            
            done := False;
              
        elsif rising_edge(clk) then
            
            noise_ce      <= '0';
            o_RAMWE_noise <= '0';
            osif_ctrl_signal <= ( others => '0');
            
            case state is            
            when STATE_INIT =>

                -- Init not used

                state <= STATE_WAITING;           
            
            when STATE_WAITING =>

                -- Software process "Synthesizer" sends the start signal via mbox_start
                osif_mbox_get(i_osif, o_osif, MBOX_START, osif_ctrl_signal, done);
                if done then
                    if osif_ctrl_signal = NOISE_START then
                        
                        sample_count <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);

                        state        <= STATE_PROCESS;

                    elsif osif_ctrl_signal = NOISE_EXIT then
                        
                        state   <= STATE_EXIT;

                    end if;    
                end if;
                
            when STATE_PROCESS =>
                if sample_count > 0 then
                    
                    noise_ce        <= '1';
                    o_RAMWE_noise   <= '1';
                    o_RAMAddr_noise <= std_logic_vector(unsigned(o_RAMAddr_noise) + 1);
                    sample_count  <= sample_count - 1;
                else
                    -- Samples have been generated
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

