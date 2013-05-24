# Script pour convertir rapidement un repertoire de fichiers svg en pdf avec inkscape.exe
# Par Clement Perreau
#

import os

if __name__ == "__main__":
	
	fileList = os.listdir(".")
	
	for input in fileList:
		real_input = input
		try:
			real_input = input.split(" ")[1]
			os.rename(input, real_input)
		except:
			pass
		if(input[len(real_input)-3:] == "svg"):
			output = input[:len(real_input)-3] + "pdf"
			print real_input + " --> " + output
			os.system("inkscape --export-text-to-path --without-gui --file=" + real_input + " --export-pdf="+ output)
		
	