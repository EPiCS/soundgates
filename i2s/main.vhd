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
		-- i2s output
		sd  	: out std_logic;
		ws	 	: out std_logic;
		mclk 	: out std_logic;
		sck 	: out std_logic		
);
end main;

architecture Behavioral of main is

constant bps : integer := 24;

component ClockGenerator12288
port
 (-- Clock in ports
  CLK_IN1_P         : in     std_logic;
  CLK_IN1_N         : in     std_logic;
  -- Clock out ports
  CLK_OUT1          : out    std_logic;
  -- Status and control signals
  RESET             : in     std_logic;
  LOCKED            : out    std_logic
 );
end component;


component square_generator
	generic(
		RefClkFrequency : integer;
		SampleWidth 	 : integer
	 );
	
    Port ( 
			  clk 			: in  STD_LOGIC;
           sample_req 	: in  STD_LOGIC;
           sample_l_out : out  STD_LOGIC_VECTOR (23 downto 0);
           sample_r_out : out STD_LOGIC_VECTOR (23 downto 0)			  
			  );
end component;

COMPONENT i2s
	 generic(
		
		RefClkFrequency : integer;		-- clock frequency of the reference clock in hz
		BitsPerSample   : integer;		-- 8/16/32
		SampleRate      : integer;		-- in Hz
		NumChannels     : integer		-- 1 = mono, 2 = stereo
	);
    PORT(
         mclk 	: in std_logic;
         rst 	: in  std_logic;
			
         sample_l_in : in  std_logic_vector(BitsPerSample - 1 downto 0);
			sample_r_in : in  std_logic_vector(BitsPerSample - 1 downto 0);
			
			-- i2s output
			sd 	: out  std_logic;
         ws 	: out  std_logic;
         sck 	: out  std_logic;
			
         ------
			load_sample : out std_logic
			
        );
    END COMPONENT;


signal clk_12288 : std_logic;

signal reset : std_logic;

signal sample_in 		: std_logic_vector(bps - 1 downto 0);

signal sample_l_int	: std_logic_vector(bps - 1 downto 0);
signal sample_r_int	: std_logic_vector(bps - 1 downto 0);

signal word_select  	: std_logic;
signal serial_clk 	: std_logic;
signal master_clk    : std_logic;
signal load_sample 	: std_logic;
signal serial_data  	: std_logic;

signal dcm_locked : std_logic;

begin
	
	
  ClockGenerator12288_I_1 : ClockGenerator12288
  port map
   (-- Clock in ports
    CLK_IN1_P => lvdsclk_p,
    CLK_IN1_N => lvdsclk_n,
    -- Clock out ports
    CLK_OUT1 => clk_12288,
    -- Status and control signals
    RESET  => '0',
    LOCKED => dcm_locked);
		
	square_gen_inst : square_generator
	generic map(
		RefClkFrequency => 12_228_8000,
		SampleWidth => bps)
	port map ( 
		clk => clk_12288,
      sample_req => load_sample,
      sample_l_out => sample_l_int,
      sample_r_out => sample_r_int
		);

	i2s_inst : i2s generic map(
		RefClkFrequency 	=> 122288000,
		BitsPerSample 		=> bps,
		SampleRate    		=> 48000,
		NumChannels   		=> 2)
		port map(
			mclk => clk_12288,
			rst => reset,
			sd => serial_data,
			ws => word_select,
			sck => serial_clk,
			
			sample_l_in => sample_l_int,
			sample_r_in => "000000000000000000000000",
			load_sample => load_sample 
		);
		
-- wire i2s output
	mclk 	<= clk_12288;
	ws 	<= word_select;
	sck 	<= serial_clk;
	sd 	<= serial_data;
------
	
	reset <= not dcm_locked;
	
end Behavioral;