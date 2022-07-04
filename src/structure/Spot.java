package structure;
import java.util.ArrayList;

public class Spot {
	  public int i;
	  public int j;
	  public int f;
	  public int g;
	  public int h;
	  public ArrayList<Spot> neighbors = new ArrayList<>();
	  public Spot previous;
	  public Spot(int i, int j, int f, int g, int h, ArrayList<Spot> neighbors, Spot previous) {
			super();
			this.i = i;
			this.j = j;
			this.f = f;
			this.g = g;
			this.h = h;
			this.neighbors = neighbors;
			this.previous = previous;
		}
//	  constructor(i: number, j: number) {
//	    this.i = i;
//	    this.j = j;
//	    this.f = 0;
//	    this.g = 0;
//	    this.h = 0;
//	  }

	  public void addNeighbors(Spot[] ableSpot) {
	    for (int k = 0; k < ableSpot.length; k++) {
	      if (this.i + 1 == ableSpot[k].i && this.j == ableSpot[k].j) {
	        this.neighbors.add(ableSpot[k]);
	      } else if (this.i == ableSpot[k].i && this.j + 1 == ableSpot[k].j) {
	        this.neighbors.add(ableSpot[k]);
	      } else if (this.i - 1 == ableSpot[k].i && this.j == ableSpot[k].j) {
	        this.neighbors.add(ableSpot[k]);
	      } else if (this.i == ableSpot[k].i && this.j - 1 == ableSpot[k].j) {
	        this.neighbors.add(ableSpot[k]);
	      }
	    }
	  }

	public boolean equal(Spot spot) {
	    if (this.i == spot.i && this.j == spot.j) return true;
	    return false;
	  }
}
