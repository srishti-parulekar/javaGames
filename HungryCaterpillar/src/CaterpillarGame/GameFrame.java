package CaterpillarGame;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	GameFrame(){
		/*
		GamePanel panel = new GamePanel();
		this.add(panel);
		can be shortened to:
		*/
		this.add(new GamePanel());
		this.setTitle("Srishti's HungryCaterpillar!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		/* if components are added then the pack method ensures that
		 * they are snugly fit within the frame.
		 */
		this.pack();
		this.setVisible(true);
		/*to ensure that our frame appears at the center of our frame 
		 * can set its location relative to null.
		 */
		
		this.setLocationRelativeTo(null);
				
	}
}
