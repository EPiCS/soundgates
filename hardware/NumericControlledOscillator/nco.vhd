----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    10:34:34 09/09/2013 
-- Design Name: 
-- Module Name:    nco - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;
-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;


library soundgates;
use soundgates.soundcomponents.all;

entity nco is
	 generic(
		FPGA_FREQUENCY : integer := 100_000_000;
		WAVEFORM : WAVEFORM_TYPE := SIN
	 );
    Port ( clk 		: in  STD_LOGIC;
			  frequency : in  unsigned(15 downto 0);			  
			  data 		: out signed(31 downto 0)
           );
end nco;

architecture Behavioral of nco is
	
	type FREQ_COMMIT_STATES is (s0, s1, s2, s3);
	
	signal fqcommit_cs : FREQ_COMMIT_STATES := s0;
	
	--------------------------------------------------------------------------------	
	component cordic
		generic ( pipeline_stages : integer := 24	);
		port ( clk : in std_logic;
				x   : in  signed(31 downto 0);
				y   : in  signed(31 downto 0);
				phi : in  signed(31 downto 0);  -- 0 < phi < 2 * pi
				sin : out signed(31 downto 0);
				cos : out signed(31 downto 0)
		);
	end component cordic;
	
	constant cordic_x_init : signed(31 downto 0) := to_signed(integer(real(1.0  * 2**SOUNDGATE_FIX_PT_SCALING)),32);
	constant cordic_y_init : signed(31 downto 0) := to_signed(integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING)),32);
	
	constant phi_offset : Phase_Increment_Array := Precalculate_Cordic_Phase_Increments(FPGA_FREQUENCY);
	
	signal cordic_phi_offset : signed(31 downto 0) := to_signed(0, 32);
	signal cordic_phi_acc 	 : signed(31 downto 0) := to_signed(0, 32);
	--------------------------------------------------------------------------------
	
	signal frequency_current : unsigned(15 downto 0) := (others => '0');

begin
	
	FREQ_REG_PROCESS : process(clk)
	begin
		if rising_edge(clk) then
			
			if frequency_current /= frequency then
				cordic_phi_offset <= phi_offset(to_integer(frequency));
				frequency_current <= frequency;
			end if;
		end if;
	end process;
	
	SIN_GENERATOR : if WAVEFORM = SIN generate
		
			CORDIC_INSTA : cordic
			port map( clk 	=> clk,
						 x 	=> cordic_x_init,
						 y 	=> cordic_y_init,
						 phi  => cordic_phi_acc,
						 sin  => data,
						 cos  => open );
			
			PHASE_STIMULIS_PROCESS : process(clk)		-- calculate one periode
			begin
				
				if rising_edge(clk) then
					
					cordic_phi_acc <= cordic_phi_acc + cordic_phi_offset;	
					
  					if cordic_phi_acc >= to_signed(integer(real(MATH_PI * 2.0 * 2 ** SOUNDGATE_FIX_PT_SCALING)), 32) then
						cordic_phi_acc <= (others => '0');						
					end if;
					
				end if;				
			end process;
		
	end generate SIN_GENERATOR;	
end Behavioral;

