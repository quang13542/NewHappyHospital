package classes;

import constant.*;
enum StateOfNode {
  EMPTY,
  BUSY,
  NOT_ALLOW,
}


public class Node {
  public int x; // 0 <= x <= 52
  public int y; // 0 <= y <= 28
  private double lambda = 0.4;
  public Node nodeW;
  public Node nodeN;
  public Node nodeS;
  public Node nodeE;
  public int w_edge_W = Integer.MAX_VALUE; // trong so canh
  public int w_edge_N = Integer.MAX_VALUE; // trong so canh
  public int w_edge_S = Integer.MAX_VALUE; // trong so canh
  public int w_edge_E = Integer.MAX_VALUE; // trong so canh
  private double w = 0; // predicted stopping time (ms)
  private double u = 0; // real stopping time (ms)
  public StateOfNode state; // status of node
  public double p_random; // the rate of node change to  Busy
  public double t_min; // the minimum of time which status of node is busy (ms)
  public double t_max; // the maximum of time which status of node is busy (ms)

  public Node nodeVW;
  public Node nodeVN;
  public Node nodeVS;
  public Node nodeVE;

  public double w_edge_VW = Integer.MAX_VALUE; // trong so canh
  public double w_edge_VN = Integer.MAX_VALUE; // trong so canh
  public double w_edge_VS = Integer.MAX_VALUE; // trong so canh
  public double w_edge_VE = Integer.MAX_VALUE; // trong so canh

  public boolean isVirtualNode = false;
  private double _weight = 0;
  
  public Node(int x, int y, boolean isVirtualNode, StateOfNode state, double p_random, double t_min, double t_max) {
    this.x = x;
    this.y = y;
    this.state = StateOfNode.NOT_ALLOW;
    this.p_random = 0.05;
    this.t_min = 2000;
    this.t_max = 3000;
    this.isVirtualNode = false;
  }

  public double getW() {
    if(Constant.MODE() == ModeOfPathPlanning.FRANSEN)
      return this.w;
    else
//      return this.weight;  ?? wtf
    	return this._weight;
  }

  public double getweight() {
    return this._weight;
  }
  public void setweight(double value) {
    this._weight = value;
  }

  public void setNeighbor(Node node) {
    if(node == null)
      return;
    if(node.isVirtualNode) {
      if (this.x + 1 == node.x && this.y == node.y) {
          this.nodeVE = node;
          this.w_edge_VE = 1;
      } else if (this.x == node.x && this.y + 1 == node.y) {
          this.nodeVS = node;
          this.w_edge_VS = 1;
      } else if (this.x - 1 == node.x && this.y == node.y) {
          this.nodeVW = node;
          this.w_edge_VW = 1;
      } else if (this.x == node.x && this.y - 1 == node.y) {
          this.nodeVN = node;
          this.w_edge_VN = 1;
      }
      return;
    }
    this.setRealNeighbor(node);
    return;        
  }

  private void setRealNeighbor(Node node) {
    if (this.x + 1 == node.x && this.y == node.y) {
      this.nodeE = node;
      this.w_edge_E = 1;
    } else if (this.x == node.x && this.y + 1 == node.y) {
      this.nodeS = node;
      this.w_edge_S = 1;
    } else if (this.x - 1 == node.x && this.y == node.y) {
      this.nodeW = node;
      this.w_edge_W = 1;
    } else if (this.x == node.x && this.y - 1 == node.y) {
      this.nodeN = node;
      this.w_edge_N = 1;
    }
  }

  public void setState(StateOfNode state) {
    this.state = state;
  }

  public boolean equal(Node node) {
    if(node.isVirtualNode != this.isVirtualNode)
      return false;
    return this.x == node.x && this.y == node.y;
  }

  public boolean madeOf(Node node) {
    return this.equal(node);
  }

  public void setU(int u) {
    this.u = Math.floor(u);
    this.updateW();
  }

  public void updateW() {
    this.w = (1 - lambda) * this.w + lambda * this.u;
  }
}