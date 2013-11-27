
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.numeric_std.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

Library unimacro;
use unimacro.vcomponents.all;

ENTITY hwt_nco_tb IS
END hwt_nco_tb;

ARCHITECTURE behavior OF hwt_nco_tb IS 

 COMPONENT hwt_nco
  generic(
		SND_COMP_CLK_FREQ : integer := 100_000_000;
		SND_COMP_NCO_TPYE : integer := 0
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
    END COMPONENT;
    
    
    -- OSIF FIFO ports
    signal OSIF_FIFO_Sw2Hw_Data    : std_logic_vector(31 downto 0);-- := (others => '0');
    signal OSIF_FIFO_Sw2Hw_Fill    : std_logic_vector(8 downto 0);-- := (others => '0');
    signal OSIF_FIFO_Sw2Hw_Empty   : std_logic;-- := '1';
    signal OSIF_FIFO_Sw2Hw_RE      : std_logic;
    
    signal osif_fifo_sw2hw_din : std_logic_vector(31 downto 0) := (others => '0');
    signal osif_fifo_sw2hw_wren : std_logic := '0';
    signal osif_fifo_sw2hw_rdcount : std_logic_vector(8 downto 0);
    
    signal OSIF_FIFO_Hw2Sw_Data    : std_logic_vector(31 downto 0);
    signal OSIF_FIFO_Hw2Sw_Rem     : std_logic_vector(15 downto 0);
    signal OSIF_FIFO_Hw2Sw_Full    : std_logic := '0';
    signal OSIF_FIFO_Hw2Sw_WE      : std_logic;
    -- MEMIF FIFO ports
    signal MEMIF_FIFO_Hwt2Mem_Data : std_logic_vector(31 downto 0);
    signal MEMIF_FIFO_Hwt2Mem_Rem  : std_logic_vector(15 downto 0);
    signal MEMIF_FIFO_Hwt2Mem_Full : std_logic;
    signal MEMIF_FIFO_Hwt2Mem_WE   : std_logic;
    
    signal MEMIF_FIFO_Mem2Hwt_Data : std_logic_vector(31 downto 0);
    signal MEMIF_FIFO_Mem2Hwt_Fill : std_logic_vector(15 downto 0);
    signal MEMIF_FIFO_Mem2Hwt_Empty : std_logic;
    signal MEMIF_FIFO_Mem2Hwt_RE    : std_logic;
    signal HWT_Clk   :  std_logic;
    signal HWT_Rst   :  std_logic;
    
    signal clk : std_logic;
    signal rst : std_logic;
    
    constant clk_period : time := 10 ns;
    
    constant NCO_START : std_logic_vector(31 downto 0) := x"0000000F";
    constant NCO_EXIT  : std_logic_vector(31 downto 0) := x"000000F0";
    
    signal OSIF_FIFO_Sw2Hw_Fill_extend : std_logic_vector(15 downto 0);
    
BEGIN
    
    HWT_Clk <= clk;
    HWT_Rst <= rst;
    OSIF_FIFO_Sw2Hw_Fill_extend <= "0000000" & OSIF_FIFO_Sw2Hw_Fill;
  -- Component Instantiation
    uut: hwt_nco PORT MAP(
        OSIF_FIFO_Sw2Hw_Data    => OSIF_FIFO_Sw2Hw_Data,
		OSIF_FIFO_Sw2Hw_Fill    => OSIF_FIFO_Sw2Hw_Fill_extend,
		OSIF_FIFO_Sw2Hw_Empty   => OSIF_FIFO_Sw2Hw_Empty,
		OSIF_FIFO_Sw2Hw_RE      => OSIF_FIFO_Sw2Hw_RE,
        
		OSIF_FIFO_Hw2Sw_Data    => OSIF_FIFO_Hw2Sw_Data,
		OSIF_FIFO_Hw2Sw_Rem     => OSIF_FIFO_Hw2Sw_Rem,
		OSIF_FIFO_Hw2Sw_Full    => OSIF_FIFO_Hw2Sw_Full,
		OSIF_FIFO_Hw2Sw_WE      => OSIF_FIFO_Hw2Sw_WE,
        
        MEMIF_FIFO_Hwt2Mem_Data  => MEMIF_FIFO_Hwt2Mem_Data,
		MEMIF_FIFO_Hwt2Mem_Rem   => MEMIF_FIFO_Hwt2Mem_Rem,
		MEMIF_FIFO_Hwt2Mem_Full  => MEMIF_FIFO_Hwt2Mem_Full,
		MEMIF_FIFO_Hwt2Mem_WE    =>  MEMIF_FIFO_Hwt2Mem_WE,
        
		MEMIF_FIFO_Mem2Hwt_Data  => MEMIF_FIFO_Mem2Hwt_Data,
		MEMIF_FIFO_Mem2Hwt_Fill  => MEMIF_FIFO_Mem2Hwt_Fill,
		MEMIF_FIFO_Mem2Hwt_Empty => MEMIF_FIFO_Mem2Hwt_Empty,
		MEMIF_FIFO_Mem2Hwt_RE    => MEMIF_FIFO_Mem2Hwt_RE,
		HWT_Clk  => HWT_Clk,
		HWT_Rst  => HWT_Rst
      );
     
OSIF_SW2HW_FIFO_INST : FIFO_SYNC_MACRO
        generic map (
            DEVICE => "7SERIES",  -- Target Device: "VIRTEX5, "VIRTEX6", "7SERIES"
            ALMOST_FULL_OFFSET => X"0080", -- Sets almost full threshold
            ALMOST_EMPTY_OFFSET => X"0080", -- Sets the almost empty threshold
            DATA_WIDTH => 32,-- Valid values are 1-72 (37-72 only valid when FIFO_SIZE="36Kb")
            FIFO_SIZE => "18Kb") -- Target BRAM, "18Kb" or "36Kb"
            port map ( ALMOSTEMPTY => open, -- 1-bit output almost empty
                        ALMOSTFULL => open, -- 1-bit output almost full
                        DO => OSIF_FIFO_Sw2Hw_Data, -- Output data, width defined by DATA_WIDTH parameter
                        EMPTY => OSIF_FIFO_Sw2Hw_Empty, -- 1-bit output empty
                        FULL => open, -- 1-bit output full
                        RDCOUNT => osif_fifo_sw2hw_rdcount,-- Output read count, width determined by FIFO depth
                        RDERR => open, -- 1-bit output read error
                        WRCOUNT => OSIF_FIFO_Sw2Hw_Fill,         -- Output write count, width determined by FIFO depth
                        WRERR => open,              -- 1-bit output write error
                        CLK => clk,                 -- 1-bit input clock
                        DI => osif_fifo_sw2hw_din,  -- Input data, width defined by DATA_WIDTH parameter
                        RDEN => OSIF_FIFO_Sw2Hw_RE, -- 1-bit input read enable
                        RST => rst,                 -- 1-bit input reset
                        WREN => osif_fifo_sw2hw_wren-- 1-bit input write enable
                        );
            -- End of FIFO_SYNC_MACRO_inst instantiation

   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
   end process;
   
   stimulus_process :process
   begin
    rst <= '0';
    wait for clk_period*10;
    --rst <= '0';
    
    osif_fifo_sw2hw_din <= NCO_START;
    wait for clk_period;
    osif_fifo_sw2hw_wren <= '1';
    --OSIF_FIFO_Sw2Hw_Data <= NCO_START;
    --OSIF_FIFO_Sw2Hw_Fill <= std_logic_vector(to_unsigned(1, 16));
    
   end process;

END;
