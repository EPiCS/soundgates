library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.MATH_REAL.ALL;
use IEEE.NUMERIC_STD.ALL;
 
library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;
 
ENTITY amplifier_tb IS
END amplifier_tb;
 
ARCHITECTURE behavior OF amplifier_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT amplifier
    PORT(
         clk       : in  std_logic;
        rst       : in  std_logic;
        ce        : in  std_logic;
        wave      : in  signed(31 downto 0);
	    percentage: in  signed(31 downto 0);       
	    amp       : out signed(31 downto 0)
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
   signal ce : std_logic := '1';
   signal incr : signed(31 downto 0) := to_signed(integer(real( 0.1 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
   signal incr2 : signed(31 downto 0) := to_signed(integer(real( 0.2 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);

 	--Outputs
   signal rmp : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: amplifier PORT MAP (
          clk => clk,
          rst => rst,
          ce => ce,
          incr => incr,
          incr2 => incr2,
          rmp => rmp
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
