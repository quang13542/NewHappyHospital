package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import classes.*;
import classes.StateOfNode;

public class Graph {
	public Node[][] nodes;
	public int width;
	public int height;
	public Agent[] agents;
	public int[][] busy;
	public Position[] pathPos;
	private Set<AutoAgv> autoAgvs;
	private Agv agv;

	public Graph(int width, int height, Position[][][] danhsachke, Position[] pathPos
	// scene: MainScene
	) {
		this.width = width;
		this.height = height;
		this.nodes = new Node[width][height];
		this.pathPos = pathPos;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.nodes[i][j] = new Node(i, j, true, StateOfNode.NOT_ALLOW, 0, 0, 0);
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < danhsachke[i][j].length; k++) {
					Position nutke = danhsachke[i][j][k];
					this.nodes[i][j].setNeighbor(this.nodes[nutke.x][nutke.y]);
				}
			}
		}
		for (Position p : pathPos) {
			this.nodes[p.x][p.y].setState(StateOfNode.EMPTY);
		}

		this.busy = new int[52][28];
		for (int i = 0; i < 52; i++) {
			for (int j = 0; j < 28; j++) {
				if (this.nodes[i][j].state == StateOfNode.EMPTY) {
					this.busy[i][j] = 0;
				} else {
					this.busy[i][j] = 2;
				}
			}
		}
	}

	public void setAutoAgvs(Set<AutoAgv> agvs) {
		this.autoAgvs = agvs;
	}

	public Set<AutoAgv> getAutoAgvs() {
		return this.autoAgvs;
	}

	public void setMAgv(Agv agv) {
		this.agv = agv;
	}

	public void setAgents(Agent[] agents) {
		for (Position p : this.pathPos) {
			this.nodes[p.x][p.y].setState(StateOfNode.EMPTY);
		}
		this.busy = new int[52][28];
		for (int i = 0; i < 52; i++) {
			for (int j = 0; j < 28; j++) {
				if (this.nodes[i][j].state == StateOfNode.EMPTY) {
					this.busy[i][j] = 0;
				} else {
					this.busy[i][j] = 2;
				}
			}
		}
		this.agents = agents;
	}

	public void updateState() {
    int[][] cur = new int[52][28];
    for (int i = 0; i < 52; i++) {
      for (int j = 0; j < 28; j++) {
        cur[i][j] = 0;
      }
    }

        for (Agent agent : this.agents) {
            if (agent.active) {
                int xl = (int) Math.floor(agent.getX() / 32);
                int xr = (int) Math.floor((agent.getX() + 31) / 32);
                int yt = (int) Math.floor(agent.getY() / 32);
                int yb = (int) Math.floor((agent.getY() + 31) / 32);
                cur[xl][yt] = 1;
                cur[xl][yb] = 1;
                cur[xr][yt] = 1;
                cur[xr][yb] = 1;
            }
        }
    
    for (int i = 0; i < 52; i++) {
      for (int j = 0; j < 28; j++) {
        if (this.busy[i][j] == 2) {
          continue;
        } else if (this.busy[i][j] == 0) {
          if ((cur[i][j] == 0)) continue;
          this.nodes[i][j].setState(StateOfNode.BUSY);
          this.busy[i][j] = 1;
        } else {
          if (cur[i][j] == 1) continue;
          this.nodes[i][j].setState(StateOfNode.EMPTY);
          this.busy[i][j] = 0;
        }
      }
    }
  }

	public void removeAgent(Agent agent) {
    int i = (int)(agent.getX() / 32);
    int j = (int)(agent.getY() / 32);
    this.nodes[i][j].setState(StateOfNode.EMPTY);
    this.busy[i][j] = 0;
  }

	public ArrayList<Node> calPathAStar(Node start, Node end) {
    /**
       * Khoi tao cac bien trong A*
    */
	ArrayList<Node> openSet = new ArrayList<>();
	ArrayList<Node> closeSet = new ArrayList<>();;
	ArrayList<Node> path = new ArrayList<>();;
	int[][] astar_f = new int[this.width][];
	int[][] astar_g = new int[this.width][];
	int[][] astar_h = new int[this.width][];
    Node[][] previous = new Node[this.width][];
    
    for (int i = 0; i < this.width; i++) {
      astar_f[i] = new int[this.height];
      astar_g[i] = new int[this.height];
      astar_h[i] = new int[this.height];
      previous[i] = new Node[this.height];
      for (int j = 0; j < this.height; j++) {
        astar_f[i][j] = 0;
        astar_g[i][j] = 0;
        astar_h[i][j] = 0;
      }
    }
    
    int lengthOfPath = 1;
    /**
     * Thuat toan
     */
    
    openSet.add(this.nodes[(int)start.x][(int)start.y]);
    while (openSet.size() > 0) {
      int winner = 0;
      for (int i = 0; i < openSet.size(); i++) {
        if (
          astar_f[(int)(openSet.get(i).x)][(int)(openSet.get(i).y)] < astar_f[(int) openSet.get(winner).x][(int) openSet.get(winner).y]
        ) {
          winner = i;
        }
      }
      Node current = (Node) openSet.toArray()[winner];
      if (openSet.get(winner).equal(end)) {
    	Node cur = this.nodes[(int) end.x][(int) end.y];
        path.add(cur);
        while (previous[(int) cur.x][(int) cur.y] != null) {
          path.add(previous[(int) cur.x][(int) cur.y]);
          cur = previous[(int) cur.x][(int) cur.y];
        }
          Collections.reverse(path);
        return path;
      }
      openSet.remove(winner);
      closeSet.add(current);
      Node[] neighbors = {current.nodeN, current.nodeE, current.nodeS, current.nodeW, current.nodeVN, current.nodeVE, current.nodeVS, current.nodeVW};

      for (int i = 0; i < neighbors.length; i++) {
        Node neighbor = neighbors[i];
        if (neighbor != null) {
          int timexoay = 0;
          if (
            previous[(int) current.x][(int) current.y] != null &&
            neighbor.x != previous[(int) current.x][(int) current.y].x &&
            neighbor.y != previous[(int) current.x][(int) current.y].y
          ) {
            timexoay = 1;
          }
          double tempG = astar_g[(int) current.x][(int) current.y] + 1 + current.getW() + timexoay;

          if (!this.isInclude(neighbor, closeSet)) {
            if (this.isInclude(neighbor, openSet)) {
              if (tempG < astar_g[(int) neighbor.x][(int) neighbor.y]) {
                astar_g[(int) neighbor.x][(int) neighbor.y] = (int) tempG;
              }
            } else {
              astar_g[(int) neighbor.x][(int) neighbor.y] = (int) tempG;
              openSet.add(neighbor);
              lengthOfPath++;
            }
            astar_h[(int) neighbor.x][(int) neighbor.y] = (int) this.heuristic(neighbor, end);
            astar_f[(int) neighbor.x][(int) neighbor.y] =
              astar_h[(int) neighbor.x][(int) neighbor.y] + astar_g[(int) neighbor.x][(int) neighbor.y];
            previous[(int) neighbor.x][(int) neighbor.y] = current;
          } else {
            if (tempG < astar_g[(int) neighbor.x][neighbor.y]) {
              openSet.add(neighbor);
              int index = closeSet.indexOf(neighbor);
              if (index > -1) {
                closeSet.remove(index);
              }
            }
          }
        }
      }
    }//end of while (openSet.length > 0)
    return null;
  }

	public boolean isInclude(Node node, ArrayList<Node> nodes) {
        for (Node value : nodes) {
            if (node.equal(value)) return true;
        }
    return false;
  }

	public double heuristic(Node node1, Node node2) {
    return Math.abs(node1.x - node2.x) + Math.abs(node1.y - node2.y);
  }
}
