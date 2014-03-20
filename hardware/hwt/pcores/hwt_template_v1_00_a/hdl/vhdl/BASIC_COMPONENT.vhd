--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - BASIC_COMPONENT.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  basic component example, which is used in the hardware
--                 thread
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity BASIC_COMPONENT is
port(                
        clk     : in  std_logic;
        rst     : in  std_logic;
        ce      : in  std_logic;
        param   : in  signed(31 downto 0); 
        output  : out signed(31 downto 0)
    );

end BASIC_COMPONENT;

architecture Behavioral of BASIC_COMPONENT is

		  
	begin
		            
        CALC : process (clk, rst)
        begin
            if rst = '1' then
                x    <= offset;
            else
            if rising_edge(clk) then
                if ce = '1' then
                    --do your calculations here
                    
                end if;
            end if;
				end if;
        end process;  
        
end Behavioral;
