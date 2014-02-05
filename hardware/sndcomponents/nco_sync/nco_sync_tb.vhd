library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.MATH_REAL.ALL;
use IEEE.NUMERIC_STD.ALL;


library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

ENTITY nco_sync_tb IS
END nco_sync_tb;
 
ARCHITECTURE behavior OF nco_sync_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
component nco_sync
	 generic(
		FPGA_FREQUENCY : integer := 100_000_000;
		WAVEFORM       : WAVEFORM_TYPE := SAW
	 );
    Port ( 
            clk    : in  std_logic;           
            rst    : in  std_logic;
            ce     : in  std_logic;
            master_phase_offset : in  signed(31 downto 0);
            master_phase_incr   : in  signed(31 downto 0);
            slave_phase_offset  : in  signed(31 downto 0);
            slave_phase_incr    : in  signed(31 downto 0);
            data_master         : out signed(31 downto 0);
			data_slave          : out signed(31 downto 0)
           );
end component;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
   signal ce  : std_logic := '0';
  
   signal phase_offset  : signed(31 downto 0) := (others => '0');
   signal master_phase_incr    : signed(31 downto 0) := to_signed(integer(real(0.07 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
	signal slave_phase_incr    : signed(31 downto 0) := to_signed(integer(real(0.05 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    
   constant C_MAX_SAMPLE_COUNT : integer := 1024;
   constant FPGA_FREQUENCY : integer := 100000000;
 	--Outputs
   signal m_data : signed(31 downto 0);
   signal s_data : signed(31 downto 0);
	
   -- Clock period definitions
   constant clk_period : time := 10 ns;
      
BEGIN
  
    -- Instantiate the Unit Under Test (UUT)
   uut: nco_sync PORT MAP (
          clk => clk,
          rst => rst,
          ce  => ce,
          master_phase_offset => phase_offset,
	      master_phase_incr   => master_phase_incr,
          slave_phase_offset => phase_offset,
	      slave_phase_incr   => slave_phase_incr,
          data_master => m_data,
			 data_slave=> s_data
        );
   
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
   
END;
