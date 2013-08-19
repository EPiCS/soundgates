------------------------------------------------------------------------------
-- user_logic.vhd - entity/architecture pair
------------------------------------------------------------------------------

-- DO NOT EDIT BELOW THIS LINE --------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library reconos_v3_00_b;
use reconos_v3_00_b.reconos_pkg.all;

library proc_common_v3_00_a;
use proc_common_v3_00_a.proc_common_pkg.all;

entity musicgenerator is
  port
  (
	-- OSIF FSL
	OSFSL_S_Read    : out std_logic;                 -- Read signal, requiring next available input to be read
	OSFSL_S_Data    : in  std_logic_vector(0 to 31); -- Input data
	OSFSL_S_Control : in  std_logic;                 -- Control Bit, indicating the input data are control word
	OSFSL_S_Exists  : in  std_logic;                 -- Data Exist Bit, indicating data exist in the input FSL bus

	OSFSL_M_Write   : out std_logic;                 -- Write signal, enabling writing to output FSL bus
	OSFSL_M_Data    : out std_logic_vector(0 to 31); -- Output data
	OSFSL_M_Control : out std_logic;                 -- Control Bit, indicating the output data are contol word
	OSFSL_M_Full    : in  std_logic;                 -- Full Bit, indicating output FSL bus is full
		
	-- FIFO Interface
	FIFO32_S_Data : in std_logic_vector(31 downto 0);
	FIFO32_M_Data : out std_logic_vector(31 downto 0);
	FIFO32_S_Fill : in std_logic_vector(15 downto 0);
	FIFO32_M_Rem : in std_logic_vector(15 downto 0);
	FIFO32_S_Rd : out std_logic;
	FIFO32_M_Wr : out std_logic;
		
	-- HWT reset and clock
	clk           : in std_logic;
	rst           : in std_logic;	
	
	-- i2s
	sd  	: out std_logic;
	ws	: out std_logic;
	mclk 	: out std_logic;
	sck 	: out std_logic	
 );

--  attribute MAX_FANOUT : string;
--  attribute SIGIS : string;

end entity musicgenerator;

------------------------------------------------------------------------------
-- Architecture section
------------------------------------------------------------------------------

architecture IMP of musicgenerator is

	--USER signal declarations added here, as needed for user logic
	constant bps : integer := 24;
	signal sample_in 	: std_logic_vector(bps - 1 downto 0);
	signal sample_l_int	: std_logic_vector(bps - 1 downto 0);
	signal sample_r_int	: std_logic_vector(bps - 1 downto 0);
	signal word_select      : std_logic;
	signal serial_clk 	: std_logic;
	signal master_clk       : std_logic;
	signal load_sample 	: std_logic;
	signal serial_data      : std_logic;
	signal freq_in		: std_logic_vector(31 downto 0);
	--signal uclk : std_logic;
	type STATE_TYPE is (STATE_GET_INIT, STATE_WRITE_MBOX, STATE_READ_MBOX, STATE_EXIT);
	signal state    : STATE_TYPE;
	-- ReconOS stuff
	constant MBOX_READFREQ      : std_logic_vector(C_FSL_WIDTH-1 downto 0) := x"00000000";
	constant MBOX_DEBUG         : std_logic_vector(C_FSL_WIDTH-1 downto 0) := x"00000001";
	signal frequency        : std_logic_vector(C_FSL_WIDTH-1 downto 0);
	signal i_osif   : i_osif_t;
	signal o_osif   : o_osif_t;
		signal ignore   : std_logic_vector(C_FSL_WIDTH-1 downto 0);
	
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
	
	COMPONENT sin_generator
    Port ( 
		 sin 		  : in  STD_LOGIC;
		 sample_req   : in  std_logic;
         sfp_wave     : out STD_LOGIC_VECTOR(23 downto 0);
		 uint_wave	  : out STD_LOGIC_VECTOR(23 downto 0);
		 hertz	  	  : in  STD_LOGIC_VECTOR(15 downto 0);
		clk	          : in  STD_LOGIC);
	end component;	 
  
  

begin

	fsl_setup(
		i_osif,
		o_osif,
		OSFSL_S_Data,
		OSFSL_S_Exists,
		OSFSL_M_Full,
		OSFSL_M_Data,
		OSFSL_S_Read,
		OSFSL_M_Write,
		OSFSL_M_Control
	);
  
  	-- do not use memory interface (memif)
	FIFO32_M_Data <= (others => '0');
	FIFO32_S_Rd   <= '0';
	FIFO32_M_Wr   <= '0';

  --USER logic implementation added here
	sin_gen_inst : sin_generator
	Port map (sin 		  => '1',
		  sample_req =>  load_sample,
		  sfp_wave    => open,
		  uint_wave	  => sample_in,
		  hertz	  	  => freq_in (15 downto 0),
		  clk		  => clk);

	i2s_inst : i2s generic map(
		RefClkFrequency 	=> 100_000_000,
		BitsPerSample 		=> bps,
		SampleRate    		=> 78125,
		NumChannels   		=> 2)
		port map(
			clk => clk,
			rst => rst,
			sd => serial_data,
			ws => word_select,
			sck => serial_clk,
			mclk => master_clk,
			sample_l_in => sample_l_int,
			sample_r_in => sample_r_int,
			load_sample => load_sample 
		);

-- os and memory synchronisation state machine
	process (clk) is
		variable done : boolean;
	begin
		if rst = '1' then
			fsl_reset(o_osif);
			state <= STATE_GET_INIT;
			done := False;
			frequency <= (others => '0');

		elsif rising_edge(clk) then
			case state is
		 	
			when STATE_GET_INIT =>
 				osif_get_init_data(i_osif,o_osif,frequency,done);
				if done then
					state <= STATE_WRITE_MBOX;
					--answer <= message;
				end if;
				
   			when STATE_WRITE_MBOX =>		
				osif_mbox_put(i_osif, o_osif, MBOX_DEBUG, frequency, ignore, done);
  			  	if done then
  			  		state <= STATE_READ_MBOX;
  			  	end if;	
  			  	
  			when STATE_READ_MBOX =>	
				osif_mbox_get(i_osif, o_osif, MBOX_READFREQ, frequency, done);
  			  	if done then
  			  		state <= STATE_WRITE_MBOX;
  			  	end if;	
					       
			when STATE_EXIT =>
	   			osif_thread_exit(i_osif,o_osif);
			
			end case;
		end if;
	end process;
		
sample_l_int <= sample_in;
sample_r_int <= sample_in;

freq_in <= frequency;
mclk <= master_clk;
ws <=  word_select;
sck <= serial_clk;
sd <= serial_data;		

end IMP;