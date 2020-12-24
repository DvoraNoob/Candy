package com.dvoragame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable, MouseMotionListener, MouseListener{
	
	public static final int WIDTH = 288;
	public static final int HEIGHT = 288;
	public static final int SCALE = 2;
	
	public static final int FPS = 1000/60;
	
	public Board board;
	public UI ui;
	
	public static boolean selected = false;
	public static int previousx = 0, previousy = 0;
	public static int nextx = -1, nexty = -1;
	
	public static int score = 0;
	
	public BufferedImage image =  new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_BGR);
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		initFrame();
		board = new Board();
		ui = new UI();
	}
	
	public static void main(String[] Args) {
		Game game = new Game();
		new Thread(game).start();
		
	}
	
	public void initFrame() {
		JFrame frame = new JFrame("Candy Crush");
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setAutoRequestFocus(true);
	}
	
	public void update() {
		board.update();
		
		if(Game.previousx < 0 || Game.previousx >= Board.grid * Board.WIDTH
		|| Game.previousy < 0 || Game.previousy >= Board.grid * Board.HEIGHT) {
			Game.nextx = -1;
			Game.nexty = -1;
			Game.selected = false;
		}
		
		if(selected && (nextx != -1 && nexty != -1)) {
			
			if(Game.previousx < 0 || Game.nextx >= Board.grid * Board.WIDTH
			|| Game.previousy < 0 || Game.nexty >= Board.grid * Board.HEIGHT) {
				Game.nextx = -1;
				Game.nexty = -1;
				Game.selected = false;
			}

			int posx1 = previousx/Board.grid;
			int posy1 = previousy/Board.grid;
			
			int posx2 = nextx/Board.grid;
			int posy2 = nexty/Board.grid;
			
			if((posx2 == posx1 + 1 || posx2 == posx1 - 1) &&
			posy2 == posy1 || posy2 == posy1 - 1 ||  posy2 == posy1 + 1 ) {
				if((posx2 >= posx1 + 1 || posx2 <= posx1 - 1) &&
						(posy2 >= posy1 + 1 || posy2 <= posy1 - 1)	) {
					System.out.println("nao pode mover");
					nextx = -1;
					nexty = -1;
					selected = false;
					return;
				}
				
				int val1 = Board.BOARD[posx2][posy2];
				int val2 = Board.BOARD[posx1][posy1];
				Board.BOARD[posx1][posy1] = val1;
				Board.BOARD[posx2][posy2] = val2;
				nextx = -1;
				nexty = -1;
				System.out.println("moveu");
				selected = false;
				return;
			}else {
				nextx = -1;
				nexty = -1;
				System.out.println("nao pode mover");
				selected = false;
				return;
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(255,192,203));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		board.render(g);
		ui.render(g);
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE, null);
		bs.show();
	}

	public void run() {
		while(true) {
			update();
			render();
			try {
				Thread.sleep(FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(selected == false) {
			previousx = e.getX()/SCALE - Board.offsetx;
			previousy = e.getY()/SCALE - Board.offsety;
			selected = true;
		}else {
			nextx = e.getX()/SCALE - Board.offsetx;
			nexty = e.getY()/SCALE - Board.offsety;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
