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
use STD.textio.all;
 
library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

ENTITY cordic_tb IS
END cordic_tb;
 
ARCHITECTURE behavior OF cordic_tb IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT cordic
    generic (
         pipeline_stages : integer
    );
    PORT(
    phi : in  signed(31 downto 0);  -- 0 <= phi <= pi/2
    sin : out signed(31 downto 0);
    cos : out signed(31 downto 0);
     clk : in std_logic;
     rst : in std_logic;
     ce  : in std_logic
     );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic;
   signal ce  : std_logic;
   constant phi_init : signed(31 downto 0) := to_signed(0, 32);
   signal phi 	: signed(31 downto 0) := to_signed(0, 32);--phi_init;
	

 	--Outputs
   signal sin : signed(31 downto 0);
   signal cos : signed(31 downto 0);
  
   -- Clock period definitions
   constant clk_period : time := 10 ns;
   
   constant cordic_p_stages : integer := 16;
   constant standard_cordic_offset : integer := integer(real(MATH_PI * 2.0 * 2 ** SOUNDGATE_FIX_PT_SCALING));

    constant f_sin  : integer := 2000; 	   -- in Hz
    constant f_fpga : integer := 100000000; -- in Hz;
    
    constant phase_incr : signed(31 downto 0) := Get_Cordic_Phase_Increment(f_fpga, f_sin);
   -- simulation related signals
   
   signal init_done : std_logic := '0';
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: cordic
   generic map (
      pipeline_stages => cordic_p_stages
	)
   PORT MAP (
          clk => clk,
          rst => rst,
          ce => ce,
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

   begin 
   
         wait for clk_period;
         
         rst <= '1';      
         
         wait for cordic_p_stages * clk_period;
         
         rst <= '0';
         ce  <= '1';
         
         init_done <= '1';
         loop
             if phi >= standard_cordic_offset then
                phi <= phase_incr;
             else
                phi <= phi + phase_incr;
             end if;
             
             wait for clk_period;
         end loop;
   end process;

END;
