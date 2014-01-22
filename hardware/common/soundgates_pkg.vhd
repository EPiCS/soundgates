--
--	Package File Template
--
--	Purpose: This package defines supplemental types, subtypes, 
--		 constants, and functions 
--
--   To use any of the example code shown below, uncomment the lines and modify as necessary
--

library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;


package soundgates_pkg is


-- Constant declarations

constant SOUNDGATE_FIX_PT_SCALING : real := 27.0;

constant MAX_NCO_FREQUNCY : integer := 16000;


-- Type declarations

type Phase_Increment_Array is array(0 to MAX_NCO_FREQUNCY) of signed(31 downto 0);

type WAVEFORM_TYPE is ( SIN, SQUARE, SAWTOOTH, TRIANGLE);


-- Functions and Procedure declarations

function Precalculate_Phase_Increments (FPGA_FREQUENCY : integer) return Phase_Increment_Array;

function Precalculate_Cordic_Phase_Increments (FPGA_FREQUENCY : integer) return Phase_Increment_Array;

function Get_Cordic_Phase_Increment (FPGA_FREQUENCY, SIN_FREQUENCY : integer) return signed;

end soundcomponents;

package body soundcomponents is


function Get_Cordic_Phase_Increment (FPGA_FREQUENCY, SIN_FREQUENCY : integer) return signed is
	
	variable stepsize       : integer;
	variable phi_incr_real   : real;
   variable phi_incr_signed : signed(31 downto 0);
begin	
   if SIN_FREQUENCY > 0 then
      stepsize        := FPGA_FREQUENCY / SIN_FREQUENCY;
      phi_incr_real   := MATH_PI * 2.0 / real(stepsize);
      phi_incr_signed := to_signed(integer(real(phi_incr_real) * 2**SOUNDGATE_FIX_PT_SCALING), 32);
   else
      phi_incr_signed := to_signed(0, 32);
   end if;		
	
	return phi_incr_signed;
end Get_Cordic_Phase_Increment;


function Precalculate_Cordic_Phase_Increments (FPGA_FREQUENCY : integer) return Phase_Increment_Array is
	variable tmp 			: phase_increment_array;
	variable stepsize 	: integer;
	variable phi_offset 	: real;
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

---- Example 1
--  function <function_name>  (signal <signal_name> : in <type_declaration>  ) return <type_declaration> is
--    variable <variable_name>     : <type_declaration>;
--  begin
--    <variable_name> := <signal_name> xor <signal_name>;
--    return <variable_name>; 
--  end <function_name>;

---- Example 2
--  function <function_name>  (signal <signal_name> : in <type_declaration>;
--                         signal <signal_name>   : in <type_declaration>  ) return <type_declaration> is
--  begin
--    if (<signal_name> = '1') then
--      return <signal_name>;
--    else
--      return 'Z';
--    end if;
--  end <function_name>;

---- Procedure Example
--  procedure <procedure_name>  (<type_declaration> <constant_name>  : in <type_declaration>) is
--    
--  begin
--    
--  end <procedure_name>;
 
end soundgates_pkg;
