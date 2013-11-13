--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - adsr.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  ADSR Envelope
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity adsr is
port(                
        clk     : in  std_logic;
        ce      : in  std_logic;
        attack  : in  signed(31 downto 0); 
        decay   : in  signed(31 downto 0);  
        sustain : in  signed(31 downto 0);
        release : in  signed(31 downto 0);
        wave    : out signed(31 downto 0)
    );

end adsr;

architecture Behavioral of adsr is
		  
	begin
		  
		  
        
end Behavioral;
