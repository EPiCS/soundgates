--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - cordic_stage.vhd
--
--   project:      PG-Soundgates
--   author:       Lukas Funke, University of Paderborn
--
--   description:  Part of the cordic implementation
--
-- ======================================================================
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.all;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity cordic_stage is
	 Generic(   stage : integer := 1;
               alpha : real 	 := 0.5
        );
    Port ( 
         clk   : in  STD_LOGIC;
         rst 	: in  STD_LOGIC;
         ce    : in  STD_LOGIC;

         x  : in  SIGNED (31 downto 0);
         y  : in  SIGNED (31 downto 0);
         z  : in  SIGNED (31 downto 0);
    	  
         x_n   : out SIGNED (31 downto 0);
         y_n   : out SIGNED (31 downto 0);
         z_n   : out SIGNED (31 downto 0)
           );
end cordic_stage;

architecture Behavioral of cordic_stage is
	
	-- registers
   signal x_next : signed(31 downto 0) := (others => '0');
   signal y_next : signed(31 downto 0) := (others => '0');
   signal z_next : signed(31 downto 0) := (others => '0');

	-- intermediate signals
   signal x_next_i : signed(31 downto 0);
   signal y_next_i : signed(31 downto 0);
   signal z_next_i : signed(31 downto 0);
	
   constant arctan_init	   : real                := ARCTAN(alpha) * 2**SOUNDGATE_FIX_PT_SCALING;
   constant scaled_arctan	: signed(31 downto 0) := to_signed(integer(arctan_init), 32);

   signal y_shift : signed(31 downto 0);
   signal x_shift : signed(31 downto 0);	
	
begin

	SHIFT_PROCESS : process(x, y)
	begin
		
		y_shift <= shift_right(y, stage);	--  x 2^-stage
		x_shift <= shift_right(x, stage);			
		
	end process;
	
	ARTIHM_PROCESS : process(x, y, z, x_shift, y_shift)
		
		variable x_next : signed(31 downto 0);
		variable y_next : signed(31 downto 0);
		variable z_next : signed(31 downto 0);
	begin
			
      if z(31) = '0' then		--  sgn = + 1
         x_next := x + (-y_shift);
         y_next := x_shift + y;
         z_next := z + (-scaled_arctan);
      else					-- sgn = -1
         x_next := x +  y_shift;
         y_next := (-x_shift) + y;
         z_next := z + scaled_arctan;
      end if;
      
      x_next_i <= x_next;
      y_next_i <= y_next;			
      z_next_i <= z_next;
	end process;
	
	REG_PROCESS : process(clk)
	begin	
		if rising_edge(clk) then               
         if rst = '1' then
            x_next <= (others => '0');
            y_next <= (others => '0');
            z_next <= (others => '0');            
         elsif ce = '1' then
            x_next <= x_next_i;
            y_next <= y_next_i;
            z_next <= z_next_i;
         end if;
    end if;
	end process;
	
   x_n <= x_next;
   y_n <= y_next;
   z_n <= z_next;
	
end Behavioral;
