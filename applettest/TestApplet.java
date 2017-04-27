package applettest;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class TestApplet extends Applet implements KeyListener, Runnable {
	boolean p1up = false, p1down = false, p1serve = false,
			p2up = false, p2down = false, p2serve = false,
			turn = false, gameRuning = false, served = false,
			closeInfo = false, textOnOff = true;
	ArrayList<Elements> elements = new ArrayList<>();
	int pw = 17, ph = 85, ps = 6,
		bs = 13, bdy = 4, bdx = 5,
		ah, aw,
		fps = 60,
		p1score = 0, p2score = 0,
		textFlash = 0;

	//koda, ki prepreči utripanje zaslona
	private Image offScreenImage;
	private Dimension offScreenSize;
	private Graphics offScreenGraphics;

	@Override
	public final synchronized void update(Graphics g) {
		@SuppressWarnings("deprecation")
		Dimension d = size();
		if ((offScreenImage == null) || (d.width != offScreenSize.width) || (d.height != offScreenSize.height)) {
			offScreenImage = createImage(d.width, d.height);
			offScreenSize = d;
			offScreenGraphics = offScreenImage.getGraphics();
		}
		offScreenGraphics.clearRect(0, 0, d.width, d.height);
		paint(offScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	//konec kode za preprečevanje utripanja zaslona
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		System.out.println("*Applet Initialized*");
		setBackground(Color.decode("#242424")); // ozadje
		addKeyListener(this);
		setFocusable(true);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		System.out.println("*Applet Started*");
		Dimension appletsize = this.getSize();
		ah = appletsize.height; // applets height
		aw = appletsize.width; // applets width
		turn = ((int)(Math.random()*2+1)) == 1 ? true : false; //naključno izbere kdo je na vrsti
		elements.add(new Paddle(0, 0, pw, ph)); // player 2
		elements.add(new Paddle(aw - pw, 0, pw, ph)); // player 1
		if(turn)
			elements.add(new Ball(aw * 3 / 4, ah / 2, bs)); // žogica
		else{
			elements.add(new Ball(aw / 4, ah / 2, bs)); // žogica
		}
		(new Thread(this)).start(); // zažene thread 
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				repaint();
				Thread.sleep(1000 / fps);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("*Applet Stopped*");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("*Applet Destroyed*");
	}

	@Override
	public void paint(Graphics g) {
		if(closeInfo){
			drawBoard(g);
			movePlayers(g);
			if(!gameRuning)
				startGame(g);
			else
				moveBall(g);
		}
		else{
			displayInfo(g);
		}
	}
	
	public void displayInfo(Graphics g){
		g.setColor(Color.decode("#848484"));
		g.setFont(new Font("Lucida Console", Font.PLAIN, 15));
		//player 1 commands
		g.drawString("Player 1 commands: ", 30, 30);
		g.drawString("up arrow", 30, 46);
		g.drawString("up", 155, 46);
		g.drawString("down arrow", 30, 62);
		g.drawString("down", 155, 62);
		g.drawString("left arrow", 30, 78);
		g.drawString("serve", 155, 78);
		//player 2 commands
		g.drawString("Player 2 commands: ", 30, 110);
		g.drawString("w", 30, 126);
		g.drawString("up", 155, 126);
		g.drawString("s", 30, 142);
		g.drawString("down", 155, 142);
		g.drawString("d", 30, 158);
		g.drawString("serve", 155, 158);
		//close this
		textFlash++;
		if(textFlash % 40 == 0)
			textOnOff = !textOnOff;
		if(textOnOff)
			g.drawString("-- PRESS ANY KEY TO CLOSE THIS WINDOW --", 70, ah * 3 / 4);
	}

	public void drawBoard(Graphics g) {
		g.setColor(Color.white); // spremeniš barvo črt / polnila
		g.drawLine(aw / 2, 0, aw / 2, ah); // sredinska črta
		for (Elements x : elements) // izris elementov z for each
			x.draw(g); // abstraktna metoda
		String sc = p2score + " - " + p1score;
		g.setFont(new Font("Lucida Console", Font.BOLD, 20));
		g.drawString(sc, (aw / 2) - 32, 20);
		g.setColor(Color.decode("#303030"));
		g.setFont(new Font("Lucida Console", Font.BOLD, 50));
		if(!served){
			if(!turn)
				g.setColor(Color.decode("#848484"));
			else 
				g.setColor(Color.decode("#303030"));
		}
		g.drawString("P2", aw / 4 - 25, ah / 2);
		if(!served){
			if(turn)
				g.setColor(Color.decode("#848484"));
			else 
				g.setColor(Color.decode("#303030"));
		}
		g.drawString("P1", aw * 3 / 4 - 25, ah / 2);
	}
	
	public void movePlayers(Graphics g) {
		if (p1up) {
			if (elements.get(1).getY() > 0) {
				elements.get(1).setY(elements.get(1).getY() - ps);
				elements.get(1).draw(g);
			}
		}
		if (p1down) {
			if (elements.get(1).getY() < (ah - ph)) {
				elements.get(1).setY(elements.get(1).getY() + ps);
				elements.get(1).draw(g);
			}
		}
		if (p2up) {
			if (elements.get(0).getY() > 0) {
				elements.get(0).setY(elements.get(0).getY() - ps);
				elements.get(0).draw(g);
			}
		}
		if (p2down) {
			if (elements.get(0).getY() < (ah - ph)) {
				elements.get(0).setY(elements.get(0).getY() + ps);
				elements.get(0).draw(g);
			}
		}
	}
	
	public void moveBall(Graphics g){
		int[] coor = elements.get(2).getCoor();
		if((coor[1] <= 0 + bs) || (coor[1] >= ah - bs))
			bdy = -bdy;
		if(coor[0] <= 0 + bs || coor[0] >= aw -bs)
			reset();
		//odboj pri P2
		int[] coorp2 = elements.get(0).getCoor();
		if(coor[1] >= coorp2[1] && coor[1] <= coorp2[1] + ph)
			if(coor[0] - bs == coorp2[0] + pw){
				bdx = -bdx;
				turn = !turn;
			}
		//odboj pri P1
		int[] coorp1 = elements.get(1).getCoor();
		if(coor[1] >= coorp1[1] && coor[1] <= coorp1[1] + ph)
			if(coor[0] + bs == coorp1[0]){
				bdx = -bdx;
				turn = !turn;
			}
		elements.get(2).setX(elements.get(2).getX() + bdx);
		elements.get(2).setY(elements.get(2).getY() + bdy);
	}
	
	public void reset(){
		gameRuning = false;
		bdy = 5;
		if(turn){
			p1score++;
			bdx = -bdx;
			elements.get(2).setY(ah / 2);
			elements.get(2).setX(aw * 3 / 4);
		}
		else{
			p2score++;
			elements.get(2).setY(ah / 2);
			elements.get(2).setX(aw / 4);
		}
		served = false;
	}

	public void startGame(Graphics g) {
		int x = (int)(Math.random()*2+1);
		if(x == 2)
			bdy = -bdy;
		if(turn && p1serve){
			gameRuning = true;
			bdx = -bdx;
			System.out.println("--P1 served--");
			served = true;
		}
		else if(!turn && p2serve){
			gameRuning = true;
			System.out.println("--P2 served--");
			served = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {
		case 37:
			p1serve = true;
			break; // left arrow
		case 38:
			p1up = true;
			break; // up arrow
		case 40:
			p1down = true;
			break; // down arrow
		case 68:
			p2serve = true;
			break; // d
		case 87:
			p2up = true;
			break; // w
		case 83:
			p2down = true;
			break; // s
		}
		
		closeInfo = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {
		case 37:
			p1serve = false;
			break;
		case 38:
			p1up = false;
			break;
		case 40:
			p1down = false;
			break;
		case 68:
			p2serve = false;
			break;
		case 87:
			p2up = false;
			break;
		case 83:
			p2down = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public static void infoBox(String infoMessage){
		JOptionPane.showMessageDialog(null, infoMessage);
	}

}
