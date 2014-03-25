--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - ramp.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  Ramp filter / envelope
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity ramp is
port(                
        clk       : in  std_logic;
        rst       : in  std_logic;
        ce        : in  std_logic;
        incr      : in  signed(31 downto 0);         
        incr2     : in  signed(31 downto 0); 
        bang      : in  signed(31 downto 0);        
	    rmp       : out signed(31 downto 0)
    );

end ramp;

architecture Behavioral of ramp is

    type states is (s_idle, s_increasing, s_decreasing, s_exit);
	 signal state : states := s_idle;
	 signal s_one : signed (31 downto 0) := to_signed(integer(real(1.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);	
	 signal s_zero : signed (31 downto 0) := to_signed(integer(real(0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);	
    constant c_Bang : signed (31 downto 0) := x"0000000F";

    signal x        : signed (31 downto 0) := to_signed(integer(real( 0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
		  
	begin
		  
        rmp <= x;
          
        CALC_RAMP : process (clk, x, incr, rst)
        begin
            if rst = '1' then
                x <= to_signed(integer(real( 0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
                state <= s_idle;
            else
                if rising_edge(clk) then
                    if ce = '1' then
                        if bang = c_Bang then
                            state <= s_increasing;
                        end if;

		                case state is
			                when s_increasing =>
				                if x >= s_one - incr then
					                 state <= s_decreasing;
										  x<=s_one;
									 else
										  x <= x + incr;
				                end if;
			                when s_decreasing => 
				                if x <= s_zero + incr2 then
					                state <= s_exit;
										 x<=s_zero;
									 else
										 x <= x - incr2;
				                end if; 
                         when s_exit =>
								    state <= s_idle;
								 when s_idle =>
									-- nothing
						    end case;
                        end if;
                    end if;
				end if;
        end process;  
	  
        
end Behavioral;
