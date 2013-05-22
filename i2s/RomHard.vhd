----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    18:37:35 05/17/2013 
-- Design Name: 
-- Module Name:    RomHard - Behavioral 
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
use IEEE.NUMERIC_STD.ALL;

entity RomHard is
    Port ( CLK    : in  STD_LOGIC;
			  ENABLE : in STD_LOGIC;
			  DATA   : out std_logic_vector(31 downto 0);
			  rst    : in std_logic
			 );
end RomHard;

architecture Behavioral of RomHard is
	-- ROM stuff
		type rom_type is array (0 to 1059) of std_logic_vector(31 downto 0);
constant rom_array : rom_type := 
( 	0 => X"52494646",
	1 => X"88100000",
	2 => X"57415645",
	3 => X"666d7420",
	4 => X"10000000",
	5 => X"01000200",
	6 => X"2d310100",
	7 => X"b4c40400",
	8 => X"04001000",
	9 => X"64617461",
	10 => X"64100000",
	11 => X"caff79ff",
	12 => X"c6ff69ff",
	13 => X"a2ff4bff",
	14 => X"7cff62ff",
	15 => X"5fff91ff",
	16 => X"63ffbeff",
	17 => X"71ff9dff",
	18 => X"82ff61ff",
	19 => X"8eff51ff",
	20 => X"96ff75ff",
	21 => X"9aff7bff",
	22 => X"97ff66ff",
	23 => X"7aff56ff",
	24 => X"5eff7bff",
	25 => X"37ff78ff",
	26 => X"32ff5dff",
	27 => X"4fff3bff",
	28 => X"79ff38ff",
	29 => X"8aff22ff",
	30 => X"7fff0eff",
	31 => X"6bff06ff",
	32 => X"67ff49ff",
	33 => X"7dff80ff",
	34 => X"96ff8bff",
	35 => X"abffacff",
	36 => X"b1ffa2ff",
	37 => X"9fffd3ff",
	38 => X"9fff8300",
	39 => X"8bff6000",
	40 => X"a0ff3200",
	41 => X"faff8100",
	42 => X"39002600",
	43 => X"5f000600",
	44 => X"8c006600",
	45 => X"5f00c6ff",
	46 => X"0b00b2ff",
	47 => X"e6ff3400",
	48 => X"a6ff87ff",
	49 => X"b9ff7eff",
	50 => X"fdffa1ff",
	51 => X"fdff66fe",
	52 => X"03009afe",
	53 => X"ebff41ff",
	54 => X"7cfff2fe",
	55 => X"50ff7bff",
	56 => X"53ffa3ff",
	57 => X"3fff79fe",
	58 => X"5dffdffe",
	59 => X"5aff17ff",
	60 => X"2aff9afe",
	61 => X"6cff89ff",
	62 => X"82ffbeff",
	63 => X"6afff2fe",
	64 => X"a2ffa3ff",
	65 => X"b4ff07ff",
	66 => X"8fff19fe",
	67 => X"98ff45ff",
	68 => X"53ff40ff",
	69 => X"43ffc5fe",
	70 => X"a7ff98ff",
	71 => X"b0ff95fe",
	72 => X"aaff48fe",
	73 => X"b8ff3f00",
	74 => X"57ff0500",
	75 => X"7efffcff",
	76 => X"06004801",
	77 => X"f8ffebff",
	78 => X"e7ffa4ff",
	79 => X"3200f400",
	80 => X"1700a8ff",
	81 => X"2900d0ff",
	82 => X"02004c01",
	83 => X"e1ff0500",
	84 => X"a7004e00",
	85 => X"fb00eb00",
	86 => X"400085fe",
	87 => X"e3ffc7fe",
	88 => X"deff2500",
	89 => X"3a0095fe",
	90 => X"90004fff",
	91 => X"eeff6b02",
	92 => X"c1005106",
	93 => X"d0037e0a",
	94 => X"a205e606",
	95 => X"a805eafc",
	96 => X"240541f1",
	97 => X"260540e0",
	98 => X"2804e9db",
	99 => X"34fa06f5",
	100 => X"73ea670e",
	101 => X"84eb770e",
	102 => X"cefe5ffb",
	103 => X"99096cd9",
	104 => X"230672c0",
	105 => X"bc000fdc",
	106 => X"c8f43820",
	107 => X"51e65748",
	108 => X"09ef8c2a",
	109 => X"710fe0e8",
	110 => X"a424a3cf",
	111 => X"00161bf2",
	112 => X"06f2f00f",
	113 => X"9bde3ffe",
	114 => X"f0ea01de",
	115 => X"c3fe42d9",
	116 => X"a70430f3",
	117 => X"87018214",
	118 => X"2dfcec2c",
	119 => X"44f8c23d",
	120 => X"a6fd3337",
	121 => X"6f0a5308",
	122 => X"cc1002dc",
	123 => X"da08dce3",
	124 => X"42fc5602",
	125 => X"2afdc905",
	126 => X"320783fa",
	127 => X"1403aafe",
	128 => X"9ff27afb",
	129 => X"39e9e4cf",
	130 => X"b9e51aad",
	131 => X"1ae39ae0",
	132 => X"2bea873d",
	133 => X"51fcb551",
	134 => X"b40e0120",
	135 => X"f7116d09",
	136 => X"8804862e",
	137 => X"e003e358",
	138 => X"c11eac51",
	139 => X"3d33c91c",
	140 => X"d72c36e6",
	141 => X"8f1fcdc4",
	142 => X"e214b8bd",
	143 => X"e60755da",
	144 => X"9cfb8dfb",
	145 => X"b0ee6cea",
	146 => X"d4ddc8bc",
	147 => X"70cdacbb",
	148 => X"25c703e4",
	149 => X"95d645eb",
	150 => X"f4f02cc1",
	151 => X"d2f5a5b3",
	152 => X"f6e75ded",
	153 => X"9ee5942a",
	154 => X"4eec7332",
	155 => X"78eed323",
	156 => X"fffa6516",
	157 => X"c119eefc",
	158 => X"a5335fec",
	159 => X"1634c701",
	160 => X"a3228c23",
	161 => X"40112723",
	162 => X"b4ff90ff",
	163 => X"c6ea8aef",
	164 => X"0fe42012",
	165 => X"15f4ef2f",
	166 => X"7f04bc21",
	167 => X"2f04741b",
	168 => X"ffff7d3b",
	169 => X"5f0de056",
	170 => X"2e23334d",
	171 => X"1120b428",
	172 => X"f8099501",
	173 => X"b4088be2",
	174 => X"1b178eca",
	175 => X"e6139fca",
	176 => X"c807a7e3",
	177 => X"9a0b04ec",
	178 => X"161646d6",
	179 => X"f40b21bd",
	180 => X"cde8d0b4",
	181 => X"9ec81ed4",
	182 => X"03c1fd0e",
	183 => X"bacd8d26",
	184 => X"d9ec0a11",
	185 => X"1a13e8fb",
	186 => X"251ec8f2",
	187 => X"980930f2",
	188 => X"1cf376f5",
	189 => X"e4e6e4eb",
	190 => X"06e393dc",
	191 => X"17e50ad1",
	192 => X"26e6f7c2",
	193 => X"46e997c0",
	194 => X"efec7fc9",
	195 => X"c2ddffc7",
	196 => X"26c079cd",
	197 => X"56b222ea",
	198 => X"88bf170f",
	199 => X"6ddd2039",
	200 => X"74ff9455",
	201 => X"3220d64e",
	202 => X"4041f543",
	203 => X"7a592349",
	204 => X"3759284b",
	205 => X"72432e47",
	206 => X"442b8c3d",
	207 => X"ec22a135",
	208 => X"b62c7d43",
	209 => X"5936ac4c",
	210 => X"dc335b39",
	211 => X"e327ca2d",
	212 => X"ba12f429",
	213 => X"e2fec009",
	214 => X"1bfc29df",
	215 => X"ecfe52c8",
	216 => X"18f607c5",
	217 => X"cbe7fccc",
	218 => X"84df86ca",
	219 => X"d9e179b8",
	220 => X"e6e9c9ab",
	221 => X"c0e843a7",
	222 => X"a1db65af",
	223 => X"6cd21acf",
	224 => X"7fd779ee",
	225 => X"8fe36b01",
	226 => X"9ce70b18",
	227 => X"5fe7d926",
	228 => X"24fb9822",
	229 => X"2420da1d",
	230 => X"38344424",
	231 => X"a72d5e3c",
	232 => X"a61e0661",
	233 => X"c216df72",
	234 => X"211c3b66",
	235 => X"7527a443",
	236 => X"a22ffd0b",
	237 => X"3d375bdd",
	238 => X"6333f6d8",
	239 => X"dc1235e5",
	240 => X"c5e4a4e2",
	241 => X"07c8d4db",
	242 => X"88c738d9",
	243 => X"38dabcd2",
	244 => X"b4ebc7c9",
	245 => X"19ee7acf",
	246 => X"80e6aeeb",
	247 => X"7edee903",
	248 => X"87db12fe",
	249 => X"7cde70ea",
	250 => X"3ee421dd",
	251 => X"6bf21dd5",
	252 => X"430bead8",
	253 => X"761962e7",
	254 => X"fe0dd4e7",
	255 => X"abf6a2dc",
	256 => X"84e486e9",
	257 => X"d9dfd313",
	258 => X"51eb1c35",
	259 => X"a8fd8934",
	260 => X"7a0aad28",
	261 => X"f40e6b33",
	262 => X"870f194e",
	263 => X"f2128852",
	264 => X"421b3c37",
	265 => X"17252318",
	266 => X"6b2dc905",
	267 => X"5a2f7401",
	268 => X"5926aa0f",
	269 => X"a616561f",
	270 => X"330aef15",
	271 => X"7d072004",
	272 => X"2c0db905",
	273 => X"9a13180e",
	274 => X"7016e90b",
	275 => X"38134d05",
	276 => X"50088b02",
	277 => X"1afe1403",
	278 => X"61fbdefb",
	279 => X"34f98dec",
	280 => X"1cf57beb",
	281 => X"c0f6e7f5",
	282 => X"5dfd2def",
	283 => X"8ffecadd",
	284 => X"dbf17dd6",
	285 => X"66df23d0",
	286 => X"f9d9a4c2",
	287 => X"eadf9eb6",
	288 => X"53de71b5",
	289 => X"72d2acc4",
	290 => X"7dc894dc",
	291 => X"06cadaef",
	292 => X"24d993fe",
	293 => X"c1ea5d09",
	294 => X"f6f5d50b",
	295 => X"a5ffe50c",
	296 => X"35086011",
	297 => X"860bc710",
	298 => X"a60dfe0a",
	299 => X"1d0efb07",
	300 => X"010b2709",
	301 => X"430b600d",
	302 => X"fd0d6613",
	303 => X"5f0cf518",
	304 => X"e4090e1f",
	305 => X"4f088622",
	306 => X"0b07b41f",
	307 => X"1f0a6e1b",
	308 => X"870e0e16",
	309 => X"6c0ef30c",
	310 => X"9e0bb308",
	311 => X"d006b409",
	312 => X"4803ca01",
	313 => X"6e0653f2",
	314 => X"15093de9",
	315 => X"29062de8",
	316 => X"600550e8",
	317 => X"07071ce6",
	318 => X"33047fe5",
	319 => X"defc0beb",
	320 => X"13f399ee",
	321 => X"b5ebf8ea",
	322 => X"d4eb15ee",
	323 => X"fceff3fc",
	324 => X"3af59508",
	325 => X"d7fb470a",
	326 => X"3afe5e07",
	327 => X"00fc1f04",
	328 => X"81fc8b04",
	329 => X"15ff4307",
	330 => X"7300980a",
	331 => X"57037312",
	332 => X"3d08c519",
	333 => X"980c8516",
	334 => X"650e200d",
	335 => X"1a0c0e07",
	336 => X"5707f309",
	337 => X"8b029c16",
	338 => X"fbfd7320",
	339 => X"57fb411b",
	340 => X"59fbda0e",
	341 => X"6afcdc03",
	342 => X"efffa1f9",
	343 => X"ec0513f1",
	344 => X"f40743e9",
	345 => X"e50205e1",
	346 => X"4efbc2db",
	347 => X"c9f5a2d8",
	348 => X"8df2edd7",
	349 => X"32f0dbde",
	350 => X"0dee60e6",
	351 => X"13ec6ce4",
	352 => X"61ebb1df",
	353 => X"25ed47e2",
	354 => X"0eef3eee",
	355 => X"24eec8fd",
	356 => X"f8ecdb03",
	357 => X"eeeeadfd",
	358 => X"5ff281f7",
	359 => X"aaf4daf6",
	360 => X"56f87efa",
	361 => X"47ff0204",
	362 => X"c504a10d",
	363 => X"b4048011",
	364 => X"e401a511",
	365 => X"0302e00f",
	366 => X"6208dd12",
	367 => X"c7121420",
	368 => X"5e1af22b",
	369 => X"961a7e2c",
	370 => X"d615f925",
	371 => X"2512b91e",
	372 => X"8b126c1b",
	373 => X"4e168b1a",
	374 => X"ee1a4a15",
	375 => X"e51c6a0d",
	376 => X"c8198b08",
	377 => X"00122305",
	378 => X"8908de02",
	379 => X"bb01da03",
	380 => X"89ff8103",
	381 => X"c6003cff",
	382 => X"ee011afb",
	383 => X"d40041f7",
	384 => X"91fe70f4",
	385 => X"95fb66f5",
	386 => X"8cf5d8f6",
	387 => X"71ed67f6",
	388 => X"2aeab8f2",
	389 => X"22eeb9ea",
	390 => X"b9f356e6",
	391 => X"9ef684e8",
	392 => X"e3f538ea",
	393 => X"6bf17cea",
	394 => X"d9ebabeb",
	395 => X"01ebc0ec",
	396 => X"0ef1cbec",
	397 => X"18f96fee",
	398 => X"91fe85f6",
	399 => X"6000ca04",
	400 => X"72ffeb11",
	401 => X"66ff9318",
	402 => X"0c029619",
	403 => X"de07f719",
	404 => X"1e0f421b",
	405 => X"26140d1e",
	406 => X"7e170d22",
	407 => X"9a19f222",
	408 => X"6a185221",
	409 => X"7614c81e",
	410 => X"9d10c815",
	411 => X"390f7b07",
	412 => X"c70d89fb",
	413 => X"2308d4f6",
	414 => X"20ff31f7",
	415 => X"c6f60cf2",
	416 => X"42f1ebe6",
	417 => X"5eee5ae1",
	418 => X"afedc6e1",
	419 => X"31ee1ce3",
	420 => X"47edc5e4",
	421 => X"2cec8ce7",
	422 => X"70ef10ee",
	423 => X"e3f609f5",
	424 => X"9dfd77f8",
	425 => X"6600f2fb",
	426 => X"80012600",
	427 => X"7d05b003",
	428 => X"ee0a140a",
	429 => X"a70be813",
	430 => X"6907501c",
	431 => X"c7032c1d",
	432 => X"9a048c17",
	433 => X"200af013",
	434 => X"8e0f6115",
	435 => X"b410f517",
	436 => X"3c0e9618",
	437 => X"ed099b16",
	438 => X"8c06fc12",
	439 => X"8e06a70e",
	440 => X"c6082909",
	441 => X"190a0c03",
	442 => X"530863fe",
	443 => X"c503fdfb",
	444 => X"08febffa",
	445 => X"6df91ff8",
	446 => X"82f76ff2",
	447 => X"b4f681ea",
	448 => X"56f65de3",
	449 => X"02f668de",
	450 => X"85f4c2dc",
	451 => X"b7f277df",
	452 => X"38f106e5",
	453 => X"03f020ea",
	454 => X"6dee71ec",
	455 => X"48ecdaeb",
	456 => X"3fecebeb",
	457 => X"14f1ccef",
	458 => X"a7f976f5",
	459 => X"6c000df9",
	460 => X"7901c2fa",
	461 => X"33fee5fd",
	462 => X"45fbcc02",
	463 => X"7afb3a07",
	464 => X"77fd910a",
	465 => X"8dff0a0d",
	466 => X"9f01ac0e",
	467 => X"3c04880f",
	468 => X"cb084110",
	469 => X"870d4911",
	470 => X"54109211",
	471 => X"bf102911",
	472 => X"de0dde11",
	473 => X"a90b1513",
	474 => X"590dbf12",
	475 => X"db104110",
	476 => X"9113dd0c",
	477 => X"6a12fc0a",
	478 => X"e10c620a",
	479 => X"4205d208",
	480 => X"04ffa005",
	481 => X"ebfc3b00",
	482 => X"0cfd0cf9",
	483 => X"5efc6ef2",
	484 => X"6df921ec",
	485 => X"1cf4f5e5",
	486 => X"77efe7e2",
	487 => X"48ecffe2",
	488 => X"b1e977e3",
	489 => X"29e8d5e2",
	490 => X"2ee756e1",
	491 => X"0de8cae1",
	492 => X"4aeb08e6",
	493 => X"f8eec9ea",
	494 => X"9cf2daed",
	495 => X"f1f62af2",
	496 => X"77fb04f9",
	497 => X"f6fdebff",
	498 => X"5dfe2604",
	499 => X"60ff2406",
	500 => X"8d01ef06",
	501 => X"33041606",
	502 => X"9f065204",
	503 => X"3309fc04",
	504 => X"2d0c6f08",
	505 => X"8a0d500c",
	506 => X"f40a9d0e",
	507 => X"e005c00f",
	508 => X"e002af10",
	509 => X"2e03af0f",
	510 => X"1305920b",
	511 => X"8c07ad06",
	512 => X"a509b403",
	513 => X"fa0aae03",
	514 => X"b509f904",
	515 => X"86038d03",
	516 => X"a0fb3dff",
	517 => X"b4f73bfb",
	518 => X"93f90df8",
	519 => X"13fecaf4",
	520 => X"9a0095f1",
	521 => X"ccff95ef",
	522 => X"c2fcd4f0",
	523 => X"f3f864f4",
	524 => X"24f681f7",
	525 => X"3cf626f9",
	526 => X"33fa3ffa",
	527 => X"11ff67fc",
	528 => X"1b024900",
	529 => X"f103d204",
	530 => X"2a053909",
	531 => X"b9059d0d",
	532 => X"27063611",
	533 => X"ab05ea11",
	534 => X"ca034f0e",
	535 => X"74024308",
	536 => X"04025403",
	537 => X"2602e201",
	538 => X"32037d03",
	539 => X"da028605",
	540 => X"4d00d605",
	541 => X"94feae05",
	542 => X"bffd9005",
	543 => X"5bfd9404",
	544 => X"04ff5e02",
	545 => X"d802baff",
	546 => X"3007a9fd",
	547 => X"4c09f0fc",
	548 => X"ee071cfe",
	549 => X"c305bf01",
	550 => X"c104c805",
	551 => X"cf03dd05",
	552 => X"a901e200",
	553 => X"e5ffbbf9",
	554 => X"59ff31f4",
	555 => X"eefe2ef1",
	556 => X"91fef5f0",
	557 => X"3bfdfcf2",
	558 => X"70faebf4",
	559 => X"6bf7e3f5",
	560 => X"84f41df6",
	561 => X"4ff319f6",
	562 => X"90f517f8",
	563 => X"36fa53fc",
	564 => X"4aff5501",
	565 => X"86030706",
	566 => X"f305f708",
	567 => X"7706df0a",
	568 => X"c005380d",
	569 => X"8505b30e",
	570 => X"8206220f",
	571 => X"25080f0f",
	572 => X"f3097e0e",
	573 => X"ae0bb90e",
	574 => X"9d0cb80f",
	575 => X"270d9e10",
	576 => X"cf0c5d11",
	577 => X"560ac010",
	578 => X"3507500e",
	579 => X"92054f0b",
	580 => X"ad053908",
	581 => X"b806f505",
	582 => X"7206f104",
	583 => X"29046b03",
	584 => X"46017000",
	585 => X"40fe5bfd",
	586 => X"b5fbd0fa",
	587 => X"8ffa51f7",
	588 => X"f8facef2",
	589 => X"56fc2fef",
	590 => X"8dfc97ee",
	591 => X"b3fbc4f0",
	592 => X"a3fb3ef3",
	593 => X"a7fbe6f4",
	594 => X"87fbeaf5",
	595 => X"93fb95f5",
	596 => X"fbfb71f5",
	597 => X"b9fcc2f8",
	598 => X"f0fc62ff",
	599 => X"05fd0c06",
	600 => X"62fe060a",
	601 => X"8900b90b",
	602 => X"b602540c",
	603 => X"44045e0b",
	604 => X"39050b09",
	605 => X"fb059107",
	606 => X"f1051108",
	607 => X"9305dc08",
	608 => X"5c058507",
	609 => X"95045f05",
	610 => X"7e039304",
	611 => X"ce021605",
	612 => X"82023906",
	613 => X"ad013307",
	614 => X"caff7e07",
	615 => X"c0fe8506",
	616 => X"0700ac03",
	617 => X"4602c200",
	618 => X"070485ff",
	619 => X"ff04f9fe",
	620 => X"2705a8fe",
	621 => X"650403fe",
	622 => X"dc0235fc",
	623 => X"e40073f9",
	624 => X"e1fee1f6",
	625 => X"76fd69f5",
	626 => X"19fdfdf4",
	627 => X"2cfd84f5",
	628 => X"effb29f7",
	629 => X"0ff9fdf7",
	630 => X"fef524f7",
	631 => X"36f410f6",
	632 => X"7af4c5f6",
	633 => X"c3f5cef9",
	634 => X"ebf71bfd",
	635 => X"c5fa60ff",
	636 => X"5bfcbc00",
	637 => X"bcfdce00",
	638 => X"a5ff4f00",
	639 => X"52015000",
	640 => X"fe024d01",
	641 => X"6a035e03",
	642 => X"12033405",
	643 => X"01033406",
	644 => X"e9019006",
	645 => X"2101bf05",
	646 => X"1e019304",
	647 => X"8a00fb03",
	648 => X"14001703",
	649 => X"77fff301",
	650 => X"b8fe1d00",
	651 => X"a6fe77fd",
	652 => X"abfe8bfb",
	653 => X"41ff42fb",
	654 => X"49006bfc",
	655 => X"9400d8fc",
	656 => X"e2ff31fc",
	657 => X"d8fe21fb",
	658 => X"ccfeb6f8",
	659 => X"80ff5df5",
	660 => X"d7ffeaf2",
	661 => X"d0fed8f2",
	662 => X"a6fcf1f4",
	663 => X"93fb5ff6",
	664 => X"56fbdbf6",
	665 => X"52faf7f7",
	666 => X"aff9fef7",
	667 => X"e8f933f7",
	668 => X"0ffa1cf8",
	669 => X"74fa0efc",
	670 => X"8bfb1202",
	671 => X"b0fd5e07",
	672 => X"af003f0a",
	673 => X"8102020b",
	674 => X"d5022909",
	675 => X"50030e07",
	676 => X"0e04de06",
	677 => X"2904dc07",
	678 => X"b503ff08",
	679 => X"4503d608",
	680 => X"dc02f006",
	681 => X"f501e504",
	682 => X"e900e401",
	683 => X"330063fe",
	684 => X"86ff91fb",
	685 => X"c3fe06fa",
	686 => X"f2fd71fa",
	687 => X"4ffdbafb",
	688 => X"b9fd83fc",
	689 => X"8dfecafc",
	690 => X"12ff93fb",
	691 => X"ebffc9f9",
	692 => X"27006af9",
	693 => X"85ff4dfa",
	694 => X"a8feb6fa",
	695 => X"c4fd37f9",
	696 => X"46fdabf6",
	697 => X"74fcb1f5",
	698 => X"79faa6f6",
	699 => X"b4f87cf8",
	700 => X"0df8f2f9",
	701 => X"96f83bfa",
	702 => X"f6f937fa",
	703 => X"72fb47fb",
	704 => X"c2fc55fe",
	705 => X"f6fde302",
	706 => X"1bff8107",
	707 => X"5300f20a",
	708 => X"c901290c",
	709 => X"4d04d30a",
	710 => X"b6073308",
	711 => X"2e0a4206",
	712 => X"aa0a6106",
	713 => X"6e094608",
	714 => X"1c077d0a",
	715 => X"da04650b",
	716 => X"a003cd09",
	717 => X"91035706",
	718 => X"26049e02",
	719 => X"6a04bdff",
	720 => X"7d030ffe",
	721 => X"3d01ccfd",
	722 => X"7aff61fe",
	723 => X"4bfffdfe",
	724 => X"8600f6fe",
	725 => X"ba01cefd",
	726 => X"eb01cbfb",
	727 => X"e800eef9",
	728 => X"89ff68f9",
	729 => X"defd98fa",
	730 => X"1afc64fb",
	731 => X"3cfb80fa",
	732 => X"5afbbcf8",
	733 => X"8efb27f7",
	734 => X"10fb22f6",
	735 => X"9af924f7",
	736 => X"07f8aafa",
	737 => X"59f71fff",
	738 => X"baf83702",
	739 => X"7bfc4303",
	740 => X"63008103",
	741 => X"e302ea03",
	742 => X"8d03cf05",
	743 => X"55039509",
	744 => X"cd04460d",
	745 => X"89073e0f",
	746 => X"a1095b0e",
	747 => X"dc0a160b",
	748 => X"ee0a3708",
	749 => X"cf098807",
	750 => X"9508ae08",
	751 => X"7b07af0a",
	752 => X"bb065e0b",
	753 => X"c2069309",
	754 => X"8306d605",
	755 => X"10056001",
	756 => X"ee0386fe",
	757 => X"c10320fe",
	758 => X"a90249ff",
	759 => X"2d016800",
	760 => X"8c003100",
	761 => X"ef009cfe",
	762 => X"3402c1fc",
	763 => X"8d0250fb",
	764 => X"21019bfa",
	765 => X"9cfe4ffa",
	766 => X"7afb3dfa",
	767 => X"26f95dfa",
	768 => X"14f907fa",
	769 => X"b1fa1ef9",
	770 => X"a6fb1af8",
	771 => X"4ffa29f8",
	772 => X"f0f72bfa",
	773 => X"f9f686fd",
	774 => X"bcf84f01",
	775 => X"8afc1504",
	776 => X"6c00ee04",
	777 => X"74035005",
	778 => X"5205d305",
	779 => X"8e05d306",
	780 => X"bb046d08",
	781 => X"3e047009",
	782 => X"b504170a",
	783 => X"f005360a",
	784 => X"28077e08",
	785 => X"37075706",
	786 => X"30067905",
	787 => X"27052d06",
	788 => X"1c04ad07",
	789 => X"f7026a07",
	790 => X"d401ed04",
	791 => X"de006101",
	792 => X"5c0080fd",
	793 => X"b6ffadfb",
	794 => X"0aff65fc",
	795 => X"87fe3afd",
	796 => X"5efee3fc",
	797 => X"64fe25fb",
	798 => X"88fd48f9",
	799 => X"2ffc32f8",
	800 => X"76fba7f7",
	801 => X"d5fb1af8",
	802 => X"4afd17f9",
	803 => X"52fe47f9",
	804 => X"0ffe14f9",
	805 => X"eafcbaf8",
	806 => X"c0fb7cf8",
	807 => X"9ffbd1f8",
	808 => X"a4fbf5f9",
	809 => X"27fb6efc",
	810 => X"22fb0c00",
	811 => X"dcfb4403",
	812 => X"66fd5e05",
	813 => X"5affb106",
	814 => X"62015e07",
	815 => X"e003fd06",
	816 => X"5e057805",
	817 => X"65054b04",
	818 => X"8a05d704",
	819 => X"7e06a006",
	820 => X"a6074907",
	821 => X"57074706",
	822 => X"01057904",
	823 => X"1a020d02",
	824 => X"faffa7ff",
	825 => X"20fe93fd",
	826 => X"86fc09fc",
	827 => X"d8fb85fb",
	828 => X"40fb6afb",
	829 => X"dbf9a8fa",
	830 => X"c3f886f9",
	831 => X"2af90cf8",
	832 => X"54fafef6",
	833 => X"c7fae5f6",
	834 => X"cefa7ef6",
	835 => X"33fbcaf5",
	836 => X"05fca9f5",
	837 => X"cafc68f6",
	838 => X"fefc57f8",
	839 => X"eafc54fa",
	840 => X"31fdbbfb",
	841 => X"14fd93fc",
	842 => X"6ffc68fd",
	843 => X"2dfc8afe",
	844 => X"73fc6500",
	845 => X"82fdc902",
	846 => X"1eff3705",
	847 => X"a100a606",
	848 => X"ee018506",
	849 => X"53037405",
	850 => X"c104a104",
	851 => X"b0056c04",
	852 => X"9a05ac04",
	853 => X"f804cc04",
	854 => X"f3039304",
	855 => X"dd021e04",
	856 => X"f3016d03",
	857 => X"4c01d101",
	858 => X"1601dcff",
	859 => X"2a0046fe",
	860 => X"6dfea3fc",
	861 => X"4cfd79fa",
	862 => X"a4fc60f8",
	863 => X"25fc27f7",
	864 => X"1ffc4af7",
	865 => X"a8fc4ff7",
	866 => X"64fde3f6",
	867 => X"55fdfbf6",
	868 => X"2afcf2f7",
	869 => X"e7fa47f9",
	870 => X"35fa93fa",
	871 => X"00faf0fa",
	872 => X"63fa08fb",
	873 => X"04fb60fb",
	874 => X"88fb73fc",
	875 => X"d0fb7efe",
	876 => X"81fc8000",
	877 => X"a8fd0101",
	878 => X"1affb300",
	879 => X"90004201",
	880 => X"a2012a03",
	881 => X"94021606",
	882 => X"4204cb08",
	883 => X"2406910a",
	884 => X"0707520b",
	885 => X"b306840a",
	886 => X"36067808",
	887 => X"a106c506",
	888 => X"2a072e06",
	889 => X"a406e505",
	890 => X"2105a005",
	891 => X"2b036305",
	892 => X"6e011d05",
	893 => X"b0007903",
	894 => X"c8002100",
	895 => X"ee0011fd",
	896 => X"62001afc",
	897 => X"dafe58fc",
	898 => X"2afdfbfb",
	899 => X"72fc64fa",
	900 => X"42fc9af8",
	901 => X"e8fbdaf7",
	902 => X"effb82f8",
	903 => X"9ffc32fa",
	904 => X"26fd6bfb",
	905 => X"41fd6ffb",
	906 => X"e8fc8bfa",
	907 => X"c1fc1ffa",
	908 => X"5ffd33fb",
	909 => X"6efeb1fd",
	910 => X"21ff6900",
	911 => X"74ff7602",
	912 => X"e1ff8f03",
	913 => X"f800b003",
	914 => X"8302b903",
	915 => X"97039d04",
	916 => X"0d048e06",
	917 => X"27040a09",
	918 => X"e703120b",
	919 => X"9d03170c",
	920 => X"3704410c",
	921 => X"4e067d0b",
	922 => X"ce08d109",
	923 => X"bd09da07",
	924 => X"c0082a06",
	925 => X"2b077a05",
	926 => X"df05ba05",
	927 => X"b0042306",
	928 => X"7d031d06",
	929 => X"a102b404",
	930 => X"35024601",
	931 => X"a0019afd",
	932 => X"8d0086fb",
	933 => X"75ff28fb",
	934 => X"42fe40fc",
	935 => X"b4fcd6fd",
	936 => X"c4fb18ff",
	937 => X"89fcf6ff",
	938 => X"0efea0ff",
	939 => X"01ffd2fd",
	940 => X"46ffbbfb",
	941 => X"d7fe90fa",
	942 => X"c6fdfbfa",
	943 => X"99fc24fd",
	944 => X"8bfc1fff",
	945 => X"18fe8fff",
	946 => X"e2ff37ff",
	947 => X"6f00bcfe",
	948 => X"3000e9fe",
	949 => X"3c002900",
	950 => X"7d002402",
	951 => X"85007504",
	952 => X"96004006",
	953 => X"0f01dd06",
	954 => X"52028607",
	955 => X"f6039c08",
	956 => X"06052708",
	957 => X"ab05f605",
	958 => X"2c06e303",
	959 => X"3206ce02",
	960 => X"6e052602",
	961 => X"a7037401",
	962 => X"66014401",
	963 => X"dbffb201",
	964 => X"77ff4101",
	965 => X"97fff8fe",
	966 => X"60ff3efc",
	967 => X"31fea6fa",
	968 => X"7bfcecf9",
	969 => X"63fbd7f9",
	970 => X"eafadcfa",
	971 => X"28fbbafc",
	972 => X"7efce9fd",
	973 => X"73fe65fd",
	974 => X"e1ffd5fb",
	975 => X"4900cffa",
	976 => X"05001dfb",
	977 => X"330087fc",
	978 => X"aa00ccfe",
	979 => X"83002c01",
	980 => X"c8ff8502",
	981 => X"d9ffde02",
	982 => X"96002303",
	983 => X"37019203",
	984 => X"1801a103",
	985 => X"81003203",
	986 => X"fcffa702",
	987 => X"15000003",
	988 => X"f900aa03",
	989 => X"2f029a03",
	990 => X"bb02b202",
	991 => X"39023602",
	992 => X"41013502",
	993 => X"cf00c701",
	994 => X"d1003500",
	995 => X"b7006dfe",
	996 => X"3000abfd",
	997 => X"aeffd8fd",
	998 => X"fafe03fe",
	999 => X"1dfef8fd",
	1000 => X"8efd3cfd",
	1001 => X"dbfd84fb",
	1002 => X"68fef2f8",
	1003 => X"05fe24f7",
	1004 => X"3efc0df7",
	1005 => X"4ffa6bf8",
	1006 => X"c4f90ffa",
	1007 => X"17fbb8fb",
	1008 => X"08fdf8fc",
	1009 => X"6bfe91fd",
	1010 => X"0cff47fd",
	1011 => X"2dff54fd",
	1012 => X"2affa7fe",
	1013 => X"5dffe200",
	1014 => X"d6ff0503",
	1015 => X"15005e04",
	1016 => X"2b002304",
	1017 => X"98007502",
	1018 => X"d0017e00",
	1019 => X"1e0384ff",
	1020 => X"43032c00",
	1021 => X"31028801",
	1022 => X"9b002302",
	1023 => X"a7ff5502",
	1024 => X"dbff0103",
	1025 => X"4b012c04",
	1026 => X"dd020805",
	1027 => X"6e039b04",
	1028 => X"91020503",
	1029 => X"1a019c01",
	1030 => X"a4ff9b00",
	1031 => X"81fe91ff",
	1032 => X"fefd42fe",
	1033 => X"cafddefc",
	1034 => X"a2fda6fb",
	1035 => X"f9fd72fa",
	1036 => X"64fef3f8",
	1037 => X"11fe71f7",
	1038 => X"1bfdc1f6",
	1039 => X"e5fb44f7",
	1040 => X"31fb62f9",
	1041 => X"bffbd7fc",
	1042 => X"1bfd4b00",
	1043 => X"abfeb901",
	1044 => X"1b009400",
	1045 => X"e500ebfe",
	1046 => X"9b00e1fe",
	1047 => X"8dfffbff",
	1048 => X"bafe3001",
	1049 => X"c0fe8802",
	1050 => X"58ff8603",
	1051 => X"19003703",
	1052 => X"12016801",
	1053 => X"56029cff",
	1054 => X"8e0394ff",
	1055 => X"c103f500",
	1056 => X"6d02db02",
	1057 => X"d6004a05",
	1058 => X"8a004007",
	1059 => X"24028507");
		
	signal count    : std_logic_vector(10 downto 0) := (others => '0');
	
begin

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

		getRomData: process (count)
	begin
		case count is
		when "00000000000" => data <= rom_array(0);
		when "00000000001" => data <= rom_array(1);
		when "00000000010" => data <= rom_array(2);
		when "00000000011" => data <= rom_array(3);
		when "00000000100" => data <= rom_array(4);
		when "00000000101" => data <= rom_array(5);
		when "00000000110" => data <= rom_array(6);
		when "00000000111" => data <= rom_array(7);
		when "00000001000" => data <= rom_array(8);
		when "00000001001" => data <= rom_array(9);
		when "00000001010" => data <= rom_array(10);
		when "00000001011" => data <= rom_array(11);
		when "00000001100" => data <= rom_array(12);
		when "00000001101" => data <= rom_array(13);
		when "00000001110" => data <= rom_array(14);
		when "00000001111" => data <= rom_array(15);
		when "00000010000" => data <= rom_array(16);
		when "00000010001" => data <= rom_array(17);
		when "00000010010" => data <= rom_array(18);
		when "00000010011" => data <= rom_array(19);
		when "00000010100" => data <= rom_array(20);
		when "00000010101" => data <= rom_array(21);
		when "00000010110" => data <= rom_array(22);
		when "00000010111" => data <= rom_array(23);
		when "00000011000" => data <= rom_array(24);
		when "00000011001" => data <= rom_array(25);
		when "00000011010" => data <= rom_array(26);
		when "00000011011" => data <= rom_array(27);
		when "00000011100" => data <= rom_array(28);
		when "00000011101" => data <= rom_array(29);
		when "00000011110" => data <= rom_array(30);
		when "00000011111" => data <= rom_array(31);
		when "00000100000" => data <= rom_array(32);
		when "00000100001" => data <= rom_array(33);
		when "00000100010" => data <= rom_array(34);
		when "00000100011" => data <= rom_array(35);
		when "00000100100" => data <= rom_array(36);
		when "00000100101" => data <= rom_array(37);
		when "00000100110" => data <= rom_array(38);
		when "00000100111" => data <= rom_array(39);
		when "00000101000" => data <= rom_array(40);
		when "00000101001" => data <= rom_array(41);
		when "00000101010" => data <= rom_array(42);
		when "00000101011" => data <= rom_array(43);
		when "00000101100" => data <= rom_array(44);
		when "00000101101" => data <= rom_array(45);
		when "00000101110" => data <= rom_array(46);
		when "00000101111" => data <= rom_array(47);
		when "00000110000" => data <= rom_array(48);
		when "00000110001" => data <= rom_array(49);
		when "00000110010" => data <= rom_array(50);
		when "00000110011" => data <= rom_array(51);
		when "00000110100" => data <= rom_array(52);
		when "00000110101" => data <= rom_array(53);
		when "00000110110" => data <= rom_array(54);
		when "00000110111" => data <= rom_array(55);
		when "00000111000" => data <= rom_array(56);
		when "00000111001" => data <= rom_array(57);
		when "00000111010" => data <= rom_array(58);
		when "00000111011" => data <= rom_array(59);
		when "00000111100" => data <= rom_array(60);
		when "00000111101" => data <= rom_array(61);
		when "00000111110" => data <= rom_array(62);
		when "00000111111" => data <= rom_array(63);
		when "00001000000" => data <= rom_array(64);
		when "00001000001" => data <= rom_array(65);
		when "00001000010" => data <= rom_array(66);
		when "00001000011" => data <= rom_array(67);
		when "00001000100" => data <= rom_array(68);
		when "00001000101" => data <= rom_array(69);
		when "00001000110" => data <= rom_array(70);
		when "00001000111" => data <= rom_array(71);
		when "00001001000" => data <= rom_array(72);
		when "00001001001" => data <= rom_array(73);
		when "00001001010" => data <= rom_array(74);
		when "00001001011" => data <= rom_array(75);
		when "00001001100" => data <= rom_array(76);
		when "00001001101" => data <= rom_array(77);
		when "00001001110" => data <= rom_array(78);
		when "00001001111" => data <= rom_array(79);
		when "00001010000" => data <= rom_array(80);
		when "00001010001" => data <= rom_array(81);
		when "00001010010" => data <= rom_array(82);
		when "00001010011" => data <= rom_array(83);
		when "00001010100" => data <= rom_array(84);
		when "00001010101" => data <= rom_array(85);
		when "00001010110" => data <= rom_array(86);
		when "00001010111" => data <= rom_array(87);
		when "00001011000" => data <= rom_array(88);
		when "00001011001" => data <= rom_array(89);
		when "00001011010" => data <= rom_array(90);
		when "00001011011" => data <= rom_array(91);
		when "00001011100" => data <= rom_array(92);
		when "00001011101" => data <= rom_array(93);
		when "00001011110" => data <= rom_array(94);
		when "00001011111" => data <= rom_array(95);
		when "00001100000" => data <= rom_array(96);
		when "00001100001" => data <= rom_array(97);
		when "00001100010" => data <= rom_array(98);
		when "00001100011" => data <= rom_array(99);
		when "00001100100" => data <= rom_array(100);
		when "00001100101" => data <= rom_array(101);
		when "00001100110" => data <= rom_array(102);
		when "00001100111" => data <= rom_array(103);
		when "00001101000" => data <= rom_array(104);
		when "00001101001" => data <= rom_array(105);
		when "00001101010" => data <= rom_array(106);
		when "00001101011" => data <= rom_array(107);
		when "00001101100" => data <= rom_array(108);
		when "00001101101" => data <= rom_array(109);
		when "00001101110" => data <= rom_array(110);
		when "00001101111" => data <= rom_array(111);
		when "00001110000" => data <= rom_array(112);
		when "00001110001" => data <= rom_array(113);
		when "00001110010" => data <= rom_array(114);
		when "00001110011" => data <= rom_array(115);
		when "00001110100" => data <= rom_array(116);
		when "00001110101" => data <= rom_array(117);
		when "00001110110" => data <= rom_array(118);
		when "00001110111" => data <= rom_array(119);
		when "00001111000" => data <= rom_array(120);
		when "00001111001" => data <= rom_array(121);
		when "00001111010" => data <= rom_array(122);
		when "00001111011" => data <= rom_array(123);
		when "00001111100" => data <= rom_array(124);
		when "00001111101" => data <= rom_array(125);
		when "00001111110" => data <= rom_array(126);
		when "00001111111" => data <= rom_array(127);
		when "00010000000" => data <= rom_array(128);
		when "00010000001" => data <= rom_array(129);
		when "00010000010" => data <= rom_array(130);
		when "00010000011" => data <= rom_array(131);
		when "00010000100" => data <= rom_array(132);
		when "00010000101" => data <= rom_array(133);
		when "00010000110" => data <= rom_array(134);
		when "00010000111" => data <= rom_array(135);
		when "00010001000" => data <= rom_array(136);
		when "00010001001" => data <= rom_array(137);
		when "00010001010" => data <= rom_array(138);
		when "00010001011" => data <= rom_array(139);
		when "00010001100" => data <= rom_array(140);
		when "00010001101" => data <= rom_array(141);
		when "00010001110" => data <= rom_array(142);
		when "00010001111" => data <= rom_array(143);
		when "00010010000" => data <= rom_array(144);
		when "00010010001" => data <= rom_array(145);
		when "00010010010" => data <= rom_array(146);
		when "00010010011" => data <= rom_array(147);
		when "00010010100" => data <= rom_array(148);
		when "00010010101" => data <= rom_array(149);
		when "00010010110" => data <= rom_array(150);
		when "00010010111" => data <= rom_array(151);
		when "00010011000" => data <= rom_array(152);
		when "00010011001" => data <= rom_array(153);
		when "00010011010" => data <= rom_array(154);
		when "00010011011" => data <= rom_array(155);
		when "00010011100" => data <= rom_array(156);
		when "00010011101" => data <= rom_array(157);
		when "00010011110" => data <= rom_array(158);
		when "00010011111" => data <= rom_array(159);
		when "00010100000" => data <= rom_array(160);
		when "00010100001" => data <= rom_array(161);
		when "00010100010" => data <= rom_array(162);
		when "00010100011" => data <= rom_array(163);
		when "00010100100" => data <= rom_array(164);
		when "00010100101" => data <= rom_array(165);
		when "00010100110" => data <= rom_array(166);
		when "00010100111" => data <= rom_array(167);
		when "00010101000" => data <= rom_array(168);
		when "00010101001" => data <= rom_array(169);
		when "00010101010" => data <= rom_array(170);
		when "00010101011" => data <= rom_array(171);
		when "00010101100" => data <= rom_array(172);
		when "00010101101" => data <= rom_array(173);
		when "00010101110" => data <= rom_array(174);
		when "00010101111" => data <= rom_array(175);
		when "00010110000" => data <= rom_array(176);
		when "00010110001" => data <= rom_array(177);
		when "00010110010" => data <= rom_array(178);
		when "00010110011" => data <= rom_array(179);
		when "00010110100" => data <= rom_array(180);
		when "00010110101" => data <= rom_array(181);
		when "00010110110" => data <= rom_array(182);
		when "00010110111" => data <= rom_array(183);
		when "00010111000" => data <= rom_array(184);
		when "00010111001" => data <= rom_array(185);
		when "00010111010" => data <= rom_array(186);
		when "00010111011" => data <= rom_array(187);
		when "00010111100" => data <= rom_array(188);
		when "00010111101" => data <= rom_array(189);
		when "00010111110" => data <= rom_array(190);
		when "00010111111" => data <= rom_array(191);
		when "00011000000" => data <= rom_array(192);
		when "00011000001" => data <= rom_array(193);
		when "00011000010" => data <= rom_array(194);
		when "00011000011" => data <= rom_array(195);
		when "00011000100" => data <= rom_array(196);
		when "00011000101" => data <= rom_array(197);
		when "00011000110" => data <= rom_array(198);
		when "00011000111" => data <= rom_array(199);
		when "00011001000" => data <= rom_array(200);
		when "00011001001" => data <= rom_array(201);
		when "00011001010" => data <= rom_array(202);
		when "00011001011" => data <= rom_array(203);
		when "00011001100" => data <= rom_array(204);
		when "00011001101" => data <= rom_array(205);
		when "00011001110" => data <= rom_array(206);
		when "00011001111" => data <= rom_array(207);
		when "00011010000" => data <= rom_array(208);
		when "00011010001" => data <= rom_array(209);
		when "00011010010" => data <= rom_array(210);
		when "00011010011" => data <= rom_array(211);
		when "00011010100" => data <= rom_array(212);
		when "00011010101" => data <= rom_array(213);
		when "00011010110" => data <= rom_array(214);
		when "00011010111" => data <= rom_array(215);
		when "00011011000" => data <= rom_array(216);
		when "00011011001" => data <= rom_array(217);
		when "00011011010" => data <= rom_array(218);
		when "00011011011" => data <= rom_array(219);
		when "00011011100" => data <= rom_array(220);
		when "00011011101" => data <= rom_array(221);
		when "00011011110" => data <= rom_array(222);
		when "00011011111" => data <= rom_array(223);
		when "00011100000" => data <= rom_array(224);
		when "00011100001" => data <= rom_array(225);
		when "00011100010" => data <= rom_array(226);
		when "00011100011" => data <= rom_array(227);
		when "00011100100" => data <= rom_array(228);
		when "00011100101" => data <= rom_array(229);
		when "00011100110" => data <= rom_array(230);
		when "00011100111" => data <= rom_array(231);
		when "00011101000" => data <= rom_array(232);
		when "00011101001" => data <= rom_array(233);
		when "00011101010" => data <= rom_array(234);
		when "00011101011" => data <= rom_array(235);
		when "00011101100" => data <= rom_array(236);
		when "00011101101" => data <= rom_array(237);
		when "00011101110" => data <= rom_array(238);
		when "00011101111" => data <= rom_array(239);
		when "00011110000" => data <= rom_array(240);
		when "00011110001" => data <= rom_array(241);
		when "00011110010" => data <= rom_array(242);
		when "00011110011" => data <= rom_array(243);
		when "00011110100" => data <= rom_array(244);
		when "00011110101" => data <= rom_array(245);
		when "00011110110" => data <= rom_array(246);
		when "00011110111" => data <= rom_array(247);
		when "00011111000" => data <= rom_array(248);
		when "00011111001" => data <= rom_array(249);
		when "00011111010" => data <= rom_array(250);
		when "00011111011" => data <= rom_array(251);
		when "00011111100" => data <= rom_array(252);
		when "00011111101" => data <= rom_array(253);
		when "00011111110" => data <= rom_array(254);
		when "00011111111" => data <= rom_array(255);
		when "00100000000" => data <= rom_array(256);
		when "00100000001" => data <= rom_array(257);
		when "00100000010" => data <= rom_array(258);
		when "00100000011" => data <= rom_array(259);
		when "00100000100" => data <= rom_array(260);
		when "00100000101" => data <= rom_array(261);
		when "00100000110" => data <= rom_array(262);
		when "00100000111" => data <= rom_array(263);
		when "00100001000" => data <= rom_array(264);
		when "00100001001" => data <= rom_array(265);
		when "00100001010" => data <= rom_array(266);
		when "00100001011" => data <= rom_array(267);
		when "00100001100" => data <= rom_array(268);
		when "00100001101" => data <= rom_array(269);
		when "00100001110" => data <= rom_array(270);
		when "00100001111" => data <= rom_array(271);
		when "00100010000" => data <= rom_array(272);
		when "00100010001" => data <= rom_array(273);
		when "00100010010" => data <= rom_array(274);
		when "00100010011" => data <= rom_array(275);
		when "00100010100" => data <= rom_array(276);
		when "00100010101" => data <= rom_array(277);
		when "00100010110" => data <= rom_array(278);
		when "00100010111" => data <= rom_array(279);
		when "00100011000" => data <= rom_array(280);
		when "00100011001" => data <= rom_array(281);
		when "00100011010" => data <= rom_array(282);
		when "00100011011" => data <= rom_array(283);
		when "00100011100" => data <= rom_array(284);
		when "00100011101" => data <= rom_array(285);
		when "00100011110" => data <= rom_array(286);
		when "00100011111" => data <= rom_array(287);
		when "00100100000" => data <= rom_array(288);
		when "00100100001" => data <= rom_array(289);
		when "00100100010" => data <= rom_array(290);
		when "00100100011" => data <= rom_array(291);
		when "00100100100" => data <= rom_array(292);
		when "00100100101" => data <= rom_array(293);
		when "00100100110" => data <= rom_array(294);
		when "00100100111" => data <= rom_array(295);
		when "00100101000" => data <= rom_array(296);
		when "00100101001" => data <= rom_array(297);
		when "00100101010" => data <= rom_array(298);
		when "00100101011" => data <= rom_array(299);
		when "00100101100" => data <= rom_array(300);
		when "00100101101" => data <= rom_array(301);
		when "00100101110" => data <= rom_array(302);
		when "00100101111" => data <= rom_array(303);
		when "00100110000" => data <= rom_array(304);
		when "00100110001" => data <= rom_array(305);
		when "00100110010" => data <= rom_array(306);
		when "00100110011" => data <= rom_array(307);
		when "00100110100" => data <= rom_array(308);
		when "00100110101" => data <= rom_array(309);
		when "00100110110" => data <= rom_array(310);
		when "00100110111" => data <= rom_array(311);
		when "00100111000" => data <= rom_array(312);
		when "00100111001" => data <= rom_array(313);
		when "00100111010" => data <= rom_array(314);
		when "00100111011" => data <= rom_array(315);
		when "00100111100" => data <= rom_array(316);
		when "00100111101" => data <= rom_array(317);
		when "00100111110" => data <= rom_array(318);
		when "00100111111" => data <= rom_array(319);
		when "00101000000" => data <= rom_array(320);
		when "00101000001" => data <= rom_array(321);
		when "00101000010" => data <= rom_array(322);
		when "00101000011" => data <= rom_array(323);
		when "00101000100" => data <= rom_array(324);
		when "00101000101" => data <= rom_array(325);
		when "00101000110" => data <= rom_array(326);
		when "00101000111" => data <= rom_array(327);
		when "00101001000" => data <= rom_array(328);
		when "00101001001" => data <= rom_array(329);
		when "00101001010" => data <= rom_array(330);
		when "00101001011" => data <= rom_array(331);
		when "00101001100" => data <= rom_array(332);
		when "00101001101" => data <= rom_array(333);
		when "00101001110" => data <= rom_array(334);
		when "00101001111" => data <= rom_array(335);
		when "00101010000" => data <= rom_array(336);
		when "00101010001" => data <= rom_array(337);
		when "00101010010" => data <= rom_array(338);
		when "00101010011" => data <= rom_array(339);
		when "00101010100" => data <= rom_array(340);
		when "00101010101" => data <= rom_array(341);
		when "00101010110" => data <= rom_array(342);
		when "00101010111" => data <= rom_array(343);
		when "00101011000" => data <= rom_array(344);
		when "00101011001" => data <= rom_array(345);
		when "00101011010" => data <= rom_array(346);
		when "00101011011" => data <= rom_array(347);
		when "00101011100" => data <= rom_array(348);
		when "00101011101" => data <= rom_array(349);
		when "00101011110" => data <= rom_array(350);
		when "00101011111" => data <= rom_array(351);
		when "00101100000" => data <= rom_array(352);
		when "00101100001" => data <= rom_array(353);
		when "00101100010" => data <= rom_array(354);
		when "00101100011" => data <= rom_array(355);
		when "00101100100" => data <= rom_array(356);
		when "00101100101" => data <= rom_array(357);
		when "00101100110" => data <= rom_array(358);
		when "00101100111" => data <= rom_array(359);
		when "00101101000" => data <= rom_array(360);
		when "00101101001" => data <= rom_array(361);
		when "00101101010" => data <= rom_array(362);
		when "00101101011" => data <= rom_array(363);
		when "00101101100" => data <= rom_array(364);
		when "00101101101" => data <= rom_array(365);
		when "00101101110" => data <= rom_array(366);
		when "00101101111" => data <= rom_array(367);
		when "00101110000" => data <= rom_array(368);
		when "00101110001" => data <= rom_array(369);
		when "00101110010" => data <= rom_array(370);
		when "00101110011" => data <= rom_array(371);
		when "00101110100" => data <= rom_array(372);
		when "00101110101" => data <= rom_array(373);
		when "00101110110" => data <= rom_array(374);
		when "00101110111" => data <= rom_array(375);
		when "00101111000" => data <= rom_array(376);
		when "00101111001" => data <= rom_array(377);
		when "00101111010" => data <= rom_array(378);
		when "00101111011" => data <= rom_array(379);
		when "00101111100" => data <= rom_array(380);
		when "00101111101" => data <= rom_array(381);
		when "00101111110" => data <= rom_array(382);
		when "00101111111" => data <= rom_array(383);
		when "00110000000" => data <= rom_array(384);
		when "00110000001" => data <= rom_array(385);
		when "00110000010" => data <= rom_array(386);
		when "00110000011" => data <= rom_array(387);
		when "00110000100" => data <= rom_array(388);
		when "00110000101" => data <= rom_array(389);
		when "00110000110" => data <= rom_array(390);
		when "00110000111" => data <= rom_array(391);
		when "00110001000" => data <= rom_array(392);
		when "00110001001" => data <= rom_array(393);
		when "00110001010" => data <= rom_array(394);
		when "00110001011" => data <= rom_array(395);
		when "00110001100" => data <= rom_array(396);
		when "00110001101" => data <= rom_array(397);
		when "00110001110" => data <= rom_array(398);
		when "00110001111" => data <= rom_array(399);
		when "00110010000" => data <= rom_array(400);
		when "00110010001" => data <= rom_array(401);
		when "00110010010" => data <= rom_array(402);
		when "00110010011" => data <= rom_array(403);
		when "00110010100" => data <= rom_array(404);
		when "00110010101" => data <= rom_array(405);
		when "00110010110" => data <= rom_array(406);
		when "00110010111" => data <= rom_array(407);
		when "00110011000" => data <= rom_array(408);
		when "00110011001" => data <= rom_array(409);
		when "00110011010" => data <= rom_array(410);
		when "00110011011" => data <= rom_array(411);
		when "00110011100" => data <= rom_array(412);
		when "00110011101" => data <= rom_array(413);
		when "00110011110" => data <= rom_array(414);
		when "00110011111" => data <= rom_array(415);
		when "00110100000" => data <= rom_array(416);
		when "00110100001" => data <= rom_array(417);
		when "00110100010" => data <= rom_array(418);
		when "00110100011" => data <= rom_array(419);
		when "00110100100" => data <= rom_array(420);
		when "00110100101" => data <= rom_array(421);
		when "00110100110" => data <= rom_array(422);
		when "00110100111" => data <= rom_array(423);
		when "00110101000" => data <= rom_array(424);
		when "00110101001" => data <= rom_array(425);
		when "00110101010" => data <= rom_array(426);
		when "00110101011" => data <= rom_array(427);
		when "00110101100" => data <= rom_array(428);
		when "00110101101" => data <= rom_array(429);
		when "00110101110" => data <= rom_array(430);
		when "00110101111" => data <= rom_array(431);
		when "00110110000" => data <= rom_array(432);
		when "00110110001" => data <= rom_array(433);
		when "00110110010" => data <= rom_array(434);
		when "00110110011" => data <= rom_array(435);
		when "00110110100" => data <= rom_array(436);
		when "00110110101" => data <= rom_array(437);
		when "00110110110" => data <= rom_array(438);
		when "00110110111" => data <= rom_array(439);
		when "00110111000" => data <= rom_array(440);
		when "00110111001" => data <= rom_array(441);
		when "00110111010" => data <= rom_array(442);
		when "00110111011" => data <= rom_array(443);
		when "00110111100" => data <= rom_array(444);
		when "00110111101" => data <= rom_array(445);
		when "00110111110" => data <= rom_array(446);
		when "00110111111" => data <= rom_array(447);
		when "00111000000" => data <= rom_array(448);
		when "00111000001" => data <= rom_array(449);
		when "00111000010" => data <= rom_array(450);
		when "00111000011" => data <= rom_array(451);
		when "00111000100" => data <= rom_array(452);
		when "00111000101" => data <= rom_array(453);
		when "00111000110" => data <= rom_array(454);
		when "00111000111" => data <= rom_array(455);
		when "00111001000" => data <= rom_array(456);
		when "00111001001" => data <= rom_array(457);
		when "00111001010" => data <= rom_array(458);
		when "00111001011" => data <= rom_array(459);
		when "00111001100" => data <= rom_array(460);
		when "00111001101" => data <= rom_array(461);
		when "00111001110" => data <= rom_array(462);
		when "00111001111" => data <= rom_array(463);
		when "00111010000" => data <= rom_array(464);
		when "00111010001" => data <= rom_array(465);
		when "00111010010" => data <= rom_array(466);
		when "00111010011" => data <= rom_array(467);
		when "00111010100" => data <= rom_array(468);
		when "00111010101" => data <= rom_array(469);
		when "00111010110" => data <= rom_array(470);
		when "00111010111" => data <= rom_array(471);
		when "00111011000" => data <= rom_array(472);
		when "00111011001" => data <= rom_array(473);
		when "00111011010" => data <= rom_array(474);
		when "00111011011" => data <= rom_array(475);
		when "00111011100" => data <= rom_array(476);
		when "00111011101" => data <= rom_array(477);
		when "00111011110" => data <= rom_array(478);
		when "00111011111" => data <= rom_array(479);
		when "00111100000" => data <= rom_array(480);
		when "00111100001" => data <= rom_array(481);
		when "00111100010" => data <= rom_array(482);
		when "00111100011" => data <= rom_array(483);
		when "00111100100" => data <= rom_array(484);
		when "00111100101" => data <= rom_array(485);
		when "00111100110" => data <= rom_array(486);
		when "00111100111" => data <= rom_array(487);
		when "00111101000" => data <= rom_array(488);
		when "00111101001" => data <= rom_array(489);
		when "00111101010" => data <= rom_array(490);
		when "00111101011" => data <= rom_array(491);
		when "00111101100" => data <= rom_array(492);
		when "00111101101" => data <= rom_array(493);
		when "00111101110" => data <= rom_array(494);
		when "00111101111" => data <= rom_array(495);
		when "00111110000" => data <= rom_array(496);
		when "00111110001" => data <= rom_array(497);
		when "00111110010" => data <= rom_array(498);
		when "00111110011" => data <= rom_array(499);
		when "00111110100" => data <= rom_array(500);
		when "00111110101" => data <= rom_array(501);
		when "00111110110" => data <= rom_array(502);
		when "00111110111" => data <= rom_array(503);
		when "00111111000" => data <= rom_array(504);
		when "00111111001" => data <= rom_array(505);
		when "00111111010" => data <= rom_array(506);
		when "00111111011" => data <= rom_array(507);
		when "00111111100" => data <= rom_array(508);
		when "00111111101" => data <= rom_array(509);
		when "00111111110" => data <= rom_array(510);
		when "00111111111" => data <= rom_array(511);
		when "01000000000" => data <= rom_array(512);
		when "01000000001" => data <= rom_array(513);
		when "01000000010" => data <= rom_array(514);
		when "01000000011" => data <= rom_array(515);
		when "01000000100" => data <= rom_array(516);
		when "01000000101" => data <= rom_array(517);
		when "01000000110" => data <= rom_array(518);
		when "01000000111" => data <= rom_array(519);
		when "01000001000" => data <= rom_array(520);
		when "01000001001" => data <= rom_array(521);
		when "01000001010" => data <= rom_array(522);
		when "01000001011" => data <= rom_array(523);
		when "01000001100" => data <= rom_array(524);
		when "01000001101" => data <= rom_array(525);
		when "01000001110" => data <= rom_array(526);
		when "01000001111" => data <= rom_array(527);
		when "01000010000" => data <= rom_array(528);
		when "01000010001" => data <= rom_array(529);
		when "01000010010" => data <= rom_array(530);
		when "01000010011" => data <= rom_array(531);
		when "01000010100" => data <= rom_array(532);
		when "01000010101" => data <= rom_array(533);
		when "01000010110" => data <= rom_array(534);
		when "01000010111" => data <= rom_array(535);
		when "01000011000" => data <= rom_array(536);
		when "01000011001" => data <= rom_array(537);
		when "01000011010" => data <= rom_array(538);
		when "01000011011" => data <= rom_array(539);
		when "01000011100" => data <= rom_array(540);
		when "01000011101" => data <= rom_array(541);
		when "01000011110" => data <= rom_array(542);
		when "01000011111" => data <= rom_array(543);
		when "01000100000" => data <= rom_array(544);
		when "01000100001" => data <= rom_array(545);
		when "01000100010" => data <= rom_array(546);
		when "01000100011" => data <= rom_array(547);
		when "01000100100" => data <= rom_array(548);
		when "01000100101" => data <= rom_array(549);
		when "01000100110" => data <= rom_array(550);
		when "01000100111" => data <= rom_array(551);
		when "01000101000" => data <= rom_array(552);
		when "01000101001" => data <= rom_array(553);
		when "01000101010" => data <= rom_array(554);
		when "01000101011" => data <= rom_array(555);
		when "01000101100" => data <= rom_array(556);
		when "01000101101" => data <= rom_array(557);
		when "01000101110" => data <= rom_array(558);
		when "01000101111" => data <= rom_array(559);
		when "01000110000" => data <= rom_array(560);
		when "01000110001" => data <= rom_array(561);
		when "01000110010" => data <= rom_array(562);
		when "01000110011" => data <= rom_array(563);
		when "01000110100" => data <= rom_array(564);
		when "01000110101" => data <= rom_array(565);
		when "01000110110" => data <= rom_array(566);
		when "01000110111" => data <= rom_array(567);
		when "01000111000" => data <= rom_array(568);
		when "01000111001" => data <= rom_array(569);
		when "01000111010" => data <= rom_array(570);
		when "01000111011" => data <= rom_array(571);
		when "01000111100" => data <= rom_array(572);
		when "01000111101" => data <= rom_array(573);
		when "01000111110" => data <= rom_array(574);
		when "01000111111" => data <= rom_array(575);
		when "01001000000" => data <= rom_array(576);
		when "01001000001" => data <= rom_array(577);
		when "01001000010" => data <= rom_array(578);
		when "01001000011" => data <= rom_array(579);
		when "01001000100" => data <= rom_array(580);
		when "01001000101" => data <= rom_array(581);
		when "01001000110" => data <= rom_array(582);
		when "01001000111" => data <= rom_array(583);
		when "01001001000" => data <= rom_array(584);
		when "01001001001" => data <= rom_array(585);
		when "01001001010" => data <= rom_array(586);
		when "01001001011" => data <= rom_array(587);
		when "01001001100" => data <= rom_array(588);
		when "01001001101" => data <= rom_array(589);
		when "01001001110" => data <= rom_array(590);
		when "01001001111" => data <= rom_array(591);
		when "01001010000" => data <= rom_array(592);
		when "01001010001" => data <= rom_array(593);
		when "01001010010" => data <= rom_array(594);
		when "01001010011" => data <= rom_array(595);
		when "01001010100" => data <= rom_array(596);
		when "01001010101" => data <= rom_array(597);
		when "01001010110" => data <= rom_array(598);
		when "01001010111" => data <= rom_array(599);
		when "01001011000" => data <= rom_array(600);
		when "01001011001" => data <= rom_array(601);
		when "01001011010" => data <= rom_array(602);
		when "01001011011" => data <= rom_array(603);
		when "01001011100" => data <= rom_array(604);
		when "01001011101" => data <= rom_array(605);
		when "01001011110" => data <= rom_array(606);
		when "01001011111" => data <= rom_array(607);
		when "01001100000" => data <= rom_array(608);
		when "01001100001" => data <= rom_array(609);
		when "01001100010" => data <= rom_array(610);
		when "01001100011" => data <= rom_array(611);
		when "01001100100" => data <= rom_array(612);
		when "01001100101" => data <= rom_array(613);
		when "01001100110" => data <= rom_array(614);
		when "01001100111" => data <= rom_array(615);
		when "01001101000" => data <= rom_array(616);
		when "01001101001" => data <= rom_array(617);
		when "01001101010" => data <= rom_array(618);
		when "01001101011" => data <= rom_array(619);
		when "01001101100" => data <= rom_array(620);
		when "01001101101" => data <= rom_array(621);
		when "01001101110" => data <= rom_array(622);
		when "01001101111" => data <= rom_array(623);
		when "01001110000" => data <= rom_array(624);
		when "01001110001" => data <= rom_array(625);
		when "01001110010" => data <= rom_array(626);
		when "01001110011" => data <= rom_array(627);
		when "01001110100" => data <= rom_array(628);
		when "01001110101" => data <= rom_array(629);
		when "01001110110" => data <= rom_array(630);
		when "01001110111" => data <= rom_array(631);
		when "01001111000" => data <= rom_array(632);
		when "01001111001" => data <= rom_array(633);
		when "01001111010" => data <= rom_array(634);
		when "01001111011" => data <= rom_array(635);
		when "01001111100" => data <= rom_array(636);
		when "01001111101" => data <= rom_array(637);
		when "01001111110" => data <= rom_array(638);
		when "01001111111" => data <= rom_array(639);
		when "01010000000" => data <= rom_array(640);
		when "01010000001" => data <= rom_array(641);
		when "01010000010" => data <= rom_array(642);
		when "01010000011" => data <= rom_array(643);
		when "01010000100" => data <= rom_array(644);
		when "01010000101" => data <= rom_array(645);
		when "01010000110" => data <= rom_array(646);
		when "01010000111" => data <= rom_array(647);
		when "01010001000" => data <= rom_array(648);
		when "01010001001" => data <= rom_array(649);
		when "01010001010" => data <= rom_array(650);
		when "01010001011" => data <= rom_array(651);
		when "01010001100" => data <= rom_array(652);
		when "01010001101" => data <= rom_array(653);
		when "01010001110" => data <= rom_array(654);
		when "01010001111" => data <= rom_array(655);
		when "01010010000" => data <= rom_array(656);
		when "01010010001" => data <= rom_array(657);
		when "01010010010" => data <= rom_array(658);
		when "01010010011" => data <= rom_array(659);
		when "01010010100" => data <= rom_array(660);
		when "01010010101" => data <= rom_array(661);
		when "01010010110" => data <= rom_array(662);
		when "01010010111" => data <= rom_array(663);
		when "01010011000" => data <= rom_array(664);
		when "01010011001" => data <= rom_array(665);
		when "01010011010" => data <= rom_array(666);
		when "01010011011" => data <= rom_array(667);
		when "01010011100" => data <= rom_array(668);
		when "01010011101" => data <= rom_array(669);
		when "01010011110" => data <= rom_array(670);
		when "01010011111" => data <= rom_array(671);
		when "01010100000" => data <= rom_array(672);
		when "01010100001" => data <= rom_array(673);
		when "01010100010" => data <= rom_array(674);
		when "01010100011" => data <= rom_array(675);
		when "01010100100" => data <= rom_array(676);
		when "01010100101" => data <= rom_array(677);
		when "01010100110" => data <= rom_array(678);
		when "01010100111" => data <= rom_array(679);
		when "01010101000" => data <= rom_array(680);
		when "01010101001" => data <= rom_array(681);
		when "01010101010" => data <= rom_array(682);
		when "01010101011" => data <= rom_array(683);
		when "01010101100" => data <= rom_array(684);
		when "01010101101" => data <= rom_array(685);
		when "01010101110" => data <= rom_array(686);
		when "01010101111" => data <= rom_array(687);
		when "01010110000" => data <= rom_array(688);
		when "01010110001" => data <= rom_array(689);
		when "01010110010" => data <= rom_array(690);
		when "01010110011" => data <= rom_array(691);
		when "01010110100" => data <= rom_array(692);
		when "01010110101" => data <= rom_array(693);
		when "01010110110" => data <= rom_array(694);
		when "01010110111" => data <= rom_array(695);
		when "01010111000" => data <= rom_array(696);
		when "01010111001" => data <= rom_array(697);
		when "01010111010" => data <= rom_array(698);
		when "01010111011" => data <= rom_array(699);
		when "01010111100" => data <= rom_array(700);
		when "01010111101" => data <= rom_array(701);
		when "01010111110" => data <= rom_array(702);
		when "01010111111" => data <= rom_array(703);
		when "01011000000" => data <= rom_array(704);
		when "01011000001" => data <= rom_array(705);
		when "01011000010" => data <= rom_array(706);
		when "01011000011" => data <= rom_array(707);
		when "01011000100" => data <= rom_array(708);
		when "01011000101" => data <= rom_array(709);
		when "01011000110" => data <= rom_array(710);
		when "01011000111" => data <= rom_array(711);
		when "01011001000" => data <= rom_array(712);
		when "01011001001" => data <= rom_array(713);
		when "01011001010" => data <= rom_array(714);
		when "01011001011" => data <= rom_array(715);
		when "01011001100" => data <= rom_array(716);
		when "01011001101" => data <= rom_array(717);
		when "01011001110" => data <= rom_array(718);
		when "01011001111" => data <= rom_array(719);
		when "01011010000" => data <= rom_array(720);
		when "01011010001" => data <= rom_array(721);
		when "01011010010" => data <= rom_array(722);
		when "01011010011" => data <= rom_array(723);
		when "01011010100" => data <= rom_array(724);
		when "01011010101" => data <= rom_array(725);
		when "01011010110" => data <= rom_array(726);
		when "01011010111" => data <= rom_array(727);
		when "01011011000" => data <= rom_array(728);
		when "01011011001" => data <= rom_array(729);
		when "01011011010" => data <= rom_array(730);
		when "01011011011" => data <= rom_array(731);
		when "01011011100" => data <= rom_array(732);
		when "01011011101" => data <= rom_array(733);
		when "01011011110" => data <= rom_array(734);
		when "01011011111" => data <= rom_array(735);
		when "01011100000" => data <= rom_array(736);
		when "01011100001" => data <= rom_array(737);
		when "01011100010" => data <= rom_array(738);
		when "01011100011" => data <= rom_array(739);
		when "01011100100" => data <= rom_array(740);
		when "01011100101" => data <= rom_array(741);
		when "01011100110" => data <= rom_array(742);
		when "01011100111" => data <= rom_array(743);
		when "01011101000" => data <= rom_array(744);
		when "01011101001" => data <= rom_array(745);
		when "01011101010" => data <= rom_array(746);
		when "01011101011" => data <= rom_array(747);
		when "01011101100" => data <= rom_array(748);
		when "01011101101" => data <= rom_array(749);
		when "01011101110" => data <= rom_array(750);
		when "01011101111" => data <= rom_array(751);
		when "01011110000" => data <= rom_array(752);
		when "01011110001" => data <= rom_array(753);
		when "01011110010" => data <= rom_array(754);
		when "01011110011" => data <= rom_array(755);
		when "01011110100" => data <= rom_array(756);
		when "01011110101" => data <= rom_array(757);
		when "01011110110" => data <= rom_array(758);
		when "01011110111" => data <= rom_array(759);
		when "01011111000" => data <= rom_array(760);
		when "01011111001" => data <= rom_array(761);
		when "01011111010" => data <= rom_array(762);
		when "01011111011" => data <= rom_array(763);
		when "01011111100" => data <= rom_array(764);
		when "01011111101" => data <= rom_array(765);
		when "01011111110" => data <= rom_array(766);
		when "01011111111" => data <= rom_array(767);
		when "01100000000" => data <= rom_array(768);
		when "01100000001" => data <= rom_array(769);
		when "01100000010" => data <= rom_array(770);
		when "01100000011" => data <= rom_array(771);
		when "01100000100" => data <= rom_array(772);
		when "01100000101" => data <= rom_array(773);
		when "01100000110" => data <= rom_array(774);
		when "01100000111" => data <= rom_array(775);
		when "01100001000" => data <= rom_array(776);
		when "01100001001" => data <= rom_array(777);
		when "01100001010" => data <= rom_array(778);
		when "01100001011" => data <= rom_array(779);
		when "01100001100" => data <= rom_array(780);
		when "01100001101" => data <= rom_array(781);
		when "01100001110" => data <= rom_array(782);
		when "01100001111" => data <= rom_array(783);
		when "01100010000" => data <= rom_array(784);
		when "01100010001" => data <= rom_array(785);
		when "01100010010" => data <= rom_array(786);
		when "01100010011" => data <= rom_array(787);
		when "01100010100" => data <= rom_array(788);
		when "01100010101" => data <= rom_array(789);
		when "01100010110" => data <= rom_array(790);
		when "01100010111" => data <= rom_array(791);
		when "01100011000" => data <= rom_array(792);
		when "01100011001" => data <= rom_array(793);
		when "01100011010" => data <= rom_array(794);
		when "01100011011" => data <= rom_array(795);
		when "01100011100" => data <= rom_array(796);
		when "01100011101" => data <= rom_array(797);
		when "01100011110" => data <= rom_array(798);
		when "01100011111" => data <= rom_array(799);
		when "01100100000" => data <= rom_array(800);
		when "01100100001" => data <= rom_array(801);
		when "01100100010" => data <= rom_array(802);
		when "01100100011" => data <= rom_array(803);
		when "01100100100" => data <= rom_array(804);
		when "01100100101" => data <= rom_array(805);
		when "01100100110" => data <= rom_array(806);
		when "01100100111" => data <= rom_array(807);
		when "01100101000" => data <= rom_array(808);
		when "01100101001" => data <= rom_array(809);
		when "01100101010" => data <= rom_array(810);
		when "01100101011" => data <= rom_array(811);
		when "01100101100" => data <= rom_array(812);
		when "01100101101" => data <= rom_array(813);
		when "01100101110" => data <= rom_array(814);
		when "01100101111" => data <= rom_array(815);
		when "01100110000" => data <= rom_array(816);
		when "01100110001" => data <= rom_array(817);
		when "01100110010" => data <= rom_array(818);
		when "01100110011" => data <= rom_array(819);
		when "01100110100" => data <= rom_array(820);
		when "01100110101" => data <= rom_array(821);
		when "01100110110" => data <= rom_array(822);
		when "01100110111" => data <= rom_array(823);
		when "01100111000" => data <= rom_array(824);
		when "01100111001" => data <= rom_array(825);
		when "01100111010" => data <= rom_array(826);
		when "01100111011" => data <= rom_array(827);
		when "01100111100" => data <= rom_array(828);
		when "01100111101" => data <= rom_array(829);
		when "01100111110" => data <= rom_array(830);
		when "01100111111" => data <= rom_array(831);
		when "01101000000" => data <= rom_array(832);
		when "01101000001" => data <= rom_array(833);
		when "01101000010" => data <= rom_array(834);
		when "01101000011" => data <= rom_array(835);
		when "01101000100" => data <= rom_array(836);
		when "01101000101" => data <= rom_array(837);
		when "01101000110" => data <= rom_array(838);
		when "01101000111" => data <= rom_array(839);
		when "01101001000" => data <= rom_array(840);
		when "01101001001" => data <= rom_array(841);
		when "01101001010" => data <= rom_array(842);
		when "01101001011" => data <= rom_array(843);
		when "01101001100" => data <= rom_array(844);
		when "01101001101" => data <= rom_array(845);
		when "01101001110" => data <= rom_array(846);
		when "01101001111" => data <= rom_array(847);
		when "01101010000" => data <= rom_array(848);
		when "01101010001" => data <= rom_array(849);
		when "01101010010" => data <= rom_array(850);
		when "01101010011" => data <= rom_array(851);
		when "01101010100" => data <= rom_array(852);
		when "01101010101" => data <= rom_array(853);
		when "01101010110" => data <= rom_array(854);
		when "01101010111" => data <= rom_array(855);
		when "01101011000" => data <= rom_array(856);
		when "01101011001" => data <= rom_array(857);
		when "01101011010" => data <= rom_array(858);
		when "01101011011" => data <= rom_array(859);
		when "01101011100" => data <= rom_array(860);
		when "01101011101" => data <= rom_array(861);
		when "01101011110" => data <= rom_array(862);
		when "01101011111" => data <= rom_array(863);
		when "01101100000" => data <= rom_array(864);
		when "01101100001" => data <= rom_array(865);
		when "01101100010" => data <= rom_array(866);
		when "01101100011" => data <= rom_array(867);
		when "01101100100" => data <= rom_array(868);
		when "01101100101" => data <= rom_array(869);
		when "01101100110" => data <= rom_array(870);
		when "01101100111" => data <= rom_array(871);
		when "01101101000" => data <= rom_array(872);
		when "01101101001" => data <= rom_array(873);
		when "01101101010" => data <= rom_array(874);
		when "01101101011" => data <= rom_array(875);
		when "01101101100" => data <= rom_array(876);
		when "01101101101" => data <= rom_array(877);
		when "01101101110" => data <= rom_array(878);
		when "01101101111" => data <= rom_array(879);
		when "01101110000" => data <= rom_array(880);
		when "01101110001" => data <= rom_array(881);
		when "01101110010" => data <= rom_array(882);
		when "01101110011" => data <= rom_array(883);
		when "01101110100" => data <= rom_array(884);
		when "01101110101" => data <= rom_array(885);
		when "01101110110" => data <= rom_array(886);
		when "01101110111" => data <= rom_array(887);
		when "01101111000" => data <= rom_array(888);
		when "01101111001" => data <= rom_array(889);
		when "01101111010" => data <= rom_array(890);
		when "01101111011" => data <= rom_array(891);
		when "01101111100" => data <= rom_array(892);
		when "01101111101" => data <= rom_array(893);
		when "01101111110" => data <= rom_array(894);
		when "01101111111" => data <= rom_array(895);
		when "01110000000" => data <= rom_array(896);
		when "01110000001" => data <= rom_array(897);
		when "01110000010" => data <= rom_array(898);
		when "01110000011" => data <= rom_array(899);
		when "01110000100" => data <= rom_array(900);
		when "01110000101" => data <= rom_array(901);
		when "01110000110" => data <= rom_array(902);
		when "01110000111" => data <= rom_array(903);
		when "01110001000" => data <= rom_array(904);
		when "01110001001" => data <= rom_array(905);
		when "01110001010" => data <= rom_array(906);
		when "01110001011" => data <= rom_array(907);
		when "01110001100" => data <= rom_array(908);
		when "01110001101" => data <= rom_array(909);
		when "01110001110" => data <= rom_array(910);
		when "01110001111" => data <= rom_array(911);
		when "01110010000" => data <= rom_array(912);
		when "01110010001" => data <= rom_array(913);
		when "01110010010" => data <= rom_array(914);
		when "01110010011" => data <= rom_array(915);
		when "01110010100" => data <= rom_array(916);
		when "01110010101" => data <= rom_array(917);
		when "01110010110" => data <= rom_array(918);
		when "01110010111" => data <= rom_array(919);
		when "01110011000" => data <= rom_array(920);
		when "01110011001" => data <= rom_array(921);
		when "01110011010" => data <= rom_array(922);
		when "01110011011" => data <= rom_array(923);
		when "01110011100" => data <= rom_array(924);
		when "01110011101" => data <= rom_array(925);
		when "01110011110" => data <= rom_array(926);
		when "01110011111" => data <= rom_array(927);
		when "01110100000" => data <= rom_array(928);
		when "01110100001" => data <= rom_array(929);
		when "01110100010" => data <= rom_array(930);
		when "01110100011" => data <= rom_array(931);
		when "01110100100" => data <= rom_array(932);
		when "01110100101" => data <= rom_array(933);
		when "01110100110" => data <= rom_array(934);
		when "01110100111" => data <= rom_array(935);
		when "01110101000" => data <= rom_array(936);
		when "01110101001" => data <= rom_array(937);
		when "01110101010" => data <= rom_array(938);
		when "01110101011" => data <= rom_array(939);
		when "01110101100" => data <= rom_array(940);
		when "01110101101" => data <= rom_array(941);
		when "01110101110" => data <= rom_array(942);
		when "01110101111" => data <= rom_array(943);
		when "01110110000" => data <= rom_array(944);
		when "01110110001" => data <= rom_array(945);
		when "01110110010" => data <= rom_array(946);
		when "01110110011" => data <= rom_array(947);
		when "01110110100" => data <= rom_array(948);
		when "01110110101" => data <= rom_array(949);
		when "01110110110" => data <= rom_array(950);
		when "01110110111" => data <= rom_array(951);
		when "01110111000" => data <= rom_array(952);
		when "01110111001" => data <= rom_array(953);
		when "01110111010" => data <= rom_array(954);
		when "01110111011" => data <= rom_array(955);
		when "01110111100" => data <= rom_array(956);
		when "01110111101" => data <= rom_array(957);
		when "01110111110" => data <= rom_array(958);
		when "01110111111" => data <= rom_array(959);
		when "01111000000" => data <= rom_array(960);
		when "01111000001" => data <= rom_array(961);
		when "01111000010" => data <= rom_array(962);
		when "01111000011" => data <= rom_array(963);
		when "01111000100" => data <= rom_array(964);
		when "01111000101" => data <= rom_array(965);
		when "01111000110" => data <= rom_array(966);
		when "01111000111" => data <= rom_array(967);
		when "01111001000" => data <= rom_array(968);
		when "01111001001" => data <= rom_array(969);
		when "01111001010" => data <= rom_array(970);
		when "01111001011" => data <= rom_array(971);
		when "01111001100" => data <= rom_array(972);
		when "01111001101" => data <= rom_array(973);
		when "01111001110" => data <= rom_array(974);
		when "01111001111" => data <= rom_array(975);
		when "01111010000" => data <= rom_array(976);
		when "01111010001" => data <= rom_array(977);
		when "01111010010" => data <= rom_array(978);
		when "01111010011" => data <= rom_array(979);
		when "01111010100" => data <= rom_array(980);
		when "01111010101" => data <= rom_array(981);
		when "01111010110" => data <= rom_array(982);
		when "01111010111" => data <= rom_array(983);
		when "01111011000" => data <= rom_array(984);
		when "01111011001" => data <= rom_array(985);
		when "01111011010" => data <= rom_array(986);
		when "01111011011" => data <= rom_array(987);
		when "01111011100" => data <= rom_array(988);
		when "01111011101" => data <= rom_array(989);
		when "01111011110" => data <= rom_array(990);
		when "01111011111" => data <= rom_array(991);
		when "01111100000" => data <= rom_array(992);
		when "01111100001" => data <= rom_array(993);
		when "01111100010" => data <= rom_array(994);
		when "01111100011" => data <= rom_array(995);
		when "01111100100" => data <= rom_array(996);
		when "01111100101" => data <= rom_array(997);
		when "01111100110" => data <= rom_array(998);
		when "01111100111" => data <= rom_array(999);
		when "01111101000" => data <= rom_array(1000);
		when "01111101001" => data <= rom_array(1001);
		when "01111101010" => data <= rom_array(1002);
		when "01111101011" => data <= rom_array(1003);
		when "01111101100" => data <= rom_array(1004);
		when "01111101101" => data <= rom_array(1005);
		when "01111101110" => data <= rom_array(1006);
		when "01111101111" => data <= rom_array(1007);
		when "01111110000" => data <= rom_array(1008);
		when "01111110001" => data <= rom_array(1009);
		when "01111110010" => data <= rom_array(1010);
		when "01111110011" => data <= rom_array(1011);
		when "01111110100" => data <= rom_array(1012);
		when "01111110101" => data <= rom_array(1013);
		when "01111110110" => data <= rom_array(1014);
		when "01111110111" => data <= rom_array(1015);
		when "01111111000" => data <= rom_array(1016);
		when "01111111001" => data <= rom_array(1017);
		when "01111111010" => data <= rom_array(1018);
		when "01111111011" => data <= rom_array(1019);
		when "01111111100" => data <= rom_array(1020);
		when "01111111101" => data <= rom_array(1021);
		when "01111111110" => data <= rom_array(1022);
		when "01111111111" => data <= rom_array(1023);
		when "10000000000" => data <= rom_array(1024);
		when "10000000001" => data <= rom_array(1025);
		when "10000000010" => data <= rom_array(1026);
		when "10000000011" => data <= rom_array(1027);
		when "10000000100" => data <= rom_array(1028);
		when "10000000101" => data <= rom_array(1029);
		when "10000000110" => data <= rom_array(1030);
		when "10000000111" => data <= rom_array(1031);
		when "10000001000" => data <= rom_array(1032);
		when "10000001001" => data <= rom_array(1033);
		when "10000001010" => data <= rom_array(1034);
		when "10000001011" => data <= rom_array(1035);
		when "10000001100" => data <= rom_array(1036);
		when "10000001101" => data <= rom_array(1037);
		when "10000001110" => data <= rom_array(1038);
		when "10000001111" => data <= rom_array(1039);
		when "10000010000" => data <= rom_array(1040);
		when "10000010001" => data <= rom_array(1041);
		when "10000010010" => data <= rom_array(1042);
		when "10000010011" => data <= rom_array(1043);
		when "10000010100" => data <= rom_array(1044);
		when "10000010101" => data <= rom_array(1045);
		when "10000010110" => data <= rom_array(1046);
		when "10000010111" => data <= rom_array(1047);
		when "10000011000" => data <= rom_array(1048);
		when "10000011001" => data <= rom_array(1049);
		when "10000011010" => data <= rom_array(1050);
		when "10000011011" => data <= rom_array(1051);
		when "10000011100" => data <= rom_array(1052);
		when "10000011101" => data <= rom_array(1053);
		when "10000011110" => data <= rom_array(1054);
		when "10000011111" => data <= rom_array(1055);
		when "10000100000" => data <= rom_array(1056);
		when "10000100001" => data <= rom_array(1057);
		when "10000100010" => data <= rom_array(1058);
		when "10000100011" => data <= rom_array(1059);
		when others => data <= X"00000000";
		end case;
	end process;


end Behavioral;

