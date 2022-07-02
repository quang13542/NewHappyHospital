package classes;

import java.awt.event.KeyEvent;

public class Agv extends Actor {
	private int dx;
	private int dy;
	private int x ;
	private int y ;
	private String cur_dir = "1";
	private final int dir_x[] = {0, 1, 0, -1};
	private final int dir_y[] = {-1, 0, 1, 0};
	
	public Agv(int x, int y, String texture, String frame) {
		super(x, y, texture, frame);
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}

	public boolean move(String stat, int siz, int up, int right, int down, int left) {
		
		String fl = "";
		String tocen = "";
		boolean dir_up = false;
		boolean dir_down = false;
		boolean dir_left = false;
		boolean dir_right = false;
		
		for(char ch: stat.toCharArray()) {
			if(ch == '0') dir_up = true;
			else if(ch == '1') dir_right = true;
			else if(ch == '2') dir_down = true;
			else if(ch == '3') dir_left = true;
		}
		
		if(y % siz == siz/2 && x % siz >= siz/2) {
			if(dx == 1 && left != 0) fl += '1';
			if(dx == -1 && dir_right && x % siz != siz/2) tocen += '3';
		}
		if(y % siz == siz/2 && x % siz <= siz/2) {
			if(dx == -1 && right !=0) fl += '3';
			if(dx == 1 && dir_left && x % siz != siz/2) tocen += '1';
			
		}
		if(x % siz == siz/2 && y % siz <= siz/2) {
			if(dy == -1 && down != 0) fl += '0';
			if(dy == 1 && dir_up && y % siz != siz/2) tocen += '2';
		}
		if(x % siz == siz/2 && y % siz >= siz/2) {
			if(dy == 1 && up != 0) fl += '2';
			if(dy == -1 && dir_down && y % siz != siz/2) tocen += '0';
		}
		if(fl == "" && tocen == "") return false;
		for(char fle: fl.toCharArray()) {
			for(char ch: stat.toCharArray()){
				if(fle == ch && fle != cur_dir.charAt(0)) {
					x += dir_x[Character.getNumericValue(fle)];
					y += dir_y[Character.getNumericValue(fle)];
					cur_dir = String.valueOf(fle);
					return true;
				}
			}
		}
		
		if(fl.contains(cur_dir) && stat.contains(cur_dir) ) {
			x += dir_x[Character.getNumericValue(cur_dir.charAt(0))];
			y += dir_y[Character.getNumericValue(cur_dir.charAt(0))];
			return true;
		}
		for(char ch: tocen.toCharArray()) {
			if(ch != cur_dir.charAt(0)) {
				x += dir_x[Character.getNumericValue(ch)];
				y += dir_y[Character.getNumericValue(ch)];
				cur_dir = String.valueOf(ch);
				return true;
			}
		}
		
		if(tocen.contains(cur_dir)) {
			x += dir_x[Character.getNumericValue(cur_dir.charAt(0))];
			y += dir_y[Character.getNumericValue(cur_dir.charAt(0))];
			return true;
		}
		return false;
	}
	public boolean move(int stat) {
		
		if(stat == 12 && dy == -1) {
			y += dy;
			return true;
		}
		if(stat == 20 && dy == 1) {
			y += dy;
			return true;
		}
		if(stat == 28 && dx == 1) {
			x += dx;
			return true;
		}
		if(stat == 36 && dx == -1) {
			x += dx;
			return true;
		}
		return false;
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
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) dx = -1;
        if (key == KeyEvent.VK_RIGHT) dx = 1;
        if (key == KeyEvent.VK_UP) dy = -1;
        if (key == KeyEvent.VK_DOWN) dy = 1;
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) dx = 0;
        if (key == KeyEvent.VK_RIGHT) dx = 0;
        if (key == KeyEvent.VK_UP) dy = 0;
        if (key == KeyEvent.VK_DOWN) dy = 0;
    }
	
}
