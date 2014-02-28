--  ____                        _             _            
-- / ___|  ___  _   _ _ __   __| | __ _  __ _| |_ ___  ___ 
-- \___ \ / _ \| | | | '_ \ / _` |/ _` |/ _` | __/ _ \/ __|
--  ___) | (_) | |_| | | | | (_| | (_| | (_| | ||  __/\__ \
-- |____/ \___/ \__,_|_| |_|\__,_|\__, |\__,_|\__\___||___/
--                                |___/                    
--
-- ======================================================================
--
--   title:        VHDL Package - soundgates_reconos_pkg
--
--   project:      PG-Soundgates
--   author:       Lukas Funke, University of Paderborn
--
--   description:  ReconOS extension for the integration of sound components
--
-- ======================================================================



library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.ALL;

library reconos_v3_00_c;
use reconos_v3_00_c.reconos_pkg.all;

package soundgates_reconos_pkg is

-- Constant declarations

constant MAX_HWT_ARGS : integer := 32;

constant DWORD_WIDTH  : integer := 32;
constant ADDR_WIDTH   : integer := 32;

-- Type declarations
type snd_comp_header_msg_t is record
        base_addr       : std_logic_vector(31 downto 0);
        source_addr     : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- memory address of the data source buffer
        src_len         : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- data length of the source buffer
        dest_addr       : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- memory address destination buffer
       
        opt_arg_addr    : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- memory address of optional arguments
        opt_arg_len     : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- number of optional arguments
		
        f_step          : integer range 0 to 15;
end record;


type hwtargs_t is array (0 to MAX_HWT_ARGS - 1) of std_logic_vector(DWORD_WIDTH - 1 downto 0);

type hwtio_t is record    
    base_addr : std_logic_vector(DWORD_WIDTH - 1 downto 0);
    f_step    : natural; 
    argptr    : unsigned(4 downto 0);
    argv      : hwtargs_t;   
end record;

------------------------------------------------------------
-- Functions and Procedure declarations
------------------------------------------------------------

------------------------------------------------------------

procedure snd_comp_get_header(
		signal i_osif   : in  i_osif_t;
		signal o_osif   : out o_osif_t;
		signal i_memif  : in  i_memif_t;
        signal o_memif  : out o_memif_t;
		signal snd_comp_header : inout snd_comp_header_msg_t;
		variable done   : out boolean
		);

procedure snd_comp_init_header ( signal snd_comp_header : out snd_comp_header_msg_t );

procedure hwtio_init( signal   hwt_args : inout hwtio_t );

procedure get_hwt_args(
   		signal   i_osif   : in    i_osif_t;
		signal   o_osif   : out   o_osif_t;
		signal   i_memif  : in    i_memif_t;
        signal   o_memif  : out   o_memif_t;
        signal   hwt_args : inout hwtio_t;
        constant argc     : in    integer;
   		variable done     : out   boolean
        );

-- Reverse a std_logic_vector   (to <--> downto)
function reverse_vector (a: in std_logic_vector) return std_logic_vector;

------------------------------------------------------------

end soundgates_reconos_pkg;

package body soundgates_reconos_pkg is
    
    function reverse_vector (a: in std_logic_vector) return std_logic_vector is
        variable result: std_logic_vector(a'RANGE);
        alias aa: std_logic_vector(a'REVERSE_RANGE) is a;
    begin
        for i in aa'RANGE loop
            result(i) := aa(i);
        end loop;
        return result;
    end; -- function reverse_any_vector


    procedure hwtio_init( signal   hwt_args : inout hwtio_t ) is

    begin    
        hwt_args.f_step     <= 0;
        hwt_args.base_addr  <= (others => '0');
        hwt_args.argptr     <= (others => '0');
        hwt_args.argv       <= (others => (others => '0'));
    end procedure hwtio_init;

    procedure get_hwt_args(
   		signal   i_osif   : in    i_osif_t;
		signal   o_osif   : out   o_osif_t;
		signal   i_memif  : in    i_memif_t;
        signal   o_memif  : out   o_memif_t;
        signal   hwt_args : inout hwtio_t;
        constant argc     : in    integer;
   		variable done     : out   boolean
        ) is
    variable patially_done : boolean := False;
    
    begin
        done := false;
        case hwt_args.f_step is
                  
            when 0 => 
               -- get header address
               osif_get_init_data(i_osif, o_osif, hwt_args.base_addr, patially_done);
                    
               if (patially_done) then
                hwt_args.f_step <= 1;
               end if;
            when 1 =>   -- argc + 1
                if (hwt_args.argptr = to_unsigned(argc, 5)) then
                    hwt_args.f_step <= 2;
                else
                    memif_read_word(i_memif, o_memif, 
                                    std_logic_vector(unsigned(hwt_args.base_addr) + (SHIFT_LEFT(hwt_args.argptr, 2))), 
                                    hwt_args.argv(to_integer(hwt_args.argptr)) , patially_done);
                                    
                    if (patially_done) then                    
                        hwt_args.argptr <= hwt_args.argptr + 1;
                    end if;
                end if;            
            when others =>
                done := True;
                hwt_args.f_step <= 0;
        end case;

    end procedure get_hwt_args;


procedure snd_comp_init_header ( signal snd_comp_header : out snd_comp_header_msg_t ) is 
begin
    snd_comp_header.source_addr  <= (others => '0');
    snd_comp_header.src_len      <= (others => '0');
    snd_comp_header.dest_addr    <= (others => '0');
    snd_comp_header.opt_arg_addr <= (others => '0');
    
    snd_comp_header.f_step <= 0;
    end procedure snd_comp_init_header;

    procedure snd_comp_get_header(
        signal i_osif   : in  i_osif_t;
        signal o_osif   : out o_osif_t;
        signal i_memif  : in  i_memif_t;
        signal o_memif  : out o_memif_t;
        signal snd_comp_header : inout snd_comp_header_msg_t;
        variable done   : out boolean
    ) is 
    
    variable patially_done : boolean := False;
    
        
    begin
        case snd_comp_header.f_step is
            when 0 => 
                -- get header address
                osif_get_init_data(i_osif, o_osif, snd_comp_header.base_addr, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 1;
                end if;
            when 1 =>
                memif_read_word(i_memif, o_memif, snd_comp_header.base_addr, snd_comp_header.source_addr, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 2;
                end if;
            when 2 =>
                memif_read_word(i_memif, o_memif, std_logic_vector(unsigned(snd_comp_header.base_addr)+4), snd_comp_header.src_len, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 3;
                end if;
            when 3 =>
                memif_read_word(i_memif, o_memif, std_logic_vector(unsigned(snd_comp_header.base_addr)+8), snd_comp_header.dest_addr, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 4;
                end if;
            when 4 =>
                memif_read_word(i_memif, o_memif, std_logic_vector(unsigned(snd_comp_header.base_addr)+12), snd_comp_header.opt_arg_addr, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 5;
                end if;
            when others =>            
                done := True;
                snd_comp_header.f_step <= 0;
         end case;
         
    end procedure snd_comp_get_header;
 
end package body soundgates_reconos_pkg;
