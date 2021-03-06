--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - triangle.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  Triangle wave generator
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity triangle is
port(                
        clk     : in  std_logic;
        rst     : in  std_logic;
        ce      : in  std_logic;
        incr    : in  signed(31 downto 0); 
        offset  : in  signed(31 downto 0);  
        tri     : out signed(31 downto 0)
    );

end triangle;

architecture Behavioral of triangle is

    signal direction : std_logic := '0';

    signal x        : signed (31 downto 0) := to_signed(integer(real( 0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);

    constant upper  : signed (31 downto 0) := to_signed(integer(real( 1.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    constant lower  : signed (31 downto 0) := to_signed(integer(real(-1.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
        		  
	begin
		  
        tri <= x;
          

        CALC_TRI : process (clk, rst)
        begin
            if rst = '1' then
                x <= offset;
            else
            if rising_edge(clk) then
                if ce = '1' then
                    if direction = '0' then
                        x <= x + incr;
                        if x > upper then
                            direction <= '1';
                        end if;
                    elsif direction = '1' then
                       x <= x - incr;
                        if x < lower then
                            direction <= '0';
                        end if; 
                    end if;
                end if;
            end if;
				end if;
        end process;  

end Behavioral;
