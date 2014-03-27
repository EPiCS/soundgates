--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - sub.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  subtracts two samples
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity sub is
port(                
        clk       : in  std_logic;
        rst       : in  std_logic;
        ce        : in  std_logic;
        sample_in     : in  signed(31 downto 0);
	    sample_in2     : in  signed(31 downto 0);       
	    output    : out signed(31 downto 0)
    );

end sub;

architecture Behavioral of sub is
  
	begin
		sub_proc : process (clk, rst, ce)
        begin
            if rising_edge(clk) then
                if ce = '1' then
                    if sample_in - sample_in2 > to_signed(integer(real(INT_MAX)), 32) then 
                        output <= to_signed(integer(real(INT_MAX)), 32);
                    elsif sample_in - sample_in2 < to_signed(integer(real(INT_MIN)), 32) then 
                        output <= to_signed(integer(real(INT_MIN)), 32);
                    else
                        output <= sample_in - sample_in2;
                    end if;
                end if;
            end if;
        end process;
        
end Behavioral;