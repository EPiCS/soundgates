--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - arithmetic.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  arithmetic component,
--                 adds, subtracts or multiplies to samples
--
-- ======================================================================
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.MATH_REAL.ALL;
use IEEE.NUMERIC_STD.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity arithmetic is
	 generic(
		ARITH       : ARITHMETIC_TYPE := ADD
	 );
    Port ( 
            clk    : in  std_logic;           
            rst    : in  std_logic;
            ce     : in  std_logic;
            wave1  : in  signed (31 downto 0);
            wave2  : in  signed (31 downto 0);
            output : out signed (31 downto 0)
           );
end arithmetic;

architecture Behavioral of arithmetic is 
    
    component signal_add
    port(
        clk     : in  std_logic;
        ce      : in  std_logic;
        rst     : in  std_logic;
        wave1   : in  signed(31 downto 0);
        wave2   : in  signed(31 downto 0);
        output  : out signed(31 downto 0)
    );
    end component signal_add;
	 
    component signal_sub
    port(
        clk     : in  std_logic;
        ce      : in  std_logic;
        rst     : in  std_logic;
        wave1   : in  signed(31 downto 0);
        wave2   : in  signed(31 downto 0);
        output  : out signed(31 downto 0)
    );
    end component signal_sub;

    component signal_mul
    port(
        clk     : in  std_logic;
        ce      : in  std_logic;
        rst     : in  std_logic;
        wave1   : in  signed(31 downto 0);
        wave2   : in  signed(31 downto 0);
        output  : out signed(31 downto 0)
    );
    end component signal_mul;


begin
   
    ADD_ARITHMETIC : if ARITH = ADD generate
		
		S_ADD : signal_add
			port map(
                clk  => clk,
                rst  => rst,
                ce   => ce,
                wave1  => wave1,
                wave2  => wave2,
                output  => output );
			  
    end generate;

    SUB_ARITHMETIC : if ARITH = SUB generate
		
		S_SUB : signal_sub
			port map(
                clk  => clk,
                rst  => rst,
                ce   => ce,
                wave1  => wave1,
                wave2  => wave2,
                output  => output );
			  
    end generate;

    MUL_ARITHMETIC : if ARITH = MUL generate
		
		S_MUL : signal_mul
			port map(
                clk  => clk,
                rst  => rst,
                ce   => ce,
                wave1  => wave1,
                wave2  => wave2,
                output  => output );
			  
    end generate;
	
end Behavioral;
