
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.MATH_REAL.ALL;

USE std.textio.all;

library soundgates_v1_00_a;
use soundgates_v1_00_a.soundgates_common_pkg.all;
--use soundgates_v1_00_a.soundgates_reconos_pkg.all;

ENTITY fir_transposed_tb IS
generic(
    stim_filename   : string := "sound_conv.out";
    output_filename : string := "filtered.out"
);
END fir_transposed_tb;

ARCHITECTURE behavior OF fir_transposed_tb IS 
    
    constant FIR_ORDER : integer := 28;
   
    -- Component Declaration
    COMPONENT fir
        generic(
            FIR_ORDER   : integer := 9  --> 10 coefficients
        );
        port(
            clk          : in  std_logic;
            rst          : in  std_logic;
            ce           : in  std_logic;
            coefficients : in  mem16(FIR_ORDER downto 0);
            x_in         : in  signed(23 downto 0);
            y_out        : out signed(23 downto 0)
        );
    END COMPONENT;
 
    signal clk   : std_logic    := '0';
    signal rst   : std_logic    := '0';
    signal ce    : std_logic    := '1';

    constant scaling : real := 14.0;
 
    constant coefficients : mem16(FIR_ORDER downto 0) := ( 
        to_signed(-22, 16),
        to_signed(-20, 16),
        to_signed(-15, 16),
        to_signed(1, 16),
        to_signed(38, 16),
        to_signed(108, 16),
        to_signed(217, 16),
        to_signed(367, 16),
        to_signed(553, 16),
        to_signed(763, 16),
        to_signed(979, 16),
        to_signed(1180, 16),
        to_signed(1343, 16),
        to_signed(1449, 16),
        to_signed(1486, 16),
        to_signed(1449, 16),
        to_signed(1343, 16),
        to_signed(1180, 16),
        to_signed(979, 16),
        to_signed(763, 16),
        to_signed(553, 16),
        to_signed(367, 16),
        to_signed(217, 16),
        to_signed(108, 16),
        to_signed(38, 16),
        to_signed(1, 16),
        to_signed(-15, 16),
        to_signed(-20, 16),
        to_signed(-22, 16)
    ); 

    signal x_i  : signed(23 downto 0);
    signal y_i  : signed(23 downto 0);
    
    file stimulus   : TEXT is in  stim_filename;
    file outputfile : TEXT is out output_filename;
    -- Clock period definitions
    constant clk_period : time := 10 ns;
    
BEGIN

  -- Component Instantiation
    uut: fir 
    generic map (
        FIR_ORDER => FIR_ORDER
    )
    PORT MAP(    
        clk             => clk,
        rst             => rst,
        ce              => ce,
        coefficients    => coefficients,
        x_in            => x_i,
        y_out           => y_i
    );
    
    clk_process : process
    begin
        clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
    end process;
    
    
    --  Stimulus process
    stim_process : PROCESS
        variable sound_in_l  : LINE;
        variable sound_out_l : LINE;
        variable sample_in   : integer;
        variable sample_out  : integer;
        variable tmp         : std_logic_vector(31 downto 0);
    BEGIN
        rst <= '1';
        
        wait for 100 ns; -- wait until global set/reset completes
        
        rst <= '0';
        
        
        while not endfile(stimulus) loop
            -- read sound data from input file 
            readline(stimulus, sound_in_l);
            read(sound_in_l , sample_in);
            
            tmp         := std_logic_vector(to_signed(sample_in, 32));
            x_i         <= signed(tmp(31 downto 8));
            
            if y_i(23) = '1' then
                sample_out  := to_integer(signed(std_logic_vector(y_i) & X"11"));
            else
                sample_out  := to_integer(signed(std_logic_vector(y_i) & X"00"));
            end if;
            
            write(sound_out_l, sample_out);
            writeline(outputfile, sound_out_l);
            wait until CLK = '1';
        end loop;
        -- Add user defined stimulus here

        wait; -- will wait forever
    END PROCESS stim_process;
  
  
  --  End Test Bench
END;
