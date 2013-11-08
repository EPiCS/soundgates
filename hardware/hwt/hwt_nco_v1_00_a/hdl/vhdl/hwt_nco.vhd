library ieee;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

library proc_common_v3_00_a;
use proc_common_v3_00_a.proc_common_pkg.all;

library work;
use soundgates_pkg.all
use soundgates_reconos_pkg.all

entity hwt_nco is
    generic(
		SND_COMP_CLK_FREQ : integer := 100_000_000;
		SND_COMP_NCO_TPYE : integer := 0
	);
   port (
		-- OSIF FIFO ports
		OSIF_FIFO_Sw2Hw_Data    : in  std_logic_vector(31 downto 0);
		OSIF_FIFO_Sw2Hw_Fill    : in  std_logic_vector(15 downto 0);
		OSIF_FIFO_Sw2Hw_Empty   : in  std_logic;
		OSIF_FIFO_Sw2Hw_RE      : out std_logic;

		OSIF_FIFO_Hw2Sw_Data    : out std_logic_vector(31 downto 0);
		OSIF_FIFO_Hw2Sw_Rem     : in  std_logic_vector(15 downto 0);
		OSIF_FIFO_Hw2Sw_Full    : in  std_logic;
		OSIF_FIFO_Hw2Sw_WE      : out std_logic;

		-- MEMIF FIFO ports
		MEMIF_FIFO_Hwt2Mem_Data    : out std_logic_vector(31 downto 0);
		MEMIF_FIFO_Hwt2Mem_Rem     : in  std_logic_vector(15 downto 0);
		MEMIF_FIFO_Hwt2Mem_Full    : in  std_logic;
		MEMIF_FIFO_Hwt2Mem_WE      : out std_logic;

		MEMIF_FIFO_Mem2Hwt_Data    : in  std_logic_vector(31 downto 0);
		MEMIF_FIFO_Mem2Hwt_Fill    : in  std_logic_vector(15 downto 0);
		MEMIF_FIFO_Mem2Hwt_Empty   : in  std_logic;
		MEMIF_FIFO_Mem2Hwt_RE      : out std_logic;

		HWT_Clk   : in  std_logic;
		HWT_Rst   : in  std_logic
    );
end hwt_nco;

architecture Behavioral of hwt_nco is

    ----------------------------------------------------------------
    -- Subcomponent declarations
    ----------------------------------------------------------------
    
    component nco is
	generic(
		FPGA_FREQUENCY  : integer       := 100_000_000;
		WAVEFORM        : WAVEFORM_TYPE := SIN
	);
    Port ( 
            clk          : in  std_logic;           
            rst          : in  std_logic;
            ce           : in  std_logic;
            phase_offset : in  signed(31 downto 0);
            phase_incr   : in  signed(31 downto 0);
            data         : out signed(31 downto 0)
           );
    end component nco;





    signal clk   : std_logic;
	signal rst   : std_logic;

-- ReconOS Stuff
    signal i_osif   : i_osif_t;
    signal o_osif   : o_osif_t;
    signal i_memif  : i_memif_t;
    signal o_memif  : o_memif_t;
    
    signal i_ram    : i_ram_t;
    signal o_ram    : o_ram_t;
-- /ReconOS Stuff

    type STATE_TYPE is (STATE_INIT, STATE_REFRESH_INPUTS, STATE_PROCESS, STATE_WAIT);
    signal state    : STATE_TYPE;
    
    ----------------------------------------------------------------
    -- Common sound component signals
    ----------------------------------------------------------------
    signal snd_com_header : snd_comp_header_msg_t;
    
    signal sample_size        : unsigned(15 downto 0);
    
    signal arg_check_interval : unsigned(15 downto 0);      -- TODO: define what that means
    signal arg_check_interval_acc : unsigned(15 downto 0);  -- TODO: define what that means
    
    ----------------------------------------------------------------
    -- Component dependent signals
    ----------------------------------------------------------------
    signal nco_ce        : std_logic;


    signal phase_offset  : signed(31 downto 0); 

    signal phase_incr    : signed(31 downto 0);   

    signal nco_data      : signed(31 downto 0);
begin
    
    clk <= HWT_Clk;
	rst <= HWT_Rst;

-- Initialized sound component header	
snc_com_init_header(snd_com_header);

    
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


    nco_inst : nco
    generic map(
		FPGA_FREQUENCY  => SND_COMP_CLK_FREQ
		WAVEFORM        => WAVEFORM_TYPE'val(SND_COMP_NCO_TPYE)
	);
    Port map( 
            clk          => clk,
            rst          => rst,
            ce           => nco_ce,
            phase_offset => phase_offset,
            phase_incr   => phase_incr,
            data         => nco_data
            );
           
    process (clk) is
        variable done : boolean;

    begin
        if rst = '1' then
                    
            osif_reset(o_osif);
			memif_reset(o_memif);

            
            snc_com_init_header(snd_com_header);
            
            state       <= STATE_INIT;
            sample_size <= unsigned(0, 16);
            
            done := False;
              
        elsif rising_edge(clk) then
            case state is
            
            -- INIT State gets the address of the header struct
            when STATE_INIT =>
                
                -- init data is fine, but can only be one word?
                snd_comp_get_header(i_osif, o_osif, C_MBOX_RECV, snd_com_header, done);
                
                if done then
                    -- Initialize your signals
                    state <= STATE_REFRESH;
                    sample_size <= unsigned(0, 16);

                end if;
                
            when STATE_REFRESH_INPUTS =>
                -- Read and assign optional arguments
                
                memif_read_word(i_memif, o_memif, header_address, TARGETSIGNAL, done);
                if done then
                    state <= STATE_PROCESS;
                end if;

            when STATE_PROCESS =>
                if sample_size > 0 then
                    state <= STATE_PROCESS;
                    
                    if sample_size = arg_check_interval_acc then                        
                        state <= STATE_REFRESH_INPUTS                    
                    end if;
                    
                else
                    state <= STATE_EXIT;
                end if;
                
                arg_check_interval_acc  <= arg_check_interval_acc + arg_check_interval;
                sample_size             <= sample_size - 1;
                
                
                -- case calc_state is
                
                    -- when 0 =>
                    -- -- Read your data i.e.
                    -- memif_read_word(i_memif, o_memif, source_address + pSourceOffset, DATASIGNAL, done);
                    -- if(done) then
                        -- calc_state     := calc_state + 1;
                        -- source_offset  := source_offset + 4;
                    -- end if;
                
                    -- when 1 =>
                    -- -- Manipulate DATASIGNAL ie. CALCLULATED_DATA <= DATASIGNAL +1;
                    -- calc_state := calc_state + 1;
                
                    -- when 2 =>
                    -- -- Write result
                    -- memif_write_word(i_memif, o_memif, target_address + target_offset, CALCLULATED_DATA, done);
                    -- if done then
                        -- calc_state    := 0;
                        -- target_offset <= target_offset + 4;
                        -- state         <= STATE_CHECK;
                    -- end if;    
                
                -- end case;
            
            --when STATE_WAIT =>    
            -- EXIT OR WAIT FOR RESTART?
            --    target_offset <= 0;
            --    source_offset <= 0;           
            
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

