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
        attack      : in  unsigned(31 downto 0); 
        decay       : in  unsigned(31 downto 0);  
        release     : in  unsigned(31 downto 0);
        start_amp   : in  unsigned(31 downto 0);
        attack_amp  : in  unsigned(31 downto 0);
        sustain_amp : in  unsigned(31 downto 0);
        release_amp : in  unsigned(31 downto 0);
        wave        : out signed(31 downto 0)
    );

end adsr;

architecture Behavioral of adsr is

    type   adsr_states is (s_attack, s_decay, s_sustain, s_release, s_exit);
    signal state  : adsr_states;
    signal i_wave : signed (31 downto 0);
    signal stop : std_logic;

    begin
	
        wave <= i_wave * input_wave;
        
        ADSR_PROC : process (clk, rst)

        begin
            if rst = '1' then
                i_wave <= start_amp;
                state <= s_attack;
                stop <= '0';
            else
            
            if rising_edge(clk) then
                if bang_stop = '1' then
                    stop <= '1'
                end if;
                if ce = '1' then
                    case state is
                        when s_attack   =>
                            if start = '1' then
                                i_wave <= i_wave + attack;
                                if i_wave >= attack_amp then
                                    state <= s_decay;
                                end if;
                            end if;
                        when s_decay    =>
                            i_wave <= i_wave - decay;
                            if i_wave <= sustain_amp then
                                state <= s_sustain;
                            end if;
                        when s_sustain  =>
                            if stop = '1' then
                                state <= s_release;
                                stop <= '0';
                            end if;
                        when s_release  =>
                            i_wave <= i_wave - release;
                            if i_wave <= release_amp then
                                state <= s_exit;
                            end if;
				        when s_exit		=>
					        state <= s_attack;
                    end case;
                end if;                       
            end if;
		end if;
        end process;

        
        
end Behavioral;
