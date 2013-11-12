library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.MATH_REAL.ALL;
use IEEE.NUMERIC_STD.ALL;
 
library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all; 

ENTITY square_tb IS
END square_tb;
 
 ARCHITECTURE behavior OF square_tb IS 
  
    COMPONENT square
    PORT(
            clk     : in  std_logic;
            ce      : in  std_logic;
            incr    : in  signed(31 downto 0); 
            offset  : in  signed(31 downto 0);  
            sq      : out signed(31 downto 0)
            );
    END COMPONENT;
       
    --Inputs
    signal clk       : std_logic := '0';
    signal calc      : std_logic := '1';
    signal offset    : signed(31 downto 0) := to_signed(integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    signal incr      : signed(31 downto 0) := to_signed(integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    signal incr2     : signed(31 downto 0) := to_signed(integer(real(0.05 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);

    --Outputs
    signal sq  : signed(31 downto 0);
    signal sq1 : signed(31 downto 0);

    -- Clock period definitions
    constant clk_period : time := 10 ns;
 
BEGIN
 
    uut: square PORT MAP (
        clk => clk,
        ce => '1',
        offset => offset,
        incr => incr,
        sq => sq
    );
	
	uut2: square PORT MAP (
        clk => clk,
        ce => '1',
        offset => offset,
        incr => incr2,
        sq => sq1
    );


    clk_process :process
    begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
    end process;


END;
