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
			lvdsclk_p : in std_logic;
			lvdsclk_n : in std_logic;
					
			-- i2s output
			sd  	: out std_logic;
			ws	 	: out std_logic;
			mclk 	: out std_logic;
			sck 	: out std_logic
			
        );
    END COMPONENT;

   --Inputs
   signal lvdsclk_n : std_logic := '0';
	signal lvdsclk_p : std_logic := '1';
   signal en : std_logic := '1';
	
 	--Outputs
	
	--signal DCM_LOCKED : std_logic;
	signal MCLK : std_logic;
   signal SD 	: std_logic;
   signal WS 	: std_logic;
   signal SCK 	: std_logic;
		
   -- Clock period definitions
   constant clk_period : time := 5 ns;
 
BEGIN
 
	uut : main
			PORT MAP (
         lvdsclk_n => lvdsclk_n,
			lvdsclk_p => lvdsclk_p,
			
			-- i2s output
			sd  	=> SD,
			ws	 	=> WS,
			mclk 	=> MCLK,
			sck 	=> SCK
        );
	
	
	lvdsclk_p <= not lvdsclk_n;
	
   -- Clock process definitions
   clk_process :process
   begin
		lvdsclk_n <= '0';
		wait for clk_period/2;
		lvdsclk_n <= '1';
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