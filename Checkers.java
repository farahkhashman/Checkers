import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Checkers extends JPanel implements MouseListener, Runnable {
	
	// keeps track of the playing board. A -1 represents an empty space,
	// a 0 represents a piece for player 1, and a 1 represents a piece
	// for player 2
	private int[][] board;
	
	// player 1: turn = 0; player 2: turn = 1
	private int turn = 0;	

	// lasti and lastj represent the coordinates of the piece to be moved.
	// newi and newj represent the coordinates of the space to possibly move to.
	// precondition: all four coordinates are legal coordinates (between 0 and 7)
	
	// this method should check whether the piece at lasti, lastj can be moved to
	// the space at i, j.
	// if it can be moved, the board should be updated to reflect this move, and
	// true should be returned.
	// otherwise, the board should not change, and false should be returned.
	// 
	public boolean move(int lasti, int lastj, int newi, int newj) {
		boolean check = false;
		if(turn == 0) {
			if(board[newi][newj]==-1 && newi-1==lasti && (newj-1==lastj || newj+1==lastj)) {
				check = true;
				board[lasti][lastj]=-1;
				board[newi][newj]=0;
			}
			else if(board[newi][newj]==-1 && newi-2==lasti && (newj-2==lastj || newj+2==lastj) && board[((newi+lasti)/2)][((newj+lastj)/2)]==1) {
				check = true;
				board[lasti][lastj]=-1;
				board[newi][newj]=0;
				board[((newi+lasti)/2)][((newj+lastj)/2)]=-1;
			}
		}
		if(turn == 1) {
			if(board[newi][newj]==-1 && newi+1==lasti && (newj-1==lastj || newj+1==lastj)) {
				check = true;
				board[lasti][lastj]=-1;
				board[newi][newj]=1;
			}
			else if(board[newi][newj]==-1 && newi+2==lasti && (newj-2==lastj || newj+2==lastj) &&  board[((newi+lasti)/2)][((newj+lastj)/2)]==0) {
				check = true;
				board[((newi+lasti)/2)][((newj+lastj)/2)]=-1;
				board[lasti][lastj]=-1;
				board[newi][newj]=1;
			}
		}
		return check;
	}
	
	// ****DON'T GO PAST HERE*** //
	
	// constants that are predefined and won't change
	private final int width = 600, height = 600;
	private final int square_width = width/8, piece_width = square_width*2/3;
	private int lastx = -1,lasty = -1;
	
	public Checkers() {
		int[][] tempboard = {{-1,0,-1,0,-1,0,-1,0},
							{0,-1,0,-1,0,-1,0,-1},
							{-1,0,-1,0,-1,0,-1,0},
							{-1,-1,-1,-1,-1,-1,-1,-1},
							{-1,-1,-1,-1,-1,-1,-1,-1},
							{1,-1,1,-1,1,-1,1,-1},
							{-1,1,-1,1,-1,1,-1,1},
							{1,-1,1,-1,1,-1,1,-1}};
		
		board = tempboard;
	}
	
	public void run() {}
	
	// very simple main method to get the game going
	public static void main(String[] args) {
		Checkers game = new Checkers(); 
		game.start_game();
	}
 
	// does complicated stuff to initialize the graphics and key listeners
	public void start_game() {
		
		JFrame frame = new JFrame();
		frame.setSize(width, height+20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		this.addMouseListener(this);
		this.setFocusable(true);
		Thread t = new Thread(this);
		t.start();
	}

	// defines what we want to happen anytime we draw the game.
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j ++) {
				if ((i+j)%2 == 1)
					g.setColor(Color.black);
				else
					g.setColor(Color.WHITE);
				g.fillRect(j*square_width, i*square_width, square_width, square_width);
				if (j == lastx && i == lasty) {
					g.setColor(Color.yellow);
					g.fillOval(j*square_width+square_width/6, i*square_width+square_width/6, piece_width, piece_width);
				}
				else if (board[i][j] == 0) {
					g.setColor(Color.WHITE);
					g.drawOval(j*square_width+square_width/6, i*square_width+square_width/6, piece_width, piece_width);
				}
				else if (board[i][j] == 1) {
					g.setColor(Color.RED);
					g.fillOval(j*square_width+square_width/6, i*square_width+square_width/6, piece_width, piece_width);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int j = e.getX()/square_width;
		int i = e.getY()/square_width;
		
		if (board[i][j] == turn) {
			lastx = j;
			lasty = i;
		}
		
		
		else if (lastx >= 0 && move(lasty,lastx, i,j)) {
			turn = (turn+1)%2;
			lastx = -1; lasty = -1;
		}
		
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}