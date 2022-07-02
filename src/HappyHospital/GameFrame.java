package HappyHospital;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	GameFrame(){
		
		this.add(new GamePanel());
		this.setTitle("Happy Hospital");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setSize(500,500);
		this.setResizable(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

	
}
