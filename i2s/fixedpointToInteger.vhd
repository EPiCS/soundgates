----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:14:26 05/27/2013 
-- Design Name: 
-- Module Name:    fixedpointToInteger - Behavioral 
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
-- Converts a 16 bit signed fixed point decimal ( <sign,2^0,2^-1,...> ) to an unsigned integer value
-- This will not use the whole possible range, since the fixed point decimal is at most [-1,1]
-- Maps:
--		 -1 to 0
--		 0 to 32767
--		 1 to 65535
-- 	 and respectively in between
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
--use ieee.std_logic_arith.all;
--use ieee.std_logic_unsigned.all;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity fixedpointToInteger is
    Port ( clk : in STD_LOGIC;
			  signedFixed : in  STD_LOGIC_VECTOR(23 downto 0);
           signedInt : out  STD_LOGIC_VECTOR(23 downto 0));
end fixedpointToInteger;

architecture Behavioral of fixedpointToInteger is

--	signal tempShift  : unsigned(23 downto 0);
	
	constant offset : unsigned(23 downto 0) := "011111111111111111111111";
begin

	convertProcess : process(clk) is
		variable temp : unsigned(23 downto 0);
	begin

	if rising_edge(clk) then
		if signedFixed(23) = '0' then
			temp := unsigned(signedFixed) + offset;
			temp := temp(22 downto 0) & '1';
		else
			temp := unsigned(signedFixed(22 downto 0) & '0');
		end if;
		
		signedInt <= std_logic_vector(temp);
	end if;

	
	
--		if signedFixed(15) = '1' then    -- negative value
--			unsignedInt(15) <= '0';			-- eliminate the prefix
--			if signedFixed(14) = '0' then -- if the second bit is a 0, 
--													-- then the value is something <= -1
--													-- because the algorithm afterwards only work for values (-1,0]
--													-- we just set everything to 0, which would be the correct conversion for -1
--				unsignedInt(14 downto 0) <= "000000000000000";
--			else
--				unsignedInt(14 downto 1) <= signedFixed(13 downto 0);  -- shift the rest to the left
--				unsignedInt(0) <= '0';			-- fill with a 0
--			end if;
--		else		-- positive value or zero
--			tempShift(15 downto 1) <= unsigned(signedFixed(14 downto 0)); -- shift the input to the left
--			tempShift(0) <= '0';
--			unsignedInt <= STD_LOGIC_VECTOR(tempShift  + "0111111111111111" ); -- add the shifted input to the middle value
--		end if;
--	end if;

	end process convertProcess;
	
	--signedInt <= signedFixed;
	
end Behavioral;
