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
		en  	: in std_logic;
		
		-- i2s output
		sd  	: out std_logic;
		ws	 	: out std_logic;
		mclk 	: out std_logic;
		sck 	: out std_logic				
);
end main;

architecture Behavioral of main is

constant bps : integer := 24;

component SampleDataRom
 Port ( 		
		clk    	: in  STD_LOGIC;
		enable 	: in STD_LOGIC;
		data_out : out std_logic_vector(24 - 1 downto 0);
		rst    	: in std_logic
		);
end component;



component square_generator
	generic(
		RefClkFrequency : integer := 200_000_000
	 );
	
    Port ( 
			  clk : in  STD_LOGIC;
           sample_req : in  STD_LOGIC;
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

--signal sample_in 		: std_logic_vector(bps - 1 downto 0);

signal sample_l_int	: std_logic_vector(bps - 1 downto 0);
signal sample_r_int	: std_logic_vector(bps - 1 downto 0);


signal word_select  	: std_logic;
signal serial_clk 	: std_logic;
signal master_clk    : std_logic;
signal load_sample 	: std_logic;
signal serial_data  	: std_logic;
signal uclk : std_logic;
begin
	
	
	LVDS_CLOCK : IBUFGDS
   port map ( O  => uclk,
              I  => lvdsclk_p,
              IB => lvdsclk_n
   );
	
--	sdata_rom  : SampleDataRom port map(
--		clk => uclk,
--		enable => load_sample,
--		data_out => sample_in,
--		rst => rst
--	);
	
	square_gen_inst : square_generator
	port map ( 
		clk => uclk,
      sample_req => load_sample,
      sample_l_out => sample_l_int,
      sample_r_out => sample_r_int
		);

	i2s_inst : i2s generic map(
		RefClkFrequency 	=> 200_000_000,
		BitsPerSample 		=> bps,
		SampleRate    		=> 78125,
		NumChannels   		=> 2)
		port map(
			clk => uclk,
			rst => rst,
			sd => serial_data,
			ws => word_select,
			sck => serial_clk,
			mclk => master_clk,
			sample_l_in => sample_l_int,
			sample_r_in => sample_r_int,
			load_sample => load_sample 
		);
		
-- wire i2s output
	mclk <= master_clk;
	ws <=  word_select;
	sck <= serial_clk;
	sd <= serial_data;
------

end Behavioral;

