library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
library UNISIM;
use UNISIM.VComponents.all;


entity i2s is
generic(
		
		RefClkFrequency : integer := 25000000;		-- clock frequency of the reference clock in hz
		BitsPerSample : integer := 24;				-- 8/16/24
		SampleRate    : integer := 44100;			-- in Hz
		NumChannels   : integer := 2					-- 1 = mono, 2 = stereo
);
port(

		clk    : in std_logic;		-- reference clock
		rst    : in std_logic; 		-- reset

		sample_l_in   : in  std_logic_vector(BitsPerSample - 1 downto 0);		-- sample for left and right channel
		sample_r_in   : in  std_logic_vector(BitsPerSample - 1 downto 0);		-- sample for left and right channel
		
		sd 	 : out std_logic;		-- serial data
		ws 	 : out std_logic;		-- word select
		sck 	 : out std_logic;	   -- serial data clock
		mclk   : out std_logic;    -- master clock
		
		load_sample : out std_logic
		);
end entity i2s;

architecture Behavior of i2s is 


-- calculate MCLK_ACC_PRESCALER
constant MCLK_FREQ : integer := 256 * SampleRate;
constant MCLK_ACC_WIDTH : integer := 16;	
constant MCLK_ACC_PRESCALER : integer := integer(real(2**MCLK_ACC_WIDTH)/(real(RefClkFrequency)/real(MCLK_FREQ)));

-- calculate SCLK_ACC_PRESCALER
constant SCLK_FREQ : real := (real(MCLK_FREQ)/real(256)) * real(BitsPerSample * NumChannels);
constant SCLK_ACC_WIDTH : integer := 16;
constant SCLK_ACC_PRESCALER : integer := integer(real(2**SCLK_ACC_WIDTH)/(real(MCLK_FREQ)/real(SCLK_FREQ)));


signal sclk_acc : unsigned(SCLK_ACC_WIDTH - 1 downto 0):= (others => '0');
signal mclk_acc : unsigned(MCLK_ACC_WIDTH - 1 downto 0) := (others => '0');

signal mclk_i : STD_LOGIC;
signal sclk_i : STD_LOGIC;

-------------------------------------------------------------------------------------------------------------
-- Shift register
-------------------------------------------------------------------------------------------------------------
constant sample_index_init : UNSIGNED(7 downto 0)	:= to_unsigned(BitsPerSample - 1, 8);

signal sample_data_shift : std_logic_vector(BitsPerSample - 1 downto 0) := (others => '0');

signal sample_parallel_load : std_logic := '0';


-- TODO: increase width of sample index for larger samples later
signal sample_index : UNSIGNED(7 downto 0) := sample_index_init;

-------------------------------------------------------------------------------------------------------------
-- internal i2s related signals
-------------------------------------------------------------------------------------------------------------

--signal serial_data 	: std_logic := '0';

signal word_select 	: std_logic := '0';

---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------

begin

	
		-- generates mclk as defined by MCLK_MHZ
	mclk_gen : process(rst, clk)
	begin
		if rst = '1' then
			mclk_acc <= (others => '0');
		elsif clk'event and clk = '1' then
			mclk_acc <= mclk_acc + to_unsigned(MCLK_ACC_PRESCALER,MCLK_ACC_WIDTH);		
		end if;
	end process mclk_gen;
	
	mclk_i <= mclk_acc(MCLK_ACC_WIDTH-1);
	
	sclk_gen : process(rst, mclk_i)
	begin
		if rst = '1' then
			sclk_acc <= (others => '0');
		elsif mclk_i'event and mclk_i = '1' then
			sclk_acc <= sclk_acc + to_unsigned(SCLK_ACC_PRESCALER,SCLK_ACC_WIDTH);
		end if;
	end process sclk_gen;
	
	sclk_i <= sclk_acc(SCLK_ACC_WIDTH-1);
	

	word_select_gen : process(sclk_i, clk, rst)
	begin
		if rst = '1' then
			word_select <= '0';
		end if;
	
		if falling_edge(sclk_i) then
			if sample_index = 1 then
					word_select <= not word_select;
			end if;
		end if;
	end process;
	
	serial_data_shift : process(sclk_i, clk, rst)
	begin
		if rst = '1' then
			sample_index <= sample_index_init;
			sample_data_shift <= (others => '0');
		end if;
		if falling_edge(sclk_i) then
	
			--- Load new sample
			if sample_index = 0 then
			
				sample_parallel_load <= '1';
				sample_index <= sample_index_init;
				if word_select = '1' then
					sample_data_shift <= sample_l_in;
				else
					sample_data_shift <= sample_r_in;
				end if;
			else	-- shift one bit of a sample
				
				-- serial_data <= sample_data_shift(BitsPerSample - 1);
				sample_data_shift <= sample_data_shift(BitsPerSample - 2 downto 0) & '0';
				sample_index <= sample_index - 1;
			
				sample_parallel_load <= '0';
			end if;
			
		end if;
	end process;
	
	-- i2s wiring
	sd <= sample_data_shift(BitsPerSample - 1);
	mclk <= mclk_i;
	ws <= word_select;
	sck <= sclk_i;
	load_sample <= sample_parallel_load;
end architecture;