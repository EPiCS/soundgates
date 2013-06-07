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
use ieee.std_logic_unsigned.all;
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
         sfp_wave : OUT  std_logic_vector(15 downto 0);
			uint_wave : OUT std_logic_vector(15 downto 0);
         hertz : IN  std_logic_vector(15 downto 0);
         clk : IN  std_logic
        );
    END COMPONENT;

   --Inputs
   signal sin : std_logic := '1';
   signal hertz : std_logic_vector(15 downto 0) := "0000000110111000"; -- 440 Hz
   signal clk : std_logic := '0';
	signal passes : std_logic_vector(1 downto 0):= "00";

 	--Outputs
   signal sfp_wave : std_logic_vector(15 downto 0);
	signal uint_wave : std_logic_vector(15 downto 0);

   -- Clock period definitions
   --constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: sin_generator PORT MAP (
          sin => sin,
          sfp_wave => sfp_wave,
			 uint_wave => uint_wave,
          hertz => hertz,
          clk => clk
        );

   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for 2.5 ns;
		clk <= '1';
		wait for 2.5 ns;
   end process;
	
	adjust_process :process(passes)
	begin
		if passes = "00" then
			hertz <= "0000000011011100"; -- 220 Hz
		else
			if passes = "10" then
				hertz <= "0000000110111000"; -- 4400 Hz
			end if;
		end if;
	end process;
	
	pass_process :process(uint_wave)
	begin
		if uint_wave = "1111111111111111" then
			passes <= passes + "01";
		end if;
	end process;
	
END;