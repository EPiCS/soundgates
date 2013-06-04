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
use IEEE.numeric_std.ALL;
use std.textio.all;
use ieee.std_logic_textio.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity sin_generator is
    Port ( sin 		  : in  STD_LOGIC;
  --         sample_rate : in integer;
  --		  ref_clk	  : in integer;
           wave        : out  STD_LOGIC_VECTOR(15 downto 0);
			  phase_inc	  : in STD_LOGIC_VECTOR(15 downto 0);
			  clk			  : in STD_LOGIC);
end sin_generator;

architecture Behavioral of sin_generator is

	 COMPONENT cordic_v4_0
    PORT(
         phase_in : IN  std_logic_vector(15 downto 0);
         x_out 	: OUT std_logic_vector(15 downto 0);
         y_out 	: OUT std_logic_vector(15 downto 0);
			rdy	   : OUT std_logic;
			ND 		: IN  std_logic;
         clk 		: IN  std_logic
        );
    END COMPONENT;

	signal phase_in	 : std_logic_vector(15 downto 0) := "0000000000000000";
   signal x_out 		 : std_logic_vector(15 downto 0);
	signal y_out		 : std_logic_vector(15 downto 0);
	signal rdy 			 : std_logic;
	signal ND 			 : std_logic := '1';
	signal intern_clk  : std_logic := '0';
	constant pi : std_logic_vector(15 downto 0) := "0110010010000000";
	constant n_pi : std_logic_vector(15 downto 0) := "1001101110000000" ;
	constant clk_period  : integer := 256;  -- 200MHz / 78.125KHz
	signal clk_counter : integer := 0;
	--signal increase	 : STD_LOGIC_VECTOR(15 downto 0);
	signal phase		 : std_logic_vector(15 downto 0) := "0000000000000000";
	signal count_direction : std_logic := '0'; -- 0 = hoch

	FILE test_out_data: TEXT open WRITE_MODE is "/home/hendrik/Soundgates/output_cordic.txt";

begin

	cordic: cordic_v4_0 PORT MAP (
          phase_in => phase_in,
          x_out 	 => x_out,  --sinus
          y_out    => y_out,	--cosinus
			 rdy		 => rdy,
			 ND		 => ND,
          clk 		 => clk
        );
	
	
	--increase <= phase_inc;
   phase_in <= phase;
	wave <= x_out when sin = '1' else y_out;

		
		
		
  increase_phase : process	( intern_clk ) is
		
  variable L1              : LINE;
    begin
	 


		 if rising_edge(intern_clk) then 
			if count_direction = '0' then
				phase <= phase + phase_inc;
			else
				phase <= phase - phase_inc;
			end if;
			
			if phase >= pi or phase <= n_pi then
				count_direction <= not count_direction;
			end if;
			
			write(L1, phase);
		   write(L1, " ");
		   write(L1, x_out);  -- first value in row
		 
		   writeline(test_out_data, L1); -- write row to output file
		end if;
  end process increase_phase;


  create_intern_clk : process ( clk ) is
  begin
		
		if rising_edge(clk) then
			if clk_counter = clk_period then
				clk_counter <= 0;
				intern_clk <= not intern_clk;
			else
				clk_counter <= clk_counter + 1;
			end if;
			
		end if;
  end process create_intern_clk;
  
  

end Behavioral;

