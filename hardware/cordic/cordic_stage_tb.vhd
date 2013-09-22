--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   15:32:45 09/05/2013
-- Design Name:   
-- Module Name:   /home/soundgates/wave_generators/cordic/cordic_stage_tb.vhd
-- Project Name:  cordic
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: cordic_stage
-- 
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends
-- that these types always be used for the top-level I/O of a design in order
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
use ieee.math_real.ALL;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
USE ieee.numeric_std.ALL;
 
library soundgates;
use soundgates.soundcomponents.all;

ENTITY cordic_stage_tb IS
END cordic_stage_tb;
 
ARCHITECTURE behavior OF cordic_stage_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT cordic_stage
	 Generic(   stage : integer := 1;
					alpha : real 	 := 0.5
					);
    PORT(
         clk 	: IN  std_logic;
         x 		: IN   signed(31 downto 0);
         y 		: IN   signed(31 downto 0);
         z 		: IN   signed(31 downto 0);
         x_n 	: OUT  signed(31 downto 0);
         y_n 	: OUT  signed(31 downto 0);
         z_n 	: OUT  signed(31 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal x : signed(31 downto 0) := to_signed(integer(real(1.0 * 2**16.0)), 32);
   signal y : signed(31 downto 0) := to_signed(integer(real(0.0 * 2**16.0)), 32);
   signal z : signed(31 downto 0) := to_signed(integer(real(15.0 * 2**16.0)), 32);

 	--Outputs
   signal x_n : signed(31 downto 0);
   signal y_n : signed(31 downto 0);
   signal z_n : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: cordic_stage	
		PORT MAP (
          clk => clk,
          x => x,
          y => y,
          z => z,
          x_n => x_n,
          y_n => y_n,
          z_n => z_n
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
