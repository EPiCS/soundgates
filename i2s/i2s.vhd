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
		BitsPerSample : integer := 16;				-- 8/16/24
		SampleRate    : integer := 44100;			-- in Hz
		NumChannels   : integer := 2					-- 1 = mono, 2 = stereo
);
port(

		clk    : in std_logic;		-- reference clock
		rst    : in std_logic; 		-- reset

		SD 	 : out std_logic;		-- serial data
		WS 	 : out std_logic;		-- word select
		SCK 	 : out std_logic;	   -- serial data clock
		
		BUFFER_FULL : out std_logic; --indicates that the internal buffer is full
		
		sample_in   : in  std_logic_vector((BitsPerSample * 2) - 1 downto 0);		-- sample for left channel
		load_sample : out std_logic
		);
end entity i2s;


architecture Behavior of i2s is 

--	component fifo generic( 
--		depth      : integer;
--		data_width : integer
--	);
--
--	port ( clk : in std_logic;
--          enr : in std_logic;   --enable read,should be '0' when not in use.
--          enw : in std_logic;   --enable write,should be '0' when not in use.
--          dataout : out std_logic_vector(data_width -1 downto 0);    --output data
--          datain : in std_logic_vector (data_width -1 downto 0);     --input data
--          empty : out std_logic;     --set as '1' when the queue is empty
--          full : out std_logic     --set as '1' when the queue is full
--   );
--
--	end component;

constant serial_clock_period : real := 1.0 / (real(SampleRate) * real(BitsPerSample) * real(NumChannels));
constant refclk_divide : real := real(RefClkFrequency) * serial_clock_period;

constant u_refclk_divide : UNSIGNED(7 downto 0) 	:= (to_unsigned(integer(refclk_divide), 8) / 2) - 1;


signal u_refclk_divide_count : UNSIGNED(7 downto 0) := u_refclk_divide;

constant sample_index_init : UNSIGNED(7 downto 0)	:= to_unsigned((BitsPerSample * 2) - 1, 8) + 1;

constant sample_width  : integer :=  (BitsPerSample * NumChannels);

-- TODO: increase width of sample index for larger samples later
signal sample_index : UNSIGNED(7 downto 0) := sample_index_init;

signal reset_i : std_logic := '1';

signal sample_parallel_load : std_logic := '0';
signal serial_data : std_logic := '0';
signal serial_clk  : std_logic := '0';
signal word_select : std_logic := '1';

signal sample_data_shift : std_logic_vector(sample_width - 1 downto 0) := (others => '0');

--signal BUFFER_FULL : std_logic := '0';
---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------

--type fifo_state_type is (s0, s1, s2);
--signal fifo_ctlr_state : fifo_state_type := s0;
--
--signal fifo_out : std_logic_vector((BitsPerSample * 2) -1 downto 0);
--
--signal fifo_0_in  : std_logic_vector((BitsPerSample * 2) -1 downto 0);
--signal fifo_1_in  : std_logic_vector((BitsPerSample * 2) -1 downto 0);
--
--signal fifo_0_out  : std_logic_vector((BitsPerSample * 2) -1 downto 0);
--signal fifo_1_out  : std_logic_vector((BitsPerSample * 2) -1 downto 0);
--
--signal fifo_in_sel  : std_logic; 
--signal fifo_out_sel : std_logic;
--
--signal fifo_0_clk : std_logic;
--signal fifo_1_clk : std_logic;
--
--signal fifo_0_write_enable : std_logic := '0';
--signal fifo_0_read_enable : std_logic  := '0';
--
--signal fifo_1_write_enable : std_logic := '0';
--signal fifo_1_read_enable : std_logic  := '0';
--
--signal fifo_0_full : std_logic;
--signal fifo_1_full : std_logic;
--
--signal fifo_0_empty : std_logic;
--signal fifo_1_empty : std_logic;
--
--signal fifo_empty : std_logic;
--signal fifo_full  : std_logic;
--constant fifo_depth : integer := 8;
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

begin

--	
--	sample_fifo_0 : fifo generic map(
--		depth 	  => fifo_depth,
--		data_width => (BitsPerSample * 2)
--	)
--	port map (
--		clk 		=> clk,
--		enr 		=> fifo_0_read_enable,
--		enw 		=> fifo_0_write_enable,
--		datain 	=> sample_in,
--		dataout  => fifo_out,
--		empty	 	=> fifo_0_empty,
--		full 		=> fifo_0_full
--	);
--	
--	sample_fifo_1 : fifo generic map(
--		depth 	  => fifo_depth,
--		data_width => (BitsPerSample * 2)
--	)
--	port map (
--		clk 		=> clk,
--		enr 		=> fifo_read_enable,
--		enw 		=> fifo_write_enable,
--		datain 	=> sample_in,
--		dataout  => fifo_out,
--		empty	 	=> fifo_empty,
--		full 		=> fifo_1_full
--	);
--

---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
--	
--	fifo_ctrl : process(clk, serial_clk)
--	
--	begin
--	
--	
--	end process;
--	
--

---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------	
	
--	sample_in2fifo : process(clk)	
--	begin
--		if rising_edge(clk) then
--			if fifo_read_enable = '0' then
--				fifo_write_enable <= '1';
--			else
--				fifo_write_enable <= '0';
--			end if;
--		end if;	
--	end process;
--	
--	fifo2shift : process(serial_clk, load_sample)	
--	begin		
--		if rising_edge(serial_clk) and load_sample = '1' then
--			fifo_read_enable <= '1';
--			fifo_write_enable <= '0';
--			sample_data_shift <= fifo_out;
--		else
--			fifo_read_enable <= '0';
--		end if;	
--	end process;

	
	sample_in2shift : process(serial_clk)
	
	begin
			if rising_edge(serial_clk) then
				if sample_index = 0 then
					
					sample_parallel_load <= '1';
				else
					sample_parallel_load <= '0';
				end if;

			end if;
			
	end process;
	
	
	clk_divide : process(clk)
	begin
		if rising_edge(clk) then
			if(u_refclk_divide_count = 0) then
				serial_clk <= not serial_clk;
				u_refclk_divide_count <= u_refclk_divide;
			else
				u_refclk_divide_count <= u_refclk_divide_count - 1;
			end if;
		end if;
	end process;
	
	serial_data_shift : process(serial_clk)
	begin
		if falling_edge(serial_clk) then
			
			if reset_i = '1' then
					
				sample_index <= sample_index_init;
				sample_data_shift <= (others => '0');
				serial_data <= '0';
			else
			
				-- shift one bit of a sample
				serial_data <= sample_data_shift(sample_width - 1);
				sample_data_shift <= sample_data_shift(sample_width - 2 downto 0) & '0';
			
				-- word select
				if(sample_index = (BitsPerSample + 1) or sample_index = 1) then
					word_select <= not word_select;
				end if;
						
				-- reload shift register
				if sample_index = 0 then
					sample_data_shift <= sample_in;
					sample_index <= sample_index_init;
				else
					sample_index <= sample_index - 1;
				end if;
			
			end if;
			
		end if;
	end process;
	
	-- wiring 
	reset_i <= rst;

	buffer_full <= '0';
	
	load_sample <= sample_parallel_load;
	
	SD <= serial_data;
	
	WS <= word_select;
	SCK <= serial_clk;

end architecture;