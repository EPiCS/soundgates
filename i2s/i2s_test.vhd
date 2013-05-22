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
 
    COMPONENT i2s
	 generic(
		
		RefClkFrequency : integer;		-- clock frequency of the reference clock in hz
		BitsPerSample : integer;		-- 8/16/32
		SampleRate    : integer;		-- in Hz
		NumChannels   : integer			-- 1 = mono, 2 = stereo
	);
    PORT(
         clk : IN  std_logic;
         rst : IN  std_logic;
         SD : OUT  std_logic;
         WS : OUT  std_logic;
         SCK : OUT  std_logic;
         BUFFER_FULL : OUT  std_logic;
         sample_in : IN  std_logic_vector((BitsPerSample * 2) -1 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
	signal sample_in : std_logic_vector(31 downto 0) := X"FFFFFFFF";

 	--Outputs
   signal SD : std_logic;
   signal WS : std_logic;
   signal SCK : std_logic;
   signal BUFFER_FULL : std_logic;

   -- Clock period definitions
   constant clk_period : time := 20 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: i2s
	generic map(
		RefClkFrequency => 50000000,
		BitsPerSample  => 16,
		SampleRate     => 44100,
		NumChannels    => 2
	)

	PORT MAP (
          clk => clk,
          rst => rst,
          SD => SD,
          WS => WS,
          SCK => SCK,
          BUFFER_FULL => BUFFER_FULL,
          sample_in => sample_in
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