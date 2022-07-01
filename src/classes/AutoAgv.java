package classes;

import java.awt.Taskbar.State;

import classes.*;
import classes.StateOfAutoAGV.*;

//import { HybridState } from "./statesOfAutoAGV/HybridState";
//import { RunningState } from "./statesOfAutoAGV/RunningState";
//import { MainScene } from "../scenes";
//const PriorityQueue = require("priorityqueuejs");
//
public class AutoAgv extends Actor {
  public Graph graph;
  public Node[] path;
  public Node curNode;
  public Node endNode;
  public double cur;
  public double waitT;
  public double sobuocdichuyen;
  public double thoigiandichuyen;
  public HybridState hybridState;
  public double endX;
  public double endY;
  public Text firstText;
  public double startX;
  public double startY;

  AutoAgv(int x, int y, String texture, String frame)  {
    super(x * 32, y * 32, "agv", "");
    this.startX = x * 32;
    this.startY = y * 32;
    this.endX = endX * 32;
    this.endY = endY * 32;

    this.graph = graph;
//    this.getBody().setSize(32, 32);
//    this.setOrigin(0, 0);
    this.cur = 0;
    this.waitT = 0;
//    this.curNode = this.graph.nodes[x][y];
//    this.curNode.setState(StateOfNode2D.BUSY);
//    this.endNode = this.graph.nodes[endX][endY];
//    this.firstText = new Text(
//      this.scene,
//      endX * 32,
//      endY * 32,
//      "DES",
//      "16px",
//      "#F00"
//    );
//    this.path = this.calPathAStar(this.curNode, this.endNode);
//    this.sobuocdichuyen = 0;
//    this.thoigiandichuyen = performance.now();
//    this.estimateArrivalTime(x * 32, y * 32, endX * 32, endY * 32);
//    this.hybridState = new RunningState();
  }

  protected void preUpdate(double time, double delta) {
    //this.move();
    // console.log(this.x, this.y);
//    this.hybridState?.move(this);
	  System.out.println();
  }

  public Node[] calPathAStar(Node start, Node end) {
//    return this.graph.calPathAStar(start, end);   
	  Node[] a = null;
	  return a;
  }

  public void changeTarget() {
//    var mainScene = this.scene as MainScene;
//    let agvsToGate1: Array<number> = mainScene.mapOfExits.get(
//      "Gate1"
//    ) as Array<number>;
//    let agvsToGate2: Array<number> = mainScene.mapOfExits.get(
//      "Gate2"
//    ) as Array<number>;
//    var choosenGate = agvsToGate1[2] < agvsToGate2[2] ? "Gate1" : "Gate2";
//    var newArray = mainScene.mapOfExits.get(choosenGate) as Array<number>;
//    newArray[2]++;
//    mainScene.mapOfExits.set(choosenGate, newArray);
//
//    this.startX = this.endX;
//    this.startY = this.endY;
//
//    var xEnd: number = newArray[0];
//    var yEnd: number = newArray[1];
//
//    this.endX = xEnd * 32;
//    this.endY = yEnd * 32;
//
//    var finalAGVs = (mainScene.mapOfExits.get(choosenGate) as Array<number>)[2];
//
//    this.endNode = this.graph.nodes[xEnd][yEnd];
//    this.firstText = new Text(
//      this.scene,
//      xEnd * 32,
//      yEnd * 32,
//      "DES_" + finalAGVs,
//      "16px",
//      "#F00"
//    );
//    this.path = this.calPathAStar(this.curNode, this.endNode);
//    this.cur = 0;
//    this.sobuocdichuyen = 0;
//    this.thoigiandichuyen = performance.now();
//    this.estimateArrivalTime(
//      32 * this.startX,
//      32 * this.startY,
//      this.endX * 32,
//      this.endY * 32
//    );
  }
}
