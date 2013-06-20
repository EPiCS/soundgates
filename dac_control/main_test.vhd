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
use IEEE.NUMERIC_STD.ALL;
 
ENTITY main_test IS
END main_test;
 
ARCHITECTURE behavior OF main_test IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
	 
	 COMPONENT main
    PORT(
			lvdsclk_p : in std_logic;
			lvdsclk_n : in std_logic;
			
			rst 	: in std_logic;
				
			-- dac out left
			AUDIO_DAC_left_MOSI_pin : out std_logic;
			AUDIO_DAC_left_SCK_pin	: out std_logic;
			AUDIO_DAC_left_SS_pin	: out std_logic;
			-- dac out right
			AUDIO_DAC_right_MOSI_pin : out std_logic;
			AUDIO_DAC_right_SCK_pin	 : out std_logic;
			AUDIO_DAC_right_SS_pin	 : out std_logic
			
        );
    END COMPONENT;

   --Inputs
   signal lvdsclk_n : std_logic := '0';
	signal lvdsclk_p : std_logic := '1';
   signal rst : std_logic := '0';
	
 	--Outputs
	
	-- dac input
	
	signal dac_l_mosi : std_logic;
	signal dac_l_sck  : std_logic;
	signal dac_l_sync : std_logic;
	
	signal dac_r_mosi : std_logic;
	signal dac_r_sck  : std_logic;
	signal dac_r_sync : std_logic;
	
   -- Clock period definitions
   constant clk_period : time := 5 ns;
 
BEGIN

	uut : main
			PORT MAP (
         lvdsclk_n => lvdsclk_n,
			lvdsclk_p => lvdsclk_p,
			rst 	=>  rst,
		
			-- dac out left
			AUDIO_DAC_left_MOSI_pin => dac_l_mosi,
			AUDIO_DAC_left_SCK_pin	=> dac_l_sck,
			AUDIO_DAC_left_SS_pin	=> dac_l_sync,
			-- dac out right
			AUDIO_DAC_right_MOSI_pin => dac_r_mosi,
			AUDIO_DAC_right_SCK_pin	 => dac_r_sck,
			AUDIO_DAC_right_SS_pin	 => dac_r_sync
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