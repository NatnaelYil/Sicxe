# Sicxe

This program simply implements a symbol table that will be used by the assembler during pass1 and pass2. 
The program constructs an efficient hashing table, by reading text file as a command line argument.
After reading this file the �main � routine creates a symbol table by reading each line of the text file as a string.
This line string is then split into two strings and inserted into a string array. If a line consists of only one string,
then the string is searched in the symbol table for retrieval, if it is found then the array index and element is displayed in the output text file, 
error is shown if the file string doesn�t exist. if a line contains two string, the first string is hashed into the symbol table using the hashfunction. 
If collisions are detected, it is saved in the output text file or if there is no collision the elements index and the element is saved in the output file.  

Example of the input and output is displayed below:

Input.txt:
moss 25
moss 35
eno
fred
gorge 18
nat 123
yilma 
moss 25
joshpej 12

The Ouput text file will hold:

Hashing in position 8 - moss
Error 'moss' already exist at location -> 8
ERROR 'eno' not found.
ERROR 'fred' not found.
Hashing in position 1 - gorge
Hashing in position 10 - nat
ERROR 'yilma' not found.
Error 'moss' already exist at location -> 8
Hashing in position 6 - joshpej
The elements in the array ...



At Index-1	 gorge 18
At Index-6	 joshpej 12
At Index-8	 moss 25
At Index-10	 nat 123



