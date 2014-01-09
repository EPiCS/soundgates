library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.MATH_REAL.ALL;
use IEEE.NUMERIC_STD.ALL;
 
library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

ENTITY sawtooth_tb IS
END sawtooth_tb;
 
 ARCHITECTURE behavior OF sawtooth_tb IS 
  
    COMPONENT sawtooth
    PORT(
				  clk     : in  std_logic;
				  rst     : in  std_logic;
				  ce      : in  std_logic;
				  incr    : in  signed(31 downto 0); 
				  offset  : in  signed(31 downto 0);  
				  saw     : out signed(31 downto 0)
            );
    END COMPONENT;
    
    --Inputs
    signal clk       : std_logic := '0';
    signal rst       : std_logic := '0';
    signal ce        : std_logic := '1';
    signal offset    : signed(31 downto 0) := to_signed(integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    signal incr      : signed(31 downto 0) := to_signed(integer(real(0.1  * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    signal incr2     : signed(31 downto 0) := to_signed(integer(real(0.05 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);

    --Outputs
    signal saw  : signed(31 downto 0);
    signal saw1 : signed(31 downto 0);

    -- Clock period definitions
    constant clk_period : time := 10 ns;
 
BEGIN
        
    uut: sawtooth PORT MAP (
        clk => clk,
		  rst => rst,
        ce => ce,
		  incr => incr,
        offset => offset,
        saw => saw
    );
	
	uut2: sawtooth PORT MAP (
        clk => clk,
		  rst => rst,
        ce => ce,
		  incr => incr2,
        offset => offset,
        saw => saw1
    );


    clk_process :process
    begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
    end process;


END;
