----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    01:31:20 05/18/2013 
-- Design Name: 
-- Module Name:    main - Behavioral 
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
--use work.sine_package.all;
-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
library UNISIM;
use UNISIM.VComponents.all;

entity main is

port( 	
		lvdsclk_p : in std_logic;
		lvdsclk_n : in std_logic;
		
		rst 	: in std_logic;
			
	   -- dac out left
		AUDIO_DAC_left_MOSI_pin : out std_logic; -- Master Out Serial In
		AUDIO_DAC_left_SCK_pin	: out std_logic; -- Serial Clock
		AUDIO_DAC_left_SS_pin	: out std_logic; -- Sync Signal
		-- dac out right
		AUDIO_DAC_right_MOSI_pin : out std_logic;
		AUDIO_DAC_right_SCK_pin	 : out std_logic;
		AUDIO_DAC_right_SS_pin	 : out std_logic
		
);
end main;

architecture Behavioral of main is
------------------------------------------------
-- DAC Control
------------------------------------------------
component DACControl
	Port(
			clk : in std_logic;
			rst : in std_logic;
			
			data : in std_logic_vector(11 downto 0);
			op_mode : in std_logic_vector(1 downto 0);
			
			--busy : out std_logic;
			
			dac_clk  : out std_logic;
			dac_data : out std_logic;
			dac_sync : out std_logic
	);
end component;
------------------------------------------------

constant DAC_OPMODE_LEFT : std_logic_vector(1 downto 0) := "00";
constant DAC_OPMODE_RIGHT : std_logic_vector(1 downto 0) := "00";

signal dac_tst_signal : unsigned(11 downto 0) := (others => '0');
signal ramp_acc : unsigned(15 downto 0) := (others => '0');
signal uclk : std_logic;		-- internal clock signal

begin
	
	
	LVDS_CLOCK : IBUFGDS
   port map ( 
		O  => uclk,
      I  => lvdsclk_p,
      IB => lvdsclk_n
   );
	------------------------------------------------
	-- dac controller instance left channel
	------------------------------------------------
	dac_isnt_l : DACControl
	port map(
		   clk => uclk,
			rst => rst,
			data => std_logic_vector(dac_tst_signal),
			op_mode => DAC_OPMODE_LEFT,
			
			--busy => open,
			
			dac_clk  => AUDIO_DAC_left_SCK_pin,
			dac_data => AUDIO_DAC_left_MOSI_pin,
			dac_sync => AUDIO_DAC_left_SS_pin
	);
	------------------------------------------------
	-- dac controller instance right channel
	------------------------------------------------
	dac_isnt_r : DACControl
	port map(
		   clk => uclk,
			rst => rst,
			data => std_logic_vector(dac_tst_signal),
			op_mode => DAC_OPMODE_RIGHT,
			
			--busy => open,
			
			dac_clk  => AUDIO_DAC_right_SCK_pin,
			dac_data => AUDIO_DAC_right_MOSI_pin,
			dac_sync => AUDIO_DAC_right_SS_pin
	);
	
	------------------------------------------------
	
	dac_ramp_gen : process(uclk)
	begin
		if rising_edge(uclk) then
		
			if(ramp_acc) = 48828 then
				ramp_acc <= (others => '0');
				
				if dac_tst_signal = 4095 then
					dac_tst_signal <= (others => '0');
				else
					dac_tst_signal <= dac_tst_signal + 1;
				end if;
				
			else
				ramp_acc <= ramp_acc + 1;			
			end if;				
		end if;
		
	end process;
	
	
end Behavioral;

