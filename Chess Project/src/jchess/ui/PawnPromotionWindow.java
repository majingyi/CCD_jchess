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
import jchess.ui.lang.Language;
import jchess.util.Player;

// TODO promotion needs to take into account, that there can only be promotion
// to already captured piece.

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

	public PawnPromotionWindow(Frame parent, Player.colors color) {
		super(parent);
		this.setTitle(Language.getString("PawnPromotionWindow.0")); //$NON-NLS-1$
		this.setMinimumSize(new Dimension(520, 130));
		this.setSize(new Dimension(520, 130));
		this.setMaximumSize(new Dimension(520, 130));
		this.setResizable(false);
		this.setLayout(new GridLayout(1, 4));

		setColor(color);

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
	public void setColor(Player.colors color) {
		this.knightButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Knight.SYMBOL)));
		this.bishopButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Bishop.SYMBOL)));
		this.rookButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Rook.SYMBOL)));
		this.queenButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Queen.SYMBOL)));

	}

	/**
	 * Method which is changing a pawn into queen, rook, bishop or knight
	 * 
	 * @param event
	 *          Capture information about performed action
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == queenButton) {
			result = Queen.SYMBOL;
		} else if (event.getSource() == rookButton) {
			result = Rook.SYMBOL;
		} else if (event.getSource() == bishopButton) {
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
