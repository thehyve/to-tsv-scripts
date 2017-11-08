#!/usr/bin/env python

import sys
import savReaderWriter

sav_file_name = sys.argv[1]

# DYLD_LIBRARY_PATH has to be set
with savReaderWriter.SavReader(sav_file_name, ioLocale='en_US.UTF-8') as reader:
	for line in reader:
		print(line)