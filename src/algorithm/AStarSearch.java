package algorithm;

import classes.Position;
import structure.DynamicSpotArray;

public class AStarSearch {
	public int width, height;
	public Spot start, end;
	public Spot[] ableSpot;
	public Spot[][] grid;
	public DynamicSpotArray path;
	
	public AStarSearch(int width, int height, Position startPos, Position endPos, Position[] ablePos){
		this.width = width;
	    this.height = height;
	    this.start = new Spot(startPos.x, startPos.y);
	    this.end = new Spot(endPos.x, endPos.y);
	
		this.grid = new Spot[width][height];
	    for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.grid[i][j] = new Spot(i, j);
			}
	    }
	    
	    this.ableSpot = new Spot[ablePos.length];
	    for (int i = 0; i < ablePos.length; i++) {
	    	this.ableSpot[i] = this.grid[ablePos[i].x][ablePos[i].y];
	    }
	    
	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	        	this.grid[i][j].addNeighbors(this.ableSpot);
	        }
	    }
	}
	
	private double heuristic(Spot spot1, Spot spot2) {
		return Math.abs(spot1.i - spot2.i) + Math.abs(spot1.j - spot2.j);
	}

	private double heuristic2(Spot spot1, Spot spot2) {
	    return Math.sqrt(Math.pow((spot1.i - spot2.i), 2) + Math.pow((spot1.j - spot2.j), 2));
	}

	private boolean isInclude(Spot spot, Spot[] spots) {
	    for (int i = 0; i < spots.length; i++) {
	        if (spot.i == spots[i].i && spot.j == spots[i].j) return true;
	    }
	    return false;
	}
	
	public Position[] cal() {
	    DynamicSpotArray openSet = new DynamicSpotArray();
	    DynamicSpotArray closeSet = new DynamicSpotArray();
	    openSet.addElement(this.grid[this.start.i][this.start.j]);
	    while (openSet.count > 0) {
	      int winner = 0;
	      for (int i = 0; i < openSet.count; i++) {
	          if (openSet.get(i).f < openSet.get(winner).f) {
	        	  winner = i;
	          }
	      }

	      Spot current = openSet.get(winner);

	      if (openSet.get(winner).equal(this.end)) {
	        Spot cur = this.grid[this.end.i][this.end.j];
	        this.path.addElement(cur);
	        while (cur.previous != null) {
	          this.path.addElement(cur.previous);
	          cur = cur.previous;
	        }
	        this.path.reverse();
	        Position[] result = new Position[this.path.count];
	        for (int k = 0; k < this.path.count; k++) {
	          result[k] = new Position(this.path.get(k).i, this.path.get(k).j);
	        }
	        return result;
	      }

	      openSet.removeElementAt(winner);
	      closeSet.addElement(current);

	      DynamicSpotArray neighbors = current.neighbors;

	      for (int i = 0; i < neighbors.count; i++) {
	        Spot neighbor = neighbors.get(i);
	        if (!this.isInclude(neighbor, closeSet.array)) {
	          int tempG = current.g + 1;
	          if (this.isInclude(neighbor, openSet.array)) {
	            if (tempG < neighbor.g) {
	              neighbor.g = tempG;
	            }
	          } else {
	            neighbor.g = tempG;
	            openSet.addElement(neighbor);
	          }

	          neighbor.h = this.heuristic2(neighbor, this.end);
	          neighbor.f = neighbor.h + neighbor.g;
	          neighbor.previous = current;
	        } else {
	          int tempG = current.g + 1;
	          if (tempG < neighbor.g) {
	            openSet.addElement(neighbor);
	            int id;
	            id = closeSet.indexOf(neighbor);
	            if (id > -1) {
	              closeSet.removeElementAt(id);
	            }
	          }
	        }
	      }
	    }
//	    console.log("Path not found!");
	    return null;
	  }
}
