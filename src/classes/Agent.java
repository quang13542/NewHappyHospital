package classes;

import algorithm.AStarSearch;
import classes.*;
import constant.*;

import java.util.ArrayList;
import java.util.List;

public class Agent extends Actor {
	private Position startPos;
	private Position endPos;
	private ArrayList<Position> groundPos;
	private Position[] path;
	private ArrayList<Position> vertexs = new ArrayList<>();
	private AStarSearch astar;
	private int next = 1;
	private int id;
	public boolean isOverlap = false;
	public boolean active = false;
	public double speed = 38;
	private int x;
	private int y;

	public Agent(Position startPos, Position endPos, ArrayList<Position> groundPos, int id) {
		super(startPos.x, startPos.y, "", "");
		this.x = startPos.x * Constant.BLOCKS_SIZE;
		this.y = startPos.y * Constant.BLOCKS_SIZE;
		this.id = id;
		this.startPos = startPos;
		this.endPos = endPos;
		this.groundPos = groundPos;
		this.astar = new AStarSearch(Constant.BLOCKS_WIDTH, Constant.BLOCKS_HEIGHT, startPos, endPos, groundPos);
		this.path = astar.cal();
		this.active = true;
		this.initVertexs();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Position getCurrentPos() {
		this.preUpdate();
		Position currentPos = new Position(this.x, this.y);
		return currentPos;
	}

	public void setX(int x) {

		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	// public goToDestinationByPath() {
	// if (!this.path) return;
	// if (this.next == this.path?.length) return;
	// if (this.x != this.path[this.next].x * 32) {
	// if (this.x < this.path[this.next].x * 32) {
	// this.x += 2;
	// this.agentText.x += 2;
	// } else {
	// this.x -= 2;
	// this.agentText.x -= 2;
	// }
	// } else if (this.y != this.path[this.next].y * 32) {
	// if (this.y < this.path[this.next].y * 32) {
	// this.y += 2;
	// this.agentText.y += 2;
	// } else {
	// this.y -= 2;
	// this.agentText.y -= 2;
	// }
	// } else {
	// this.next++;
	// }
	// }

	public void goToDestinationByVertexs() {
		if (this.next == this.vertexs.size()) {
//      this.agentText.setText("DONE");
//      this.agentText.setFontSize(12);
//      this.agentText.setX(this.x - 1);
//      this.x = this.vertexs[this.vertexs.length - 1].x * 32;
//      this.y = this.vertexs[this.vertexs.length - 1].y * 32;
//      this.setVelocity(0, 0);
			this.eliminate();
			return;
		}
		int thisBlockX = (int)(this.x / Constant.BLOCKS_SIZE);
		int thisBlockY = (int)(this.y / Constant.BLOCKS_SIZE);
		int nextBlockX = this.vertexs.get(this.next).x;
		int nextBlockY = this.vertexs.get(this.next).y;
		
		if (Math.abs(this.vertexs.get(this.next).x - thisBlockX) > 0 || Math.abs(this.vertexs.get(this.next).y - thisBlockY) > 0) {
			int deltaX = nextBlockX*Constant.BLOCKS_SIZE - this.x;
			int deltaY = nextBlockY*Constant.BLOCKS_SIZE - this.y;
			if(deltaX > 0) {
				this.x++;
			}else if(deltaX < 0) {
				this.x--;
			}
			if(deltaY > 0) {
				this.y++;
			}else if(deltaY < 0) {
				this.y--;
			}
		} else {
			this.next++;
		}
	}

	public void addRandomVertexs(Position start, Position end) {
		double dis = Math.sqrt(Math.pow((start.x - end.x), 2) + Math.pow((start.y - end.y), 2));
		double num = Math.ceil((dis * 32) / 50);
		for (int i = 1; i < num; i++) {
			while (true) {
				Position rV = new Position((int) (((end.x - start.x) / num) * i + start.x + (Math.random() - 0.5)),
						(int) (((end.y - start.y) / num) * i + start.y + (Math.random() - 0.5)));
				Position _1, _2, _3, _4;
				boolean b_1 = false, b_2 = false, b_3 = false, b_4 = false;
				_1 = new Position(rV.x, rV.y);
				_2 = new Position(rV.x + 1, rV.y);
				_3 = new Position(rV.x + 1, rV.y + 1);
				_4 = new Position(rV.x, rV.y + 1);

				for (Position p : this.groundPos) {
					if (_1.x < p.x + 1 && _1.y < p.y + 1 && _1.x >= p.x && _1.y >= p.y) {
						b_1 = true;
					}
					if (_2.x < p.x + 1 && _2.y < p.y + 1 && _2.x >= p.x && _2.y >= p.y) {
						b_2 = true;
					}
					if (_3.x < p.x + 1 && _3.y < p.y + 1 && _3.x >= p.x && _3.y >= p.y) {
						b_3 = true;
					}
					if (_4.x < p.x + 1 && _4.y < p.y + 1 && _4.x >= p.x && _4.y >= p.y) {
						b_4 = true;
					}
				}
				if (b_1 && b_2 && b_3 && b_4) {
					this.vertexs.add(rV);
					break;
				}
			}
		}
	}

	public void initVertexs() {
		if (this.path != null) {
			this.vertexs.add(path[0]);
			for (int cur = 2; cur < this.path.length; cur++) {
				if ((this.path[cur].x == this.path[cur - 1].x && this.path[cur].x == this.path[cur - 2].x)
						|| (this.path[cur].y == this.path[cur - 1].y && this.path[cur].y == this.path[cur - 2].y)) {
					continue;
				}

				Position curV = this.vertexs.get(this.vertexs.size() - 1);
				Position nextV = this.path[cur - 1];
				this.addRandomVertexs(curV, nextV);
				this.vertexs.add(nextV);
			}
			this.addRandomVertexs(this.vertexs.get(this.vertexs.size() - 1), this.path[this.path.length - 1]);
			this.vertexs.add(this.path[this.path.length - 1]);
		}
	}

	void preUpdate() {
		this.goToDestinationByVertexs();
	}

	public Position getStartPos() {
		return this.startPos;
	}

	public Position getEndPos() {
		return this.endPos;
	}

	public int getId() {
		return this.id;
	}

	public void eliminate() {
		this.active = false;
	}

	public void pause() {
//    this.setVelocity(0, 0);
//    this.setActive(false);
	}

	public void restart() {
//    this.setActive(true);
	}

	public void handleOverlap() {
//    if (this.isOverlap) return;
//    this.isOverlap = true;
//    setTimeout(() => {
//      this.isOverlap = false;
//    }, 4000);
//    let r = Math.random();
//    if (r < 0.5) {
//      return;
//    } else {
//      this.setVelocity(0, 0);
//      this.setActive(false);
//      setTimeout(() => {
//        this.setActive(true);
//      }, 2000);
//    }
	}
}
