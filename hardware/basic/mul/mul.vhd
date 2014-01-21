--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - mul.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  multiplies two samples
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity mul is
port(                
        clk       : in  std_logic;
        rst       : in  std_logic;
        ce        : in  std_logic;
        wave1     : in  signed(31 downto 0);
	    wave2     : in  signed(31 downto 0);       
	    output    : out signed(31 downto 0)
    );

end mul;

architecture Behavioral of mul is
  
  signal output64 : signed (63 downto 0);
  
	begin
	output <= output64(31 downto 0);
	
		adder : process (clk, rst, ce)
        begin
            if rising_edge(clk) then
                if ce = '1' then
                    output64 <= wave1 * wave2;
                end if;
            end if;
        end process;
        
end Behavioral;
