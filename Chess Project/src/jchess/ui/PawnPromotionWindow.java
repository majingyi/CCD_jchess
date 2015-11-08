package jchess.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import jchess.pieces.Bishop;
import jchess.pieces.Knight;
import jchess.pieces.Pawn;
import jchess.pieces.Queen;
import jchess.pieces.Rook;

/**
 * Class responsible for promotion of a pawn. When pawn reach the end of the
 * chessboard it can be change to rook, bishop, queen or knight. For what pawn
 * is promoted decides player.
 * 
 * @param parent
 *          Information about the current piece
 * @param color
 *          The player color
 */
public class PawnPromotionWindow extends JDialog implements ActionListener {

	private static final long	serialVersionUID	= -9128995891728983191L;

	private JButton						knightButton			= null;
	private JButton						bishopButton			= null;
	private JButton						rookButton				= null;
	private JButton						queenButton				= null;
	private String						result						= Pawn.SYMBOL;

	public PawnPromotionWindow(Frame parent, String color) {
		super(parent);
		this.setTitle("Choose piece");
		this.setMinimumSize(new Dimension(520, 130));
		this.setSize(new Dimension(520, 130));
		this.setMaximumSize(new Dimension(520, 130));
		this.setResizable(false);
		this.setLayout(new GridLayout(1, 4));

		this.knightButton = new JButton(new ImageIcon(GUI.loadImage("Knight-" + color + ".png")));
		this.bishopButton = new JButton(new ImageIcon(GUI.loadImage("Bishop-" + color + ".png")));
		this.rookButton = new JButton(new ImageIcon(GUI.loadImage("Rook-" + color + ".png")));
		this.queenButton = new JButton(new ImageIcon(GUI.loadImage("Queen-" + color + ".png")));

		this.knightButton.addActionListener(this);
		this.bishopButton.addActionListener(this);
		this.rookButton.addActionListener(this);
		this.queenButton.addActionListener(this);

		this.add(queenButton);
		this.add(rookButton);
		this.add(bishopButton);
		this.add(knightButton);
	}

	/**
	 * Method setting the color fo promoted pawn
	 * 
	 * @param color
	 *          The players color
	 */
	public void setColor(String color) {
		this.knightButton.setIcon(new ImageIcon(GUI.loadImage("Knight-" + color + ".png")));
		this.bishopButton.setIcon(new ImageIcon(GUI.loadImage("Bishop-" + color + ".png")));
		this.rookButton.setIcon(new ImageIcon(GUI.loadImage("Rook-" + color + ".png")));
		this.queenButton.setIcon(new ImageIcon(GUI.loadImage("Queen-" + color + ".png")));
	}

	/**
	 * Method wich is changing a pawn into queen, rook, bishop or knight
	 * 
	 * @param arg0
	 *          Capt information about performed action
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == queenButton) {
			result = Queen.SYMBOL;
		} else if (arg0.getSource() == rookButton) {
			result = Rook.SYMBOL;
		} else if (arg0.getSource() == bishopButton) {
			result = Bishop.SYMBOL;
		} else // knight
		{
			result = Knight.SYMBOL;
		}
		this.setVisible(false);
	}

	public String getResult() {
		return result;
	}
}
