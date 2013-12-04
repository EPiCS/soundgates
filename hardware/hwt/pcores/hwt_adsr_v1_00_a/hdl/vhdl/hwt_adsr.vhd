--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - hwt_adsr
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  Hardware thread for generating adsr
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

entity hwt_adsr is
    generic(
    	SND_COMP_CLK_FREQ   : integer := 100_000_000;
		SND_COMP_adsr_TPYE : integer := 0
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
end hwt_adsr;

architecture Behavioral of hwt_adsr is

    ----------------------------------------------------------------
    -- Subcomponent declarations
    ----------------------------------------------------------------
     memif_read_word(i_memif, o_memif, rlse_amp_addr, rlse_amp, done);
                    if done then
                        refresh_state <= "0";
                        state <= STATE_PROCESS;
    component adsr is
    Port ( 
            clk         : in  std_logic;
            rst         : in  std_logic;
            ce          : in  std_logic;
            input_wave  : in  signed(31 downto 0);
            start       : in  std_logic;
            stop        : in  std_logic;
            attack      : in  unsigned(31 downto 0); 
            decay       : in  unsigned(31 downto 0);  
            release     : in  unsigned(31 downto 0);
            start_amp   : in  unsigned(31 downto 0);
            attack_amp  : in  unsigned(31 downto 0);
            sustain_amp : in  unsigned(31 downto 0);
            release_amp : in  unsigned(31 downto 0);
            wave        : out signed(31 downto 0)
           );
    end component adsr;
 
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

    type STATE_TYPE is (STATE_INIT, STATE_REFRESH, STATE_REFRESH_RELEASE, STATE_WAITING, STATE_PROCESS, STATE_WRITE_MEM, STATE_NOTIFY, STATE_EXIT);
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
        
    signal o_RAMAddr_adsr : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMData_adsr : std_logic_vector(0 to 31);   -- adsr to local ram
	signal i_RAMData_adsr : std_logic_vector(0 to 31);   -- local ram to adsr
    signal o_RAMWE_adsr   : std_logic;
	
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
    signal adsr_ce          : std_logic;           -- adsr clock enable (like a start/stop signal)
    
    signal input_data       : signed(31 downto 0);
    signal adsr_data        : signed(31 downto 0);
    signal start            : std_logic;
    signal stop             : std_logic;
    
    signal refresh_state    : unsigned(2 downto 0);
    signal process_state    : unsigned(2 downto 0);
    signal bang_state       : unsigned(2 downto 0);
    
    signal bang_addr             : std_logic_vector(31 downto 0);
    signal bang_stop_addr        : std_logic_vector(31 downto 0);
    signal atck_dura_addr        : std_logic_vector(31 downto 0);
    signal dcay_dura_addr        : std_logic_vector(31 downto 0);
    signal rlse_dura_addr        : std_logic_vector(31 downto 0);
    signal strt_amp_addr         : std_logic_vector(31 downto 0);
    signal atck_amp_addr         : std_logic_vector(31 downto 0);
    signal sust_amp_addr         : std_logic_vector(31 downto 0);
    signal rlse_amp_addr         : std_logic_vector(31 downto 0);

    signal bang             : signed(31 downto 0);
    signal bang_stop        : signed(31 downto 0);
    signal atck_dura        : signed(31 downto 0);
    signal dcay_dura        : signed(31 downto 0);
    signal rlse_dura        : signed(31 downto 0);
    signal strt_amp         : signed(31 downto 0);
    signal atck_amp         : signed(31 downto 0);
    signal sust_amp         : signed(31 downto 0);
    signal rlse_amp         : signed(31 downto 0);
    
    
    
    ----------------------------------------------------------------
    -- OS Communication
    ----------------------------------------------------------------
    
    constant ADSR_START : std_logic_vector(31 downto 0) := x"0000000F";
    constant ADSR_EXIT  : std_logic_vector(31 downto 0) := x"000000F0";
    
    constant C_START_BANG : std_logic_vector(31 downto 0) := x"00000001";
    constant C_STOP_BANG  : std_logic_vector(31 downto 0) := x"FFFFFFFF";

begin
    -----------------------------------
    -- Hard wirings
    -----------------------------------
    clk <= HWT_Clk;
	rst <= HWT_Rst;
    o_RAMData_adsr <= std_logic_vector(adsr_data);
    
    
    
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
    adsr_INST : adsr
    port map( 
            clk         => clk,
            rst         => rst,
            ce          => adsr_ce,
            input_wave  => input_data,
            start       => start,
            stop        => stop,
            attack      => atck_dura,
            decay       => dcay_dura, 
            release     => rlse_dura,
            start_amp   => strt_amp,
            attack_amp  => atck_amp,
            sustain_amp => sust_amp,
            release_amp => rlse_amp,
            wave        => adsr_data
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
			if (o_RAMWE_adsr = '1') then
				local_ram(to_integer(unsigned(o_RAMAddr_adsr))) := o_RAMData_adsr;
            --else      -- else not needed, because adsr is not consuming any samples
			--	i_RAMData_adsr <= local_ram(conv_integer(unsigned(o_RAMAddr_adsr)));
			end if;
		end if;
	end process;
    
    
    ADSR_CTRL_FSM_PROC : process (clk, rst, o_osif, o_memif) is
        variable done : boolean;            
    begin
        if rst = '1' then
                    
            osif_reset(o_osif);
			memif_reset(o_memif);           
            ram_reset(o_ram);
            
            state           <= STATE_INIT;
            sample_count    <= to_unsigned(0, 16);
            osif_ctrl_signal <= (others => '0');
            
            
            adsr_ce     <= '0';
            start       <= '0';
            stop        <= '0';
            attack      <= (others => '0');
            decay       <= (others => '0');
            release     <= (others => '0');
            start_amp   <= (others => '0');
            attack_amp  <= (others => '0');
            sustain_amp <= (others => '0');
            release_amp <= (others => '0');
            o_RAMWE_adsr<= '0';

            refresh_state <= 0;
            process_state <= 0;
            bang        <= (others => '0');
            
            done := False;
              
        elsif rising_edge(clk) then
            
            adsr_ce      <= '0';
            o_RAMWE_adsr <= '0';
            osif_ctrl_signal <= ( others => '0');
            
            case state is            
            -- INIT State gets the address of the header struct
            when STATE_INIT =>               
                snd_comp_get_header(i_osif, o_osif, i_memif, o_memif, snd_comp_header, done);         
                if done then
                    -- Initialize your signals
                        bang_addr      <= snd_comp_header.opt_arg_addr;
                        stop_addr      <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 4);
                        atck_dura_addr <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 8);
                        dcay_dura_addr <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 12);
                        rlse_dura_addr <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 16);
                        strt_amp_addr  <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 20);
                        atck_amp_addr  <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 24);
                        sust_amp_addr  <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 28);
                        rlse_amp_addr  <= std_logic_vector(unsigned(snd_comp_header.opt_arg_addr) + 32);

                        state <= STATE_WAITING;
                end if;    

            when STATE_WAITING =>
                -- Software process "Synthesizer" sends the start signal via mbox_start
                osif_mbox_get(i_osif, o_osif, MBOX_START, osif_ctrl_signal, done);
                if done then
                    if osif_ctrl_signal = ADSR_START then
                        
                        sample_count <= to_unsigned(0, 16);
                        state        <= STATE_CHECK_BANG;

                    elsif osif_ctrl_signal = ADSR_EXIT then
                        
                        state   <= STATE_EXIT;

                    end if;    
                end if;
                 
            when STATE_REFRESH =>
                
                -- Refresh your signals
                case refresh_state is
                when "0" =>
                    memif_read_word(i_memif, o_memif, atck_dura_addr, atck_dura, done);
                    if done then
                        refresh_state <= "1";
                    end if;
                when "1" =>
                    memif_read_word(i_memif, o_memif, dcay_dura_addr, dcay_dura, done);
                    if done then
                        refresh_state <= "2";
                    end if;
                when "2" =>
                    memif_read_word(i_memif, o_memif, strt_amp_addr, strt_amp, done);
                    if done then
                        refresh_state <= "3";
                    end if;
                when "3" =>
                    memif_read_word(i_memif, o_memif, atck_amp_addr, atck_amp, done);
                    if done then
                        refresh_state <= "4";
                    end if;
                when "4" =>
                    memif_read_word(i_memif, o_memif, sust_amp_addr, sust_amp, done);
                    if done then
                        refresh_state <= "0";
                        state <= STATE_PROCESS;
                    end if;
            end case;
            
            when STATE_REFRESH_RELEASE => 
                stop <= '0';   
                case refresh_state is
                    when => "0"
                        memif_read_word(i_memif, o_memif, rlse_amp_addr, rlse_amp, done);
                        if done then
                            refresh_state <= "1";
                        end if;
                    when "1" =>
                        memif_read_word(i_memif, o_memif, rlse_dura_addr, rlse_dura, done);
                        if done then
                            refresh_state <= "0";
                            state <= STATE_PROCESS;
                        end if;
                end case;

            when STATE_CHECK_BANG =>    
                case bang_state is                
                    when "0" =>
                        memif_read_word(i_memif, o_memif, bang_addr, bang, done);
                        if done then
                            if bang = C_START_BANG then
                                bang_state <= "1";
                                state <= STATE_REFRESH;
                            else
                                state <= STATE_WAITING;
                            end if;
                        end if;
 
                    when "1" =>
                        memif_read_word(i_memif, o_memif, bang_stop_addr, bang_stop, done);
                        if done then
                            if (bang_stop = C_STOP_BANG) then
                                bang_state <= "0";
                                stop <= '1';
                                state <= STATE_REFRESH_RELEASE;
                            else
                                state <= STATE_REFRESH;
                            end if;
                        end if;

            when STATE_PROCESS =>
                if sample_count < to_unsigned(C_MAX_SAMPLE_COUNT, 16) then
                   
                    case process_state is

                    when "0" => 
                        adsr_ce        <= '1';
                        process_state  <= "1";

                    when "1" => 
                        o_RAMData_adsr <= std_logic_vector(resize(adsr_data * signed(i_RAMData_adsr), 32));
                        o_RAMWE_adsr   <= '1';

                        adsr_ce        <= '0';
                        process_state  <= "2";       

                    when "2" =>
                        o_RAMWE_adsr   <= '0';
                        o_RAMAddr_adsr <= std_logic_vector(unsigned(o_RAMAddr_adsr) + 1);
                        sample_count  <= sample_count + 1;

                        process_state  <= "0";  
                    end case;

                else
                    -- Samples have been generated
                    o_RAMAddr_adsr  <= (others => '0');
                    sample_count    <= to_unsigned(0, 16);
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

