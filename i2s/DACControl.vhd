----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    16:23:10 06/10/2013 
-- Design Name: 
-- Module Name:    DACControl - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity DACControl is
	Generic(		
		RefClkFrequency : integer := 200_000_000; --Reference clock
		SampleRate		 : integer := 200_000	-- Sample rate
	);
	Port(
			clk : in std_logic;
			rst : in std_logic;
			
			data : in std_logic_vector(11 downto 0);
			op_mode : in std_logic_vector(1 downto 0);
			
			busy : out std_logic;
			
			dac_clk  : out std_logic;
			dac_data : out std_logic;
			dac_sync : out std_logic
	);

end DACControl;

architecture Behavioral of DACControl is
	
	constant DacDepth : integer := 12;
	
	constant sclk_freq : integer := SampleRate * (DacDepth + 4);
	
	constant sclk_prescaler : integer := integer(real(RefClkFrequency) / real(sclk_freq) / 2.0);
		
	type DAC_STATE is (IDLE, PREPARE, SYNC, TRANSMIT);
	signal current_state, next_state : DAC_STATE;
	
	signal sclk_acc : unsigned(15 downto 0) := (others => '0');
	
	signal dac_data_shift : std_logic_vector(15 downto 0) := "0000000000000000";
	signal dac_data_count : unsigned(5 downto 0) := (others => '0');
	signal busy_int : std_logic  := '0';
	
	signal sync_int  : std_logic := '1';
	signal sclk_int  : std_logic := '0';
	signal dac_sd_int : std_logic := '0';	
	
	signal delay_reg_sclk_falling : std_logic;
	signal delay_reg_sclk_rising : std_logic;
	
	signal sclk_falling : std_logic;	-- generates a tick on a falling edge of sclk
	signal sclk_rising  : std_logic;
	signal tsucl_hold : unsigned(1 downto 0) := "00";
begin
	
	--------------------------------------------------------------
	--------------------------------------------------------------
	sclk_rising_gen: process (clk, rst)
	begin
		if rst = '1' then
		
		elsif rising_edge(clk) then
			delay_reg_sclk_rising <= sclk_int;		
		end if;
		
	end process;
	
	sclk_rising <= (not delay_reg_sclk_rising) and sclk_int;
	--------------------------------------------------------------
	--------------------------------------------------------------
	sclk_falling_gen: process (clk, rst)
	begin
		if rst = '1' then
		
		elsif rising_edge(clk) then
			delay_reg_sclk_falling <= sclk_int;		
		end if;
		
	end process;
	
	sclk_falling <= (delay_reg_sclk_falling) and not sclk_int;
	
	--------------------------------------------------------------
	--------------------------------------------------------------
		
	sclk_gen : process(clk)
	begin	
		if rising_edge(clk) then
			if sclk_acc = sclk_prescaler then
				sclk_acc <= (others => '0');
				sclk_int <= not sclk_int;
			else
				sclk_acc <= sclk_acc + 1;
			end if;
		end if;		
	end process;
	--------------------------------------------------------------
	--------------------------------------------------------------
	
	
	--------------------------------------------------------------
	-- state register
	--------------------------------------------------------------
--	fsm_nextstate : process(sclk_int, rst)
--	begin
--		if rst = '1' then
--			current_state <= IDLE;		
--		elsif rising_edge(sclk_int) then
--			current_state <= next_state;		
--		end if;	
--	end process;
	
	dac_ctrl : process(clk, sclk_int, current_state, dac_data_count)
	begin
	
	if rising_edge(clk) then
						
			case current_state is
				when IDLE =>
						
					current_state <= PREPARE;
					
					sync_int <= '1';
					
				when PREPARE =>
					dac_data_shift	<= "00" & op_mode & data;
					
					if sclk_rising = '1' then
						sync_int <= '0';
						current_state <= SYNC;
					end if;
				when SYNC =>
					if sclk_rising = '1' then
						current_state <= TRANSMIT;
					end if;
				when TRANSMIT =>
					
					sync_int <= '0';
					
					if sclk_rising = '1' then
						current_state <= TRANSMIT;
												
						if dac_data_count = 15 then
							sync_int <= '1';
							dac_data_count <= (others => '0');
							current_state <= IDLE;
						else
							dac_data_count <= dac_data_count + 1;
						end if;
						
						dac_data_shift <= dac_data_shift(14 downto 0) & '0';
					end if;
				when others => null;
			end case;
		
	end if;
	end process;
	
	busy <= busy_int;
	dac_clk  <= sclk_int;
	dac_data <= dac_data_shift(15);
	dac_sync <= sync_int;
end Behavioral;

