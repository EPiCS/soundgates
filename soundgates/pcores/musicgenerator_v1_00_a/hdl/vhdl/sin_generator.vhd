----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    12:23:37 05/23/2013 
-- Design Name: 
-- Module Name:    cordic_wave_generator - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
--use ieee.std_logic_unsigned.all;
use ieee.std_logic_signed.all;
--use IEEE.numeric_std.ALL;
--use std.textio.all;
--use ieee.std_logic_textio.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity sin_generator is
    Port ( sin 		  : in  STD_LOGIC;
			  sample_req  : in  std_logic;
           sfp_wave    : out  STD_LOGIC_VECTOR(23 downto 0);
			  uint_wave	  : out STD_LOGIC_VECTOR(23 downto 0);
			  hertz	  	  : in STD_LOGIC_VECTOR(15 downto 0);
			  clk			  : in STD_LOGIC);
end sin_generator;

architecture Behavioral of sin_generator is

	 COMPONENT cordic
    PORT(
         phase_in : IN  std_logic_vector(15 downto 0);
         x_out 	: OUT std_logic_vector(23 downto 0);
         y_out 	: OUT std_logic_vector(23 downto 0);
         clk 		: IN  std_logic
        );
    END COMPONENT;
	 
	 COMPONENT fixedpointToInteger
	 PORT (
			clk : in STD_LOGIC;
			signedFixed : in  STD_LOGIC_VECTOR(23 downto 0);
			signedInt : out  STD_LOGIC_VECTOR(23 downto 0)
	 );
	 END COMPONENT;

	signal phase_in	 : std_logic_vector(15 downto 0) := "0000000000000000";
   signal x_out 		 : std_logic_vector(23 downto 0);
	signal y_out		 : std_logic_vector(23 downto 0);
	--signal intern_clk  : std_logic := '0';
	constant pi : std_logic_vector(15 downto 0) := "0110010010000000";
	constant n_pi : std_logic_vector(15 downto 0) := "1001101110000000" ;
	
	--constant clk_period  : integer := 2560;  -- 200MHz / 78.125KHz
	--signal clk_counter : integer := 0;
	--signal increase	 : STD_LOGIC_VECTOR(15 downto 0);
	signal phase		 : std_logic_vector(15 downto 0) := "0000000000000000";
	signal count_direction : std_logic := '0'; -- 0 = hoch
	signal direction_lock : std_logic_vector(1 downto 0) := "00";
	signal converterInput : std_logic_vector(23 downto 0);
	signal converterOutput : std_logic_vector(23 downto 0);
	
	signal phase_inc : std_logic_vector(15 downto 0) := "0000000000000000"; -- smallest possible phase increase yield a frequency of 0.75914373445 HZ
	
	signal temp_phase_inc : std_logic_vector(31 downto 0) := "00000000000000000000000000000000"; -- larger vector for temporary multiplikation

	constant t1 : unsigned(15 downto 0) := "1010100010011101"; --multiplikator ( 43165 ) --> 43165 / 32768
	


--	FILE test_out_data: TEXT open WRITE_MODE is "F:\Temp\output_cordic.txt";

begin

	cordic_inst: cordic PORT MAP (
          phase_in => phase_in,
          x_out 	 => x_out,  --sinus
          y_out    => y_out,	--cosinus
          clk 		 => clk
        );
		  
	converter: fixedpointToInteger PORT MAP (
			clk => clk,
			signedFixed => converterInput,
			signedInt => converterOutput
	);
	
	
	--increase <= phase_inc;
   phase_in <= phase;
	sfp_wave <= x_out when sin = '1' else y_out;
	converterInput <= x_out when sin = '1' else y_out;
	uint_wave <= converterOutput;
		
		
-- Steps to set the correct phase_inc
-- Interpret PI as integer and multiply by 4 =: R
-- osc_max := R / intern_clk 
-- osc_max is the longest time a single sine oscillation may take (given that phase_inc is 000.0001)
-- F_min := 1 / osc_max  is therefore the minimum frequency
-- phase_inc := hertz / F_min
-- F_min in our case is 0.75914 Hz or 37957/50000
-- => phase_inc = hertz * 50000 / 37957
-- Division is complicated if not with powers of to. Therefore we approximate the value by 43165 / 32768
	set_phase_increase : process (clk, sample_req, hertz, temp_phase_inc) is
	begin
		if rising_edge(clk) then
			if sample_req = '1' then
				temp_phase_inc <= unsigned(hertz) * t1;
				phase_inc(15 downto 0) <= temp_phase_inc(30 downto 15); -- / 32768
			end if;
		end if;
	end process set_phase_increase;

	
  increase_phase : process	(clk, sample_req, count_direction, direction_lock, phase, phase_inc) is
		
--  variable L1              : LINE;
    begin
		 if rising_edge(clk) then 
		 
			if sample_req = '1' then		 
				if (phase >= pi or phase <= n_pi) and direction_lock = "00"  then
					direction_lock <= "11";
					count_direction <= not count_direction;
				end if;
				if count_direction = '0' then
					phase <= phase + phase_inc;
					if not (direction_lock = "00") then
						direction_lock <= direction_lock - "01";
					end if;
				else
					phase <= phase - phase_inc;
					if not(direction_lock = "00") then
						direction_lock <= direction_lock - "01";
					end if;
				end if;
			end if;
--			write(L1, phase);
--		   write(L1, " ");
--		   write(L1, x_out);  -- first value in row
--			write(L1, " ");
--			write(L1, converterOutput);
--		 
--		   writeline(test_out_data, L1); -- write row to output file
		end if;
  end process increase_phase;


--  create_intern_clk : process ( clk ) is
--  begin
--		
--		if rising_edge(clk) then
--			if clk_counter = clk_period then
--				clk_counter <= 0;
--				intern_clk <= not intern_clk;
--			else
--				clk_counter <= clk_counter + 1;
--			end if;
--			
--		end if;
--  end process create_intern_clk;
  
  

end Behavioral;
