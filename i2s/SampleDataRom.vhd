-- Derived from ..\rechteck.raw
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
entity SampleDataRom is
Port ( clk    	 : in  STD_LOGIC;
	  	enable 	 : in STD_LOGIC;
	  	data_out : out std_logic_vector( 24 - 1 downto 0);
		rst    	 : in std_logic );
end SampleDataRom;
architecture Behavioral of SampleDataRom is
	type rom_type is array (0 to 132) of std_logic_vector(24 - 1 downto 0);
constant rom_array : rom_type := 
( 0 => "100100010110000010110011",
1 => "101001101001000101011110",
2 => "100111101010011010011110",
3 => "110111101001010111011101",
4 => "100111001001011110010101",
5 => "000101111001110010011011",
6 => "100101110001110010010111",
7 => "101110111001101110111001",
8 => "100101111100011110011011",
9 => "001100011001011111000001",
10 => "100110110010100110011011",
11 => "001111111001100001000100",
12 => "100110101011111010011000",
13 => "101001101001101010111101",
14 => "100110001010100110011000",
15 => "011000001001101001011101",
16 => "100110001111111110011010",
17 => "000010101001100100000000",
18 => "100110100000100010011010",
19 => "010100011001100101010100",
20 => "100110011011110010011001",
21 => "100111111001100110111000",
22 => "100110011001111010011001",
23 => "011100001001100101110010",
24 => "100110011110011010011001",
25 => "001011011001100111100100",
26 => "100110010010111010011001",
27 => "001001011001101000100101",
28 => "100110001111000010011010",
29 => "010111101001100011110010",
30 => "100110100101110110011010",
31 => "101110011001100010111110",
32 => "100110101001000010011000",
33 => "100011101001101010001111",
34 => "100110001001010110011000",
35 => "101101111001101010101111",
36 => "100110000111000010011010",
37 => "110010011001100001110100",
38 => "100110101100101010011010",
39 => "011001001001100001100101",
40 => "100110101101000010011000",
41 => "011010011001101011001110",
42 => "100110000110101110011000",
43 => "110000001001101010111101",
44 => "100110001000010110011010",
45 => "100100001001100010001001",
46 => "100110101001010110011010",
47 => "110000111001100011000001",
48 => "100110100100011010011000",
49 => "001000101001101001000110",
50 => "100110010010000110011001",
51 => "110011101001100111010011",
52 => "100110011011001110011001",
53 => "001000111001100110110000",
54 => "100110010010010010011001",
55 => "100000101001101010000100",
56 => "100110000010001110011010",
57 => "110011111001100000011011",
58 => "100110111100100110011011",
59 => "011101001001011001110110",
60 => "100111100001011010010110",
61 => "000001111001111000010101",
62 => "100100110000100110010011",
63 => "000011011010010000000101",
64 => "100001010010000110100100",
65 => "110010101000010100100111",
66 => "000010001100110100001000",
67 => "011001010111101101100100",
68 => "010110110001010001111011",
69 => "111010110101101100010100",
70 => "011011011110110001101101",
71 => "111110000110000011110101",
72 => "011010100111101101100000",
73 => "010010010110101001111110",
74 => "011000110100100001100011",
75 => "110000110110100011000110",
76 => "011001001010010001101000",
77 => "101100000110010010011110",
78 => "011001111010100001100111",
79 => "100011100110010110001111",
80 => "011001101110000001100101",
81 => "001110010110011011100101",
82 => "011001100011111001100110",
83 => "010011010110011001001000",
84 => "011001101011110101100110",
85 => "110101010110011011000011",
86 => "011001011101011101100101",
87 => "001001000110011100100110",
88 => "011001011000000001100111",
89 => "011100100110010101111101",
90 => "011001110111001101100111",
91 => "001111010110010100111011",
92 => "011001111010101101100101",
93 => "000100000110011110101000",
94 => "011001010000110001100101",
95 => "110100010110011111010000",
96 => "011001001110111001100111",
97 => "111001000110010011110010",
98 => "011001111110010101100111",
99 => "111001000110010011100100",
100 => "011001111110100101100100",
101 => "111010110110011111100111",
102 => "011001001110011001100100",
103 => "110110010110011111011110",
104 => "011001010000001001100111",
105 => "101111100110010011111100",
106 => "011001111011011001100111",
107 => "001001100110010100110001",
108 => "011001111000011001100101",
109 => "011100000110011110000001",
110 => "011001010110111101100101",
111 => "001100110110011100110001",
112 => "011001011100110101100111",
113 => "110000000110010111010001",
114 => "011001101100000001100110",
115 => "010011110110011001010101",
116 => "011001100010110101100110",
117 => "000001000110011000100101",
118 => "011001110000000001100111",
119 => "011000000110010101100000",
120 => "011001111110101001100101",
121 => "010011100110011111101100",
122 => "011001000100111001100100",
123 => "001011110110100100101111",
124 => "011000101100010101101001",
125 => "000110110110001011000111",
126 => "011010110001110101101011",
127 => "001101110110000000110111",
128 => "011011101100101001100000",
129 => "001010100110111011001010",
130 => "010110100010111101011010",
131 => "100110110111101110010110",
132 => "001000000110110001111011");
signal count : std_logic_vector(8 -1 downto 0) := (others => '0');
begin
	getRomData: process (count)
	begin
		case count is
		when "00000000" => data_out <= rom_array(0);
		when "00000001" => data_out <= rom_array(1);
		when "00000010" => data_out <= rom_array(2);
		when "00000011" => data_out <= rom_array(3);
		when "00000100" => data_out <= rom_array(4);
		when "00000101" => data_out <= rom_array(5);
		when "00000110" => data_out <= rom_array(6);
		when "00000111" => data_out <= rom_array(7);
		when "00001000" => data_out <= rom_array(8);
		when "00001001" => data_out <= rom_array(9);
		when "00001010" => data_out <= rom_array(10);
		when "00001011" => data_out <= rom_array(11);
		when "00001100" => data_out <= rom_array(12);
		when "00001101" => data_out <= rom_array(13);
		when "00001110" => data_out <= rom_array(14);
		when "00001111" => data_out <= rom_array(15);
		when "00010000" => data_out <= rom_array(16);
		when "00010001" => data_out <= rom_array(17);
		when "00010010" => data_out <= rom_array(18);
		when "00010011" => data_out <= rom_array(19);
		when "00010100" => data_out <= rom_array(20);
		when "00010101" => data_out <= rom_array(21);
		when "00010110" => data_out <= rom_array(22);
		when "00010111" => data_out <= rom_array(23);
		when "00011000" => data_out <= rom_array(24);
		when "00011001" => data_out <= rom_array(25);
		when "00011010" => data_out <= rom_array(26);
		when "00011011" => data_out <= rom_array(27);
		when "00011100" => data_out <= rom_array(28);
		when "00011101" => data_out <= rom_array(29);
		when "00011110" => data_out <= rom_array(30);
		when "00011111" => data_out <= rom_array(31);
		when "00100000" => data_out <= rom_array(32);
		when "00100001" => data_out <= rom_array(33);
		when "00100010" => data_out <= rom_array(34);
		when "00100011" => data_out <= rom_array(35);
		when "00100100" => data_out <= rom_array(36);
		when "00100101" => data_out <= rom_array(37);
		when "00100110" => data_out <= rom_array(38);
		when "00100111" => data_out <= rom_array(39);
		when "00101000" => data_out <= rom_array(40);
		when "00101001" => data_out <= rom_array(41);
		when "00101010" => data_out <= rom_array(42);
		when "00101011" => data_out <= rom_array(43);
		when "00101100" => data_out <= rom_array(44);
		when "00101101" => data_out <= rom_array(45);
		when "00101110" => data_out <= rom_array(46);
		when "00101111" => data_out <= rom_array(47);
		when "00110000" => data_out <= rom_array(48);
		when "00110001" => data_out <= rom_array(49);
		when "00110010" => data_out <= rom_array(50);
		when "00110011" => data_out <= rom_array(51);
		when "00110100" => data_out <= rom_array(52);
		when "00110101" => data_out <= rom_array(53);
		when "00110110" => data_out <= rom_array(54);
		when "00110111" => data_out <= rom_array(55);
		when "00111000" => data_out <= rom_array(56);
		when "00111001" => data_out <= rom_array(57);
		when "00111010" => data_out <= rom_array(58);
		when "00111011" => data_out <= rom_array(59);
		when "00111100" => data_out <= rom_array(60);
		when "00111101" => data_out <= rom_array(61);
		when "00111110" => data_out <= rom_array(62);
		when "00111111" => data_out <= rom_array(63);
		when "01000000" => data_out <= rom_array(64);
		when "01000001" => data_out <= rom_array(65);
		when "01000010" => data_out <= rom_array(66);
		when "01000011" => data_out <= rom_array(67);
		when "01000100" => data_out <= rom_array(68);
		when "01000101" => data_out <= rom_array(69);
		when "01000110" => data_out <= rom_array(70);
		when "01000111" => data_out <= rom_array(71);
		when "01001000" => data_out <= rom_array(72);
		when "01001001" => data_out <= rom_array(73);
		when "01001010" => data_out <= rom_array(74);
		when "01001011" => data_out <= rom_array(75);
		when "01001100" => data_out <= rom_array(76);
		when "01001101" => data_out <= rom_array(77);
		when "01001110" => data_out <= rom_array(78);
		when "01001111" => data_out <= rom_array(79);
		when "01010000" => data_out <= rom_array(80);
		when "01010001" => data_out <= rom_array(81);
		when "01010010" => data_out <= rom_array(82);
		when "01010011" => data_out <= rom_array(83);
		when "01010100" => data_out <= rom_array(84);
		when "01010101" => data_out <= rom_array(85);
		when "01010110" => data_out <= rom_array(86);
		when "01010111" => data_out <= rom_array(87);
		when "01011000" => data_out <= rom_array(88);
		when "01011001" => data_out <= rom_array(89);
		when "01011010" => data_out <= rom_array(90);
		when "01011011" => data_out <= rom_array(91);
		when "01011100" => data_out <= rom_array(92);
		when "01011101" => data_out <= rom_array(93);
		when "01011110" => data_out <= rom_array(94);
		when "01011111" => data_out <= rom_array(95);
		when "01100000" => data_out <= rom_array(96);
		when "01100001" => data_out <= rom_array(97);
		when "01100010" => data_out <= rom_array(98);
		when "01100011" => data_out <= rom_array(99);
		when "01100100" => data_out <= rom_array(100);
		when "01100101" => data_out <= rom_array(101);
		when "01100110" => data_out <= rom_array(102);
		when "01100111" => data_out <= rom_array(103);
		when "01101000" => data_out <= rom_array(104);
		when "01101001" => data_out <= rom_array(105);
		when "01101010" => data_out <= rom_array(106);
		when "01101011" => data_out <= rom_array(107);
		when "01101100" => data_out <= rom_array(108);
		when "01101101" => data_out <= rom_array(109);
		when "01101110" => data_out <= rom_array(110);
		when "01101111" => data_out <= rom_array(111);
		when "01110000" => data_out <= rom_array(112);
		when "01110001" => data_out <= rom_array(113);
		when "01110010" => data_out <= rom_array(114);
		when "01110011" => data_out <= rom_array(115);
		when "01110100" => data_out <= rom_array(116);
		when "01110101" => data_out <= rom_array(117);
		when "01110110" => data_out <= rom_array(118);
		when "01110111" => data_out <= rom_array(119);
		when "01111000" => data_out <= rom_array(120);
		when "01111001" => data_out <= rom_array(121);
		when "01111010" => data_out <= rom_array(122);
		when "01111011" => data_out <= rom_array(123);
		when "01111100" => data_out <= rom_array(124);
		when "01111101" => data_out <= rom_array(125);
		when "01111110" => data_out <= rom_array(126);
		when "01111111" => data_out <= rom_array(127);
		when "10000000" => data_out <= rom_array(128);
		when "10000001" => data_out <= rom_array(129);
		when "10000010" => data_out <= rom_array(130);
		when "10000011" => data_out <= rom_array(131);
		when "10000100" => data_out <= rom_array(132);
		when others => data_out <= (others => '0');
		end case;
	end process;
ClockProc: process (clk, rst) is
begin
if rising_edge(clk) then
	if rst = '1' then
		count <= (others => '0');
	end if;
	if (ENABLE = '1') then
		if (unsigned(count) < rom_type'length) then
			count <= std_logic_vector(unsigned(count) + 1);
		else
			count <= (others => '0');
		end if;
	end if;
end if;
end process;
end Behavioral;
