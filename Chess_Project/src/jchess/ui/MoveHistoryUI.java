package jchess.ui;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import jchess.core.board.ChessboardField;
import jchess.core.pieces.Piece;
import jchess.core.util.Constants;
import jchess.core.util.Move;
import jchess.core.util.MoveHistory;
import jchess.core.util.MoveHistory.castling;
import jchess.ui.lang.Language;

public class MoveHistoryUI extends AbstractTableModel {

	private static final long							serialVersionUID	= 4971786700324730473L;

	private int														columnsNum				= 3;
	private JScrollPane										scrollPane				= null;
	private JTable												table							= null;
	private DefaultMoveHistoryTableModel	tableModel				= null;
	private String[]											names							= new String[] { Language.getString("white"), Language.getString("black") };	//$NON-NLS-1$ //$NON-NLS-2$
	private GameTab												gameTab						= null;
	private int														rowsNum						= 0;
	private MoveHistory										history						= null;

	public MoveHistoryUI(GameTab gameTab) {
		history = new MoveHistory();

		this.tableModel = new DefaultMoveHistoryTableModel();
		this.table = new JTable(this.tableModel);
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setMaximumSize(new Dimension(100, 100));
		this.table.setMinimumSize(new Dimension(100, 100));
		this.gameTab = gameTab;

		this.tableModel.addColumn(this.names[0]);
		this.tableModel.addColumn(this.names[1]);
		this.addTableModelListener(null);
		this.tableModel.addTableModelListener(null);
		this.scrollPane.setAutoscrolls(true);
	}

	@Override
	public String getValueAt(int x, int y) {
		return history.getMoveAt((y * 2) - 1 + (x - 1));
	}

	@Override
	public int getRowCount() {
		return this.rowsNum;
	}

	@Override
	public boolean isCellEditable(int a, int b) {
		return false;
	}

	protected void addRow() {
		this.tableModel.addRow(new String[2]);
	}

	@Override
	public int getColumnCount() {
		return this.columnsNum;
	}

	/**
	 * Method of adding new moves to the table
	 * 
	 * @param str
	 *          String which in is saved player move
	 */
	protected void addMove2Table(String str) {
		try {
			if (history.isEnterBlack() == false) {
				this.addRow();
				this.rowsNum = this.tableModel.getRowCount() - 1;
				this.tableModel.setValueAt(str, rowsNum, 0);
			} else {
				this.tableModel.setValueAt(str, rowsNum, 1);
				this.rowsNum = this.tableModel.getRowCount() - 1;
			}
			history.setEnterBlack(history.isEnterBlack() == false);
			this.table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));// scroll
																																													// to
																																													// down

		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
			if (this.rowsNum > 0) {
				this.rowsNum--;
				addMove2Table(str);
			}
		}
	}

	protected void addCastling(String move) {
		history.addCastling(move);
		if (history.isEnterBlack() == false) {
			this.tableModel.setValueAt(move, this.tableModel.getRowCount() - 1, 1);// replace
																																							// last
																																							// value
		} else {
			this.tableModel.setValueAt(move, this.tableModel.getRowCount() - 1, 0);// replace
																																							// last
																																							// value
		}
	}

	public JScrollPane getScrollPane() {
		return this.scrollPane;
	}

	/**
	 * Method of adding new move
	 * 
	 * @param move
	 *          String which in is capt player move
	 */
	public void addMove(String move) {
		if (MoveHistory.isMoveCorrect(move)) {
			history.addMove(move);
			this.addMove2Table(move);
		}
	}

	public synchronized Move undo() {
		try {
			Move last = history.popBackwardStack();
			if (last != null) {
				history.pushForwardStack(last);

				if (history.isEnterBlack()) {
					this.tableModel.setValueAt(Constants.EMPTY_STRING, this.tableModel.getRowCount() - 1, 0);
					this.tableModel.removeRow(this.tableModel.getRowCount() - 1);

					if (this.rowsNum > 0) {
						this.rowsNum--;
					}
				} else {
					if (this.tableModel.getRowCount() > 0) {
						this.tableModel.setValueAt("", this.tableModel.getRowCount() - 1, 1); //$NON-NLS-1$
					}
				}
				history.removeLastMove();
				history.setEnterBlack(history.isEnterBlack() == false);
			}
			return last;
		} catch (java.util.EmptyStackException exc) {
			history.setEnterBlack(false);
			return null;
		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
			return null;
		}
	}

	public void addMove(ChessboardField begin, ChessboardField end, boolean registerInHistory, castling castlingMove, boolean wasEnPassant, Piece promotedPiece)
			throws Exception {
		String locMove = history.addMove(begin, end, registerInHistory, castlingMove, wasEnPassant, promotedPiece, gameTab);

		if (castlingMove == castling.shortCastling) {
			this.addCastling("0-0"); //$NON-NLS-1$
		} else if (castlingMove == castling.longCastling) {
			this.addCastling("0-0-0"); //$NON-NLS-1$
		} else {
			history.addMove(locMove);
			this.addMove2Table(locMove);
		}
		this.scrollPane.scrollRectToVisible(new Rectangle(0, this.scrollPane.getHeight() - 2, 1, 1));
	}

	public MoveHistory getMoveHistory() {
		return history;
	}

	public String getMovesInString() {
		return history.getMovesInString();
	}

	public void setMoves(String moves) throws Exception {
		history.setMoves(moves, gameTab.getActivePlayer(), gameTab.getChessboard());
	}

	public void clearMoveForwardStack() {
		history.clearMoveForwardStack();
	}

	public Move getLastMoveFromHistory() {
		return history.getLastMoveFromHistory();
	}

	public Move redo() {
		return history.redo();
	}
}