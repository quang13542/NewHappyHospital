package classes;

import java.util.Set;
import constant.Constant;

public class Actor {
	private static int _id = 0;
	private int agvID;
	private double expectedTime;
	public double getExpectedTime() {
		return expectedTime;
	}
	protected int getBody() {
		return 1;
	}
	public int getAgvID() {
		return agvID;
	}

	public Set<Actor> collidedActors;

	Actor(String sence, int x, int y, String texture, String frame) {
//		super(sence, x, y, texture, frame);
//	    super(scene, x, y, texture, frame);
//
//	    scene.add.existing(this);
//	    scene.physics.add.existing(this);
//
//	    this.getBody().setCollideWorldBounds(true);
//	    
//	    if(texture == "agv") {
//	      Actor._id++;
//	      this.agvID = Actor._id;
//	    } else {
//	      this.agvID = -1;//Ám chỉ đây là agent
//	    }
//	    this.collidedActors = new Set();

	}

	private int x, y;
	static final int dir_x[] = { 0, 1, 0, -1 };
	static final int dir_y[] = { -1, 0, 1, 0 };

	public boolean move() {
		return false;
	}
	
	public void estimateArrivalTime(int startX, int startY, int endX, int endY) {
		this.expectedTime = Math.floor(Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)) * 0.085);
	}
	
	public void writeDeadline(String table) {
	    if(this.agvID != -1) {
	        var enter = "";
	        if(table.length() > 0)
	        enter = "\n";
	        table ="DES_" + this.agvID + ": " +
	          Constant.secondsToHMS(this.expectedTime) + " ± " + String.valueOf(Constant.DURATION) + enter + table;
	      }
	}
	
	 public void eraseDeadline(String table) {
		    if(this.agvID != -1) {
		      var enter = "";
//		      if(table.text.length > 0)
//		      enter = "\n"
//		      let erasedStr : string ="DES_" + this.agvID + ": " +
//		        Constant.secondsToHMS(this.expectedTime) + " ± " + Constant.DURATION + enter;
//		      table.text = table.text.replace(erasedStr, "");
		    }
		  }
	
	 public void freeze(Actor actor){
		    if(this.collidedActors == null)
		    {
		      this.collidedActors = Set.of();
		    }
		    if(!this.collidedActors.contains(actor)){
		      this.collidedActors.add(actor);
		    }
		  }
}


























