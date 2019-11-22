import java.util.ArrayList;


public class Block implements Comparable<Block>{
	public int w, l, h, a;

	public Block(int l, int w, int h) {
		this.w = w;
		this.l = l;
		this.h = h;
		//Calculate area for sorting
		a = w * l;
	}

	public ArrayList<Block> iterate() {
		ArrayList<Block> ret = new ArrayList<Block>();
	
		//Return all of the different orientations of the block
		ret.add(new Block(w, l, h));
		ret.add(new Block(l, h, w));
		ret.add(new Block(h, w, l));
		ret.add(new Block(l, w, h));
		ret.add(new Block(h, l, w));
		ret.add(new Block(w, h, l));

		return ret;
	}

	public boolean isStackable(Block block2) {
		return this.getWidth() < block2.getWidth() && this.getLength() < block2.getLength();
	}

	public int getWidth() {
		return w;
	}

	public int getLength() {
		return l;
	}
	
	public int getHeight() {
		return h;
	}
	
	public int compareTo(Block o) {
		//Compares and sorts on area
		return o.a - this.a;
	}
	
	public String toString() {
		return l + " " + w + " " + h;
	}

}
