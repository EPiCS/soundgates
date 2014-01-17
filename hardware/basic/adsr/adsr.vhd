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
        clk         : in  std_logic;
        rst         : in  std_logic;
        ce          : in  std_logic;
        input_wave  : in  signed(31 downto 0);
        start       : in  std_logic;
        bang_stop   : in  std_logic;
        attack      : in  signed(31 downto 0); 
        decay       : in  signed(31 downto 0);  
        release     : in  signed(31 downto 0);
        sustain_amp : in  signed(31 downto 0);
        wave        : out signed(31 downto 0)
    );

end adsr;

architecture Behavioral of adsr is

    type   adsr_states is (s_idle, s_attack, s_decay, s_sustain, s_release, s_exit);
    signal state  : adsr_states;
    signal i_wave : signed (31 downto 0);
    signal stop : std_logic;
	 signal s_one : signed (31 downto 0) := to_signed(integer(real(1.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);	
	 signal s_zero : signed (31 downto 0) := to_signed(integer(real(0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);	
	 signal wave64 : signed (63 downto 0);
		
    begin
	
        wave64 <= i_wave * input_wave;
		  wave <= wave64(31 downto 0);
        
        ADSR_PROC : process (clk, rst)

        begin
            if rst = '1' then
                i_wave <= s_zero;
                state <= s_idle;
                stop <= '0';
            else
            
            if rising_edge(clk) then
                if bang_stop = '1' then
                    stop <= '1';
                end if;
                if ce = '1' then
                    if start = '1' then
                        state <= s_attack;
                    end if;

                    case state is
                        when s_attack   =>
                            i_wave <= i_wave + attack;
                            if i_wave >= s_one then
                                state <= s_decay;
                            end if;
                        when s_decay    =>
                            i_wave <= i_wave - decay;
                            if i_wave <= sustain_amp then
                                state <= s_sustain;
                            end if;
                        when s_sustain  =>
                            i_wave <= sustain_amp;
                            if stop = '1' then
                                state <= s_release;
                                stop <= '0';
                            end if;
                        when s_release  =>
                            i_wave <= i_wave - release;
                            if i_wave <= s_zero then
                                state <= s_exit;
                            end if;
							  when s_exit		=>
								  state <= s_idle;
							  when others =>
									state <= s_idle;
                    end case;
                end if;                       
            end if;
		end if;
        end process;

        
        
end Behavioral;
