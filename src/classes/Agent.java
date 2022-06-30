package classes;

import algorithm.AStarSearch;
import classes.*;
import constant.*;
import java.util.List;

public class Agent extends Actor {
	private Position startPos;
	private Position endPos;
	private Position[] groundPos;
	private Position[] path;
	private List<Position> vertexs;
	private Text endText;
	private Text agentText;
    private AStarSearch astar;
	private double next = 1;
	private double id;
	public boolean isOverlap = false;
	public double speed = 38;
	private int x ;
	private int y ;

	public Agent(Position startPos, Position endPos, Position[] groundPos, int id) {
		super("", startPos.x, startPos.y, "", "");
		this.x = startPos.x;
		this.y = startPos.y;
		this.startPos = startPos;
		this.endPos = endPos;
		this.groundPos = groundPos;
		this.astar = new AStarSearch(Constant.SCREEN_WIDTH, Constant.SCREEN_WIDTH, startPos, endPos, groundPos);
		this.path = astar.cal();
	}

	public int getX() {

		return x;
	}

	public int getY() {

		return y;
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
//    if (this.next == this.vertexs.length) {
//      this.agentText.setText("DONE");
//      this.agentText.setFontSize(12);
//      this.agentText.setX(this.x - 1);
//      this.x = this.vertexs[this.vertexs.length - 1].x * 32;
//      this.y = this.vertexs[this.vertexs.length - 1].y * 32;
//      this.setVelocity(0, 0);
//      this.eliminate();
//      return;
//    }
//    if (
//      Math.abs(this.vertexs[this.next].x * 32 - this.x) > 1 ||
//      Math.abs(this.vertexs[this.next].y * 32 - this.y) > 1
//    ) {
//      this.scene.physics.moveTo(
//        this,
//        this.vertexs[this.next].x * 32,
//        this.vertexs[this.next].y * 32,
//        this.speed
//      );
//      this.agentText.setX(this.x);
//      this.agentText.setY(this.y - 16);
//    } else {
//      this.next++;
//    }
	}

	public void addRandomVertexs(Position start, Position end) {
//    let dis = Math.sqrt((start.x - end.x) ** 2 + (start.y - end.y) ** 2);
//    let num = Math.ceil((dis * 32) / 50);
//    for (let i = 1; i < num; i++) {
//      while (true) {
//        let rV = new Position(
//          ((end.x - start.x) / num) * i + start.x + (Math.random() - 0.5),
//          ((end.y - start.y) / num) * i + start.y + (Math.random() - 0.5)
//        );
//        let _1, _2, _3, _4;
//        let b_1, b_2, b_3, b_4;
//        _1 = new Position(rV.x, rV.y);
//        _2 = new Position(rV.x + 1, rV.y);
//        _3 = new Position(rV.x + 1, rV.y + 1);
//        _4 = new Position(rV.x, rV.y + 1);
//
//        for (let j = 0; j < this.groundPos.length; j++) {
//          let p = this.groundPos[j];
//          if (_1.x < p.x + 1 && _1.y < p.y + 1 && _1.x >= p.x && _1.y >= p.y) {
//            b_1 = true;
//          }
//          if (_2.x < p.x + 1 && _2.y < p.y + 1 && _2.x >= p.x && _2.y >= p.y) {
//            b_2 = true;
//          }
//          if (_3.x < p.x + 1 && _3.y < p.y + 1 && _3.x >= p.x && _3.y >= p.y) {
//            b_3 = true;
//          }
//          if (_4.x < p.x + 1 && _4.y < p.y + 1 && _4.x >= p.x && _4.y >= p.y) {
//            b_4 = true;
//          }
//        }
//        if (b_1 && b_2 && b_3 && b_4) {
//          this.vertexs.push(rV);
//          break;
//        }
//      }
//    }
	}

	public void initVertexs() {
    if (this.path) {
      this.vertexs.add(path[0]);
      for (int cur = 2; cur < this.path.length; cur++) {
        if (
          (this.path[cur].x == this.path[cur - 1].x &&
            this.path[cur].x == this.path[cur - 2].x) ||
          (this.path[cur].y == this.path[cur - 1].y &&
            this.path[cur].y == this.path[cur - 2].y)
        ) {
          continue;
        }

        Position curV = this.vertexs.get(this.vertexs.size() - 1);
        Position nextV = this.path[cur - 1];
        this.addRandomVertexs(curV, nextV);
        this.vertexs.add(nextV);
      }
      this.addRandomVertexs(
        this.vertexs[this.vertexs.length - 1],
        this.path[this.path.length - 1]
      );
      this.vertexs.push(this.path[this.path.length - 1]);
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

	public double getId() {
		return this.id;
	}

	public void eliminate() {
//    this.scene.events.emit("destroyAgent", this);
//    this.endText.destroy();
//    this.agentText.destroy();
//    this.destroy();
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
