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
 
library soundgates;
use soundgates.soundcomponents.all;

ENTITY nco_tb IS
END nco_tb;
 
ARCHITECTURE behavior OF nco_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT nco
    PORT(
         clk          : in  std_logic;
         rst          : in  std_logic;
         ce           : in  std_logic;
         phase_offset : in signed(31 downto 0);
         phase_incr   : in signed(31 downto 0);
         data         : out signed(31 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
   signal ce  : std_logic := '1';
  
   signal phase_offset : signed(31 downto 0) := (others => '0');
   signal phase_incr : signed(31 downto 0) := (others => '0');
   
   
   constant FPGA_FREQUENCY : integer := 100_000_000;
   
 	--Outputs
   signal data : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: nco PORT MAP (
          clk => clk,
          rst => rst,
          ce  => ce,
          phase_offset => phase_offset,
			 phase_incr   => phase_incr,
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
      rst <= '1';
      wait for 100 ns;	
      rst <= '0';
      wait for clk_period*10;

		-- freq <= to_unsigned(8000, 32);
   
		phase_incr <= Get_Cordic_Phase_Increment(FPGA_FREQUENCY, 8000);
      
		wait for clk_period;
		
			
		wait for 125 us;

		-- freq <= to_unsigned(10000, 32);
			
		
		wait for clk_period;
		
		
		wait for 100 us;
		
		-- freq <= to_unsigned(12000, 32);
				
		wait for clk_period;
				
		
		wait for 75 us;
		
		-- freq <= to_unsigned(14000, 32);
			
		
		wait for clk_period;
				
		wait for 50 us;
		
		--freq <= to_unsigned(16000, 32);
			
		wait for clk_period;
				
		wait for 25 us;
      

      wait;
   end process;

END;
