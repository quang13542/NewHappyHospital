package algorithm;
import structure.DynamicSpotArray;

public class Spot {
	public int i, j, g;
	public double f, h;
	public DynamicSpotArray neighbors = new DynamicSpotArray();
	public Spot previous;			////
	
	public Spot(int i, int j){
		this.i = i;
		this.j = j;
		this.f = 0;
		this.g = 0;
		this.h = 0;
	}
	
	public void addNeighbors(Spot[] ableSpot) {
		for(int k=0; k<ableSpot.length; k++)
		{
			if (this.i + 1 == ableSpot[k].i && this.j == ableSpot[k].j) {
				this.neighbors.addElement(ableSpot[k]);
			} else if (this.i == ableSpot[k].i && this.j + 1 == ableSpot[k].j) {
				this.neighbors.addElement(ableSpot[k]);
			} else if (this.i - 1 == ableSpot[k].i && this.j == ableSpot[k].j) {
				this.neighbors.addElement(ableSpot[k]);
			} else if (this.i == ableSpot[k].i && this.j - 1 == ableSpot[k].j) {
				this.neighbors.addElement(ableSpot[k]);
			}
		}
	}

	public boolean equal(Spot spot) {
		if (this.i == spot.i && this.j == spot.j) return true;
	    return false;
	}
}
