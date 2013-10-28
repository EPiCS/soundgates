--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   15:27:07 05/23/2013
-- Design Name:   
-- Module Name:   /home/hendrik/Soundgates/Cordic/tb_sin_gen.vhd
-- Project Name:  Cordic
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: sin_generator
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
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY tb_sin_gen IS
END tb_sin_gen;
 
ARCHITECTURE behavior OF tb_sin_gen IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT sin_generator
    PORT(
         sin : IN  std_logic;
         wave : OUT  std_logic_vector(15 downto 0);
         phase_inc : IN  std_logic_vector(15 downto 0);
         clk : IN  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal sin : std_logic := '1';
   signal phase_inc : std_logic_vector(15 downto 0) := "0000000001000000";
   signal clk : std_logic := '0';

 	--Outputs
   signal wave : std_logic_vector(15 downto 0);

   -- Clock period definitions
   --constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: sin_generator PORT MAP (
          sin => sin,
          wave => wave,
          phase_inc => phase_inc,
          clk => clk
        );

   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for 10 ns;
		clk <= '1';
		wait for 10 ns;
   end process;
	
END;
