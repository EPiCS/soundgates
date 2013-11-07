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
        source_addr : std_logic_vector(C_FIFO_WIDTH - 1 downto 0);
		len         : std_logic_vector(15 downto 0);
		f_step      : integer range 0 to 15;
end record;


------------------------------------------------------------
-- Functions and Procedure declarations
------------------------------------------------------------

------------------------------------------------------------

procedure snd_comp_get_header(
		signal i_osif   : in  i_osif_t;
		signal o_osif   : out o_osif_t;
		handle          : in  std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
		signal snd_comp_header_msg_t inout snd_comp_header_msg_t;
		variable done   : out boolean
		);

------------------------------------------------------------

end soundgates_reconos_pkg;

procedure snd_comp_get_header(
    signal i_osif   : in  i_osif_t;
    signal o_osif   : out o_osif_t;
    handle          : in  std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
    signal snd_comp_header_msg_t inout snd_comp_header_msg_t;
    variable done   : out boolean
    ) is 
    
    variable done_read_scr_addr : boolean := False;
    variable done_read_len      : boolean := false;
    
    begin
        case snd_comp_header_msg_t.f_step is
            when 0 => 
                osif_mbox_get(i_osif, o_osif, C_MBOX_RECV, snd_comp_header_msg_t.source_addr, done_read_scr_addr);
                
                if(done_read_scr_addr) then
                    snd_comp_header_msg_t.f_step <= 1;
                end if;
            when 1 =>
                osif_mbox_get(i_osif, o_osif, C_MBOX_RECV, snd_comp_header_msg_t.len, done_read_len);
                
                if(done_read_len) then
                    snd_comp_header_msg_t.f_step <= 2;
                end if;

            when others =>            
                done := True;
                snd_comp_header_msg_t.f_step <= 0;
         end case;
         
    end procedure osif_mbox_get;

package body soundgates_reconos_pkg is


 
end soundgates_reconos_pkg;
