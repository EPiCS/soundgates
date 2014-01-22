----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    14:10:27 09/04/2013 
-- Design Name: 
-- Module Name:    cordic_stage - Behavioral 
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
use IEEE.MATH_REAL.all;
-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity cordic_stage is
	 Generic(   stage : integer := 1;
					alpha : real 	 := 0.5
				);
    Port ( clk 	: in  	STD_LOGIC;
           x 		: in  	SIGNED (31 downto 0);
           y 		: in  	SIGNED (31 downto 0);
           z 		: in  	SIGNED (31 downto 0);
			  
			  x_n 	: out  	SIGNED (31 downto 0);
           y_n 	: out  	SIGNED (31 downto 0);
			  z_n		: out  	SIGNED (31 downto 0)
           );
end cordic_stage;

architecture Behavioral of cordic_stage is
	
	-- registers
	signal x_next : signed(31 downto 0) := to_signed(0, 32);
	signal y_next : signed(31 downto 0) := to_signed(0, 32);
	signal z_next : signed(31 downto 0) := to_signed(0, 32);
	
	-- intermediate signals
	signal x_next_i : signed(31 downto 0);
	signal y_next_i : signed(31 downto 0);
	signal z_next_i : signed(31 downto 0);
	
	constant scaling : real := 27.0;
	
	constant arctan_init	   : real 				    := ARCTAN(alpha) * 2**scaling;
	constant scaled_arctan	: signed(31 downto 0) := to_signed(integer(arctan_init), 32);
	
	signal y_shift : signed(31 downto 0);
	signal x_shift : signed(31 downto 0);	
	
begin

	SHIFT_PROCESS : process(x, y)
	begin
		
		y_shift <= shift_right(y, stage);	--  x 2^-stage
		x_shift <= shift_right(x, stage);			
		
	end process;
	
	ARTIHM_PROCESS : process(x, y, z, x_shift, y_shift)
		
		variable x_next : signed(31 downto 0);
		variable y_next : signed(31 downto 0);
		variable z_next : signed(31 downto 0);
	begin
			
		if z > 0 then		--  sgn = + 1
			x_next := x + (-y_shift);
			y_next := x_shift + y;
			z_next := z + (-scaled_arctan);
		else					-- sgn = -1
			x_next := x +  y_shift;
			y_next := (-x_shift) + y;
			z_next := z + scaled_arctan;
		end if;
				
		x_next_i <= x_next;
		y_next_i <= y_next;			
		z_next_i <= z_next;
		
	end process;
	
	REG_PROCESS : process(clk)
	begin
	
		if rising_edge(clk) then
			x_next <= x_next_i;
			y_next <= y_next_i;
			z_next <= z_next_i;
		end if;
	
	end process;
	
	x_n <= x_next;
	y_n <= y_next;
	z_n <= z_next;
	
end Behavioral;

