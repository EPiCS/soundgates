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
        IIR_ORDER   : integer := 31  --> 32 coefficients
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

end iir;

architecture Behavioral of iir is

    type mem32 is array (natural range <>) of signed(31 downto 0);
	 type mem64 is array (natural range <>) of signed(63 downto 0);
	 type mem96 is array (natural range <>) of signed(95 downto 0);
    
	 signal sum_mem64    : mem64(IIR_ORDER downto 0);
    signal coeffs_mem32 : mem32(IIR_ORDER downto 0);
    signal coeffs_feedback_mem32 : mem32(IIR_ORDER downto 0);
    signal coeff_index  : signed(31 downto 0);
    signal coeff_feedback_index  : signed(31 downto 0);
	 signal feedback_mem96    : mem96(IIR_ORDER downto 0);

    signal inputs_mem32 : mem32(IIR_ORDER downto 0);

    signal mult_mem64 : mem64(IIR_ORDER downto 0);

    signal s_zero   : signed (31 downto 0 ) := to_signed(integer(real(0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);    
	 signal s_zero64 : signed (63 downto 0 ) := to_signed(integer(real(0.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 64);
    type   calc_states is (s_read, s_calc, s_shift);
    signal state  : calc_states;
	 
	 signal i_coeff_index : integer := to_integer(config_index);
	 signal i_coeff_feedback_index : integer := to_integer(config_feedback_index);
	 
	 signal output : signed(63 downto 0);

    begin	    

        output <= sum_mem64(IIR_ORDER) - feedback_mem96(IIR_ORDER)(63 downto 0);
        wave <= output(31 downto 0);
        CALCULATE : process (clk, rst, ce)
        begin
				if rst = '1' then
                inputs_mem32 <= (others => (others => '0'));
                mult_mem64 <= (others => (others => '0'));
                state <= s_read;
                sum_mem64 <= (others => s_zero64);
				else
					 if rising_edge(clk) then
						  case state is
								when s_read   =>
									 inputs_mem32(0) <= input_wave;
									 state <= s_calc;
									 sum_mem64 <= (others => s_zero64);-- TODO: das wäre für ein clock cycle output von 0 :-(
								when s_calc   =>
									 for i in 0 to IIR_ORDER loop
										  mult_mem64(i) <= coeffs_mem32(i) * inputs_mem32(i);
										  sum_mem64(i) <= sum_mem64(i) + mult_mem64(i);
										  feedback_mem96(i) <= coeffs_feedback_mem32(i) * sum_mem64(i);
									 end loop;
									 state <= s_shift;
								when s_shift  =>
									 for i in 1 to IIR_ORDER loop
										  inputs_mem32(i) <= inputs_mem32(i - 1);
										  sum_mem64(i) <= sum_mem64(i - 1);
										  feedback_mem96(i) <= feedback_mem96(i - 1);
									 end loop;
									 state <= s_read;
						  end case;
					 end if;
				 end if;
        end process;

        CONFIGURE_COEFFICIENTS : process (clk, rst)
        begin
          if rst = '1' then
                coeffs_mem32 <= (others => (others => '0'));
            else
				 if rising_edge(clk) then
					  if config_valid = '1' then
							if coeff_index >= s_zero then
								 i_coeff_index <= to_integer(config_index);
								 coeffs_mem32(i_coeff_index) <= config_data;
							end if;
					  end if;
				 end if;
			 end if;
        end process;
		  
        CONFIGURE_FEEDBACK_COEFFICIENTS : process (clk, rst)
        begin
          if rst = '1' then
                coeffs_feedback_mem32 <= (others => (others => '0'));
            else
				 if rising_edge(clk) then
					  if config_feedback_valid = '1' then
							if coeff_feedback_index >= s_zero then
								 i_coeff_feedback_index <= to_integer(config_feedback_index);
								 coeffs_feedback_mem32(i_coeff_feedback_index) <= config_feedback_data;
							end if;
					  end if;
				 end if;
			 end if;
        end process;
end Behavioral;
