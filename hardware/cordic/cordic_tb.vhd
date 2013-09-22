--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   12:23:57 09/05/2013
-- Design Name:   
-- Module Name:   /home/soundgates/wave_generators/cordic/cordic_tb.vhd
-- Project Name:  cordic
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: cordic
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
use ieee.math_real.all;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
USE ieee.numeric_std.ALL;
 
 
library soundgates;
use soundgates.soundcomponents.all;

ENTITY cordic_tb IS
END cordic_tb;
 
ARCHITECTURE behavior OF cordic_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT cordic
    PORT(
         clk : in std_logic;
			x   : in signed(31 downto 0);
			y   : in signed(31 downto 0);
			phi : in  signed(31 downto 0);  -- 0 <= phi <= pi/2
			sin : out signed(31 downto 0);
			cos : out signed(31 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
	
	constant scaling : real := 27.0;
	
	constant x_init : integer := integer(real(1.0  * 2**SOUNDGATE_FIX_PT_SCALING));
	constant y_init : integer := integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING));
	
	constant phi_init : signed(31 downto 0) := to_signed(0, 32);
	
   signal x 	: signed(31 downto 0) := to_signed(x_init, 32);
   signal y 	: signed(31 downto 0) := to_signed(y_init, 32);
   signal phi 	: signed(31 downto 0) := to_signed(0, 32);--phi_init;
	

 	--Outputs
   signal sin : signed(31 downto 0);
   signal cos : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
	signal count : integer := 0;
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: cordic PORT MAP (
          clk => clk,
          x => x,
          y => y,
          phi => phi,
          sin => sin,
          cos => cos
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
	
		constant f_sin  : real := 440.0; 	   -- in Hz
		constant f_fpga : real := 100000000.0; -- in Hz;
		
		constant stepsize : integer := integer(f_fpga / f_sin);
		
		constant phi_offset : real := real(MATH_PI * 2.0 / real(stepsize) * 2 ** SOUNDGATE_FIX_PT_SCALING);
		constant phi_offset_scaled : signed(31 downto 0) := to_signed(integer(phi_offset), 32);
		
   begin		

			count <= count + 1;
			
			if count >= 0 and count < stepsize  then -- 0 to 2*pi
			
				phi <= phi + phi_offset_scaled;
	
			else
				phi 	<= to_signed(0, 32);
				count <= 0;
			end if;
			
			wait for clk_period;
			
   end process;

END;
