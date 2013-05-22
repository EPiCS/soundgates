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

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity main is

port( uclk : in std_logic;
		rst :  in std_logic;
		SD  : out std_logic;
		WS	 : out std_logic;
		SCK : out std_logic;
		EN  : out std_logic
);
end main;

architecture Behavioral of main is

constant bps : integer := 16;


component RomHard
 Port ( 		
		CLK    : in  STD_LOGIC;
		ENABLE : in STD_LOGIC;
		DATA   : out std_logic_vector(31 downto 0);
		rst    : in std_logic
		);
end component;

COMPONENT i2s
	 generic(
		
		RefClkFrequency : integer;		-- clock frequency of the reference clock in hz
		BitsPerSample : integer;		-- 8/16/32
		SampleRate    : integer;		-- in Hz
		NumChannels   : integer			-- 1 = mono, 2 = stereo
	);
    PORT(
         clk : IN  std_logic;
         rst : IN  std_logic;
         SD : OUT  std_logic;
         WS : OUT  std_logic;
         SCK : OUT  std_logic;
         BUFFER_FULL : OUT  std_logic;
         sample_in : IN  std_logic_vector((BitsPerSample * 2) -1 downto 0);
			
			load_sample : OUT std_logic
			
        );
    END COMPONENT;

signal sample_in 		: std_logic_vector((bps * 2) -1 downto 0);
signal word_select  	: std_logic;
signal serial_clk 	: std_logic;
signal load_sample 	: std_logic;
signal serial_data  	: std_logic;
signal buffer_full_i   : std_logic;
begin


	rom_hard_inst : RomHard port map(
		clk => serial_clk,
		enable => load_sample,
		data => sample_in,
		rst => rst
	);

	i2s_inst : i2s generic map(
		RefClkFrequency 	=> 25000000,
		BitsPerSample 		=> bps,
		SampleRate    		=> 78125,
		NumChannels   		=> 2)
		port map(
			clk => uclk,
			rst => '0',
			SD => serial_data,
			WS => word_select,
			SCK => serial_clk,
			BUFFER_FULL => buffer_full_i,
			sample_in => sample_in,
			load_sample => load_sample 
		);
		
		
WS <=  word_select;
SCK <= serial_clk;
SD <= serial_data;
EN <= '1';


end Behavioral;

