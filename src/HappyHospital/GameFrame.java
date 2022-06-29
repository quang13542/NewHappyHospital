package HappyHospital;

import javax.swing.JFrame;

import HappyHospital.GamePanel;

public class GameFrame extends JFrame{

	GameFrame(){
		
		this.add(new GamePanel());
		this.setTitle("Happy Hospital");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setResizable(true);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

	
}
