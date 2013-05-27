#!/usr/bin/env python
import binascii
import math
import sys, getopt
import errno
import struct
# Read file
ARRAYNAME = "rom_array"

OUTPUTSIGNAL = "data_out"

entity_name = "SampleDataRom"

sample_width = 24;

if(len(sys.argv) != 2):
	print "Error: Start this script with \'filename\' as argument"
	print "Exiting..."
	sys.exit(errno.EACCES)

filename = sys.argv[1]
print "Converting file: " + filename


#hexcontent = binascii.hexlify(content)

#if(hexcontent.__len__() % 8 != 0):
#	print "hex values % 8 must ne 0"
#	sys.exit(errno.EACCES)

#charcount = hexcontent.__len__()
#datacount = charcount / 8


datacount = 0
# Writing file .mem
# Content:
# type rom_type is array (0 to 15) of std_logic_vector(7 downto 0);
#
# constant ADC_array : rom_type :=
# ( 0 => "00010000",
# 1 => "00010001",
# 2 => "00010010"); 
# ...
try:
	filenameOutput = entity_name + ".vhd"
	f = open(filenameOutput, "w")
	
	f.write("-- Derived from " + filename + "\n");
	
	try:
		## Header
		f.write("library IEEE;\n");
		f.write("use IEEE.STD_LOGIC_1164.ALL;\n");
		f.write("use IEEE.NUMERIC_STD.ALL;\n");

		f.write("entity " + entity_name + " is\n");
		f.write("Port ( clk    	 : in  STD_LOGIC;\n");
		f.write("	  	enable 	 : in STD_LOGIC;\n");
		f.write("	  	data_out : out std_logic_vector( " + str(sample_width) + " - 1 downto 0);\n");
		f.write("		rst    	 : in std_logic );\n");
		f.write("end " + entity_name +";\n");
		f.write("architecture Behavioral of SampleDataRom is\n");
		
		## Content
	
		line = "constant " + ARRAYNAME + " : rom_type := \n"
		line = line + "( "
		
		fmtString = '{0:0' + str(sample_width) + 'b}'
		
		## open pcm data file
		with open(filename, 'rb') as fin:
			sample = fin.read(sample_width / 8)	# read 6 bytes
			while sample:
				sample = fin.read(sample_width / 8)
				if len(sample) == (sample_width / 8):
				
					s_sample = struct.unpack('<i',  sample + "\x00")[0]
					
					sample_data_word = str(datacount) + " => \"" + fmtString.format(s_sample) + "\",\n"
					
					datacount = datacount + 1
					
					line = line + sample_data_word
		
		line = line[0 : len(line) - 2]	# to delete last ','
		
		line = "\ttype rom_type is array (0 to " + str(datacount-1) + ") of std_logic_vector(" + str(sample_width) +" - 1 downto 0);\n" + line
		
		
		f.write(line)
		f.write(");\n");
		'''
			for i in range (0, charcount, 8):
				if (i < charcount-8):
					line = "\t" + str(i/8) + " => X\"" + hexcontent[i:i+8] + "\",\n"
				else:
					line = "\t" + str(i/8) + " => X\"" + hexcontent[i:i+8] + "\");\n"
			f.write(line)
		'''
		## Adress pointer
		
		addresswidth = int(math.ceil(math.log(datacount, 2)))
		
		f.write("signal count : std_logic_vector(" + str(addresswidth) + " -1 downto 0) := (others => '0');\n");
		
		## Begin architecture
		f.write("begin\n");
		
		
		## Adressing process
		longestbitcount = bin(datacount)
		longestbitcount = longestbitcount[2:]
		longestbitcount = len(longestbitcount) 
		getBin = lambda x: x >= 0 and str(bin(x))[2:].zfill(longestbitcount) or "-" + str(bin(x))[3:].zfill(longestbitcount)
	
	
		line = "\tgetRomData: process (count)\n\tbegin\n\t\tcase count is\n"
		f.write(line)
		for i in range (0, datacount):
			line = "\t\twhen \"" + getBin(i) + "\" => " + OUTPUTSIGNAL + " <= " + ARRAYNAME + "("+str(i)+");\n"
			f.write(line)
			
		
		line = "\t\twhen others => " + OUTPUTSIGNAL + " <= " + "(others => '0');\n"
		line = line + "\t\tend case;\n\tend process;\n"
		f.write(line)
		
		## Control process
		f.write("ClockProc: process (clk, rst) is\n");
		f.write("begin\n");
		f.write("if rising_edge(clk) then\n");
		f.write("	if rst = '1' then\n");
		f.write("		count <= (others => '0');\n");
		f.write("	end if;\n");
		f.write("	if (ENABLE = '1') then\n");
		f.write("		if (unsigned(count) < rom_type'length) then\n");
		f.write("			count <= std_logic_vector(unsigned(count) + 1);\n");
		f.write("		else\n");
		f.write("			count <= (others => '0');\n");
		f.write("		end if;\n");
		f.write("	end if;\n");
		f.write("end if;\n");
		f.write("end process;\n");
		
		f.write("end Behavioral;\n");
	finally:
		print "Done."
		f.close()

except IOError:
	print "An error occured during I/O-action"
	pass

# Writing file .process
# Content:
# begin
# process (count)
# begin
# case count is
# when "0000" => data <= ADC_array(0);
# when "0001" => data <= ADC_array(1);
# when others => data <= "00000000";
# end case;
# end process;

'''
try:
	filenameOutput = filename + ".process" 
	f = open(filenameOutput, "w")

	try:
		line = "\tgetRomData: process (count)\n\tbegin\n\t\tcase count is\n"
		f.write(line)
		for i in range (0, datacount):
			line = "\t\twhen \"" + getBin(i) + "\" => " + OUTPUTSIGNAL + " <= " + ARRAYNAME + "("+str(i)+");\n"
			f.write(line)

	finally:
		line = "\t\twhen others => " + OUTPUTSIGNAL + " <= " + "X\"00000000\";\n"
		line = line + "\t\tend case;\n\tend process;\n"
		f.write(line)
		print "Done writing to: " + filenameOutput
		f.close()

except IOError:
	pass
'''
	

