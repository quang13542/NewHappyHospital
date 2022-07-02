package classes;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AStar {
	private int BLOCKS_HEIGHT = 28;
	private int BLOCKS_WIDTH = 52;
	private int BLOCKS_SIZE;
	private int x;
	private int y;
	private short path[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 50, 28, 50, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 50, 28, 50, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 43, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 58, 28, 50, 28, 50, 28, 50, 28, 50, 28, 28, 50, 28, 59, 28, 59, 36, 50, 36, 50, 36, 50, 36, 50, 36, 36, 50, 36, 59, 36, 59, 28, 50, 28, 50, 28, 50, 28, 50, 28, 28, 50, 28, 57, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 12, 0, 20, 0, 12, 0, 0, 20, 0, 12, 0, 20, 0, 12, 0, 20, 0, 12, 0, 20, 0, 0, 12, 0, 20, 0, 12, 0, 20, 0, 12, 0, 20, 0, 12, 0, 0, 20, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 58, 36, 49, 36, 59, 36, 59, 36, 49, 36, 36, 49, 36, 49, 36, 49, 28, 49, 28, 59, 28, 59, 28, 49, 28, 28, 49, 28, 49, 28, 49, 36, 49, 36, 59, 36, 59, 36, 49, 36, 36, 49, 36, 57, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 58, 28, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 58, 36, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 58, 36, 57, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 28, 28, 28, 57, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 58, 28, 28, 28, 0, 0, 28, 28, 28, 57, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 58, 28, 28, 28, 0, 0, 0, 0, 0, 20, 0, 0, 0, 58, 36, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 58, 28, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 58, 28, 57, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 58, 28, 50, 28, 59, 28, 59, 28, 50, 28, 28, 50, 28, 50, 28, 50, 36, 50, 36, 59, 36, 59, 36, 50, 36, 36, 50, 36, 50, 36, 50, 28, 50, 28, 59, 28, 59, 28, 50, 28, 28, 50, 28, 57, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 20, 0, 12, 0, 20, 0, 0, 12, 0, 20, 0, 20, 0, 20, 0, 12, 0, 20, 0, 12, 0, 0, 20, 0, 12, 0, 12, 0, 12, 0, 20, 0, 12, 0, 20, 0, 0, 12, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 58, 36, 49, 36, 49, 36, 49, 36, 49, 36, 36, 49, 36, 59, 36, 59, 28, 49, 28, 49, 28, 49, 28, 49, 28, 28, 49, 28, 59, 28, 59, 36, 49, 36, 49, 36, 49, 36, 49, 36, 36, 49, 36, 57, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 41, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 49, 28, 49, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 49, 28, 49, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	private Queue<Integer> path_x = new LinkedList<>();
	private Queue<Integer> path_y = new LinkedList<>();
	
	private final int dir_x[] = {0, 1, 0, -1};
	private final int dir_y[] = {-1, 0, 1, 0};
	
	public AStar(int x, int y, int des_x, int des_y, int bl)
	{
		BLOCKS_SIZE = bl;
		this.x = x;
		this.y = y;
		int hash_x = 1;
		int hash_y = 100;
		int hash_dis = 10000;
		int[] prev = new int[10000];
		int[][] f = new int[100][100];
		for(int i=0;i<=BLOCKS_HEIGHT;i++)
		{
			for(int j=0;j<=BLOCKS_WIDTH;j++) {
				f[j][i]=1000000000;
			}
		}
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		int hash_pos = (this.x/BLOCKS_SIZE)*hash_x + (this.y/BLOCKS_SIZE)*hash_y;
		pq.add(hash_pos);
		while(!pq.isEmpty()) {
			hash_pos = pq.peek();
			int dis = hash_pos/hash_dis;
			hash_pos%=hash_dis;
			int cur_y = hash_pos/hash_y;
			hash_pos%=hash_y;
			int cur_x = hash_pos/hash_x;
			
						
			if(cur_x==des_x/BLOCKS_SIZE && cur_y==des_y/BLOCKS_SIZE) {
				break;
			}
			
			int pos = cur_x + cur_y*BLOCKS_WIDTH;
			
			int pre_x = cur_x;
			int pre_y = cur_y;
			
			pq.remove();
			if(cur_x == des_x/BLOCKS_SIZE && cur_y == des_y/BLOCKS_SIZE) {
				break;
			}
			
			if(path[pos] == 12) {
				pre_y += -1;
				int new_key = pre_x*hash_x + pre_y*hash_y + (dis+1)*hash_dis;
				
				if(f[pre_x][pre_y]>dis+1) {
					f[pre_x][pre_y] = dis+1;
					pq.add(new_key);
					prev[pre_x + pre_y*BLOCKS_WIDTH] = pos;
				}
				
			}
			else if(path[pos] == 20) {
				pre_y += 1;
				int new_key = pre_x*hash_x + pre_y*hash_y + (dis+1)*hash_dis;
				if(f[pre_x][pre_y]>dis+1) {
					f[pre_x][pre_y] = dis+1;
					pq.add(new_key);
					prev[pre_x + pre_y*BLOCKS_WIDTH] = pos;
				}
			}
			else if(path[pos] == 28) {
				pre_x += 1;
				int new_key = pre_x*hash_x + pre_y*hash_y + (dis+1)*hash_dis;
				if(f[pre_x][pre_y]>dis+1) {
					f[pre_x][pre_y] = dis+1;
					pq.add(new_key);
					prev[pre_x + pre_y*BLOCKS_WIDTH] = pos;
				}
			}
			else if(path[pos] == 36) {
				pre_x += -1;
				int new_key = pre_x*hash_x + pre_y*hash_y + (dis+1)*hash_dis;
				if(f[pre_x][pre_y]> dis+1) {
					f[pre_x][pre_y] = dis+1;
					pq.add(new_key);
					prev[pre_x + pre_y*BLOCKS_WIDTH] = pos;
				}
			}
			else {
				for(int id = 0; id < 4; id++) {
					pre_x = (int) (cur_x + dir_x[id]);
					pre_y = (int) (cur_y + dir_y[id]);
					
					int new_pos = (int) (pre_x + pre_y*BLOCKS_WIDTH);
					if(path[new_pos] != 0) {
						int new_key = pre_x*hash_x + pre_y*hash_y + (dis+1)*hash_dis;
						if(f[pre_x][pre_y]>dis+1) {
							f[pre_x][pre_y] = dis+1;
							pq.add(new_key);
							prev[pre_x + pre_y*BLOCKS_WIDTH] = pos;
						}
					}
				}
			}
		}
		
		int cur_x = des_x/BLOCKS_SIZE;
		int cur_y = des_y/BLOCKS_SIZE;
		Stack<Integer> st_x = new Stack<Integer>();
		Stack<Integer> st_y = new Stack<Integer>();
		while(true) {
			st_x.add(cur_x);
			st_y.add(cur_y);
			if(cur_x == this.x/BLOCKS_SIZE && cur_y == this.y/BLOCKS_SIZE) break;
			int pos = (int) prev[cur_x + cur_y*BLOCKS_WIDTH];
			cur_y = pos/BLOCKS_WIDTH;
			cur_x = pos-cur_y*BLOCKS_WIDTH;
		}
		
		while(!st_x.isEmpty()) {
			path_x.add(st_x.peek());
			st_x.pop();
			path_y.add(st_y.peek());
			st_y.pop();
		}
		
	}
	
	public Queue<Integer> get_path_x() {
		return this.path_x;
	}
	
	public Queue<Integer> get_path_y() {
		return this.path_y;
	}
}
