package com.dvoragame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Board {
	
	public static BufferedImage spritesheet;
	
	public static final int WIDTH = 6;
	public static final int HEIGHT = 6;
	public static int[][] BOARD;
	
	public static int grid = 44;
	public static int offsetx = 12,offsety = 20;
	
	public static int CANDY_1 = 0, CANDY_2 = 1, CANDY_3 = 2;
	
	public BufferedImage DOCE_0_SPRITE = Board.getSprite(1299,189,118,118);
	public BufferedImage DOCE_1_SPRITE = Board.getSprite(1449,330,130,130);
	public BufferedImage DOCE_2_SPRITE = Board.getSprite(1632,17,127,127);
	
	public Board() {
		BOARD = new  int[WIDTH][HEIGHT];
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < WIDTH; y++) {
				BOARD[x][y] = new Random().nextInt(3);
			}
		}
	}
	
	public static BufferedImage getSprite(int x,int y,int width,int height) {
		if(spritesheet == null) {
			try {
				spritesheet = ImageIO.read(Board.class.getResource("/spritesheet.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return spritesheet.getSubimage(x, y, width, height);
	}
	
	public void update() {
		ArrayList<Candy> combo = new ArrayList<Candy>();
		for(int yy = 0; yy < HEIGHT; yy++) {
			if(combo.size() == 3) {
				for(int i = 0; i < combo.size(); i++) {
					int xtemp = combo.get(i).x;
					int ytemp = combo.get(i).y;
					BOARD[xtemp][ytemp] = new Random().nextInt(3);
				}
				combo.clear();
				Game.score+=10;
				return;
			}
			combo.clear();
			for(int xx = 0; xx < WIDTH; xx++) {
				int candy = BOARD[xx][yy];
				if(combo.size() == 3) {
					for(int i = 0; i < combo.size(); i++) {
						int xtemp = combo.get(i).x;
						int ytemp = combo.get(i).y;
						BOARD[xtemp][ytemp] = new Random().nextInt(3);
					}
					combo.clear();
					return;
				}
				if(combo.size() == 0) {
					combo.add(new Candy(xx,yy,candy));
				}else if(combo.size() > 0) {
					if(combo.get(combo.size() - 1).CANDY_TYPE == candy) {
						combo.add(new Candy(xx,yy,candy));
					}else {
						combo.clear();
						combo.add(new Candy(xx,yy,candy));
					}
				}
			}	
		}
		
		combo = new ArrayList<Candy>();
		for(int xx = 0; xx < WIDTH; xx++) {
			if(combo.size() == 3) {
				for(int i = 0; i < combo.size(); i++) {
					int xtemp = combo.get(i).x;
					int ytemp = combo.get(i).y;
					BOARD[xtemp][ytemp] = new Random().nextInt(3);
				}
				combo.clear();
				Game.score+=10;
				return;
			}
			combo.clear();
			for(int yy = 0; yy < HEIGHT; yy++) {
				int candy = BOARD[xx][yy];
				if(combo.size() == 3) {
					for(int i = 0; i < combo.size(); i++) {
						int xtemp = combo.get(i).x;
						int ytemp = combo.get(i).y;
						BOARD[xtemp][ytemp] = new Random().nextInt(3);
					}
					combo.clear();
					return;
				}
				if(combo.size() == 0) {
					combo.add(new Candy(xx,yy,candy));
				}else if(combo.size() > 0) {
					if(combo.get(combo.size() - 1).CANDY_TYPE == candy) {
						combo.add(new Candy(xx,yy,candy));
					}else {
						combo.clear();
						combo.add(new Candy(xx,yy,candy));
					}
				}
			}	
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(255,10,200,255));
		g2.fillRect(0, 0, Game.WIDTH, 15);
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < WIDTH; y++) {
				g2.setColor(new Color(0,191,255,150));
				g2.fillRoundRect(x*grid + offsetx, y*grid + offsety, 42, 42, 5, 5);
				int candy = BOARD[x][y];
				if(candy == CANDY_1) {
					g2.setColor(Color.red);
					g2.drawImage(DOCE_0_SPRITE, x*grid + offsetx + 8, y*grid + offsety + 8, 25, 25,null);
				}
				if(candy == CANDY_2) {
					g2.setColor(Color.blue);
					g2.drawImage(DOCE_1_SPRITE, x*grid + offsetx + 8, y*grid + offsety + 8, 25, 25,null);
				}
				if(candy == CANDY_3) {
					g2.setColor(Color.orange);
					g2.drawImage(DOCE_2_SPRITE, x*grid + offsetx + 10, y*grid + offsety + 8, 25, 25,null);
				}
				
				if(Game.selected) {
					int posx1 = Game.previousx/48;
					int posy1 = Game.previousy/48;
					g.setColor(Color.black);
					g.drawRoundRect(posx1*grid + offsetx, posy1*grid + offsety, 42, 42, 5, 5);
				}
			}
		}
	}

}
