import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {
	final int spacing = 1;
	public boolean resetter = false;
	public boolean flagger = false;
	public String Fonts = "Tahoma";
	public int perMIne = 20;
	int neight = 0;

	String vicMes = "Nothing!";

	public int mX = -100;
	public int mY = -100;

	public int flagX = 301;
	public int flagY = 6;
	public int cflagX = flagX + 35;
	public int cflagY = flagY + 35;

	public int smileX = 605;
	public int smileY = 5;
	public int centerfaceX = smileX + 35;
	public int centerfaceY = smileY + 35;

	public int vicX = 800;
	public int vicY = -50;
	Random rand = new Random();

	int[][] mines = new int[16][9];
	int[][] neighbours = new int[16][9];
	boolean[][] revealed = new boolean[16][9];
	boolean[][] flagged = new boolean[16][9];

	public boolean life = true;
	public boolean victory = false;
	public boolean defeat = false;
	public Date start = new Date();
	public Date end = new Date();
	public int time;

	public GUI() {
		this.setTitle("KABOOM!!!");
		this.setSize(1286, 829);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (rand.nextInt(100) < perMIne) /* how many mine */ {
					mines[i][j] = 1;
				} else {
					mines[i][j] = 0;
				}
				revealed[i][j] = false;
			}
		}

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				neight = 0;
				for (int m = 0; m < 16; m++) {
					for (int n = 0; n < 9; n++) {
						if (!(m == i && n == j)) {
							if (isN(i, j, m, n) == true)
								neight++;
						}
					}
				}
				neighbours[i][j] = neight;
			}
		}
		Board board = new Board();
		this.setContentPane(board);

		Move move = new Move();
		this.addMouseMotionListener(move);

		Click click = new Click();
		this.addMouseListener(click);
	}

	public class Board extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1280, 800);
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 9; j++) {
					g.setColor(Color.gray);
					if (mines[i][j] == 1) {
						g.setColor(Color.gray); // bombs
					}
					if (revealed[i][j] == true) {
						g.setColor(Color.black);
						if (mines[i][j] == 1) {
							g.setColor(Color.red);
						}
					}
					if (mX >= spacing + i * 80 && mX < spacing + i * 80 + 80 - 2 * spacing
							&& mY >= spacing + j * 80 + 80 + 26 && mY < spacing + j * 80 + 26 + 80 + 80 - 2 * spacing) {
						g.setColor(Color.white);
					}
					g.fillRect(spacing + i * 80, spacing + j * 80 + 80, 80 - 2 * spacing, 80 - 2 * spacing);
					if (revealed[i][j] == true) {
						if (mines[i][j] == 1) {
							g.setColor(Color.black);
						} else {
							g.setColor(Color.white);
						}
						if (mines[i][j] == 0) {
							g.setFont(new Font(Fonts, Font.BOLD, 40));
							if (neighbours[i][j] != 0) {
								g.drawString(Integer.toString(neighbours[i][j]), i * 80 + 28, j * 80 + 80 + 58);
							}
						} else if (mines[i][j] == 1) {

							// bomb painting
							g.fillRect(i * 80 + 37, j * 80 + 93, 5, 40);
							g.fillRect(i * 80 + 30, j * 80 + 103, 20, 40);
							g.fillRect(i * 80 + 20, j * 80 + 113, 40, 20);
							g.fillRect(i * 80 + 25, j * 80 + 108, 30, 30);
						}
					}

					// flags painting
					if (flagged[i][j] == true) {
						g.setColor(Color.white);
						g.fillRect(i * 80 + 25, j * 80 + 80 + 10, 3, 50);
						g.setColor(Color.red);
						g.fillRect(i * 80 + 28, j * 80 + 80 + 10, 20, 23);
					}
				}
			}
			// smile painting
			g.setColor(Color.yellow);
			g.fillOval(smileX, smileY, 70, 70);
			g.setColor(Color.black);
			if (life == true) {
				g.fillOval(smileX + 15, smileY + 20, 10, 10);
				g.fillOval(smileX + 45, smileY + 20, 10, 10);
				g.fillRect(smileX + 10, smileY + 40, 50, 5);
				g.fillRect(smileX + 10, smileY + 45, 5, 5);
				g.fillRect(smileX + 55, smileY + 45, 5, 5);
				g.fillRect(smileX + 15, smileY + 50, 5, 5);
				g.fillRect(smileX + 50, smileY + 50, 5, 5);
				g.fillRect(smileX + 20, smileY + 55, 30, 5);
			} else {
				g.fillRect(smileX + 10, smileY + 15, 5, 5);
				g.fillRect(smileX + 25, smileY + 15, 5, 5);
				g.fillRect(smileX + 40, smileY + 15, 5, 5);
				g.fillRect(smileX + 55, smileY + 15, 5, 5);
				g.fillRect(smileX + 15, smileY + 20, 10, 5);
				g.fillRect(smileX + 45, smileY + 20, 10, 5);
				g.fillRect(smileX + 10, smileY + 25, 5, 5);
				g.fillRect(smileX + 25, smileY + 25, 5, 5);
				g.fillRect(smileX + 40, smileY + 25, 5, 5);
				g.fillRect(smileX + 55, smileY + 25, 5, 5);
				g.fillRect(smileX + 10, smileY + 45, 50, 5);
			}
			// time counter painting
			g.setColor(Color.white);
			g.fillRect(1075, 5, 200, 72);
			g.setColor(Color.black);
			g.fillRect(1078, 8, 194, 66);
			if (defeat == false && victory == false) {
				time = (int) (new Date().getTime() - start.getTime()) / 1000;
			}
			g.setColor(Color.white);
			if (victory == true) {
				g.setColor(Color.green);
			} else if (defeat == true) {
				g.setColor(Color.red);
			}
			g.setFont(new Font(Fonts, Font.PLAIN, 50));
			g.drawString(Integer.toString(time), 1150, 60);

			// victory message painting
			if (victory == true) {
				g.setColor(Color.green);
				vicMes = " YOU WIN~~";
			} else if (defeat == true) {
				g.setColor(Color.red);
				vicMes = " YOU LOSE!!!";
			}
			if (victory == true || defeat == true) {
				vicY = -50 + (int) (new Date().getTime() - end.getTime()) / 10;
				if (vicY > 70) {
					vicY = 70;
				}
				g.setFont(new Font(Fonts, Font.PLAIN, 55));
				g.drawString(vicMes, vicX - 70, vicY - 5);
			}

			// flag buttons painting
			g.setColor(Color.white);
			if (flagger == true) {
				g.setColor(Color.red);
			}
			g.fillOval(300, smileY, 70, 70);
			g.setColor(Color.black);
			g.fillOval(flagX, flagY, 68, 68);
			// flag
			g.setColor(Color.white);
			g.fillRect(flagX + 25, flagY + 10, 3, 50);
			g.setColor(Color.red);
			g.fillRect(flagX + 28, flagY + 10, 20, 23);

		}
	}

	public class Move implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mX = e.getX();
			mY = e.getY();
		}
	}

	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			mX = e.getX();
			mY = e.getY();
			if (inBoxX() != -1 && inBoxY() != -1) {
				if (flagger == true && revealed[inBoxX()][inBoxY()] == false) {
					if (flagged[inBoxX()][inBoxY()] == false) {
						flagged[inBoxX()][inBoxY()] = true;
					} else {
						flagged[inBoxX()][inBoxY()] = false;
					}
				} else {
					if (flagged[inBoxX()][inBoxY()] == false) {
						revealed[inBoxX()][inBoxY()] = true;
					}
				}
			} else {
				System.out.println("The point is not inside the board");
			}

			if (inFace() == true) {
				resetAll();
			}
			if (inFlagger() == true) {
				if (flagger == false) {
					flagger = true;
					System.out.println("Inflagger = true");
				} else {
					flagger = false;
					System.out.println("Inflagger = false");
				}
			}
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
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public int inBoxX() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mX >= spacing + i * 80 && mX < spacing + i * 80 + 80 - 2 * spacing
						&& mY >= spacing + j * 80 + 80 + 26 && mY < spacing + j * 80 + 26 + 80 + 80 - 2 * spacing) {
					return i;
				}
			}
		}
		return -1;
	}

	public int inBoxY() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mX >= spacing + i * 80 && mX < spacing + i * 80 + 80 - 2 * spacing
						&& mY >= spacing + j * 80 + 80 + 26 && mY < spacing + j * 80 + 26 + 80 + 80 - 2 * spacing) {
					return j;
				}
			}
		}
		return -1;
	}

	public boolean isN(int mX, int mY, int cX, int cY) {
		if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1) {
			return true;
		}
		return false;
	}

	public void checkVictory() {
		if (defeat == false) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 9; j++) {
					if (revealed[i][j] == true && mines[i][j] == 1) {
						defeat = true;
						life = false;
						end = new Date();
					}
				}
			}
		}
		if (totalBoxsRevealed() >= 144/* (16*9) */ - totalMines() && victory == false) {
			victory = true;
			end = new Date();
		}
	}

	public int totalMines() {
		int total = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mines[i][j] == 1) {
					total++;
				}
			}
		}
		return total;
	}

	public int totalBoxsRevealed() {
		int totals = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (revealed[i][j] == true) {
					totals++;
				}
			}
		}
		return totals;
	}

	public void resetAll() {

		resetter = true;
		vicMes = "Nothing!";
		flagger = false;
		start = new Date();
		vicY = -50;
		life = true;
		victory = false;
		defeat = false;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (rand.nextInt(100) < perMIne) {
					mines[i][j] = 1;
				} else {
					mines[i][j] = 0;
				}
				revealed[i][j] = false;
				flagged[i][j] = false;
			}
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				neight = 0;
				for (int m = 0; m < 16; m++) {
					for (int n = 0; n < 9; n++) {
						if (!(m == i && n == j)) {
							if (isN(i, j, m, n) == true)
								neight++;
						}
					}
				}
				neighbours[i][j] = neight;
			}
		}
		resetter = false;
	}

	public boolean inFace() {
		int dif = (int) Math.sqrt((Math.abs(mX - centerfaceX) * Math.abs(mX - centerfaceX))
				+ (Math.abs(mY - centerfaceY) * Math.abs(mY - centerfaceY)));
		if (dif < 35) {
			return true;
		}
		return false;
	}

	public boolean inFlagger() {
		int difs = (int) Math.sqrt(
				(Math.abs(mX - cflagX) * Math.abs(mX - cflagX)) + (Math.abs(mY - cflagY) * Math.abs(mY - cflagY)));
		if (difs < 35) {
			return true;
		}
		return false;
	}

}
