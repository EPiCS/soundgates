library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.MATH_REAL.ALL;
use IEEE.NUMERIC_STD.ALL;
 
--library soundgates_v1_00_a;
--use soundgates_v1_00_a.soundgates_common_pkg.all;

 
ENTITY adsr_tb IS
END adsr_tb;
 
ARCHITECTURE behavior OF adsr_tb IS 
 
    COMPONENT adsr
    PORT(
         clk : IN  std_logic;
         rst : IN  std_logic;
         ce : IN  std_logic;
         attack : IN  signed(31 downto 0);
         decay : IN  signed(31 downto 0);
         release : IN  signed(31 downto 0);
         sustain : IN  signed(31 downto 0);
         start_amp : IN  signed(31 downto 0);
         attack_amp : IN  signed(31 downto 0);
         sustain_amp : IN  signed(31 downto 0);
         release_amp : IN  signed(31 downto 0);
         wave : OUT  signed(31 downto 0)
        );
    END COMPONENT;
    
    type states is (s_reset, s_calc);
	 signal state : states := s_reset;

   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
   signal ce : std_logic := '0';
   signal attack : signed(31 downto 0) := to_signed(integer(real( 0.1 * 2**27)), 32);
   signal decay : signed(31 downto 0) := to_signed(integer(real( 0.1 * 2**27)), 32);
   signal sustain : signed(31 downto 0) := to_signed(integer(real( 2 * 2**27)), 32);
   signal release : signed(31 downto 0) := to_signed(integer(real( 0.1 * 2**27)), 32);
   signal start_amp : signed(31 downto 0) := to_signed(integer(real( 0.0 * 2**27)), 32);
   signal attack_amp : signed(31 downto 0) := to_signed(integer(real( 1.5 * 2**27)), 32);
   signal sustain_amp : signed(31 downto 0) := to_signed(integer(real( 1.0 * 2**27)), 32);
   signal release_amp : signed(31 downto 0) := to_signed(integer(real( 0.0 * 2**27)), 32);

 	--Outputs
   signal wave : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: adsr PORT MAP (
          clk => clk,
          rst => rst,
          ce => ce,
          attack => attack,
          decay => decay,
          sustain => sustain,
          release => release,
          start_amp => start_amp,
          attack_amp => attack_amp,
          sustain_amp => sustain_amp,
          release_amp => release_amp,
          wave => wave
        );

   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
   end process;
 

   -- Stimulus process
   stim_proc: process
   begin		
	
		case state is
		when s_reset => 
			rst<='1';
			state <= s_calc;
		when s_calc =>
			rst<='0';
		end case;
      -- hold reset state for 100 ns.
      wait for 100 ns;	

      wait for clk_period*10;

      -- insert stimulus here 

      wait;
   end process;

END;
