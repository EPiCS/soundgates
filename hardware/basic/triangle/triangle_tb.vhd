library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.MATH_REAL.ALL;
use IEEE.NUMERIC_STD.ALL;
 
library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all; 

ENTITY triangle_tb IS
END triangle_tb;
 
 ARCHITECTURE behavior OF triangle_tb IS 
  
    COMPONENT triangle
    PORT(
            clk     : in  std_logic;
            ce      : in  std_logic;
            incr    : in  signed(31 downto 0); 
            offset  : in  signed(31 downto 0);  
            tri     : out signed(31 downto 0)
            );
    END COMPONENT;
        
    --Inputs
    signal clk       : std_logic := '0';
    signal calc      : std_logic := '1';
    signal offset    : signed(31 downto 0) := to_signed(integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    signal incr      : signed(31 downto 0) := to_signed(integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
    signal incr2     : signed(31 downto 0) := to_signed(integer(real(0.05 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);

    --Outputs
    signal tri  : signed(31 downto 0);
    signal tri1 : signed(31 downto 0);

    -- Clock period definitions
    constant clk_period : time := 10 ns;
 
BEGIN
 
    uut: triangle PORT MAP (
        clk => clk,
        ce => '1',
        offset => offset,
        incr => incr,
        tri => tri
    );
	
	uut2: triangle PORT MAP (
        clk => clk,
        ce => '1',
        offset => offset,
        incr => incr2,
        tri => tri1
    );


    clk_process :process
    begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
    end process;


END;
