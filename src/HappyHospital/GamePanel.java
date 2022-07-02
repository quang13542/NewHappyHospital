package HappyHospital;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import classes.Agent;
import classes.Agv;
import classes.AutoAgv;
import HappyHospital.*;
import classes.Position;
import constant.Constant;
import HappyHospital.utils;

public class GamePanel extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;

	static final int BLOCKS_WIDTH = 52;
	static final int BLOCKS_HEIGHT = 28;
	static final int BLOCKS_SIZE = 25; // 32; should be odd for better performance
	static final int SCREEN_WIDTH = 52 * BLOCKS_SIZE;
	static final int SCREEN_HEIGHT = 28 * BLOCKS_SIZE;
	static final int dir_x[] = { 0, 1, 0, -1 };
	static final int dir_y[] = { -1, 0, 1, 0 };
	static final int Delay = 10;
	public int numberAgent = 10;
	private int player_id = -1;
// direction id
//		0
//		|
//	 3-----1
//		|
//		2
	static short nopath[] = views.nopath;
	static short ground[] = views.ground;
	private ArrayList<Position> groundPos = new ArrayList<>();
	static short path[] = views.path;
	static short room[] = views.room;
	static short elevator[] = views.elevator;
	static short gate[] = views.gate;
	static short bed[] = views.bed;
	static short door[] = views.door;
	static short wall[] = views.wall;
	private Thread thread;

	private Dimension d;
	private Image ii;
	private boolean inGame = false;

	private Timer timer;
	private int start_point_x[] = { 1, 1 };
	private int start_point_y[] = { 14, 13 };
	private Agv player[];
	private Agent[] agents = new Agent[this.numberAgent];

	private Image wallpng, dooruppng, doordownpng, doorrightpng, doorleftpng, bedpng, gatepng, elevatorpng, roompng, groundpng, agvpng, agentpng;
	private String ip = "localhost";
	private int port = 22222;
	private Scanner scanner = new Scanner(System.in);

	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;

	private ServerSocket serverSocket;

	private boolean accepted = false;
	private boolean unableToCommunicateWithOpponent = false;
	private boolean won = false;
	private boolean enemyWon = false;
	private boolean tie = false;

	private Font font = new Font("Verdana", Font.BOLD, 32);
	private Font smallerFont = new Font("Verdana", Font.BOLD, 20);
	private Font largerFont = new Font("Verdana", Font.BOLD, 50);

	private String waitingString = "Waiting for another player";
	private String unableToCommunicateWithOpponentString = "Unable to communicate with opponent.";
	private String wonString = "You won!";
	private String enemyWonString = "Opponent won!";
	private String tieString = "Game ended in a tie.";

	private int errors = 0;

	GamePanel() {
		this.getGroundPos();
		loadImages();
		initVariables();
		initBoard();
	}

//     private void createRandomAutoAgv() {
//	    double r = Math.floor(Math.random() * this.path.length);
//	    while (!Constant.validDestination(this.pathPos[r].x, this.pathPos[r].y, 1, 13)) {
//	      r = Math.floor(Math.random() * this.pathPos.length);
//	    }
//	    if (this.graph) {
//	      var tempAgv = new AutoAgv(this, 1, 13, this.pathPos[r].x, this.pathPos[r].y, this.graph);
//	      this.timeTable && tempAgv.writeDeadline(this.timeTable);
//	      var des = document.getElementById("des");
//	      if (des) {
//	        while (des.childNodes.length >= 1) {
//	          des.firstChild && des.removeChild(des.firstChild);
//	        }
//
//	        des.appendChild(des.ownerDocument.createTextNode(this.timeTable?.text || ""));
//	      }
//	      this.autoAgvs.add(tempAgv);
//	    }
//	  }
	  
	public void createRandomAgents() {
		for(int i=0; i < this.numberAgent; i++) {
			int startPosIndex = utils.getRandomNumber(0, this.groundPos.size());
			int endPosIndex = utils.getRandomNumber(0, this.groundPos.size());
			Position startPos = this.groundPos.get(startPosIndex);
			Position endPos = this.groundPos.get(endPosIndex);
			this.agents[i] = new Agent(startPos, endPos, this.groundPos, i);
		}
		
	}
	private void initVariables() {

//		System.out.println("Please input the IP: ");
//		ip = scanner.nextLine();
//		System.out.println("Please input the port: ");
//		port = scanner.nextInt();
//		while (port < 1 || port > 65535) {
//			System.out.println("The port you entered was invalid, please input another port: ");
//			port = scanner.nextInt();
//		}

		player = new Agv[2];
		addKeyListener(new TAdapter());
		d = new Dimension(0, 0);
		// init AGV
		player[0] = new Agv(start_point_x[0] * BLOCKS_SIZE + BLOCKS_SIZE / 2, start_point_y[0] * BLOCKS_SIZE + BLOCKS_SIZE / 2, "", "");
		player[1] = new Agv(start_point_x[1] * BLOCKS_SIZE + BLOCKS_SIZE / 2, start_point_y[1] * BLOCKS_SIZE + BLOCKS_SIZE / 2, "", "");
		
		// init agents
		this.createRandomAgents();
		
		timer = new Timer(Delay, this);
		timer.start();

		if (!connect())
			initializeServer();

		thread = new Thread(this, "HappyHospital");
		thread.start();

	}

	private BufferedImage resizeImage(Image originalImage, int targetWidth, int targetHeight) {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}

	private void drawagv(Graphics2D g2d) {
		g2d.drawImage(agvpng, player[player_id].getX() - BLOCKS_SIZE / 2, player[player_id].getY() - BLOCKS_SIZE / 2,
				this);
		g2d.drawImage(agvpng, player[1 - player_id].getX() - BLOCKS_SIZE / 2,
				player[1 - player_id].getY() - BLOCKS_SIZE / 2, this);
	}
	
	private void drawAgent(Graphics2D g2d) {
		for(Agent agent: this.agents) {
			g2d.drawImage(agentpng, agent.getX()*BLOCKS_SIZE, agent.getY()*BLOCKS_SIZE, this);
			g2d.drawString("DES_" + agent.getId(), BLOCKS_SIZE * 20, BLOCKS_SIZE * 20);
		}
	}

	private void drawHospital(Graphics2D g2d) {
		short pos = 0;
		int x, y;
		for (y = 0; y < SCREEN_HEIGHT; y += BLOCKS_SIZE) {
			for (x = 0; x < SCREEN_WIDTH; x += BLOCKS_SIZE) {
				g2d.setStroke(new BasicStroke(2));

				if (ground[pos] != 0) {
					g2d.drawImage(groundpng, x, y, this);
				}

				if (room[pos] != 0) {
					g2d.drawImage(roompng, x, y, this);
				}

				if (elevator[pos] != 0) {
					g2d.drawImage(elevatorpng, x, y, this);
					pos++;
					continue;
				}

				if (gate[pos] != 0) {
					g2d.drawImage(gatepng, x, y, this);
				}

				if (bed[pos] != 0) {
					g2d.drawImage(bedpng, x, y, this);
				}

				if (door[pos] == 19) {
					g2d.drawImage(dooruppng, x, y, this);
				} else if (door[pos] == 25) {
					g2d.drawImage(doorrightpng, x, y, this);
				} else if (door[pos] == 26) {
					g2d.drawImage(doordownpng, x, y, this);
				} else if (door[pos] == 27) {
					g2d.drawImage(doorleftpng, x, y, this);
				}

				if (wall[pos] != 0) {
					g2d.drawImage(wallpng, x, y, this);
				}

				if (path[pos] == 12) {
					Image path = new ImageIcon("src/assets/tilemaps/tiles/downup.png").getImage();
					path = resizeImage(path, BLOCKS_SIZE, BLOCKS_SIZE);
					g2d.drawImage(path, x, y, this);
				} else if (path[pos] == 20) {
					Image path = new ImageIcon("src/assets/tilemaps/tiles/updown.png").getImage();
					path = resizeImage(path, BLOCKS_SIZE, BLOCKS_SIZE);
					g2d.drawImage(path, x, y, this);
				} else if (path[pos] == 28) {
					Image path = new ImageIcon("src/assets/tilemaps/tiles/leftright.png").getImage();
					path = resizeImage(path, BLOCKS_SIZE, BLOCKS_SIZE);
					g2d.drawImage(path, x, y, this);
				} else if (path[pos] == 36) {
					Image path = new ImageIcon("src/assets/tilemaps/tiles/rightleft.png").getImage();
					path = resizeImage(path, BLOCKS_SIZE, BLOCKS_SIZE);
					g2d.drawImage(path, x, y, this);
				} else if (path[pos] != 0) {
					String tmp = "";
					for (int id = 0; id < 4; id++) {
						int pre_x = x + dir_x[id] * BLOCKS_SIZE;
						int pre_y = y + dir_y[id] * BLOCKS_SIZE;
						if (pre_x >= 0 && pre_x < SCREEN_WIDTH && pre_y >= 0 && pre_y < SCREEN_HEIGHT) {
							if (path[pre_x / BLOCKS_SIZE + pre_y / BLOCKS_SIZE * BLOCKS_WIDTH] != 0) {
								tmp += String.valueOf(id);
							}
						}
					}
					if (tmp.length() > 1) {
						tmp = "src/assets/tilemaps/tiles/" + tmp;
						tmp += ".png";
						Image path = new ImageIcon(tmp).getImage();
						path = resizeImage(path, BLOCKS_SIZE, BLOCKS_SIZE);
						g2d.drawImage(path, x, y, this);
					}
				}
				pos++;
			}
		}
	}

	private void loadImages() {
		agvpng = new ImageIcon("src/assets/sprites/agv.png").getImage();
		agentpng = new ImageIcon("src/assets/sprites/agent.png").getImage();
		wallpng = new ImageIcon("src/assets/tilemaps/tiles/wall.png").getImage();
		dooruppng = new ImageIcon("src/assets/tilemaps/tiles/doorup.png").getImage();
		doordownpng = new ImageIcon("src/assets/tilemaps/tiles/doordown.png").getImage();
		doorleftpng = new ImageIcon("src/assets/tilemaps/tiles/doorleft.png").getImage();
		doorrightpng = new ImageIcon("src/assets/tilemaps/tiles/doorright.png").getImage();
		bedpng = new ImageIcon("src/assets/tilemaps/tiles/bed.png").getImage();
		gatepng = new ImageIcon("src/assets/tilemaps/tiles/gate.png").getImage();
		elevatorpng = new ImageIcon("src/assets/tilemaps/tiles/elevator.png").getImage();
		roompng = new ImageIcon("src/assets/tilemaps/tiles/room.png").getImage();
		groundpng = new ImageIcon("src/assets/tilemaps/tiles/ground.png").getImage();
		agvpng = resizeImage(agvpng, BLOCKS_SIZE, BLOCKS_SIZE);
		agentpng = resizeImage(agentpng, BLOCKS_SIZE, BLOCKS_SIZE);
		wallpng = resizeImage(wallpng, BLOCKS_SIZE, BLOCKS_SIZE);
		bedpng = resizeImage(bedpng, BLOCKS_SIZE, BLOCKS_SIZE);
		gatepng = resizeImage(gatepng, BLOCKS_SIZE, BLOCKS_SIZE);
		elevatorpng = resizeImage(elevatorpng, BLOCKS_SIZE, BLOCKS_SIZE);
		roompng = resizeImage(roompng, BLOCKS_SIZE, BLOCKS_SIZE);
		groundpng = resizeImage(groundpng, BLOCKS_SIZE, BLOCKS_SIZE);
		dooruppng = resizeImage(dooruppng, BLOCKS_SIZE, BLOCKS_SIZE);
		doordownpng = resizeImage(doordownpng, BLOCKS_SIZE, BLOCKS_SIZE);
		doorleftpng = resizeImage(doorleftpng, BLOCKS_SIZE, BLOCKS_SIZE);
		doorrightpng = resizeImage(doorrightpng, BLOCKS_SIZE, BLOCKS_SIZE);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}

	private void doDrawing(Graphics g) {

		if (unableToCommunicateWithOpponent) {
			g.setColor(Color.RED);
			g.setFont(smallerFont);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawString(unableToCommunicateWithOpponentString, BLOCKS_WIDTH, BLOCKS_HEIGHT);
			return;
		}
		if (accepted) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(Color.gray);
			g2d.fillRect(0, 0, d.width, d.height);

			drawHospital(g2d);
			drawagv(g2d);
			drawAgent(g2d);

			// if (inGame) {
			// playGame(g2d);
			// } else {
			// showIntroScreen(g2d);
			// }

			g2d.drawImage(ii, 5, 5, this);
			Toolkit.getDefaultToolkit().sync();
			g2d.dispose();
		} else {
			g.setColor(Color.RED);
			g.setFont(font);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawString(waitingString, BLOCKS_WIDTH, BLOCKS_HEIGHT);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		step();
	}

	private void step() {
		int pos = (int) ((player[Math.max(0, player_id)].getX()) / BLOCKS_SIZE)
				+ ((int) ((player[Math.max(0, player_id)].getY()) / BLOCKS_SIZE)) * BLOCKS_WIDTH;
		if (path[pos] == 12 || path[pos] == 20 || path[pos] == 28 || path[pos] == 36) {
			player[Math.max(0, player_id)].move(path[pos]);
		} else {
			String tmp = "";
			for (int id = 0; id < 4; id++) {
				int pre_x = player[Math.max(0, player_id)].getX() + dir_x[id] * BLOCKS_SIZE;
				int pre_y = player[Math.max(0, player_id)].getY() + dir_y[id] * BLOCKS_SIZE;
				if (pre_x >= 0 && pre_x < SCREEN_WIDTH && pre_y >= 0 && pre_y < SCREEN_HEIGHT) {
					if (path[pre_x / BLOCKS_SIZE + ((int) (pre_y / BLOCKS_SIZE)) * BLOCKS_WIDTH] != 0) {
						tmp += String.valueOf(id);
					}
				}
			}
			player[Math.max(0, player_id)].move(tmp, BLOCKS_SIZE,
					path[(player[Math.max(0, player_id)].getX() + dir_x[2] * BLOCKS_SIZE) / BLOCKS_SIZE
							+ ((int) ((player[Math.max(0, player_id)].getY() + dir_y[2] * BLOCKS_SIZE) / BLOCKS_SIZE))
									* BLOCKS_WIDTH],
					path[(player[Math.max(0, player_id)].getX() + dir_x[3] * BLOCKS_SIZE) / BLOCKS_SIZE
							+ ((int) ((player[Math.max(0, player_id)].getY() + dir_y[3] * BLOCKS_SIZE) / BLOCKS_SIZE))
									* BLOCKS_WIDTH],
					path[(player[Math.max(0, player_id)].getX() + dir_x[0] * BLOCKS_SIZE) / BLOCKS_SIZE
							+ ((int) ((player[Math.max(0, player_id)].getY() + dir_y[0] * BLOCKS_SIZE) / BLOCKS_SIZE))
									* BLOCKS_WIDTH],
					path[(player[Math.max(0, player_id)].getX() + dir_x[1] * BLOCKS_SIZE) / BLOCKS_SIZE
							+ ((int) ((player[Math.max(0, player_id)].getY() + dir_y[1] * BLOCKS_SIZE) / BLOCKS_SIZE))
									* BLOCKS_WIDTH]);
		}
		if (pos != (int) ((player[Math.max(0, player_id)].getX()) / BLOCKS_SIZE)
				+ ((int) ((player[Math.max(0, player_id)].getY()) / BLOCKS_SIZE)) * BLOCKS_WIDTH) {
			try {
				dos.writeInt(pos);
				dos.flush();
			} catch (IOException e1) {
				errors++;
				e1.printStackTrace();
			}
		}
		repaint(player[Math.max(0, player_id)].getX() - BLOCKS_SIZE / 2,
				player[Math.max(0, player_id)].getY() - BLOCKS_SIZE / 2, BLOCKS_SIZE + 2, BLOCKS_SIZE + 2);
	}

	private void initBoard() {

		requestFocus();
		setFocusable(true);
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			player[player_id].keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			player[player_id].keyPressed(e);
		}
	}

	public void run() {
		while (true) {
			tick();
			repaint();

			if (!accepted) {
				listenForServerRequest();
			}

		}
	}

	void tick() {
		if (errors >= 10)
			unableToCommunicateWithOpponent = true;

		if (!unableToCommunicateWithOpponent) {
//			try {

//				int pos = dis.readInt();
//				int y = pos / BLOCKS_WIDTH * BLOCKS_SIZE;
//				int x = (pos - y/BLOCKS_WIDTH * BLOCKS_SIZE) * BLOCKS_SIZE;
//				if (circle) spaces[space] = "X";
//				else spaces[space] = "O";
//				player[1-player_id].setX(x);
//				player[1-player_id].setY(y);
			checkForTie();
			checkForEnemyWin();
			checkForWin();
//			} 
//			catch (IOException e) {
//				e.printStackTrace();
//				errors++;
//			}
		}
	}

	private void checkForWin() {

	}

	private void checkForEnemyWin() {

	}

	private void checkForTie() {

	}

	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean connect() {
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
		} catch (IOException e) {
			if (player_id == -1)
				player_id = 0;
			System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
			return false;
		}
		if (player_id == -1)
			player_id = 1;
		System.out.println("Successfully connected to the server.");
		return true;
	}

	private void initializeServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		yourTurn = true;
//		circle = false;
	}
	
	public ArrayList<Position> getGroundPos() {
		if(this.groundPos.isEmpty()) {
			for(int i = 0; i < this.ground.length; i++) {
				if(this.ground[i] == 1) {
					int x = i%Constant.BLOCKS_WIDTH;
					int y = (int)(i/Constant.BLOCKS_WIDTH);
					Position thisPos = new Position(x, y);
					this.groundPos.add(thisPos);
				}
			}
		}
		return this.groundPos;
	}

}
