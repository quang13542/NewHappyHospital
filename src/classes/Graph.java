package classes;

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
    
    for (int i = 0; i < this.agents.length; i++) {
      Agent agent = this.agents[i];
      if (agent.active) {
        int xl = (int)Math.floor(agent.getX() / 32);
        int xr = (int)Math.floor((agent.getX() + 31) / 32);
        int yt = (int)Math.floor(agent.getY() / 32);
        int yb = (int)Math.floor((agent.getY() + 31) / 32);
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

	public Node calPathAStar(Node start, Node end) {
    /**
       * Khoi tao cac bien trong A*
    */
	Set<Node> openSet;
	Set<Node> closeSet;
	Node[] path;
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
          astar_f[(int)(openSet.toArray()[i])][(int)(openSet.toArray()[i].y] < astar_f[openSet[winner].x][openSet[winner].y]
        ) {
          winner = i;
        }
      }
      let current = openSet[winner];
      if (openSet[winner].equal(end)) {
        let cur: Node2D = this.nodes[end.x][end.y];
        path.push(cur);
        while (previous[cur.x][cur.y] != undefined) {
          path.push(previous[cur.x][cur.y]);
          cur = previous[cur.x][cur.y];
        }
        path.reverse();
        //console.assert(lengthOfPath == path.length, "path has length: " + path.length + " instead of " + lengthOfPath);
        return path;
      }
      openSet.splice(winner, 1);
      closeSet.push(current);
      let neighbors = [current.nodeN, current.nodeE, current.nodeS, current.nodeW,
                       current.nodeVN, current.nodeVE, current.nodeVS, current.nodeVW ];
      
      for (let i = 0; i < neighbors.length; i++) {
        let neighbor = neighbors[i];
        if (neighbor != null) {
          let timexoay = 0;
          if (
            previous[current.x][current.y] &&
            neighbor.x != previous[current.x][current.y].x &&
            neighbor.y != previous[current.x][current.y].y
          ) {
            timexoay = 1;
          }
          let tempG =
          astar_g[current.x][current.y] + 1 + current.getW() + timexoay;

          if (!this.isInclude(neighbor, closeSet)) {
            if (this.isInclude(neighbor, openSet)) {
              if (tempG < astar_g[neighbor.x][neighbor.y]) {
                astar_g[neighbor.x][neighbor.y] = tempG;
              }
            } else {
              astar_g[neighbor.x][neighbor.y] = tempG;
              openSet.push(neighbor);
              lengthOfPath++;
            }
            astar_h[neighbor.x][neighbor.y] = this.heuristic(neighbor, end);
            astar_f[neighbor.x][neighbor.y] =
              astar_h[neighbor.x][neighbor.y] + astar_g[neighbor.x][neighbor.y];
            previous[neighbor.x][neighbor.y] = current;
          } else {
            if (tempG < astar_g[neighbor.x][neighbor.y]) {
              openSet.push(neighbor);
              const index = closeSet.indexOf(neighbor);
              if (index > -1) {
                closeSet.splice(index, 1);
              }
            }
          }
        }
      }
    }//end of while (openSet.length > 0)
    console.log("Path not found!");
    return null;
  }

	public isInclude(node: Node2D, nodes: Node2D[]): boolean {
    for (let i = 0; i < nodes.length; i++) {
      if (node.equal(nodes[i])) return true;
    }
    return false;
  }

	public heuristic(node1: Node2D, node2: Node2D): number {
    return Math.abs(node1.x - node2.x) + Math.abs(node1.y - node2.y);
  }
}
