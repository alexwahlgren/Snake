package snake;

import java.awt.Dimension;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.Random;


import javax.swing.JFrame;

import javax.swing.Timer;
import java.io.File;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;



public class Snake implements ActionListener, KeyListener {

	public static Snake snake;

	public RenderPanel renderPanel;

	public JFrame jframe;

	public Timer timer = new Timer(20, this);

	public ArrayList<Point> snakeParts = new ArrayList<Point>();

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;

	public int ticks = 0, direction = DOWN, score, tailLength, highscore;

	public Point head, cherry;

	public Random random;

	public boolean over = false, paused;
	
	public static boolean lionTheme = false;

	public Dimension dim;
	
	public static File yourFile;
	
    public static AudioInputStream stream;
    
    public static AudioFormat format;
    
    public static DataLine.Info info;
    
    public static Clip clip;
    
    

    
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Lion");
		jframe.setVisible(true);
		jframe.setSize(805, 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	
	public void startGame() {
		over = false;
		paused = false;
		score = 0;
		tailLength = 0;
		direction = DOWN;
		head = new Point(0, -1);
		random = new Random();
		snakeParts.clear();
		cherry = new Point(random.nextInt(77), random.nextInt(65));
		for (int i = 0; i < tailLength; i++) {
			snakeParts.add(new Point(head.x, head.y));
		}
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	//	System.out.println(cherry.x + ", " + cherry.y +"\t\t"+head.x+", "+head.y);
		renderPanel.repaint();
		ticks++;
		//lägre speed -> högre hastighet
		int speed = 3;
		if(score>=50)
			speed = 2;
		if(score >= 100)
			speed = 1;
		if (ticks % speed == 0 && head != null && !over && !paused) {
			snakeParts.add(new Point(head.x, head.y));
			if (direction == UP)
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
					head = new Point(head.x, head.y - 1);
				else
					over = true;
			if (direction == DOWN)
				if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1))
					head = new Point(head.x, head.y + 1);
				else
					over = true;
			if (direction == LEFT)
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
					head = new Point(head.x - 1, head.y);
				else
					over = true;
			if (direction == RIGHT)
				if (head.x + 1 < 79 && noTailAt(head.x + 1, head.y))
					head = new Point(head.x + 1, head.y);
				else
					over = true;
			if (snakeParts.size() > tailLength)
				snakeParts.remove(0);
			if (cherry != null) {
				if (head.equals(cherry)) {
					score += 10;
					if(score>highscore)
						highscore = score;
					tailLength++;
					cherry.setLocation(random.nextInt(77), random.nextInt(65));
				}

			}
		}
	}
	
	public boolean noTailAt(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args){
		snake = new Snake();
		playMusic();

	}
	
	public static void playMusic() {
		try {
		    if (lionTheme == false) {	
				yourFile = new File("C:\\Users\\alexw\\Downloads\\reggaeshark.wav");
		    }
		    if (lionTheme == true) {
		    	yourFile = new File("C:\\Users\\alexw\\Downloads\\totoAfrica.wav");
		    }
		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		    Thread.sleep(1000);
		}
		catch (Exception e){
			System.out.println("Error");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == KeyEvent.VK_A && direction != RIGHT)
			direction = LEFT;
		if (i == KeyEvent.VK_D && direction != LEFT)
			direction = RIGHT;
		if (i == KeyEvent.VK_W && direction != DOWN)
			direction = UP;
		if (i == KeyEvent.VK_S && direction != UP)
			direction = DOWN;
		if (i == KeyEvent.VK_SPACE) {
			if(over){
				if(score>highscore)
					highscore = score;
				startGame();
			}
			else {
				paused = !paused;
				if(paused) {
					clip.stop();
				}
				else clip.start();
			}
		}
		if (i == KeyEvent.VK_C && paused == true) {
			lionTheme = !lionTheme;
			clip.close();
			playMusic();
		}
			
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
