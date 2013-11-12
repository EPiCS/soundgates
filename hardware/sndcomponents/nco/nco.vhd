library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;
-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity nco is
	 generic(
		FPGA_FREQUENCY  : integer := 100_000_000;
		WAVEFORM        : WAVEFORM_TYPE := SIN
	 );
    Port ( 
            clk    : in  std_logic;           
            rst    : in  std_logic;
            ce     : in  std_logic;
            phase_offset : in signed(31 downto 0);
            phase_incr   : in signed(31 downto 0);
            data         : out signed(31 downto 0)
           );
end nco;

architecture Behavioral of nco is
		
	--------------------------------------------------------------------------------	
   -- Cordic related components and signals
   --------------------------------------------------------------------------------	
	component cordic
		generic ( pipeline_stages : integer := 24	);
		port ( 
        x       : in  signed(31 downto 0);
        y       : in  signed(31 downto 0);
        phi     : in  signed(31 downto 0);  -- 0 < phi < 2 * pi
        sin     : out signed(31 downto 0);	
        cos     : out signed(31 downto 0);
        clk     : in std_logic;	-- clock
        rst     : in std_logic;	-- reset
        ce      : in std_logic 	-- enable
		);
	end component cordic;
    
	
   -- Choose number of pipeline stages carefully:
   --  if number of pipeline stages is not power of 2 then an additional multiplier will be included
   --  this will affects timing and area consumption!
   --  also its not implemented yet
   
   constant cordic_pipeline_stages : integer := 16;    -- lower resolution (but high enough)
-- constant cordic_pipeline_stages : integer := 32;    -- very high resolution
   
    constant standard_cordic_offset : integer := integer(real(MATH_PI * 2.0 * 2 ** SOUNDGATE_FIX_PT_SCALING));
   
	constant cordic_x_init : signed(31 downto 0) := to_signed(integer(real(1.0  * 2**SOUNDGATE_FIX_PT_SCALING)),32);
	constant cordic_y_init : signed(31 downto 0) := to_signed(integer(real(0.0  * 2**SOUNDGATE_FIX_PT_SCALING)),32);

    signal cordic_phi_offset : signed(31 downto 0) := (others => '0'); 
    signal cordic_phi_incr   : signed(31 downto 0) := (others => '0'); 
	signal cordic_phi_acc 	 : signed(31 downto 0) := (others => '0'); 
	
    signal cordic_threshold  : signed(31 downto 0);
   --------------------------------------------------------------------------------
   --------------------------------------------------------------------------------
	
   --------------------------------------------------------------------------------	
   -- FOO related components and signals
   --------------------------------------------------------------------------------	
   
begin
		
	SIN_GENERATOR : if WAVEFORM = SIN generate
		
			CORDIC_INSTA : cordic
         generic map( 
            pipeline_stages => cordic_pipeline_stages
           )
			port map(
                    clk  => clk,
                    rst  => rst,
                    ce   => ce,
                    x    => cordic_x_init,
                    y    => cordic_y_init,
                    phi  => cordic_phi_acc,
                    sin  => data,
                    cos  => open );
         
         
         THRESHOLD_PROC_GEN_16 : if cordic_pipeline_stages = 16 generate
            CORDIC_THRESHOLD_PROCESS : process(phase_incr)            
            begin
               -- calculate the threshold depending on the number of pipeline stages in the cordic            
               cordic_threshold <= to_signed(standard_cordic_offset - to_integer(shift_left(phase_incr, 4)), 32);
            end process;
         end generate THRESHOLD_PROC_GEN_16;
         
         
         THRESHOLD_PROC_GEN_32 : if cordic_pipeline_stages = 32 generate
            CORDIC_THRESHOLD_PROCESS : process(phase_incr)            
            begin
               -- calculate the threshold depending on the number of pipeline stages in the cordic            
               cordic_threshold <= to_signed(standard_cordic_offset - to_integer(shift_left(phase_incr, 5)), 32);
            end process;
         end generate THRESHOLD_PROC_GEN_32;      
         
         
		PHASE_STIMULIS_PROCESS : process(clk)                  
        begin
            if rising_edge(clk) then
            
               cordic_phi_acc <= cordic_phi_acc + phase_incr;
               
               if cordic_phi_offset /= phase_offset then -- only update on change               
                    cordic_phi_acc      <= phase_offset;
                    cordic_phi_offset   <= phase_offset;
               end if;
                                          
               if cordic_phi_acc >= cordic_threshold then
                  cordic_phi_acc <= (others => '0');
               end if;
            end if;
		end process;
	end generate SIN_GENERATOR;
   
   --------------------------------------------------------------------------------	
   
--   SQUARE_GENERATOR  : if WAVEFORM = SQUARE generate
--   
--   			SQUARE_INSTA : square
--         	port map(                
--                    clk     => clk, 
--                    ce      => ce,
--                    incr    => phase_incr, 
--                    offset  => phase_offset,
--                    sq      => data );
-- 
--   end generate SQUARE_GENERATOR;
--   
--   --------------------------------------------------------------------------------	
--   
--   TRIANGLE_GENERATOR  : if WAVEFORM = TRIANGLE generate
--   
--   			TRIANGLE_INSTA : square
--         	port map(                
--                    clk     => clk, 
--                    ce      => ce,
--                    incr    => phase_incr, 
--                    offset  => phase_offset,
--                    tri     => data );   
-- 
--   end generate TRIANGLE_GENERATOR;
--      
--   --------------------------------------------------------------------------------	
--   
--   SAWTOOTH_GENERATOR  : if WAVEFORM = SAWTOOTH generate
--      
--   			SAWTOOTH_INSTA : square
--         	port map(                
--                    clk     => clk, 
--                    ce      => ce,
--                    incr    => phase_incr, 
--                    offset  => phase_offset,
--                    saw     => data );   
-- 
--   end generate SAWTOOTH_GENERATOR;

   --------------------------------------------------------------------------------   
   
end Behavioral;

