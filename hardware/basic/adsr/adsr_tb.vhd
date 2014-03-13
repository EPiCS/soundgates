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
	port(
        clk         : in  std_logic;
        rst         : in  std_logic;
        ce          : in  std_logic;
        start       : in  std_logic_vector(31 downto 0);
        stop        : in  std_logic_vector(31 downto 0);
        attack      : in  signed(31 downto 0); 
        decay       : in  signed(31 downto 0);
        sustain     : in  signed(31 downto 0);
        release     : in  signed(31 downto 0);
        wave        : out signed(31 downto 0)
    );
    END COMPONENT;
    
   --Inputs
   signal clk : std_logic := '0';
   signal rst : std_logic := '0';
   signal ce : std_logic := '1';
   signal attack : signed(31 downto 0) := to_signed(integer(real( 0.5 * 2**27)), 32);
   signal decay : signed(31 downto 0) := to_signed(integer(real( 0.5 * 2**27)), 32);
   signal release : signed(31 downto 0) := to_signed(integer(real( 0.5 * 2**27)), 32);
   signal sustain : signed(31 downto 0) := to_signed(integer(real( 0.8 * 2**27)), 32);
   signal start : std_logic_vector(31 downto 0) := x"00000000";
	signal stop : std_logic_vector(31 downto 0) := x"00000000";

 	--Outputs
   signal wave : signed(31 downto 0);

   -- Clock period definitions
   constant clk_period : time := 10 ns;
 
	signal bang_state : integer := 0;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
	adsr_INST : adsr
    port map( 
            clk         => clk,
            rst         => rst,
            ce          => ce,
            start       => start,
            stop        => stop,
            attack      => attack,
            decay       => decay,
            sustain     => sustain, 
            release     => release,
            wave        => wave
            );

   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
		rst <= '0';
   end process;
 

   -- Stimulus process
   stim_proc: process (clk)
   begin		
		if rising_edge(clk) then
		case bang_state is                
                    when 0 =>
								  bang_state <= 1;
								  start <= x"FFFFFFFF";
 
						  when 1 =>
							  bang_state <= 2;
							  start <= (others => '0');
 
                    when 2 =>
							 bang_state <= 3;
 
                    when 3 =>
							 bang_state <= 4;
 
                    when 4 =>
							 bang_state <= 5;
 
                    when 5 =>
							 bang_state <= 6;
 
                    when 6 =>
							 bang_state <= 7;
 
                    when 7 =>
							 bang_state <= 8;
 
                    when 8 =>
							 bang_state <= 9;
 
                    when 9 =>
							 bang_state <= 10;
 
                    when 10 =>
							 bang_state <= 11;
 
                    when 11 =>
							 bang_state <= 12;
 
                    when 12 =>
							 bang_state <= 13;
 
                    when 13 =>
							 bang_state <= 14;
 
                    when 14 =>
							 bang_state <= 15;

                    when 15 =>
							 bang_state <= 16;
 
                    when 16 =>
							 bang_state <= 17;
 
                    when 17 =>
							 bang_state <= 18;
 
                    when 18 =>
							 bang_state <= 19;
 
                    when 19 =>
							 bang_state <= 20;
 
                    when 20 =>
							 bang_state <= 21;
 
                    when 21 =>
							 bang_state <= 22;
 
                    when 22 =>
							 bang_state <= 23;
 
                    when 23 =>
							 bang_state <= 24;
 
                    when 24 =>
							 bang_state <= 25;
 
                    when 25 =>
								  bang_state <= 26;
								  stop <= x"FFFFFFFF";
 
                    when 26 =>
								  stop <= x"00000000";
							 
						  when others =>
							bang_state <= 0;
							 
						end case; 
						end if;
   end process;

END;
