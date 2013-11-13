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
        attack      : in  signed(31 downto 0); 
        decay       : in  signed(31 downto 0);  
        sustain     : in  signed(31 downto 0);
        release     : in  signed(31 downto 0);
        start_amp   : in  signed(31 downto 0);
        attack_amp  : in  signed(31 downto 0);
        sustain_amp : in  signed(31 downto 0);
        release_amp : in  signed(31 downto 0);
        wave        : out signed(31 downto 0)
    );

end adsr;

architecture Behavioral of adsr is

    type   adsr_states is (s_attack, s_decay, s_sustain, s_release, s_exit);
    signal state : adsr_states;

	begin
        
        ADSR_PROC : process (clk, res)

        begin
            if res = '1' then
                wave <= start_amp
            end if;
            
            if rising_edge(clk) then
                case adsr_states is
                    when s_attack   =>
                        wave <= wave + attack;
                        if wave >= attack_amp then
                            state <= s_decay;
                        end if;
                    when s_decay    =>
                        wave <= wave - decay;
                        if wave <= sustain_amp then
                            state <= s_decay;
                        end if;
                    when s_sustain  =>
                        -- wait sustain time
                        state <= s_release;
                    when s_release  =>
                        wave <= wave - release;
                        if wave <= release_amp then
                            state <= s_exit;
                        end if;
                end case;                        
            end if;
        end process;



        
end Behavioral;
