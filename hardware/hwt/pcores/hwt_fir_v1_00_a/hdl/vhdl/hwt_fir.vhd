--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - hwt_fir
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--                 Lukas Funke, University of Paderborn                   
--
--   description:  Hardware thread for FIR Filter
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

entity hwt_fir is
    generic(
    	SND_COMP_CLK_FREQ   : integer := 100_000_000		
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
end hwt_fir;

architecture Behavioral of hwt_fir is

    ----------------------------------------------------------------
    -- Subcomponent declarations
    ----------------------------------------------------------------

    COMPONENT fir
        generic(
            FIR_ORDER   : integer
        );
        port(
            clk          : in  std_logic;
            rst          : in  std_logic;
            ce           : in  std_logic;
            coefficients : in  mem16(FIR_ORDER downto 0);
            x_in         : in  signed(23 downto 0);
            y_out        : out signed(23 downto 0)
        );
    END COMPONENT;
   
    ----------------------------------------------------------------
    -- Signal declarations
    ----------------------------------------------------------------
    
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

    type STATE_TYPE is (STATE_IDLE, STATE_REFRESH_HWT_ARGS, STATE_READ_MEM, STATE_PROCESS, STATE_WRITE_MEM, STATE_NOTIFY, STATE_EXIT);
    signal state    : STATE_TYPE;
    
    ----------------------------------------------------------------
    -- Common sound component signals, constants and types
    ----------------------------------------------------------------    
    constant C_MAX_SAMPLE_COUNT : integer := 128;
    
   	-- define size of local RAM here
	constant C_LOCAL_RAM_SIZE          : integer := C_MAX_SAMPLE_COUNT;
	constant C_LOCAL_RAM_ADDRESS_WIDTH : integer := clog2(C_LOCAL_RAM_SIZE);
	constant C_LOCAL_RAM_SIZE_IN_BYTES : integer := 4*C_LOCAL_RAM_SIZE;

    type LOCAL_MEMORY_T is array (0 to C_LOCAL_RAM_SIZE-1) of std_logic_vector(31 downto 0);
        
    signal o_RAMAddr_fir : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMData_fir : std_logic_vector(0 to 31);   -- fir to local ram
	signal i_RAMData_fir : std_logic_vector(0 to 31);   -- local ram to fir
    signal o_RAMWE_fir   : std_logic;
	
  	signal o_RAMAddr_reconos   : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMAddr_reconos_2 : std_logic_vector(0 to 31);
	signal o_RAMData_reconos   : std_logic_vector(0 to 31);
	signal o_RAMWE_reconos     : std_logic;
	signal i_RAMData_reconos   : std_logic_vector(0 to 31);
    
    signal osif_ctrl_signal : std_logic_vector(31 downto 0);
    signal ignore : std_logic_vector(31 downto 0);
        
    constant o_RAMAddr_max : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1) := (others=>'1');

	shared variable local_ram : LOCAL_MEMORY_T;
    
    
    constant FIR_ORDER	: integer := 28;
    
    ----------------------------------------------------------------
    -- Memory management
    ----------------------------------------------------------------
    
    signal ptr     : natural range 0 to C_MAX_SAMPLE_COUNT-1 := 0;
    
    ----------------------------------------------------------------
    -- Hardware arguements
    ----------------------------------------------------------------
    signal      hwtio : hwtio_t;

    -- arg[0]   = source address 
    -- arg[1]   = destination address
    -- arg[2]   = 1.  coefficient
    -- arg[3]   = 2.  coefficient
    -- ...
    -- arg[30]    = 29. coefficient

    -- argc     = # 2 + number of coefficients
    
    constant    hwt_argc : integer := 2 + FIR_ORDER + 1;

    ----------------------------------------------------------------
    -- Component dependent signals
    ----------------------------------------------------------------
    signal sample_count : unsigned(15 downto 0) := to_unsigned(C_MAX_SAMPLE_COUNT, 16);
    signal fir_ce       : std_logic;           -- fir clock enable (like a start/stop signal)
    
    signal sourceaddr   : std_logic_vector(31 downto 0);
    signal destaddr     : std_logic_vector(31 downto 0);
    
    signal process_state : integer range 0 to 7;
    
    signal x_i          : signed(23 downto 0);     -- 24 bit internal input  sample
    signal y_i          : signed(23 downto 0);     -- 24 bit internal output sample
    
    signal sample_in    : std_logic_vector(SAMPLE_WIDTH - 1 downto 0) := (others => '0');
    signal sample_out   : std_logic_vector(SAMPLE_WIDTH - 1 downto 0);

    signal coefficients_i16  : mem16(FIR_ORDER downto 0);
    
    signal coefficients_i_0  : std_logic_vector(31 downto 0);
    signal coefficients_i_1  : std_logic_vector(31 downto 0);
    signal coefficients_i_2  : std_logic_vector(31 downto 0);
    signal coefficients_i_3  : std_logic_vector(31 downto 0);
    signal coefficients_i_4  : std_logic_vector(31 downto 0);
    signal coefficients_i_5  : std_logic_vector(31 downto 0);
    signal coefficients_i_6  : std_logic_vector(31 downto 0);
    signal coefficients_i_7  : std_logic_vector(31 downto 0);
    signal coefficients_i_8  : std_logic_vector(31 downto 0);
    signal coefficients_i_9  : std_logic_vector(31 downto 0);
    signal coefficients_i_10 : std_logic_vector(31 downto 0);
    signal coefficients_i_11 : std_logic_vector(31 downto 0);
    signal coefficients_i_12 : std_logic_vector(31 downto 0);
    signal coefficients_i_13 : std_logic_vector(31 downto 0);
    signal coefficients_i_14 : std_logic_vector(31 downto 0);
    signal coefficients_i_15 : std_logic_vector(31 downto 0);
    signal coefficients_i_16 : std_logic_vector(31 downto 0);
    signal coefficients_i_17 : std_logic_vector(31 downto 0);
    signal coefficients_i_18 : std_logic_vector(31 downto 0);
    signal coefficients_i_19 : std_logic_vector(31 downto 0);
    signal coefficients_i_20 : std_logic_vector(31 downto 0);
    signal coefficients_i_21 : std_logic_vector(31 downto 0);
    signal coefficients_i_22 : std_logic_vector(31 downto 0);
    signal coefficients_i_23 : std_logic_vector(31 downto 0);
    signal coefficients_i_24 : std_logic_vector(31 downto 0);
    signal coefficients_i_25 : std_logic_vector(31 downto 0);
    signal coefficients_i_26 : std_logic_vector(31 downto 0);
    signal coefficients_i_27 : std_logic_vector(31 downto 0);
    signal coefficients_i_28 : std_logic_vector(31 downto 0);
    signal coefficients_i_29 : std_logic_vector(31 downto 0);
    
    ----------------------------------------------------------------
    -- OS Communication
    ----------------------------------------------------------------
    
    constant FIR_START      : std_logic_vector(31 downto 0) := x"0000000F";
    constant FIR_EXIT       : std_logic_vector(31 downto 0) := x"000000F0";

begin

    -----------------------------------
    -- Component related wiring
    -----------------------------------
    
    x_i               <= signed(sample_in(31 downto 8));
    sample_out        <= std_logic_vector(y_i) & X"11" when y_i(23) = '1' else
                         std_logic_vector(y_i) & X"00";
    
    sourceaddr        <= hwtio.argv(0);
    destaddr          <= hwtio.argv(1);
    coefficients_i_0  <= hwtio.argv(2);
    coefficients_i_1  <= hwtio.argv(3);
    coefficients_i_2  <= hwtio.argv(4);
    coefficients_i_3  <= hwtio.argv(5);
    coefficients_i_4  <= hwtio.argv(6);
    coefficients_i_5  <= hwtio.argv(7);
    coefficients_i_6  <= hwtio.argv(8);
    coefficients_i_7  <= hwtio.argv(9);
    coefficients_i_8  <= hwtio.argv(10);
    coefficients_i_9  <= hwtio.argv(11);
    coefficients_i_10 <= hwtio.argv(12);    
    coefficients_i_11 <= hwtio.argv(13);
    coefficients_i_12 <= hwtio.argv(14);
    coefficients_i_13 <= hwtio.argv(15);
    coefficients_i_14 <= hwtio.argv(16);
    coefficients_i_15 <= hwtio.argv(17);
    coefficients_i_16 <= hwtio.argv(18);
    coefficients_i_17 <= hwtio.argv(19);
    coefficients_i_18 <= hwtio.argv(20);
    coefficients_i_19 <= hwtio.argv(21);
    coefficients_i_20 <= hwtio.argv(22);    
    coefficients_i_21 <= hwtio.argv(23);
    coefficients_i_22 <= hwtio.argv(24);
    coefficients_i_23 <= hwtio.argv(25);
    coefficients_i_24 <= hwtio.argv(26);
    coefficients_i_25 <= hwtio.argv(27);
    coefficients_i_26 <= hwtio.argv(28);
    coefficients_i_27 <= hwtio.argv(29);
    coefficients_i_28 <= hwtio.argv(30);
        
    coefficients_i16(0)  <= signed(coefficients_i_0(31)  & coefficients_i_0(14 downto 0));
    coefficients_i16(1)  <= signed(coefficients_i_1(31)  & coefficients_i_1(14 downto 0));
    coefficients_i16(2)  <= signed(coefficients_i_2(31)  & coefficients_i_2(14 downto 0));
    coefficients_i16(3)  <= signed(coefficients_i_3(31)  & coefficients_i_3(14 downto 0));
    coefficients_i16(4)  <= signed(coefficients_i_4(31)  & coefficients_i_4(14 downto 0));
    coefficients_i16(5)  <= signed(coefficients_i_5(31)  & coefficients_i_5(14 downto 0));
    coefficients_i16(6)  <= signed(coefficients_i_6(31)  & coefficients_i_6(14 downto 0));
    coefficients_i16(7)  <= signed(coefficients_i_7(31)  & coefficients_i_7(14 downto 0));
    coefficients_i16(8)  <= signed(coefficients_i_8(31)  & coefficients_i_8(14 downto 0));
    coefficients_i16(9)  <= signed(coefficients_i_9(31)  & coefficients_i_9(14 downto 0));
    coefficients_i16(10) <= signed(coefficients_i_10(31) & coefficients_i_10(14 downto 0));
    coefficients_i16(11) <= signed(coefficients_i_11(31) & coefficients_i_11(14 downto 0));
    coefficients_i16(12) <= signed(coefficients_i_12(31) & coefficients_i_12(14 downto 0));
    coefficients_i16(13) <= signed(coefficients_i_13(31) & coefficients_i_13(14 downto 0));
    coefficients_i16(14) <= signed(coefficients_i_14(31) & coefficients_i_14(14 downto 0));
    coefficients_i16(15) <= signed(coefficients_i_15(31) & coefficients_i_15(14 downto 0));
    coefficients_i16(16) <= signed(coefficients_i_16(31) & coefficients_i_16(14 downto 0));
    coefficients_i16(17) <= signed(coefficients_i_17(31) & coefficients_i_17(14 downto 0));
    coefficients_i16(18) <= signed(coefficients_i_18(31) & coefficients_i_18(14 downto 0));
    coefficients_i16(19) <= signed(coefficients_i_19(31) & coefficients_i_19(14 downto 0));
    coefficients_i16(20) <= signed(coefficients_i_20(31) & coefficients_i_20(14 downto 0));
    coefficients_i16(21) <= signed(coefficients_i_21(31) & coefficients_i_21(14 downto 0));
    coefficients_i16(22) <= signed(coefficients_i_22(31) & coefficients_i_22(14 downto 0));
    coefficients_i16(23) <= signed(coefficients_i_23(31) & coefficients_i_23(14 downto 0));
    coefficients_i16(24) <= signed(coefficients_i_24(31) & coefficients_i_24(14 downto 0));
    coefficients_i16(25) <= signed(coefficients_i_25(31) & coefficients_i_25(14 downto 0));
    coefficients_i16(26) <= signed(coefficients_i_26(31) & coefficients_i_26(14 downto 0));
    coefficients_i16(27) <= signed(coefficients_i_27(31) & coefficients_i_27(14 downto 0));
    coefficients_i16(28) <= signed(coefficients_i_28(31) & coefficients_i_28(14 downto 0));
        
    -----------------------------------------------------------------
    -- Memory Management 
    -----------------------------------------------------------------
    o_RAMAddr_fir  <= std_logic_vector(TO_UNSIGNED(ptr, C_LOCAL_RAM_ADDRESS_WIDTH));
    
    o_RAMData_fir  <= sample_out;
        
    FIR_INST : fir
    generic map (
        FIR_ORDER => FIR_ORDER
    )
    PORT MAP(
        clk             => clk,
        rst             => rst,
        ce              => fir_ce,
        coefficients    => coefficients_i16,
        x_in            => x_i,
        y_out           => y_i
    );
    
    -----------------------------------
    -- Hard wirings
    -----------------------------------
    clk <= HWT_Clk;
	rst <= HWT_Rst;

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
			if (o_RAMWE_fir = '1') then
				local_ram(to_integer(unsigned(o_RAMAddr_fir))) := o_RAMData_fir;
            else
				i_RAMData_fir <= local_ram(to_integer(unsigned(o_RAMAddr_fir)));
			end if;
		end if;
	end process;
    
    SAMPLE_IN_REG_PROC : process(clk)
    begin
        if rising_edge(clk) then
            sample_in <= i_RAMData_fir;
        end if;
    end process;
    
    FIR_CTRL_FSM_PROC : process (clk, rst, o_osif, o_memif) is
        variable done : boolean;
    begin
        if rst = '1' then
        
            osif_reset(o_osif);
		    memif_reset(o_memif);
            ram_reset(o_ram);
            
            hwtio_init(hwtio);

            osif_ctrl_signal    <= (others => '0');
            
            state               <= STATE_IDLE;
            done                := false;
            ptr                 <= 0;
            sample_count        <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);  -- number of samples processed
          
        elsif rising_edge(clk) then
            
            fir_ce           <= '0';
            o_RAMWE_fir      <= '0';
                        
            case state is            

            when STATE_IDLE =>

                osif_mbox_get(i_osif, o_osif, MBOX_START, osif_ctrl_signal, done);

                if done then
                    if osif_ctrl_signal = FIR_START then
                        sample_count     <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);
                        state            <= STATE_REFRESH_HWT_ARGS;
                        
                    elsif osif_ctrl_signal = FIR_EXIT then                        
                        state            <= STATE_EXIT;
                    end if;
                    
                    osif_ctrl_signal <= ( others => '0');
                end if;

            when STATE_REFRESH_HWT_ARGS =>               
                get_hwt_args(i_osif, o_osif, i_memif, o_memif, hwtio, hwt_argc, done);

                if done then
                    state <= STATE_READ_MEM;
                end if; 

			when STATE_READ_MEM => 
                -- store input samples in local ram
				memif_read(i_ram, o_ram, i_memif, o_memif, sourceaddr, X"00000000", 
                    std_logic_vector(to_unsigned(C_LOCAL_RAM_SIZE_IN_BYTES,24)), done); -- always in bytes
				if done then
				    state   <= STATE_PROCESS;
                    ptr     <= 0;   -- start with the first sample
			    end if;
			 
            when STATE_PROCESS =>
                if sample_count > 0 then
                    case process_state is                    
                    -- Read one sample from local memory
                    when 0 =>                    
                        fir_ce        <= '1';                        
                        process_state <= 1;
                    when 1 =>
                        o_RAMWE_fir   <= '1';
                        process_state <= 2;
                    when 2 =>                        
                        sample_count  <= sample_count - 1;
                        ptr           <= ptr + 1;
                        process_state <= 3;
                    when others =>
                        process_state <= 0;
                    end case;
                else
                    -- Samples have been generated
                    ptr             <= 0;
                    state           <= STATE_WRITE_MEM;
                end if;

             when STATE_WRITE_MEM =>
        
                memif_write(i_ram, o_ram, i_memif, o_memif, X"00000000", destaddr, 
                            std_logic_vector(to_unsigned(C_LOCAL_RAM_SIZE_IN_BYTES,24)), done);
                if done then
                    state <= STATE_NOTIFY;
				end if;
				
			when STATE_NOTIFY =>

                osif_mbox_put(i_osif, o_osif, MBOX_FINISH, destaddr, ignore, done);
                if done then
                    state <= STATE_IDLE;
				end if;
                        
            when STATE_EXIT =>
                osif_thread_exit(i_osif,o_osif);            
            end case;
        end if;
    end process;
end Behavioral;