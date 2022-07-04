<<<<<<< HEAD
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
=======
package HappyHospital;

import java.awt.BorderLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import HappyHospital.GamePanel;

public class GameFrame extends JFrame{

	GameFrame(){
		JButton saveBotton = new JButton("Save Data");
		JButton loadBotton = new JButton("Load Data");
		JButton applyBotton = new JButton("Apply");
		JLabel dealineLabel = new JLabel("AGV Deadline");
		TextField numberAgentsTextFiled = new TextField("Number of agents.");
		TextField deadlineTextFiled = new TextField("DES_47: 00:01:25 ± 4");
		
		SettingPanel settingPanel = new SettingPanel();
		settingPanel.add(saveBotton);
		settingPanel.add(loadBotton);
		settingPanel.add(numberAgentsTextFiled);
		settingPanel.add(applyBotton);
		settingPanel.add(dealineLabel, BorderLayout.EAST);
		settingPanel.add(deadlineTextFiled);
		this.add(settingPanel, BorderLayout.SOUTH);
		
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
>>>>>>> mungvt
