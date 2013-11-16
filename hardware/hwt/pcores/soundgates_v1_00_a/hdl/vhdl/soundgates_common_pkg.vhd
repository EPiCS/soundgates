--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
-- ======================================================================
--
--   title:        VHDL Package - soundgates_common_pkg
--
--   project:      PG-Soundgates
--   author:       Lukas Funke, University of Paderborn
--
--   description:  Common functions, declaration, constants for sound 
--                 processing components
--
-- ======================================================================

library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;


package soundgates_common_pkg is


-- Constant declarations

constant SOUNDGATE_FIX_PT_SCALING : real := 28.0;

constant MAX_NCO_FREQUNCY : integer := 16000;


-- Type declarations

type Phase_Increment_Array is array(0 to MAX_NCO_FREQUNCY) of signed(31 downto 0);

type WAVEFORM_TYPE is ( SIN, SQU, SAW, TRI);

type NOISE_TYPE is ( WHITE, PINK, GREY );

------------------------------------------------------------
-- Functions and Procedure declarations
------------------------------------------------------------

------------------------------------------------------------

function Precalculate_Phase_Increments (FPGA_FREQUENCY : integer) return Phase_Increment_Array;

function Precalculate_Cordic_Phase_Increments (FPGA_FREQUENCY : integer) return Phase_Increment_Array;

function Get_Cordic_Phase_Increment (FPGA_FREQUENCY, SIN_FREQUENCY : integer) return signed;

------------------------------------------------------------

end package soundgates_common_pkg;

package body soundgates_common_pkg is


function Get_Cordic_Phase_Increment (FPGA_FREQUENCY, SIN_FREQUENCY : integer) return signed is
	
    variable stepsize        : integer;
    variable phi_incr_real   : real;
    variable phi_incr_signed : signed(31 downto 0);
begin	
   if SIN_FREQUENCY > 0 then
      --stepsize        := FPGA_FREQUENCY / SIN_FREQUENCY;
      --phi_incr_real   := MATH_PI * 2.0 / real(stepsize);
      --phi_incr_signed := to_signed(integer(real(phi_incr_real) * 2**SOUNDGATE_FIX_PT_SCALING), 32);
      
      phi_incr_real   := MATH_PI * 2.0 * real(SIN_FREQUENCY)  / 96000.0;
      phi_incr_signed := to_signed(integer(real(phi_incr_real) * 2**SOUNDGATE_FIX_PT_SCALING), 32);
   else
      phi_incr_signed := to_signed(0, 32);
   end if;		
	
	return phi_incr_signed;
end Get_Cordic_Phase_Increment;


function Precalculate_Cordic_Phase_Increments (FPGA_FREQUENCY : integer) return Phase_Increment_Array is
	variable tmp 		: phase_increment_array;
	variable stepsize 	: integer;
	variable phi_offset : real;
begin	
	for i in 0 to MAX_NCO_FREQUNCY loop
		if i > 0 then
			stepsize :=	FPGA_FREQUENCY / i;
			
			phi_offset := MATH_PI * 2.0 / real(stepsize);
			
			tmp(i) := to_signed(integer(real(phi_offset) * 2**SOUNDGATE_FIX_PT_SCALING), 32);
			
		else
			tmp(i) := to_signed(0, 32);
		end if;		
	end loop;
	return tmp;
end Precalculate_Cordic_Phase_Increments;


function Precalculate_Phase_Increments (FPGA_FREQUENCY : integer) return Phase_Increment_Array is
	variable tmp : phase_increment_array;
begin	
	for i in 0 to MAX_NCO_FREQUNCY loop
		if i > 0 then
			tmp(i) := to_signed(FPGA_FREQUENCY / i, 32);
		else
			tmp(i) := to_signed(0, 32);
		end if;		
	end loop;
	return tmp;
end Precalculate_Phase_Increments;

end package body soundgates_common_pkg;
