library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use IEEE.numeric_std.ALL;

entity component_stub is
    Generic(		
		BitsPerSample 	 : integer 	:= 24;     -- 8/16/24
		SampleCount      : integer  := 64
    );

    Port(
        clk : in std_logic;
        rst : in std_logic
    );
end component_stub;

architecture Behavioral of component_stub is

-- ====================================
-- = START: USER COMPONENT DECLARATIONS (OPT)
-- ====================================
--    COMPONENT blub
--        PORT( clk : in std_logic;
--              signedFixed : in  std_logic_vector(23 downto 0);
--              signedInt   : out std_logic_vector(23 downto 0)
--        );
--     END COMPONENT;
-- ====================================
-- = END: USER COMPONENT DECLARATION 
-- ====================================

-- ReconOS Stuff
    signal i_osif   : i_osif_t;
    signal o_osif   : o_osif_t;
    signal i_memif  : i_memif_t;
    signal o_memif  : o_memif_t;
    signal i_ram    : i_ram_t;
    signal o_ram    : o_ram_t;
-- /ReconOS Stuff

    type STATE_TYPE is (STATE_INIT,  STATE_REFRESH, STATE_CALC, STATE_CHECK, STATE_WAIT);
    signal state    : STATE_TYPE;
    
    -- points to the header struct
    signal header_address   : std_logic_vector(31 downto 0);
    -- points to outgoing buffer
    signal target_address   : std_logic_vector(31 downto 0);
    -- used to point to data inside of the target_address buffer
    signal target_offset    : std_logic_vector(31 downto 0);
    
    -- Component dependent signals
    -- points to incoming data buffer
    signal source_address   : std_logic_vector(31 downto 0); 
    -- used to point to data inside of the source_address buffer
    signal source_offset    : std_logic_vector(31 downto 0);

-- ====================================
-- = START USER SIGNALS
-- ====================================
    --constant pi : std_logic_vector(15 downto 0) := "0110010010000000";
    --constant clk_period  : integer := 2560;  -- 200MHz / 78.125KHz    
    --constant t1 : unsigned(15 downto 0) := "1010100010011101"; --multiplikator ( 43165 ) --> 43165 / 32768
-- ====================================
-- = END USER SIGNALS 
-- ====================================
    
begin
-- ====================================
-- = START USER COMPONENT INSTANTIATION (OPT)
-- ====================================
    --    blub_inst: blub PORT MAP (
    --        phase_in => phase_in,
    --        x_out    => x_out,
    --        y_out    => y_out,
    --        clk      => clk
    --        );
-- ====================================
-- = END USER COMPONENT INSTANTIATION 
-- ====================================

-- ====================================
-- = START USER HARD WIRINGS (OPT)
-- ====================================
    --    phase_in <= phase;
    --    sfp_wave <= x_out when sin = '1' else y_out;
-- ====================================
-- = END USER HARD WIRINGS 
-- ====================================

-- ReconOS Stuff
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
                
memif_setup(
        i_memif,
        o_memif,
        FIFO32_S_Data,
        FIFO32_S_Fill,
        FIFO32_S_Rd,
        FIFO32_M_Data,
        FIFO32_M_Rem,
        FIFO32_M_Wr
        );
-- /ReconOS Stuff

    process (clk) is
        variable done : boolean;
        variable calc_state : integer range 0 to 16;
        variable calc_count : integer range 0 to SampleCount;
-- ====================================
-- = START USER PROCESS VARIABLES (OPT)
-- ====================================

-- ====================================
-- = END USER PROCESS VARIABLES
-- ====================================
    begin
        if rst = '1' then
            fsl_reset(o_osif);
            state <= STATE_INIT;
            done := False;
            header_address <= (others => '0');
            target_address <= (others => '0');
            target_offset  <= (others => '0');
            calc_state     := 0;
            calc_count     := 0;
            source_address <= (others => '0');
            source_offset  <= (others => '0');
-- ====================================
-- = START: RESET YOUR VARIABLES HERE
-- ==================================== 

-- ====================================
-- = END: RESET YOUR VARIABLES HERE
-- ==================================== 
        elsif rising_edge(clk) then
            case state is
            
            -- INIT State gets the address of the header struct
            when STATE_INIT =>
                osif_get_init_data(i_osif,o_osif,header_address,done);
                if done then
                    -- Initialize your signals
                    state <= STATE_REFRESH;
                    target_offset <= (others => '0');
                    calc_state    := 0;
                end if;
                
            when STATE_REFRESH =>
                -- Read your data
                memif_read_word(i_memif, o_memif, header_address, TARGETSIGNAL, done);
                if done then
                    state <= STATE_CALC;
                end if;

            when STATE_CALC =>
                case calc_state is
                
                    when 0 =>
                    -- Read your data i.e.
                    memif_read_word(i_memif, o_memif, source_address + pSourceOffset, DATASIGNAL, done);
                    if(done) then
                        calc_state     := calc_state + 1;
                        source_offset  := source_offset + 32;
                    end if;
                
                    when 1 =>
                    -- Manipulate DATASIGNAL ie. CALCLULATED_DATA <= DATASIGNAL +1;
                    calc_state := calc_state + 1;
                
                    when 2 =>
                    -- Write result
                    memif_write_word(i_memif, o_memif, target_address + target_offset, CALCLULATED_DATA, done);
                    if done then
                        calc_state    := 0;
                        target_offset <= target_offset + 32;
                        state         <= STATE_CHECK;
                    end if;    
                
                end case;
                    
            when STATE_CHECK =>    
                if (calc_count < sample_count) then
                    state <= STATE_CALC;
                else
                    state <= STATE_WAIT;
                end if;
            
            when STATE_WAIT =>    
                -- EXIT OR WAIT FOR RESTART?
                target_offset <= 0;
                source_offset <= 0; 
            
            
            when STATE_EXIT =>
                   osif_thread_exit(i_osif,o_osif);
            
            end case;
        end if;
    end process;

-- ====================================
-- = INSERT ADDITIONAL USER PROCESSES HERE
-- ====================================


end Behavioral;

-- ====================================
-- = RECONOS Function Library - Copy and Paste!
-- ====================================        
-- osif_mbox_put(i_osif, o_osif, MBOX_NAME, SOURCESIGNAL, ignore, done);
-- osif_mbox_get(i_osif, o_osif, MBOX_NAME, TARGETSIGNAL, done);

-- Read from shared memory:

-- Speicherzugriffe:
-- Wortzugriff:
-- memif_read_word(i_memif, o_memif, addr, TARGETSIGNAL, done);
-- memif_write_word(i_memif, o_memif, addr, SOURCESIGNAL, done);

-- Die Laenge ist bei Speicherzugriffen Byte adressiert!
-- memif_read(i_ram, o_ram, i_memif, o_memif, SRC_ADDR std_logic_vector(31 downto 0);
--            dst_addr std_logic_vector(31 downto 0);
--            BYTES std_logic_vector(23 downto 0);
--            done);
-- memif_write(i_ram, o_ram, i_memif, o_memif,
--             src_addr : in std_logic_vector(31 downto 0),
--             dst_addr : in std_logic_vector(31 downto 0);
--             len      : in std_logic_vector(23 downto 0);
--             done);

