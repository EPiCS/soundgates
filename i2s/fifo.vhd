library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

USE ieee.math_real.log2;
USE ieee.math_real.ceil;

entity fifo is
generic(
	depth : integer := 128;
	data_width : integer := 32 
);

port (  	 clk : in std_logic;
          enr : in std_logic;   --enable read,should be '0' when not in use.
          enw : in std_logic;   --enable write,should be '0' when not in use.
          dataout : out std_logic_vector(data_width -1 downto 0);    --output data
          datain : in std_logic_vector (data_width -1 downto 0);     --input data
          empty : out std_logic;     --set as '1' when the queue is empty
          full : out std_logic     --set as '1' when the queue is full
         );
end fifo;

architecture Behavioral of fifo is
	
	constant addr_width : integer := integer(ceil(log2(real(depth))));
	
	type memory_type is array (0 to depth) of std_logic_vector(data_width -1 downto 0);
	signal memory : memory_type := (others => (others => '0'));   --memory for queue.
	signal readptr,writeptr : std_logic_vector(data_width -1 downto 0) := (others => '0');  --read and write pointers.
	
	
	constant ptr_max : std_logic_vector(addr_width - 1 downto 0) := (others => '1');
	constant ptr_min : std_logic_vector(addr_width - 1 downto 0) := (others => '0');
	
begin
	process(clk)
	begin
		if rising_edge(clk) and enr ='1' then
			dataout <= memory(conv_integer(readptr));
			--error <= '0';
			readptr <= readptr + '1';      --points to next address.
		end if;
		
		if rising_edge(clk) and enw ='1' then
			memory(conv_integer(writeptr)) <= datain;
			writeptr <= writeptr + '1';  --points to next address.
		end if;
		
		if(readptr = ptr_max) then      --resetting read pointer.
			readptr <= (others => '0');
		end if;
		
		if(writeptr = ptr_max) then        --checking whether queue is full or not
			full <='1';
			writeptr <= (others => '0');
		else
			full <='0';
		end if;
		
		if(writeptr = ptr_min) then   --checking whether queue is empty or not
			empty <='1';
		else
			empty <='0';
		end if;
end process;

end architecture;