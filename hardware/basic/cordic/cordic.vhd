--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL module - cordic.vhd
--
--   project:      PG-Soundgates
--   author:       Lukas Funke, University of Paderborn
--
--   description:  Cordic top level entity
--
-- ======================================================================

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.math_real.all;
use IEEE.NUMERIC_STD.ALL;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;

entity cordic is
generic (
	pipeline_stages : integer := 24
	);
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

end cordic;

architecture Behavioral of cordic is
	
	type pipeline_array is array (0 to pipeline_stages + 1) of signed(31 downto 0);
	
	signal x_pipeline : pipeline_array;
	signal y_pipeline : pipeline_array;
	signal z_pipeline : pipeline_array;
	
	constant cordic_gain        : real := 0.60725293500888;
	constant cordic_gain_scaled : signed(31 downto 0) 	:= to_signed(integer(real(cordic_gain * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
	
	constant q1 : signed(31 downto 0) := to_signed(integer(real(MATH_PI / 2.0 * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
	constant q2 : signed(31 downto 0) := to_signed(integer(real(MATH_PI * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
	constant q3 : signed(31 downto 0) := to_signed(integer(real(3.0 * MATH_PI / 2.0* 2**SOUNDGATE_FIX_PT_SCALING)), 32);
	constant q4 : signed(31 downto 0) := to_signed(integer(real(2.0 * MATH_PI * 2**SOUNDGATE_FIX_PT_SCALING)), 32);
	
	signal sin_i : signed(63 downto 0) := (others => '0');
	signal cos_i : signed(63 downto 0) := (others => '0');
	
	signal cordic_out_x : signed(31 downto 0) := (others => '0');
	signal cordic_out_y : signed(31 downto 0) := (others => '0');
	
	signal x_i : signed(31 downto 0) 	:= (others => '0');
	signal y_i : signed(31 downto 0) 	:= (others => '0');
	signal phi_i : signed(31 downto 0) 	:= (others => '0');
begin
	
   
   -- rotates the vector (x,y) according to the quadrant in the unit circule
	VEC_ROTATE_PROCESS : process(clk)
	begin        
        if rising_edge(clk) then
            if rst = '1' then
            
            x_i   <= (others => '0');
            y_i   <= (others => '0');
            phi_i <= (others => '0');        
            
            elsif ce = '1' then
                if phi < q1 then
                    x_i <= x;
                    y_i <= y;
                    phi_i <= phi;                
                elsif phi < q2 then
                    phi_i <= phi + (-q1);
                    x_i 	<= (y);
                    y_i 	<= (x);
                elsif phi < q3 then
                    x_i 	<= (-x);
                    y_i 	<= (-y);
                    phi_i <= phi + (-q2);
                elsif phi < q4 then
                    x_i 	<= (-y);
                    y_i 	<= (-x);
                    phi_i <= phi + (-q3);                
                end if;
            end if; 
            
         end if;
	end process;
	
	x_pipeline(0) <= x_i;
	y_pipeline(0) <= y_i;
	z_pipeline(0) <= phi_i;
	
   -- instantiate cordic pipeline   
	CORDIC_PIPELINE : for i in 0 to pipeline_stages generate		
		PIPELINE_STAGE : entity work.cordic_stage
			generic map(
				stage => i,
				alpha => real(2**(real(-i)))
				)
			port map(
				clk => clk,
				rst => rst,
				ce  => ce,
				
				x => x_pipeline(i),
				y => y_pipeline(i),
				z => z_pipeline(i),
				
				x_n => x_pipeline(i + 1),
				y_n => y_pipeline(i + 1),
				z_n => z_pipeline(i + 1)
			);		
	end generate;
	
	REGISTER_OUT_PROCESS : process(clk)
	begin
		if rising_edge(clk) then
            if rst = '1' then
                cordic_out_x <= (others => '0');
                cordic_out_y <= (others => '0');
            elsif ce = '1' then 
                cordic_out_x <= x_pipeline(pipeline_stages + 1);
                cordic_out_y <= y_pipeline(pipeline_stages + 1);
            end if;
		end if;
	
	end process;
	
	GAIN_PROCESS : process (clk)	
	begin			
        if rising_edge(clk) then
            if rst = '1' then
                sin_i <= ( others => '0');
                cos_i <= ( others => '0');
            elsif ce = '1' then 
                sin_i <= shift_right(cordic_out_y * cordic_gain_scaled, integer(SOUNDGATE_FIX_PT_SCALING));
                cos_i <= shift_right(cordic_out_x * cordic_gain_scaled, integer(SOUNDGATE_FIX_PT_SCALING));
            end if;
		end if;
	end process;	
	sin <= RESIZE(sin_i,32);
	cos <= RESIZE(cos_i,32);
	
end Behavioral;

	