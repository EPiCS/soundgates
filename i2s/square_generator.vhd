----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    18:49:08 06/04/2013 
-- Design Name: 
-- Module Name:    square_generator - Behavioral 
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
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity square_generator is
	generic(
		RefClkFrequency : integer := 200_000_000
	 );
	
    Port ( 
			  clk : in  STD_LOGIC;
	        sample_req : in  STD_LOGIC;
           sample_l_out : out  STD_LOGIC_VECTOR (23 downto 0);
           sample_r_out : out STD_LOGIC_VECTOR (23 downto 0)
			  
			  );
end square_generator;

architecture Behavioral of square_generator is
	constant base_freq : integer := 440;
	constant square_frq_prescaler : UNSIGNED(31 downto 0) := to_unsigned(integer(real(RefClkFrequency)/real(base_freq)) / 2, 32);
	
	signal square_acc : UNSIGNED(31 downto 0) := (others => '0');
	
	constant square_low : std_logic_vector  := "100000000000000000000001"; 
	constant square_high : std_logic_vector := "011111111111111111111111"; 
	signal sample_out : std_logic_vector(23 downto 0) := square_low;
	signal square_signal : std_logic_vector(23 downto 0) := square_low;
begin
	
   square_signal_gen : process(clk, square_acc)
		
	begin
		if rising_edge(clk) then			
			if square_acc = square_frq_prescaler then
				square_acc <= (others => '0');
							
				if square_signal(23) = '1' then
					square_signal <= square_high;
				else
					square_signal <= square_low;
				end if;
			else
				square_acc <= square_acc + 1;
			end if;
		end if;
	end process;
	
	sampler : process(clk, sample_req, square_signal)	
	begin
		if rising_edge(clk) then
			if sample_req = '1' then
				sample_out <= square_signal;
			end if;
		end if;
	end process;
	
	sample_l_out <= sample_out;
	sample_r_out <= sample_out;

end Behavioral;

