--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - iir.vhd
--
--   project:      PG-Soundgates
--   author:       Hendrik Hangmann, University of Paderborn
--
--   description:  IIR filter with order IIR_ORDER and 
--                 feedback order IIR_FEEDBACK_ORDER
--                 Direct-Form 2
--
-- ======================================================================
    
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity iir is
generic(
        IIR_ORDER   : integer := 31;  --> 32 coefficients
        IIR_FEEDBACK_ORDER : integer := IIR_ORDER - 1
);
port(
        clk                  : in  std_logic;
        rst                  : in  std_logic;
        ce                   : in  std_logic;
        input_wave           : in  signed(31 downto 0);
        config_valid         : in  std_logic;
        config_index         : in  signed(31 downto 0);
        config_data          : in  signed(31 downto 0);
        config_feedback_valid: in  std_logic;
        config_feedback_index: in  signed(31 downto 0);
        config_feedback_data : in  signed(31 downto 0);
        wave                 : out signed(31 downto 0)
    );

end adsr;

architecture Behavioral of fir is

    type mem32 is array (natural range <>) of signed(31 downto 0);
    signal coeffs_mem32          : mem32(IIR_ORDER downto 0);
    signal coeffs_mem32          : mem32(IIR_ORDER downto 0);
    signal coeff_index           : signed(31 downto 0);
    signal coeffs_feedback_mem32 : mem32(IIR_ORDER downto 0);
    signal coeffs_feedback_mem32 : mem32(IIR_ORDER downto 0);
    signal coeff_feedback_index  : signed(31 downto 0);

    signal inputs_mem32 : mem32(IIR_ORDER downto 0);

    signal mult_mem32            : mem32(IIR_ORDER downto 0);
    signal mult_feedback_mem32   : mem32(IIR_FEEDBACK_ORDER downto 0);

    signal s_zero : to_signed(integer(real(0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);    

    type   calc_states is (s_read, s_calc, s_shift);
    signal state           : adsr_states;

    signal sum          : signed(32 downto 0);
    signal sum_feedback : signed(32 downto 0);

    begin
        coeff_index          <= config_index;	
        coeff_feedback_index <= config_feedback_index;  
        wave                 <= sum - sum_feedback; 

        CALCULATE : process (clk, rst, ce)
        begin
            if rst = '1' then
                coeff_mem32    <= (others => (others => "0"));
                inputs_mem32   <= (others => (others => "0"));
                mult_mem32     <= (others => (others => "0"));
                state          <= s_read;
                state_feedback <= s_read;
                sum            <= s_zero;
                sum_feedback   <= s_zero;

                coeff_index          <= s_zero;	
                coeff_feedback_index <= s_zero;   
            else
                if rising_edge(clk) then
                    case state_feedback is
                        when s_read   =>
                            inputs_mem32(0) <= input_wave;
                            state <= s_calc;
                            sum            <= s_zero;  -- TODO: das wäre für ein clock cycle output von 0 :-(
                            sum_feedback   <= s_zero;
                        when s_calc   =>
                            for i in 0 to IIR_ORDER loop
                                mult_mem32(i) <= coeffs_mem32(i) * inputs_mem32(i);
                                sum           <= sum + mult_mem32(i);
                            end loop;

                            for j in 0 to IIR_FEEDBACK_ORDER loop
                                mult_feedback_mem32(j) <= coeffs_feedback_mem32(i) * inputs_mem32(i);
                                sum_feedback           <= sum_feedback + mult_feedback_mem32(i);
                            end loop;

                            state <= s_shift;
                        when s_shift  =>
                            for i in 1 to IIR_ORDER loop
                                inputs_mem32(i) <= inputs_mem32(i - 1);
                            end loop;

                            state <= s_read;
                    end case;
                end if;
            end if;
        end process;


end Behavioral;
