package com.dvoragame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString("Score: " + Game.score, 6, 12);
	}

}
