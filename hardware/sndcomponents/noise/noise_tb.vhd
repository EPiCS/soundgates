
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all; 
 
ENTITY noise_tb IS
END noise_tb;
 
ARCHITECTURE behavior OF noise_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT noise
	 GENERIC(
		NOISE            : NOISE_TYPE := WHITE
	 );
    PORT(
         clk : IN  std_logic;
         rst : IN  std_logic;
         ce : IN  std_logic;
         data : OUT  std_logic_vector(31 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
   signal ce : std_logic := '1';

 	--Outputs
   signal data : std_logic_vector(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: noise PORT MAP (
          clk => clk,
          rst => rst,
          ce => ce,
          data => data
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
      wait for 100 ns;	

      wait for clk_period*10;

      -- insert stimulus here 

      wait;
   end process;

END;
