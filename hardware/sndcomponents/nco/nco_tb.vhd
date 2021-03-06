LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
USE ieee.numeric_std.ALL;
use STD.textio.all;


library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

ENTITY nco_tb IS
END nco_tb;
 
ARCHITECTURE behavior OF nco_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT nco
    PORT(
         clk          : in  std_logic;
         rst          : in  std_logic;
         ce           : in  std_logic;
         phase_offset : in signed(31 downto 0);
         phase_incr   : in signed(31 downto 0);
         data         : out signed(31 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
   signal ce  : std_logic := '0';
  
   signal phase_offset  : signed(31 downto 0) := (others => '0');
   signal phase_incr    : signed(31 downto 0) := (others => '0');
    
   constant C_MAX_SAMPLE_COUNT : integer := 1024;
   constant FPGA_FREQUENCY : integer := 100000000;
 	--Outputs
   signal data : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
   
   constant C_LOCAL_RAM_SIZE          : integer := C_MAX_SAMPLE_COUNT;
   constant C_LOCAL_RAM_ADDRESS_WIDTH : integer := 10;
   constant C_LOCAL_RAM_SIZE_IN_BYTES : integer := 4*C_LOCAL_RAM_SIZE;

   
   type LOCAL_MEMORY_T is array (0 to C_LOCAL_RAM_SIZE-1) of std_logic_vector(31 downto 0);
   shared variable local_ram : LOCAL_MEMORY_T;-- := ( others => (others => '0'));

   signal o_RAMAddr_nco : std_logic_vector(0 to C_LOCAL_RAM_ADDRESS_WIDTH-1) := (others => '0');
   signal o_RAMData_nco : std_logic_vector(0 to 31);   -- nco to local ram
   signal o_RAMWE_nco   : std_logic := '0';   
      
BEGIN
   phase_incr <= Get_Cordic_Phase_Increment(FPGA_FREQUENCY, 1999);
   
    -- Instantiate the Unit Under Test (UUT)
   uut: nco PORT MAP (
          clk => clk,
          rst => rst,
          ce  => ce,
          phase_offset => phase_offset,
	      phase_incr   => phase_incr,
          data => data
        );
   
   o_RAMData_nco <= std_logic_vector(data);
   
   local_ram_ctrl_2 : process (clk) is
	begin
		if rising_edge(clk) then		
			if (o_RAMWE_nco = '1') then
				local_ram(to_integer(unsigned(o_RAMAddr_nco))) := o_RAMData_nco;
			end if;
		end if;
   end process;

   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
   end process;
 
   -- Stimulus process
   stim_proc: process
        
   begin		
      -- hold reset state for 100 ns.
      rst <= '1';
      wait for 100 ns;	
      rst <= '0';
      wait for clk_period*10;
      
      wait;
   end process;
   
   process(clk)
   begin
   
    if(rising_edge(clk) )then
        
            ce            <= '1';
            o_RAMWE_nco   <= '1';
            o_RAMAddr_nco <= std_logic_vector(unsigned(o_RAMAddr_nco) + 1);
    end if;
   
   end process;
   
   write_data_proc : process
    file sine_file : TEXT open WRITE_MODE is "sine.out";
    variable wline : line;
   begin
    write(wline, to_integer(data));
	writeline(sine_file, wline);			
    wait for clk_period; 
   end process;
   

END;
