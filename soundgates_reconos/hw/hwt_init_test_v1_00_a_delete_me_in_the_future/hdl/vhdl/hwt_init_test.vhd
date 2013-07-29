library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;

library reconos_v3_00_b;
use reconos_v3_00_b.reconos_pkg.all;

entity hwt_init_test is
	port (
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
		rst           : in std_logic
	);

end hwt_init_test;

architecture implementation of hwt_init_test is
	type STATE_TYPE is (STATE_GET_INIT, STATE_WRITE_MBOX, STATE_EXIT );
	
	constant MBOX_SEND  : std_logic_vector(C_FSL_WIDTH-1 downto 0) := x"00000000";

	signal state  : STATE_TYPE;
	signal message : std_logic_vector(C_FSL_WIDTH-1 downto 0);
	signal answer : std_logic_vector(C_FSL_WIDTH-1 downto 0);	
  signal ignore   : std_logic_vector(C_FSL_WIDTH-1 downto 0);
	signal i_osif : i_osif_t;
	signal o_osif : o_osif_t;

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
		
	-- os and memory synchronisation state machine
	process (clk) is
		variable done : boolean;
	begin
		if rst = '1' then
			fsl_reset(o_osif);
			state <= STATE_GET_INIT;
			done := False;
			message <= (others => '0');
			answer <= (others => '0');
		elsif rising_edge(clk) then
			case state is
			  when STATE_GET_INIT =>
 					osif_get_init_data(i_osif,o_osif,message,done);
					if done then
						state <= STATE_WRITE_MBOX;
						answer <= message;
					end if;
				
   			when STATE_WRITE_MBOX =>
				  osif_mbox_put(i_osif, o_osif, MBOX_SEND, answer, ignore, done);
  			  if done then
  			    state <= STATE_EXIT;
  			  end if;	
					       
        when STATE_EXIT =>
          osif_thread_exit(i_osif,o_osif);
			end case;
		end if;
	end process;
	
end architecture;
