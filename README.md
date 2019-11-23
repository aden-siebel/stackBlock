# Block Stacking
## A dynamic programming project

Solves a dynamic programming problem, where three dimensional blocks need to be stacked on top of one another in an efficient way to create the tallest possible tower.
Completed for CS140 at Pomona. 

### How to run:
Make sure that the input file is in the same folder as where you are running the following commands. 
javac Block.java BlockStacker.java 
java BlockStacker -path to input.txt- -path to output.txt-

### Block.java
The block class creates a block and its other configurations. This contains functions on operations with blocks used in our main class, Block Stacker.

### BlockStacker.java
The block stacker class creates a stacker that takes in the first system argument (any text file) and will output an output file 'output.txt' with the most optimal number of blocks and the blocks listed below, largest to smallest. 
