--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   15:12:49 09/10/2013
-- Design Name:   
-- Module Name:   C:/Users/soundgates/Desktop/pg-soundgates/hardware/components/nco/nco_tb.vhd
-- Project Name:  NumericControlledOscillator
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: nco
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
USE ieee.numeric_std.ALL;
 
ENTITY nco_tb IS
END nco_tb;
 
ARCHITECTURE behavior OF nco_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT nco
    PORT(
         clk : IN  std_logic;
			freq_wren : in 	std_logic;
         freq_in : IN  unsigned(31 downto 0);
         data : OUT  signed(31 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
	signal freq_wren : std_logic := '0';
   signal freq : unsigned(31 downto 0) := (others => '0');

 	--Outputs
   signal data : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: nco PORT MAP (
          clk => clk,
          freq_in => freq,
			 freq_wren => freq_wren,
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

		freq <= to_unsigned(8000, 32);
			
		freq_wren <= '1';
		wait for clk_period;
		freq_wren <= '0';
			
		wait for 125 us;

		freq <= to_unsigned(10000, 32);
			
		freq_wren <= '1';
		wait for clk_period;
		freq_wren <= '0';
		
		wait for 100 us;
		
		freq <= to_unsigned(12000, 32);
			
		freq_wren <= '1';
		wait for clk_period;
		freq_wren <= '0';
		
		
		wait for 75 us;
		
		freq <= to_unsigned(14000, 32);
			
		freq_wren <= '1';
		wait for clk_period;
		freq_wren <= '0';
		
		wait for 50 us;
		
		freq <= to_unsigned(16000, 32);
			
		freq_wren <= '1';
		wait for clk_period;
		freq_wren <= '0';
		
		wait for 25 us;

      -- insert stimulus here 

      wait;
   end process;

END;
