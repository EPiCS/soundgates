--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - nco.vhd
--
--   project:      PG-Soundgates
--   author:       Lukas Funke, University of Paderborn
--
--   description:  Numeric controlled oscillator top level entity
--
-- ======================================================================
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity nco is
	 generic(
		FPGA_FREQUENCY : integer := 100_000_000;
		WAVEFORM       : WAVEFORM_TYPE := SIN
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
        phi     : in  signed(31 downto 0);  -- 0 < phi < 2 * pi
        sin     : out signed(31 downto 0);	
        cos     : out signed(31 downto 0);
        clk     : in std_logic;	-- clock
        rst     : in std_logic;	-- reset
        ce      : in std_logic 	-- enable
		);
	end component cordic;
    
    component sawtooth
    port(
        clk     : in std_logic;
        ce      : in std_logic;
        rst     : in  std_logic;
        incr    : in signed(31 downto 0);
        offset  : in signed(31 downto 0);
        saw     : out signed(31 downto 0)
    );
    end component sawtooth;
    
    component square 
    port(                
        clk     : in  std_logic;
        ce      : in  std_logic;
        rst     : in  std_logic;
        incr    : in  signed(31 downto 0); 
        offset  : in  signed(31 downto 0);  
        duty_on : in  signed(31 downto 0);  
        duty_off: in  signed(31 downto 0);
        sq      : out signed(31 downto 0)
    );
    end component square;
    
    component triangle
    port(                
        clk     : in  std_logic;
        ce      : in  std_logic;
        rst     : in  std_logic;
        incr    : in  signed(31 downto 0); 
        offset  : in  signed(31 downto 0);  
        tri     : out signed(31 downto 0)
    );
    end component triangle;
    
    constant cordic_pipeline_stages : integer := 16;
   
    constant standard_cordic_offset : integer := integer(real(MATH_PI * 2.0 * 2 ** SOUNDGATE_FIX_PT_SCALING));
  
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
            phi  => cordic_phi_acc,
            sin  => data,
            cos  => open );
         
		PHASE_STIMULIS_PROCESS : process(clk, rst)                  
        begin
            if rst = '1' then
                cordic_phi_acc <= (others => '0');
            elsif rising_edge(clk) then
               if ce = '1' then
                if (cordic_phi_acc + phase_incr) > standard_cordic_offset then
                    cordic_phi_acc <=  phase_incr - (standard_cordic_offset - cordic_phi_acc);
                else
                    cordic_phi_acc <= cordic_phi_acc + phase_incr;
                end if;
               end if;
            end if;
		end process;

		
	end generate SIN_GENERATOR;
   
   --------------------------------------------------------------------------------	
   
--   SQUARE_GENERATOR  : if WAVEFORM = SQU generate
--   
--   			SQUARE_INSTA : square
--         	port map(                
--                    clk     => clk, 
--                    ce      => ce,
--                    rst     => rst,
--                    incr    => phase_incr, 
--                    offset  => phase_offset,  
--                    duty_on => duty_on,
--                    duty_off=> duty_off,
--                    sq      => data );
-- 
--   end generate SQUARE_GENERATOR;
   
   --------------------------------------------------------------------------------	
   
   TRIANGLE_GENERATOR  : if WAVEFORM = TRI generate
   
   			TRIANGLE_INSTA : triangle
         	port map(                
                    clk     => clk, 
                    ce      => ce,
                    rst     => rst,
                    incr    => phase_incr, 
                    offset  => phase_offset,
                    tri     => data );   
 
   end generate TRIANGLE_GENERATOR;
      
   --------------------------------------------------------------------------------	
   
   SAWTOOTH_GENERATOR  : if WAVEFORM = SAW generate
      
   			SAWTOOTH_INSTA : sawtooth
         	port map(                
                    clk     => clk, 
                    ce      => ce,
                    rst     => rst,
                    incr    => phase_incr, 
                    offset  => phase_offset,
                    saw     => data );   
 
   end generate SAWTOOTH_GENERATOR;

   --------------------------------------------------------------------------------   
   
end Behavioral;