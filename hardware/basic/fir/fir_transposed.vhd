--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - fir.vhd
--
--   project:      PG-Soundgates
--   author:       Lukas Funke, University of Paderborn
--
--   description:  FIR filter with order FIR_ORDER
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity fir is
generic(
        FIR_ORDER   : integer := 8  --> 10 coefficients
);
port(
        clk          : in  std_logic;
        rst          : in  std_logic;
        ce           : in  std_logic;
        coefficients : in  mem16(FIR_ORDER downto 0);   -- #coefficents  = fir order + 1
        x_in         : in  signed(23 downto 0);
        y_out        : out signed(23 downto 0)
    );

end fir;

architecture Behavioral of fir is
 
    signal taps   : mem24(FIR_ORDER - 1 downto 0) := (others => (others => '0'));
    signal sample : signed(23 downto 0) := (others => '0');
begin
    --FIR_CHAIN: for i in 0 to FIR_ORDER - 1 generate   
        process (clk, rst)
            variable tmp        : signed(39 downto 0);
            variable tmp_scale  : signed(23 downto 0);
        begin
            if rst = '1' then
                taps <= (others => (others => '0'));                
            elsif rising_edge(clk) then
               if ce = '1' then
                    
                    --sample <= x_in;
                    for i in 0 to FIR_ORDER - 1 loop
                    if i = 0 then
                        tmp     := x_in * coefficients(FIR_ORDER);
                        taps(0) <= tmp(39) & tmp(37 downto 15);
                    else    
                        tmp       := x_in * coefficients(FIR_ORDER - i);
                        tmp_scale := tmp(39) & tmp(37 downto 15);
                        tmp_scale := tmp_scale + taps(i - 1);       -- possible overflow error!
                        
                        taps(i)   <= signed(tmp_scale);
                    end if;
                    end loop;          
               end if;
            end if;
        end process;    
    --end generate FIR_CHAIN;    
    y_out <= taps(FIR_ORDER - 1);

end Behavioral;
