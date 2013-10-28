library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.math_real.all;
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
library UNISIM;
use UNISIM.VComponents.all;

entity i2s is
generic(
		
		RefClkFrequency : integer 	:= 12288000;		-- clock frequency of the reference clock in hz
		BitsPerSample 	 : integer 	:= 24;				-- 8/16/24
		SampleRate		 : integer 	:= 48000;			-- in Hz
		NumChannels   	 : integer 	:= 2					-- 1 = mono, 2 = stereo
);
port(

		mclk   : in std_logic;  -- master clock
		rst    : in std_logic; 	-- reset

		sample_l_in   : in  std_logic_vector(BitsPerSample - 1 downto 0);		-- sample for left and right channel
		sample_r_in   : in  std_logic_vector(BitsPerSample - 1 downto 0);		-- sample for left and right channel
		
		sd 	 : out std_logic;		-- serial data
		ws 	 : out std_logic;		-- word select
		sck 	 : out std_logic;	   -- serial data clock		
		
		load_sample : out std_logic
		);
end entity i2s;

architecture Behavior of i2s is 

-- calculate MCLK_ACC_PRESCALER
--constant MCLK_FREQ : integer := 256 * SampleRate;
--constant MCLK_ACC_WIDTH : integer := 16;
--constant MCLK_ACC_PRESCALER : integer := integer(real(2**MCLK_ACC_WIDTH)/(real(RefClkFrequency)/real(MCLK_FREQ)));

-- calculate SCLK_ACC_PRESCALER
constant SCLK_FREQ : real := (real(RefClkFrequency)/real(256)) * real(BitsPerSample * NumChannels);
constant SCLK_ACC_WIDTH : integer := 32;	-- TODO: Overdimension
constant SCLK_ACC_PRESCALER : integer := integer(real(2**SCLK_ACC_WIDTH)/(real(RefClkFrequency)/real(SCLK_FREQ)));


signal sclk_acc : unsigned(SCLK_ACC_WIDTH - 1 downto 0):= (others => '0');
--signal mclk_acc : unsigned(MCLK_ACC_WIDTH - 1 downto 0) := (others => '0');

--signal mclk_i : STD_LOGIC;
signal sclk_i : STD_LOGIC;

-------------------------------------------------------------------------------------------------------------
-- Shift register
-------------------------------------------------------------------------------------------------------------

constant sample_index_addr_width : natural := natural(ceil(log2(real(BitsPerSample)))) - 1;

constant sample_index_init :  integer range 0 to (BitsPerSample - 1)	:= (BitsPerSample - 1);

signal sample_data_shift : std_logic_vector(BitsPerSample - 1 downto 0) := (others => '0');

signal sample_parallel_load : std_logic := '0';

signal sample_index : integer range 0 to (BitsPerSample - 1) := sample_index_init;

-------------------------------------------------------------------------------------------------------------
-- internal i2s related signals
-------------------------------------------------------------------------------------------------------------

signal word_select 	: std_logic := '0';
---------------------------------------------------------------------------------------

signal sclk_tick : std_logic;	-- generates a tick on a falling edge of sclk

begin
		
	----------------------------------------------------------------
	sclk_gen : process(rst, mclk, sclk_acc)
	begin
		if rst = '1' then
			sclk_acc <= (others => '0');
		elsif rising_edge(mclk) then
			sclk_acc <= sclk_acc + to_unsigned(SCLK_ACC_PRESCALER, SCLK_ACC_WIDTH);			
		end if;
	end process sclk_gen;
	
	sclk_i <= sclk_acc(SCLK_ACC_WIDTH-1);
	
	----------------------------------------------------------------
	serial_data_shift : process(mclk, rst, sclk_i, sample_index, word_select, sample_data_shift )
	begin
	
		if rst = '1' then
			sample_index <= sample_index_init;
			sample_data_shift <= (others => '0');
			
		elsif rising_edge(sclk_i) then
			
			sample_parallel_load <= '0';
			
		--	if sclk_i = '1' then
				
				sample_index <= sample_index - 1;
			
				case sample_index is				
					when 0 =>
						sample_parallel_load <= '1'; --- Load new sample
						
						sample_index <= sample_index_init;						
						case word_select is
							when '1' =>
								sample_data_shift <= sample_l_in;
							when '0' =>
								sample_data_shift <= sample_r_in;
							when others => null;
						end case;						
					when 1 =>
						word_select <= not word_select;
					when others =>
						sample_data_shift <= sample_data_shift(BitsPerSample - 2 downto 0) & '0';
				end case;		
		--	end if;
		end if;
	end process;
	----------------------------------------------------------------
	-- i2s wiring
	----------------------------------------------------------------
	
	sd 	<= sample_data_shift(BitsPerSample - 1);
	
	ws 	<= word_select;
	
	sck 	<= sclk_i;
	
	load_sample <= sample_parallel_load;
end architecture;