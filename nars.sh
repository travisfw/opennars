#!/bin/sh

# Usage:
#   Console I/O
#       nars.sh 
#   Input from file
#       nars.sh [filename]

java -cp dist/OpenNARS.jar nars.core.NARRun $1



