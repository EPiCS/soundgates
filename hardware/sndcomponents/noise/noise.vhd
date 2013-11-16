--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - noise
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  Basic component that generates noise (white, pink,...)
--
-- ======================================================================

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;
-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity noise is
	 generic(
		FPGA_FREQUENCY   : integer    := 100_000_000;
		NOISE            : NOISE_TYPE := WHITE;
		SAMPLE_FREQUENZY : integer    := 44100
	 );
    Port ( 
            clk    : in  std_logic;           
            rst    : in  std_logic;
            ce     : in  std_logic;
            data   : out signed(31 downto 0)
           );
end noise;

architecture Behavioral of noise is
		
    

	--------------------------------------------------------------------------------	
   -- Cordic related components and signals
   --------------------------------------------------------------------------------	
	component PRBS -- white noise
        Generic ( constant levels : integer := 16);
        Port (    clk    : in  STD_LOGIC;
                  rst    : in  STD_LOGIC;
                  ce     : in  STD_LOGIC;
                  rand   : out signed (levels - 1 downto 0));
    end component PRBS;
    
   --------------------------------------------------------------------------------
   --------------------------------------------------------------------------------
	
    constant prbs_levels : integer := 32;

   --------------------------------------------------------------------------------	
   -- FOO related components and signals
   --------------------------------------------------------------------------------	
   
begin
		
    WHITE_NOISE : if NOISE = WHITE generate
        
        PRBS_INSTA : PRBS
        generic map ( levels => prbs_levels)
        port map (  clk  => clk,
                    rst  => rst,
                    ce   => ce,
                    rand => data);

    end generate;


   --------------------------------------------------------------------------------   
   
end Behavioral;

