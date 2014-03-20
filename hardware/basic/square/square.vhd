--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - square.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  Square wave generator
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity square is
port(                
        clk     : in  std_logic;
        rst     : in  std_logic;
        ce      : in  std_logic;
        incr    : in  signed(31 downto 0); 
        offset  : in  signed(31 downto 0);  
        duty_on : in  signed(31 downto 0);  
        duty_off: in  signed(31 downto 0);
        sq      : out signed(31 downto 0)
    );

end square;

architecture Behavioral of square is

    constant last_int : integer := integer(SOUNDGATE_FIX_PT_SCALING) + 1;

    constant pi     : signed (31 downto 0) := to_signed(integer(real(MATH_PI     * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    constant two_pi : signed (31 downto 0) := to_signed(integer(real(2.0 *MATH_PI* 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    constant upper  : signed (31 downto 0) := (0=>'0', others => '1');
    constant lower  : signed (31 downto 0) := (others => '1');
	--constant add    : signed (31 downto 0) := to_signed(integer(real(0.01        * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
	
    signal x        : signed (31 downto 0) := to_signed(integer(real( 0.0        * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    signal square   : signed (31 downto 0) := upper;
		  
	begin
		  
        sq <= square;
          
        CALC_SQ : process (clk, rst)
        begin
            if rst = '1' then
                x <= offset;
            else
            if rising_edge(clk) then
                if ce = '1' then
                    x <= x + incr;
                    if    x >= to_signed(integer(real( 0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32) then
                        square <= upper;
                    elsif x >= duty_on then  --to_signed(integer(real( 1.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32) then
                        square <= lower;
                    elsif x >= duty_off then --to_signed(integer(real( 2.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32) then
				        square <= upper;
                        x <= to_signed(integer(real(0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
                    end if;
                    
                end if;
            end if;
				end if;
        end process;  
        
end Behavioral;
