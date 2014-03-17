-- TestBench Template 

  LIBRARY ieee;
  USE ieee.std_logic_1164.ALL;
  USE ieee.numeric_std.ALL;
  
  library reconos_v3_00_c;
  use reconos_v3_00_c.reconos_pkg.all;
  
  library soundgates_v1_00_a;
  use soundgates_v1_00_a.soundgates_common_pkg.all;
  use soundgates_v1_00_a.soundgates_reconos_pkg.all;
  
  ENTITY testbench IS
  END testbench;
  
  
  ARCHITECTURE behavior OF testbench IS 

  -- Component Declaration
    component hwt_control_sub
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
    end component;
    
    ----------------------------------------------------------------
    -- Type declarations
    ----------------------------------------------------------------
    
    ----------------------------------------------------------------
    -- Constants
    ----------------------------------------------------------------
    
    constant control_sub_START      : std_logic_vector(31 downto 0) := x"0000000F";
    constant control_sub_EXIT       : std_logic_vector(31 downto 0) := x"000000F0";
    	 
	constant sample_in : mem32 := (to_signed(1, 32), to_signed(0, 32));

    constant sample_in2 : mem32 := (to_signed(125, 32), to_signed(0, 32));
	 
	 constant argc : integer := 3;
	 
    constant hwt_args : mem32(0 to argc-1) := (
        to_signed(1, 32),         -- source address
        to_signed(2, 32),         -- destination address
        to_signed(3, 32)         -- destination address
        );
		  

    -- arbitrary addresses
    constant sourceaddress  : std_logic_vector(31 downto 0) := X"00001000"; 
    constant sourceaddress2 : std_logic_vector(31 downto 0) := X"00002000";
    constant destadress     : std_logic_vector(31 downto 0) := X"00003000";
    
    ----------------------------------------------------------------
    -- Signals
    ----------------------------------------------------------------
    
    signal clk :  std_logic;
    signal rst :  std_logic := '1';
    
    signal OSIF_FIFO_Sw2Hw_Data     : std_logic_vector(31 downto 0);
    signal OSIF_FIFO_Sw2Hw_Fill     : std_logic_vector(15 downto 0);
    signal OSIF_FIFO_Sw2Hw_Empty    : std_logic;
    signal OSIF_FIFO_Sw2Hw_RE       : std_logic;
    
    signal OSIF_FIFO_Hw2Sw_Data     : std_logic_vector(31 downto 0);
    signal OSIF_FIFO_Hw2Sw_Rem      : std_logic_vector(15 downto 0);
    signal OSIF_FIFO_Hw2Sw_Full     : std_logic;
    signal OSIF_FIFO_Hw2Sw_WE       : std_logic;
    
    signal MEMIF_FIFO_Hwt2Mem_Data  : std_logic_vector(31 downto 0);
    signal MEMIF_FIFO_Hwt2Mem_Rem   : std_logic_vector(15 downto 0);
    signal MEMIF_FIFO_Hwt2Mem_Full  : std_logic;
    signal MEMIF_FIFO_Hwt2Mem_WE    : std_logic;

    signal MEMIF_FIFO_Mem2Hwt_Data  : std_logic_vector(31 downto 0);
    signal MEMIF_FIFO_Mem2Hwt_Fill  : std_logic_vector(15 downto 0);
    signal MEMIF_FIFO_Mem2Hwt_Empty : std_logic;
    signal MEMIF_FIFO_Mem2Hwt_RE    : std_logic;

    -- Clock period definitions
    constant clk_period : time := 10 ns;
    
    signal hw2sw_osifdata   : std_logic_vector(31 downto 0) := X"00000000";
    signal hw2sw_osifhandle : std_logic_vector(31 downto 0) := X"00000000";
    
    signal ps_slave_from_Hw_Data  : std_logic_vector(31 downto 0);
    signal ps_slave_from_Hw_Fill  : std_logic_vector(15 downto 0);
    signal ps_slave_from_Hw_Empty : std_logic;
    signal ps_slave_from_Hw_RE    : std_logic := '0';

    signal ps_master_to_Hw_Data : std_logic_vector(31 downto 0) := (others => '0');
    signal ps_master_to_Hw_Rem  : std_logic_vector(15 downto 0);
    signal ps_master_to_Hw_Full : std_logic;
    signal ps_master_to_Hw_WE   : std_logic := '0';
    
    signal mem_slave_from_Hw_Data  : std_logic_vector(31 downto 0);
    signal mem_slave_from_Hw_Fill  : std_logic_vector(15 downto 0);
    signal mem_slave_from_Hw_Empty : std_logic;
    signal mem_slave_from_Hw_RE    : std_logic := '0';
    
    signal mem_master_to_Hw_Data : std_logic_vector(31 downto 0) := (others => '0');
    signal mem_master_to_Hw_Rem  : std_logic_vector(15 downto 0);
    signal mem_master_to_Hw_Full : std_logic;
    signal mem_master_to_Hw_WE   : std_logic := '0';
    
    signal memif_hw2sw_data : std_logic_vector(31 downto 0) := (others => '0');
    signal memif_sw2hw_data : std_logic_vector(31 downto 0) := (others => '1');
        
	 signal count : integer := 0;
	 
	 signal inner : std_logic := '0';
    
  BEGIN
  
    -- Component Instantiation
    uut: hwt_control_sub PORT MAP(
        -- OSIF FIFO ports
        OSIF_FIFO_Sw2Hw_Data        => OSIF_FIFO_Sw2Hw_Data,
        OSIF_FIFO_Sw2Hw_Fill        => OSIF_FIFO_Sw2Hw_Fill,
        OSIF_FIFO_Sw2Hw_Empty       => OSIF_FIFO_Sw2Hw_Empty,
        OSIF_FIFO_Sw2Hw_RE          => OSIF_FIFO_Sw2Hw_RE,

        OSIF_FIFO_Hw2Sw_Data        => OSIF_FIFO_Hw2Sw_Data,
        OSIF_FIFO_Hw2Sw_Rem         => OSIF_FIFO_Hw2Sw_Rem,
        OSIF_FIFO_Hw2Sw_Full        => OSIF_FIFO_Hw2Sw_Full,
        OSIF_FIFO_Hw2Sw_WE          => OSIF_FIFO_Hw2Sw_WE,

        -- MEMIF FIFO ports
        MEMIF_FIFO_Hwt2Mem_Data     => MEMIF_FIFO_Hwt2Mem_Data,
        MEMIF_FIFO_Hwt2Mem_Rem      => MEMIF_FIFO_Hwt2Mem_Rem,
        MEMIF_FIFO_Hwt2Mem_Full     => MEMIF_FIFO_Hwt2Mem_Full,
        MEMIF_FIFO_Hwt2Mem_WE       => MEMIF_FIFO_Hwt2Mem_WE,

        MEMIF_FIFO_Mem2Hwt_Data     => MEMIF_FIFO_Mem2Hwt_Data,
        MEMIF_FIFO_Mem2Hwt_Fill     => MEMIF_FIFO_Mem2Hwt_Fill,
        MEMIF_FIFO_Mem2Hwt_Empty    => MEMIF_FIFO_Mem2Hwt_Empty,
        MEMIF_FIFO_Mem2Hwt_RE       => MEMIF_FIFO_Mem2Hwt_RE,
        
        HWT_Clk                     => clk,
        HWT_Rst                     => rst
    );
    
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
    -- hw2sw
    fifo32_osif_hw2sw : entity work.reconos_fifo
    generic map (
        C_FIFO_DEPTH => 32,
		C_FIFO_WIDTH => 32
    )
    port map (
        -- FIFO ports   
		FIFO_S_Clk    => clk,
		FIFO_S_Data   => ps_slave_from_Hw_Data,
		FIFO_S_Fill   => ps_slave_from_Hw_Fill,
		FIFO_S_Empty  => ps_slave_from_Hw_Empty,
		FIFO_S_RE     => ps_slave_from_Hw_RE,
		FIFO_M_Clk    => clk,
		FIFO_M_Data   => OSIF_FIFO_Hw2Sw_Data,
		FIFO_M_Rem    => OSIF_FIFO_Hw2Sw_Rem,
		FIFO_M_Full   => OSIF_FIFO_Hw2Sw_Full,
		FIFO_M_WE     => OSIF_FIFO_Hw2Sw_WE,

		FIFO_Rst      => rst,
		FIFO_Has_Data => open
    );
    
    fifo32_osif_sw2hw : entity work.reconos_fifo
    generic map (
        C_FIFO_DEPTH => 32,
		C_FIFO_WIDTH => 32
    )
    port map (
        -- FIFO ports
		FIFO_S_Clk    => clk,
		FIFO_S_Data   => OSIF_FIFO_Sw2Hw_Data,
		FIFO_S_Fill   => OSIF_FIFO_Sw2Hw_Fill,
		FIFO_S_Empty  => OSIF_FIFO_Sw2Hw_Empty,
		FIFO_S_RE     => OSIF_FIFO_Sw2Hw_RE,
		FIFO_M_Clk    => clk,
		FIFO_M_Data   => ps_master_to_Hw_Data,
		FIFO_M_Rem    => ps_master_to_Hw_Rem,
		FIFO_M_Full   => ps_master_to_Hw_Full,
		FIFO_M_WE     => ps_master_to_Hw_WE,

		FIFO_Rst      => rst,
		FIFO_Has_Data => open
    );
    
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
    fifo32_memif_hw2sw : entity work.reconos_fifo
    generic map (
        C_FIFO_DEPTH => 32,
		C_FIFO_WIDTH => 32
    )
    port map (
        -- FIFO ports
		FIFO_S_Clk    => clk,
		FIFO_S_Data   => mem_slave_from_Hw_Data,
		FIFO_S_Fill   => mem_slave_from_Hw_Fill,
		FIFO_S_Empty  => mem_slave_from_Hw_Empty,
		FIFO_S_RE     => mem_slave_from_Hw_RE,

		FIFO_M_Clk    => clk,
		FIFO_M_Data   => MEMIF_FIFO_Hwt2Mem_Data,
		FIFO_M_Rem    => MEMIF_FIFO_Hwt2Mem_Rem,
		FIFO_M_Full   => MEMIF_FIFO_Hwt2Mem_Full,
		FIFO_M_WE     => MEMIF_FIFO_Hwt2Mem_WE,

		FIFO_Rst      => rst,
		FIFO_Has_Data => open
    );
    
    fifo32_memif_sw2hw : entity work.reconos_fifo
    generic map (
        C_FIFO_DEPTH => 32,
		C_FIFO_WIDTH => 32
    )
    port map (
        -- FIFO ports
		FIFO_S_Clk    => clk,
		FIFO_S_Data   => MEMIF_FIFO_Mem2Hwt_Data,
		FIFO_S_Fill   => MEMIF_FIFO_Mem2Hwt_Fill,
		FIFO_S_Empty  => MEMIF_FIFO_Mem2Hwt_Empty,
		FIFO_S_RE     => MEMIF_FIFO_Mem2Hwt_RE,

		FIFO_M_Clk    => clk,
		FIFO_M_Data   => mem_master_to_Hw_Data,
		FIFO_M_Rem    => mem_master_to_Hw_Rem,
		FIFO_M_Full   => mem_master_to_Hw_Full,
		FIFO_M_WE     => mem_master_to_Hw_WE,

		FIFO_Rst      => rst,
		FIFO_Has_Data => open
    );
    
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

    clk_process : process
    begin
        clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
    end process;

  --  Test Bench Statements
     tb : PROCESS
        
                
        procedure PS_FIFO_READ(signal data : out std_logic_vector(31 downto 0) )
        is begin
            
            wait until clk'event and clk ='1';
            ps_slave_from_Hw_RE   <= '1';            
            data <= ps_slave_from_Hw_Data;            
            wait until clk'event and clk ='1';           
            ps_slave_from_Hw_RE   <= '0';            
            wait until clk'event and clk ='1';
            
        end procedure PS_FIFO_READ;
     
        procedure PS_FIFO_WRITE(constant data : in std_logic_vector(31 downto 0) )
        is begin
            
            wait until clk'event and clk ='1';            
            ps_master_to_HW_Data <= data;            
            wait until clk'event and clk ='1';            
            if ps_master_to_Hw_Full = '0' then
                ps_master_to_HW_WE   <= '1';
            end if;            
            wait until clk'event and clk ='1';                 
            ps_master_to_HW_WE   <= '0';            
            wait until clk'event and clk ='1';
            
        end procedure PS_FIFO_WRITE;
        
        procedure MEMIF_FIFO_READ(signal data : out std_logic_vector(31 downto 0) )
        is begin
            
            wait until clk'event and clk ='1';
            mem_slave_from_Hw_RE   <= '1';            
            data <= mem_slave_from_Hw_Data;            
            wait until clk'event and clk ='1';           
            mem_slave_from_Hw_RE   <= '0';            
            wait until clk'event and clk ='1';
            
        end procedure MEMIF_FIFO_READ;
     
        procedure MEMIF_FIFO_WRITE(constant data : in std_logic_vector(31 downto 0) )
        is begin
            
            wait until clk'event and clk ='1';            
            mem_master_to_HW_Data <= data;            
            wait until clk'event and clk ='1';            
            if mem_master_to_Hw_Full = '0' then
                mem_master_to_HW_WE   <= '1';
            end if;            
            wait until clk'event and clk ='1';                 
            mem_master_to_HW_WE   <= '0';            
            wait until clk'event and clk ='1';
            
        end procedure MEMIF_FIFO_WRITE;
        
        
     BEGIN
        rst <= '1';

        -- init
        ps_master_to_HW_WE   <= '0';
        ps_master_to_HW_Data <= (others => '0');
        ps_slave_from_Hw_RE  <= '0';

        wait for 100 ns; -- wait until global set/reset completes
        rst <= '0';
        
        wait on ps_slave_from_Hw_Fill until ps_slave_from_Hw_Fill = X"0000";
        
        PS_FIFO_READ(hw2sw_osifdata);    -- read handle
        PS_FIFO_READ(hw2sw_osifhandle);  -- read call id
        
        -- wait for data from hw
        if hw2sw_osifdata = OSIF_CMD_MBOX_GET then
            PS_FIFO_WRITE(control_sub_START);
        end if;
       
        wait on ps_slave_from_Hw_Fill until ps_slave_from_Hw_Fill = X"0000";
       
        PS_FIFO_READ(hw2sw_osifdata);   -- read call id
         
        if hw2sw_osifdata = OSIF_CMD_THREAD_GET_INIT_DATA then
            PS_FIFO_WRITE(X"00000000");
				
        end if;
        
        
        for i in 0 to argc -1  loop   -- write hardware arguments
            wait on mem_slave_from_Hw_Fill until mem_slave_from_Hw_Fill = X"0001";   -- wait until cmd and addr written
            
            MEMIF_FIFO_READ(memif_hw2sw_data);
            
            if memif_hw2sw_data = X"00000004" then   -- check if next value is address
                
                MEMIF_FIFO_READ(memif_hw2sw_data);  -- read address
                
                MEMIF_FIFO_WRITE(std_logic_vector(hwt_args(i))); -- write data to memory               
            end if;            
        end loop;
        
        wait on mem_slave_from_Hw_Fill until mem_slave_from_Hw_Fill = X"0001";   -- wait until cmd and addr written

        MEMIF_FIFO_READ(memif_hw2sw_data);

        if memif_hw2sw_data = X"00000004" then   -- check if next value is address
            MEMIF_FIFO_READ(memif_hw2sw_data);  -- read source address
        end if;                   
        
        for i in 0 to 0 loop   -- write samples
            MEMIF_FIFO_WRITE(std_logic_vector(sample_in(i))); -- write data to memory
        end loop;

        for i in 0 to 0 loop   -- write samples
            MEMIF_FIFO_WRITE(std_logic_vector(sample_in2(i))); -- write data to memory
        end loop;
        
        wait; -- will wait forever
     END PROCESS tb;
  --  End Test Bench 



  END;
