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

-- Type declarations
type snd_comp_header_msg_t is record
        
        source_addr     : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- memory address of the data source buffer
        src_len         : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- data length of the source buffer
        dest_addr       : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- memory address destination buffer
       
        opt_arg_addr    : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- memory address of optional arguments
        opt_arg_len     : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);  -- number of optional arguments
		
        f_step          : integer range 0 to 15;
end record;


------------------------------------------------------------
-- Functions and Procedure declarations
------------------------------------------------------------

------------------------------------------------------------

procedure snd_comp_get_header(
		signal i_osif   : in  i_osif_t;
		signal o_osif   : out o_osif_t;
		handle          : in  std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
		signal snd_comp_header : inout snd_comp_header_msg_t;
		variable done   : out boolean
		);

procedure snd_comp_init_header ( signal snd_comp_header : out snd_comp_header_msg_t );
        
------------------------------------------------------------

end soundgates_reconos_pkg;

package body soundgates_reconos_pkg is


procedure snd_comp_init_header ( signal snd_comp_header : out snd_comp_header_msg_t ) is 
begin
    snd_comp_header.source_addr  <= (others => '0');
    snd_comp_header.src_len      <= (others => '0');
    snd_comp_header.dest_addr    <= (others => '0');
    snd_comp_header.opt_arg_addr <= (others => '0');
    snd_comp_header.opt_arg_len  <= (others => '0');
    
    snd_comp_header.f_step <= 0;
end procedure snd_comp_init_header;

procedure snd_comp_get_header(
    signal i_osif   : in  i_osif_t;
    signal o_osif   : out o_osif_t;
    handle          : in  std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
    signal snd_comp_header : inout snd_comp_header_msg_t;
    variable done   : out boolean
    ) is 
    
    variable patially_done : boolean := False;
        
    begin
        case snd_comp_header.f_step is
            when 0 => 
                osif_mbox_get(i_osif, o_osif, handle, snd_comp_header.source_addr, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 1;
                end if;
            when 1 =>
                osif_mbox_get(i_osif, o_osif, handle, snd_comp_header.src_len, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 2;
                end if;
            when 2 =>
                osif_mbox_get(i_osif, o_osif, handle, snd_comp_header.dest_addr, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 3;
                end if;
            when 3 =>
                osif_mbox_get(i_osif, o_osif, handle, snd_comp_header.opt_arg_addr, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 4;
                end if;
            when 4 =>
                osif_mbox_get(i_osif, o_osif, handle, snd_comp_header.opt_arg_len, patially_done);
                
                if(patially_done) then
                    snd_comp_header.f_step <= 5;
                end if;
            when others =>            
                done := True;
                snd_comp_header.f_step <= 0;
         end case;
         
    end procedure snd_comp_get_header;
 
end package body soundgates_reconos_pkg;
