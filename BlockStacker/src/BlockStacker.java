import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BlockStacker {
	// File things
	public static int numBlocks = 0;
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	public ArrayList<Block> choices = new ArrayList<Block>();

	// Table of height values
	int[] table;
	
	int bestHeight;

	public BlockStacker() {

		//Add all orientations of all blocks to an array
		for (int i = 0; i < blocks.size(); i++) {
			choices.addAll(blocks.get(i).iterate());
		}
		
		// Creates dynamic programming table
		table = new int[choices.size()];

		//Turns block ArrayList into an array to be sorted
		Block[] nb = new Block[choices.size()];
		for (int i = 0; i < nb.length; i++) {
			nb[i] = choices.get(i);
		}
		
		//Sorts
		Arrays.sort(nb);
		
		//Sets this back into the ArrayList
		for (int i = 0; i < nb.length; i++) {
			choices.set(i, nb[i]);
		}
		

	}

	public void MakeTable(File outF) throws IOException {

		//Blocks to be compared
		Block currentBlock;
		Block prevBlock;
		
		//Tracks what the highest dynamic programming solution is
		int tracker = 0;
		
		//Tracks which entry in the table this is
		int whichBlock = 0;
		
		bestHeight = 0;
		int solNum = 0;
		
		ArrayList<ArrayList<Block>> sol = new ArrayList<ArrayList<Block>>();

		// Populate solution arrays with empty arrays
		for (int i = 0; i < choices.size(); i++) {
			sol.add(new ArrayList<Block>());

			// Set the solution to the initial block to start with
			sol.get(i).add(choices.get(i));
		}
		

		// Populate the table with initial height values
		for (int i = 0; i < choices.size(); i++) {
			table[i] = choices.get(i).getHeight();
		}

		// Outer loop to determine best solutions
		for (int i = 0; i < table.length; i++) {
			currentBlock = choices.get(i);
			table[i] = 0;
			tracker = 0;

			//System.out.println(currentBlock);
			for (int j = 0; j < i; j++) {
				prevBlock = choices.get(j);

				// Find the best stackable block, determine which block that is
				if (currentBlock.isStackable(prevBlock)) {
					//Which block is it?
					if (table[j] > tracker) {
						whichBlock = j;
					}
					//Track the best value
					tracker = Math.max(tracker, table[j]);

				}

			}

			//If the found block is correct and can stack, add it as a solution
			if (whichBlock != i && currentBlock.isStackable(sol.get(whichBlock).get(0))) {
				// Now add the blocks that contributed to the solution
				sol.get(i).addAll(sol.get(whichBlock));
				
			}
			
			//Set dynamic programming table
			table[i] = tracker + currentBlock.getHeight();


			//Set the best height and track where the solution is contained in the dynamic programming
			//table
			if(table[i] > bestHeight) {
				bestHeight = table[i];
				solNum = i;
			}

		}
		
		//Print statement
		System.out.println(
		"The tallest tower has " + sol.get(solNum).size() + 
		" blocks and has height " + table[solNum]);

		
		//Write to an outfile
		if (!outF.exists()) {
			outF.createNewFile();
		}
		FileWriter out = new FileWriter(outF);

		ArrayList<Block> solution = sol.get(solNum);

		out.write(sol.get(solNum).size() + "\n");
		for (int i = (solution.size() - 1); i >= 0; i--) {
			out.write(solution.get(i) + "\n");
		}

		out.close();
	}

	public static void readIn(File inF) throws IOException {
		//Read in the infile
		FileReader in = null;
		in = new FileReader(inF);
		BufferedReader inB = new BufferedReader(in);

		numBlocks = Integer.parseInt(inB.readLine());

		String current = inB.readLine();

		while (current != null) {
			String[] nums = current.split(" ");
			Block toAdd = new Block(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
			// System.out.println(toAdd);

			blocks.add(toAdd);
			current = inB.readLine();
		}
		
		//Code below is to determine the heights of the given solutions

//		int tot = 0;
//
//		for (int i = 0; i < blocks.size(); i++) {
//			tot += blocks.get(i).h;
//		}
//
//		System.out.println("total height of input is " + tot);

		in.close();

	}

	public static void main(String[] args) throws IOException {
		readIn(new File(args[0]));

		BlockStacker bs = new BlockStacker();

		bs.MakeTable(new File(args[1]));

	}

}
