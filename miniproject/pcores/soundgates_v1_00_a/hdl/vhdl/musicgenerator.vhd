library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
--use work.sine_package.all;
-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity musicgenerator is

port(     
      uclk 	: in std_logic;
      rst 	: in std_logic;
      -- mb
      freq  : in std_logic_vector(31 downto 0);
      freq_time  : in std_logic_vector(31 downto 0);    
      -- i2s output
      sd  	: out std_logic;
      ws	 	: out std_logic;
      mclk 	: out std_logic;
      sck 	: out std_logic;
      --
      led   : out std_logic_vector(3 downto 0);
      btn 	: in std_logic_vector(3 downto 0);
      en    : out std_logic

);
end musicgenerator;

architecture Behavioral of musicgenerator is

constant bps : integer := 24;

component SampleDataRom
 Port ( 		
		clk    	: in  STD_LOGIC;
		enable 	: in STD_LOGIC;
		data_out : out std_logic_vector(24 - 1 downto 0);
		rst    	: in std_logic
		);
end component;


COMPONENT i2s
	 generic(
		
		RefClkFrequency : integer;		-- clock frequency of the reference clock in hz
		BitsPerSample   : integer;		-- 8/16/32
		SampleRate      : integer;		-- in Hz
		NumChannels     : integer			-- 1 = mono, 2 = stereo
	);
    PORT(
         clk : in  std_logic;
         rst : in  std_logic;
			
         sample_l_in : in  std_logic_vector(BitsPerSample - 1 downto 0);
         sample_r_in : in  std_logic_vector(BitsPerSample - 1 downto 0);
			
          -- i2s output
         sd 	: out  std_logic;
         ws 	: out  std_logic;
         sck 	: out  std_logic;
         mclk 	: out std_logic;
         ------
         load_sample : out std_logic
        );
    END COMPONENT;

signal sample_in 		: std_logic_vector(bps - 1 downto 0);
signal word_select  	: std_logic;
signal serial_clk 	: std_logic;
signal master_clk    : std_logic;
signal load_sample 	: std_logic;
signal serial_data  	: std_logic;

begin
	Led(3 downto 0) <= btn;

	sdata_rom  : SampleDataRom port map(
		clk => serial_clk,
		enable => load_sample,
		data_out => sample_in,
		rst => rst
	);

	i2s_inst : i2s generic map(
		RefClkFrequency 	=> 100_000_000,
		BitsPerSample 		=> bps,
		SampleRate    		=> 44100,
		NumChannels   		=> 2)
		port map(
			clk => uclk,
			rst => rst,
			sd => serial_data,
			ws => word_select,
			sck => serial_clk,
			mclk => master_clk,
			sample_l_in => sample_in,
			sample_r_in => sample_in,
			load_sample => load_sample 
		);
		
-- wire i2s output
	mclk <= master_clk;
	ws <=  word_select;
	sck <= serial_clk;
	sd <= serial_data;
------
	EN <= '1';


end Behavioral;
