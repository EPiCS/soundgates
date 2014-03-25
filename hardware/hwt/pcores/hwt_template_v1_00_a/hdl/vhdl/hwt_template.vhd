--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - HW_stub.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  VHDL Stub for Hardware Sound Components
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

entity hwt_template is
    generic(
		SND_COMP_CLK_FREQ : integer := 100_000_000
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
end hwt_template;

architecture Behavioral of hwt_template is

    ----------------------------------------------------------------
    -- Subcomponent declarations
    ----------------------------------------------------------------
    component BASIC_COMPONENT is
    port(                
        clk         : in  std_logic;
        rst         : in  std_logic;
        ce          : in  std_logic;
        param       : in  signed(31 downto 0); 
        sample_out  : out signed(31 downto 0)
    );
   end component BASIC_COMPONENT;
 
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

    type STATE_TYPE is (STATE_IDLE, STATE_REFRESH_HWT_ARGS, STATE_PROCESS, STATE_WRITE_MEM, STATE_NOTIFY, STATE_EXIT);
    signal state    : STATE_TYPE;
    
    ----------------------------------------------------------------
    -- Common sound component signals, constants and types
    ----------------------------------------------------------------
    
    constant C_MAX_SAMPLE_COUNT : integer := 64;
    
   	-- define size of local RAM here
	constant C_LOCAL_RAM_SIZE          : integer := C_MAX_SAMPLE_COUNT;
	constant C_LOCAL_RAM_ADDRESS_WIDTH : integer := clog2(C_LOCAL_RAM_SIZE);    -- 6
	constant C_LOCAL_RAM_SIZE_IN_BYTES : integer := 4*C_LOCAL_RAM_SIZE;

    type LOCAL_MEMORY_T is array (0 to C_LOCAL_RAM_SIZE-1) of std_logic_vector(31 downto 0);
        
    signal o_RAMAddr_hwt : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMData_hwt : std_logic_vector(0 to 31);   -- nco to local ram
	signal i_RAMData_hwt : std_logic_vector(0 to 31);   -- local ram to nco
    signal o_RAMWE_hwt   : std_logic;

  	signal o_RAMAddr_reconos   : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1);
	signal o_RAMAddr_reconos_2 : std_logic_vector(0 to 31);
	signal o_RAMData_reconos   : std_logic_vector(0 to 31);
	signal o_RAMWE_reconos     : std_logic;
	signal i_RAMData_reconos   : std_logic_vector(0 to 31);
    
    signal osif_ctrl_signal : std_logic_vector(31 downto 0);
    signal ignore : std_logic_vector(31 downto 0);
    
    
    constant o_RAMAddr_max : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1) := (others=>'1');

	shared variable local_ram : LOCAL_MEMORY_T;
    
    ----------------------------------------------------------------
    -- Hardware arguements
    ----------------------------------------------------------------
    signal      hwtio : hwtio_t;

    
    constant    hwt_argc : integer := 2; --destination address, param
    ----------------------------------------------------------------
    -- Component dependent signals
    ----------------------------------------------------------------
    signal ce       : std_logic;                                                       -- nco clock enable (like a start/stop signal)
    signal sample_count : unsigned(15 downto 0) := to_unsigned(C_MAX_SAMPLE_COUNT, 16);
    signal data     : signed(31 downto 0);

    signal destaddr     : std_logic_vector(DWORD_WIDTH - 1 downto 0);
    signal param        : std_logic_vector(DWORD_WIDTH - 1 downto 0);

    signal state_inner_process : std_logic;
    signal input_sample : std_logic_vector(DWORD_WIDTH - 1 downto 0) := (others => '0');
    ----------------------------------------------------------------
    -- OS Communication
    ----------------------------------------------------------------
    
    constant HWT_START : std_logic_vector(31 downto 0) := x"0000000F";
    constant HWT_EXIT  : std_logic_vector(31 downto 0) := x"000000F0";

begin

    -----------------------------------
    -- Component related wiring
    -----------------------------------
    destaddr    <= hwtio.argv(0);
    param       <= hwtio.argv(1);

-- for hwts with input samples:
--  sourceaddr  <= hwtio.argv(0);
--  destaddr    <= hwtio.argv(1);
--  param       <= hwtio.argv(2);


    -----------------------------------
    -- Hard wirings
    -----------------------------------
    clk <= HWT_Clk;
	rst <= HWT_Rst;
    
    input_sample <= i_RAMData_hwt; -- store current sample from local RAM in signal    

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
    basic_inst : BASIC_COMPONENT
    port map( 
            clk          => clk,
            rst          => rst,
            ce           => ce,
            input        => signed(input_sample),
            param        => signed(param),
            output       => data
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
			if (o_RAMWE_hwt = '1') then
				local_ram(to_integer(unsigned(o_RAMAddr_hwt))) := o_RAMData_hwt;
            else
				i_RAMData_hwt <= local_ram(to_integer(unsigned(o_RAMAddr_hwt)));
			end if;
		end if;
	end process;    
    
    NCO_CTRL_FSM_PROC : process (clk, rst, o_osif, o_memif) is
        variable done : boolean;            
    begin
        if rst = '1' then
            
            osif_reset(o_osif);
			memif_reset(o_memif);           
            ram_reset(o_ram);
            osif_ctrl_signal    <= (others => '0');
            
            state               <= STATE_IDLE;
            sample_count        <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);
            ce              <= '0';
            o_RAMWE_hwt         <= '0';
            state_inner_process <= '0';
            o_RAMData_hwt <= (others => '0');
            -- Initialize hwt args         
            hwtio_init(hwtio);

            done := False;
              o_RAMAddr_hwt <= (others => '0');
        elsif rising_edge(clk) then
            o_RAMData_hwt <= std_logic_vector(data);
            ce              <= '0';
            o_RAMWE_hwt         <= '0';
            osif_ctrl_signal    <= ( others => '0');
            
            case state is
            when STATE_IDLE =>

                -- Software process "Synthesizer" sends the start signal via mbox_start
                osif_mbox_get(i_osif, o_osif, MBOX_START, osif_ctrl_signal, done);

                if done then
                    if osif_ctrl_signal = HWT_START then
                        
                        sample_count <= to_unsigned(C_MAX_SAMPLE_COUNT, 16);
                        state        <= STATE_REFRESH_HWT_ARGS;
                    elsif osif_ctrl_signal = HWT_EXIT then
                        
                        state   <= STATE_EXIT;

                    end if;    
                end if;
                 
            when STATE_REFRESH_HWT_ARGS =>               
                get_hwt_args(i_osif, o_osif, i_memif, o_memif, hwtio, hwt_argc, done);

                if done then
                   -- state <= STATE_READ; -- for input samples
                    state <= STATE_PROCESS;
                end if;

--            when STATE_READ => 
--                -- store input samples in local ram
--				memif_read(i_ram, o_ram, i_memif, o_memif, sourceaddr, X"00000000", 
--                    std_logic_vector(to_unsigned(C_LOCAL_RAM_SIZE_IN_BYTES,24)), done); -- always in bytes
--				if done then
--				    state   <= STATE_PROCESS;
--                    o_RAMAddr_hwt     <= (others => '0');   -- start with the first sample
--			    end if;

            when STATE_PROCESS =>
                if sample_count > 0 then
                    case state_inner_process is
                        when '0' =>
                            o_RAMWE_hwt         <= '1';
                            ce                  <= '1';
                            state_inner_process <= '1';
                        when '1' =>
                            o_RAMAddr_hwt       <= std_logic_vector(unsigned(o_RAMAddr_hwt) + 1);
                            sample_count        <= sample_count - 1;
                            state_inner_process <= '0';
						when others =>
						    state_inner_process <= '0';
                    end case;
                else
                    -- Samples have been generated
                    o_RAMAddr_hwt <= (others => '0');
                    state <= STATE_WRITE_MEM;
                end if;

             when STATE_WRITE_MEM =>
        
                memif_write(i_ram, o_ram, i_memif, o_memif, X"00000000", destaddr, std_logic_vector(to_unsigned(C_LOCAL_RAM_SIZE_IN_BYTES,24)), done);
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

