package CaterpillarGame;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Random;
import javax.swing.Timer;



public class GamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH=600;
	
	static final int SCREEN_HEIGHT=600;
	/*
	 we are initially dividing our screen into unit squares that will be 
	 used as a unit of measurement in our calculations. 
	 
	 it is kind of like the unit centimeter squares on a graph. 
	 the unit_size is set accordingly. 
	 
	 game units is the number of unit boxes on our screen that can vary
	 based on the values of our defined constants. 
	 
	 */
	static final int UNIT_SIZE=25;
	
	static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	
	//delay helps us set the pace of our game.
	//NOTE: we can increment the delay with duration. 
	static final int DELAY=175;
	
/*	caterpillar has several body parts. to store the coordinates of 
	each of the body parts we use two arrays. 
	each element in the array will represent the unit square that is 
	occupied by that unit body part.
	
	setting constraints on the length of the caterpillar:
	the caterpillar cannot have more body parts than the game_units on any one 
	axis. 
	*/
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	
	/*the game begins with only one body part.*/
	int bodyParts = 5;
	
	int foodEaten;
	
	//since the food is only going to be the size of one unit square
	//we can store its coordinates with integers alone. 
	int foodX;
	int foodY;
	
	/* the gam begins with the caterpillar motionary in one direction.
	 * this direction can have values R, L, U, D.
	 * as a default lets have the caterpillar start its motion in the
	 * right direction
	 * */
	char direction = 'R';
	
	/* 
	 we use the running variable to store whether the game is still
	 active or not. this allows us to quit the game whenever necessary.
	 */
	boolean running = false;
	Random random;
	Timer timer;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame(){
		newFood();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		/*thisd for loop is used to create the graph base that was used
		 * at the time of creation of this game.
		 */
		if(running) {
			for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);

			}
			//setting the color of the food:
			g.setColor(new Color(95, 158, 160));
			//the food should be rounded hence: 
			g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
			
			/*this for loop is used to create the body of the caterpillar:*/
			for (int i = 0; i<bodyParts; i++) {
				//i ==0 only for the head of the caterpillar
				if(i==0) {
					g.setColor(new Color(255, 199, 207));
					// we are making the head of the caterpillar to have an oval shape
					g.fillOval(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
					
				}
				else {
					//this color is light pink from html
					g.setColor(new Color(255, 182, 193));
					g.fillOval(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+foodEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+foodEaten))/2, g.getFont().getSize());
		
		}
		else {
			gameOver(g);
		}
		
	}
	
	 
	public void newFood() {
		/* newFood() method is called at the beginning of the game 
		 * and everytime the current food is eaten by the caterpillar, the 
		 * game must continue hence a new food is generated at random
		 * coordinates.
		 */
		
		foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

		
	}
	public void move() {
	    for(int i = bodyParts - 1; i > 0; i--) {
	        x[i] = x[i - 1];
	        y[i] = y[i - 1];
	    }
	    switch(direction) {
	        case 'U':
	            y[0] = y[0] - UNIT_SIZE;
	            break;
	        case 'D':
	            y[0] = y[0] + UNIT_SIZE;
	            break;
	        case 'L':
	            x[0] = x[0] - UNIT_SIZE;
	            break;
	        case 'R':
	            x[0] = x[0] + UNIT_SIZE;
	            break;
	    }
	}

	public void checkfood() {
		//this method checks if we ran into an food.
		if((x[0] == foodX) && (y[0] == foodY)) {
			bodyParts++;
			foodEaten++;
			newFood();
		}
		
	}
	public void checkCollisions() {
	  //if by user input the caterpillar runs into itself that is its indexing
		//of the position array matches for any two body parts then the game ends.
		for (int i=bodyParts; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
	    // if the head touches any borders
	    if (x[0] < 0) {
	        running = false;
	    }
	    if (x[0] > SCREEN_WIDTH) {
	        running = false;
	    }
	    if (y[0] < 0) {
	        running = false;
	    }
	    if (y[0] > SCREEN_HEIGHT) {
	        running = false;
	    }
	    
	    if (!running) {
	        timer.stop();
	    }
	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+foodEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+foodEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
			
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkfood();
			checkCollisions();
			
		}
		
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}


