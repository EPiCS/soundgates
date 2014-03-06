#!/usr/bin/env python
import glob
import os
import subprocess

for file in glob.glob("*.coffee"):
	print 'Compiling: ', file
	proc = subprocess.Popen(['coffee', '-c', file], stdout=subprocess.PIPE)
	stdout, stderr = proc.communicate()
