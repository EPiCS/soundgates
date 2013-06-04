--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   17:48:29 05/17/2013
-- Design Name:   
-- Module Name:   /home-e/lfunke/i2s_2/i2s_test.vhd
-- Project Name:  i2s_2
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: i2s
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
 
ENTITY i2s_test IS
END i2s_test;
 
ARCHITECTURE behavior OF i2s_test IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
	 
	 COMPONENT main
    PORT(
         uclk 	: in  std_logic;
         rst 	: in std_logic;
        
			btn 	: in  std_logic_vector(3 downto 0);
         
			SD 	: out  std_logic;
         WS 	: out  std_logic;
			Mclk 	: out std_logic;
			Led 	: out std_logic_vector(3 downto 0);
			SCK 	: out  std_logic
			
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
	signal btn :  std_logic_vector(3 downto 0) := "0000";
 	--Outputs
	signal MCLK : std_logic;
   signal SD 	: std_logic;
   signal WS 	: std_logic;
   signal SCK 	: std_logic;
	signal Led 	: std_logic_vector(3 downto 0);
   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	uut : main
			PORT MAP (
          uclk => clk,
          rst => rst,
          SD => SD,
          WS => WS,
			 mclk => MCLK,
          SCK => SCK,
			 Led => Led,
			 btn => btn
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