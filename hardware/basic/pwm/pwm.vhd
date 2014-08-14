--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - pwm.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  pwm
--
-- ======================================================================

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity pwm is
    Port (    clk    : in  STD_LOGIC;
              rst    : in  STD_LOGIC;
              ce     : in  STD_LOGIC;
              sample_in  : in signed(31 downto 0);
              sample_in2 : in signed(31 downto 0);
              pwm        : out signed(31 downto 0));
end pwm;

architecture Behavioral of pwm is

begin

 	process (clk, rst, ce)
	begin
		if ce = '1' then
			
			if (rst = '1') then
				pwm <= (others => '0');
			end if;
			
			if (rising_edge(clk)) then    
				if sample_in > sample_in2 then
                    pwm <= to_signed(integer(real(INT_MAX)), 32);
                else
                    pwm <= to_signed(integer(real(INT_MIN)), 32);
                end if;
			end if;
			
		end if;
	end process;	

end Behavioral;


