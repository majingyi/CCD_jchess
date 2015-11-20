package jchess.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import jchess.core.pieces.Bishop;
import jchess.core.pieces.Knight;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Queen;
import jchess.core.pieces.Rook;
import jchess.core.util.Player;
import jchess.ui.lang.Language;

// TODO promotion needs to take into account, that there can only be promotion
// to already captured piece.

/**
 * If pawn reach the end of the chessboard it can be promoted to rook, bishop,
 * queen or knight.
 * 
 * This class provides dialog, to let the choose, to which pieces the pawn is
 * promoted.
 * 
 * It is taken into account, which figure the player has already captured.
 * 
 */
public class PawnPromotionWindow extends JDialog implements ActionListener {

	private static final long	serialVersionUID	= -9128995891728983191L;

	private JButton						knightButton			= null;
	private JButton						bishopButton			= null;
	private JButton						rookButton				= null;
	private JButton						queenButton				= null;
	private String						result						= Pawn.SYMBOL;

	public PawnPromotionWindow(Frame parent, Player.colors color) throws FileNotFoundException {
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
	 * Method setting the color for promoted pawn
	 * 
	 * @param color
	 *          The players color
	 * @throws FileNotFoundException
	 */
	public void setColor(Player.colors color) throws FileNotFoundException {
		this.knightButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Knight.SYMBOL)));
		this.bishopButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Bishop.SYMBOL)));
		this.rookButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Rook.SYMBOL)));
		this.queenButton = new JButton(new ImageIcon(Theme.getImageForPiece(color, Queen.SYMBOL)));

	}

	/**
	 * Method which is storing the players decision.
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

	/**
	 * Returns the players decision.
	 * 
	 * @return The symbol of the chosen check piece.
	 */
	public String getResult() {
		return result;
	}
}