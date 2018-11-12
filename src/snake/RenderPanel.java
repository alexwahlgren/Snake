package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

	public BufferedImage bg, playerImg, targetImg;
	public static int curColor = 100;
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			
			if (Snake.lionTheme == false) {
				bg = ImageIO.read(new File(
						"C:\\Users\\alexw\\OneDrive\\Documents\\Studier\\Programmering\\Eclipse\\Snake\\src\\snake\\oocean.png"));
			}
			if (Snake.lionTheme == true) {
				bg = ImageIO.read(new File(
						"C:\\Users\\alexw\\OneDrive\\Documents\\Studier\\Programmering\\Eclipse\\Snake\\src\\snake\\elefant.jpg"));	
			}
			g.drawImage(bg, 0, 0, null);	
			
		g.setColor(new Color(0, curColor, 255, 100));
		g.fillRect(0, 0, 800, 700);
		if (curColor < 255)
			curColor++;
		else curColor = 100;
		
		Snake snake = Snake.snake;
		Color colorTail = Color.GREEN;
		if (Snake.lionTheme == true)
			colorTail = Color.ORANGE;
		for (Point point : snake.snakeParts) {
			g.setColor(colorTail);
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, 
					Snake.SCALE, Snake.SCALE);
		}
		if (Snake.lionTheme == false) {
			playerImg = ImageIO.read(new File(
					"C:\\Users\\alexw\\OneDrive\\Documents\\Studier\\Programmering\\Eclipse\\Snake\\src\\snake\\shark.png"));
		}
		if (Snake.lionTheme == true) {
			playerImg = ImageIO.read(new File(
					"C:\\Users\\alexw\\OneDrive\\Documents\\Studier\\Programmering\\Eclipse\\Snake\\src\\snake\\lion.png"));
		}
		g.drawImage(playerImg, snake.head.x*Snake.SCALE, snake.head.y*Snake.SCALE, null);
		g.setColor(new Color(255,255,255,0));
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, 
				Snake.SCALE, Snake.SCALE);
		if (Snake.lionTheme == false) {
			targetImg = ImageIO.read(new File(
					"C:\\Users\\alexw\\OneDrive\\Documents\\Studier\\Programmering\\Eclipse\\Snake\\src\\snake\\spliff.png"));
		}
		if (Snake.lionTheme == true) {
			targetImg = ImageIO.read(new File(
					"C:\\Users\\alexw\\OneDrive\\Documents\\Studier\\Programmering\\Eclipse\\Snake\\src\\snake\\meat.png"));
		}
		g.drawImage(targetImg, snake.cherry.x*Snake.SCALE, snake.cherry.y*Snake.SCALE, null);
		g.setColor(new Color(255,255,255,0));
		g.fillRect(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, 
				Snake.SCALE, Snake.SCALE);
		String Score = "Score: "+snake.score;
		String Highscore = "Highscore: "+snake.highscore;
		Font f= new Font("SCORE", Font.TRUETYPE_FONT, 25);
		g.setFont(f);
		g.setColor(Color.BLACK);
		g.drawString(Score, 350, 50);
		g.drawString(Highscore, 625, 50);
		if(snake.over) {
			Font s= new Font("GAMEOVER", Font.PLAIN, 50);
			g.setFont(s);
			g.setColor(Color.DARK_GRAY);
			if(snake.score < snake.highscore)	
				g.drawString("Game over!", 270, 290);
			if(snake.score>=snake.highscore && snake.score !=0) 
				g.drawString("New Highscore!", 230, 290);
			g.drawString("Press SPACE to try again", 120, 350);
			
		}
		String curTheme = "Current theme: Reggae shark";
		int xValue = 250;
		if(Snake.lionTheme == true) {
			curTheme = "Current theme: Lion";
			xValue = 295;
		}
		if(snake.paused) {
			Font s= new Font("GAMEOVER", Font.PLAIN, 50);
			g.setFont(s);
			g.setColor(Color.DARK_GRAY);
			g.drawString("Paused", 300, 350);
			g.setFont(new Font("theme", Font.PLAIN, 20));
			g.drawString(curTheme, xValue, 380);
			g.drawString("Press C to change theme", 270, 410);
		}
		}
		catch(Exception e){
			System.out.println("ERROR");
		}
		
	}
}
