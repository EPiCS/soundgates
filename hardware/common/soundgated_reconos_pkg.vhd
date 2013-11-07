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

------------------------------------------------------------
-- Functions and Procedure declarations
------------------------------------------------------------

------------------------------------------------------------

procedure snd_comp_get_header(
		signal i_osif   : in  i_osif_t;
		signal o_osif   : out o_osif_t;
		handle          : in  std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
		signal srd_addr : out std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
		signal len      : out std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
		variable done   : out boolean
		);

------------------------------------------------------------

end soundgates_reconos_pkg;

procedure snd_comp_get_header(
    signal i_osif   : in  i_osif_t;
    signal o_osif   : out o_osif_t;
    handle          : in  std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
    signal srd_addr : out std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
    signal len      : out std_logic_vector(C_OSIF_WIDTH - 1 downto 0);
    variable done   : out boolean
    ) is 
    
    variable done_read_scr_addr;
    variable done_read_len;
    
    begin
        -- not yet implemented        
    end procedure osif_mbox_get;

package body soundgates_reconos_pkg is


 
end soundgates_reconos_pkg;
