package jchess.core.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.core.board.graph.DirectedGraphEdge;
import jchess.core.board.graph.GraphEdge;
import jchess.core.board.graph.HexagonChessFieldGraphInitializer;
import jchess.core.board.graph.HexagonChessboardFieldGraph;
import jchess.core.board.graph.StraightEdge;
import jchess.core.pieces.King;
import jchess.core.pieces.Pawn;
import jchess.core.pieces.Piece;
import jchess.core.pieces.Rook;
import jchess.core.util.Logging;
import jchess.core.util.Move;
import jchess.core.util.MoveHistory;
import jchess.core.util.MoveHistory.castling;
import jchess.core.util.Player;
import jchess.core.util.Player.colors;
import jchess.ui.GameTab;
import jchess.ui.MoveHistoryUI;
import jchess.ui.lang.Language;

public class Chessboard extends HexagonChessboardFieldGraph {

	private GameTab										m_GameUI							= null;
	private ChessboardField						m_ActiveField					= null;
	private Pawn											m_TwoSquareMovedPawn	= null;

	private MoveHistoryUI							m_Moves_history				= null;
	private Map<Player.colors, King>	m_KingsMap						= new HashMap<Player.colors, King>();

	public Chessboard(GameTab ui, MoveHistoryUI movesHistory) throws Exception {
		m_GameUI = ui;
		m_Moves_history = movesHistory;
	}

	public void initChessBoard(Map<colors, Player> player) throws Exception {
		HexagonChessFieldGraphInitializer.initialise(this, player);
	}

	/**
	 * Method selecting piece in chessboard
	 * 
	 * @param sq
	 *          square to select (when clicked))
	 */
	public void select(ChessboardField field) {
		this.m_ActiveField = field;
		m_GameUI.getChessboardUI().repaint();
	}

	/**
	 * Method set variables active_x_square & active_y_square to 0 values.
	 */
	public void unselect() {
		this.m_ActiveField = null;
		if (m_GameUI.getChessboardUI() != null) {
			m_GameUI.getChessboardUI().repaint();
		}
	}

	public boolean redo() throws Exception {
		boolean result = false;
		Move first = this.m_Moves_history.redo();

		ChessboardField from = null;
		ChessboardField to = null;

		if (first != null) {
			from = first.getFrom();
			to = first.getTo();

			this.move(from, to, false);
			if (first.getPromotedPiece() != null) {
				Pawn pawn = (Pawn) to.getPiece();
				pawn.setField(null, this);

				to.setPiece(first.getPromotedPiece());
				Piece promoted = to.getPiece();
				promoted.setField(to, this);
			}
			result = true;
		}
		return result;
	}

	/**
	 * Method move piece from square to square
	 * 
	 * @param begin
	 *          filed from which move piece
	 * @param end
	 *          field where we want to move piece *
	 * @param refresh
	 *          chessboard, default: true
	 * @throws Exception
	 * */
	private void move(ChessboardField begin, ChessboardField end, boolean clearForwardHistory) throws Exception {
		castling wasCastling = MoveHistory.castling.none;
		Piece promotedPiece = null;
		boolean wasEnPassant = false;
		if (end.getPiece() != null) {
			end.getPiece().setField(null, this);
		}

		ChessboardField tempBegin = begin.copy();
		ChessboardField tempEnd = end.copy();

		begin.getPiece().setField(end, this);// set square of piece to ending
		end.setPiece(begin.getPiece());// for ending square set piece from beginin
		// square
		begin.setPiece(null);// make null piece for begining square

		if (end.getPiece().getSymbol().equals(King.SYMBOL)) {
			if (((King) end.getPiece()).wasMotion == false) {
				((King) end.getPiece()).wasMotion = true;
			}

			// TODO castling
		} else if (end.getPiece().getSymbol() == Rook.SYMBOL) {
			((Rook) end.getPiece()).wasMotion = true;
		} else if (end.getPiece().getSymbol() == Pawn.SYMBOL) {
			// TODO handle promotion
			// TODO handle en passant
		}

		this.unselect();
		m_GameUI.getChessboardUI().repaint();

		if (clearForwardHistory) {
			this.m_Moves_history.clearMoveForwardStack();
			this.m_Moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
		} else {
			this.m_Moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
		}
	}

	public synchronized boolean undo() throws Exception {
		Move last = this.m_Moves_history.undo();

		if (last != null && last.getFrom() != null) {
			ChessboardField begin = last.getFrom();
			ChessboardField end = last.getTo();
			try {
				Piece moved = last.getMovedPiece();
				begin.setPiece(moved);
				moved.setField(begin, this);
				Piece taken = last.getTakenPiece();
				if (last.getCastlingMove() != castling.none) {
					Piece rook = null;
					// TODO undo castling
					((King) moved).wasMotion = false;
					((Rook) rook).wasMotion = false;
				} else if (moved.getSymbol() == Rook.SYMBOL) {
					((Rook) moved).wasMotion = false;// TODO might be incorrect, if Rook
																						// was moved before
				} else if (moved.getSymbol() == Pawn.SYMBOL && last.wasEnPassant()) {
					// TODO enpassant
				} else if (moved.getSymbol() == Pawn.SYMBOL && last.getPromotedPiece() != null) {
					Piece promoted = end.getPiece();
					promoted.setField(null, this);
					end.setPiece(null);
				}

				if (taken != null) {
					end.setPiece(taken);
					taken.setField(end, this);
				}

				this.unselect();
				m_GameUI.getChessboardUI().repaint();
			} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
				return false;
			} catch (java.lang.NullPointerException exc) {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public King getKingForColor(Player.colors color) {
		return m_KingsMap.get(color);
	}

	public void addKing(King king) throws Exception {
		if (m_KingsMap.containsKey(king.getPlayer().getColor()) == false) {
			m_KingsMap.put(king.getPlayer().getColor(), king);
		} else {
			throw new Exception("King with color " + king.getPlayer().getColor() + " is already existing on this borad.");
		}
	}

	/**
	 * 
	 * This method tries to perform a move. If it passes, the move is executed. IF not this method does not fail, but return false.
	 * 
	 * @param begin
	 * @param end
	 * 
	 * @return true, if the move could be performed
	 * 
	 * @throws Exception
	 */
	public boolean tryMove(ChessboardField begin, ChessboardField end) throws Exception {
		boolean result = true;

		select(begin);
		if (m_ActiveField.getPiece().allMoves().contains(end)) // it is allowed
																														// field
		{
			move(begin, end, true);
		} else {
			Logging.log(Language.getString("Game.29"));
			return false;
		}
		unselect();
		m_GameUI.nextMove();
		result = true;

		return result;
	}

	public ChessboardField getActiveField() {
		return m_ActiveField;
	}

	public Pawn getTwoSquareMovedPawn() {
		return m_TwoSquareMovedPawn;
	}

	public void setTwoSquareMovedPawn(Pawn twoSquareMovedPawn) {
		this.m_TwoSquareMovedPawn = twoSquareMovedPawn;
	}

	public void move(ChessboardField begin, ChessboardField end) throws Exception {
		move(begin, end, true);
	}

	/**
	 * Calculates all straight fields starting with field.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @return the list of all straight fields which are not blocked. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getStraightFields(ChessboardField field, Player.colors activePlayersColor) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof StraightEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();
				result.add(boardField);
				result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge));
			}
		}

		return result;
	}

	private List<ChessboardField> getNodesInSpecificDirection(ChessboardField field, DirectedGraphEdge edge) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		ChessboardField next = field.getNextField(edge.getDirection(), edge.getEdgeType());

		if (next != null) {
			result.add(next);
			result.addAll(getNodesInSpecificDirection(field, edge));
		}

		return result;
	}

	private List<ChessboardField> getNodesInSpecificDirection(ChessboardField field, DirectedGraphEdge edge, int maxDepth) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		if (maxDepth > 0) {
			ChessboardField next = field.getNextField(edge.getDirection(), edge.getEdgeType());

			if (next != null) {
				result.add(next);
				result.addAll(getNodesInSpecificDirection(field, edge, maxDepth - 1));
			}
		}

		return result;
	}

	/**
	 * Calculates straight fields, which are a maximum of allowed moves away.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @param maxAllowedMoves the maximum allowed number of moves.
	 * @return the list of all straight fields which are not blocked or out of range. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getStraightFields(ChessboardField field, int maxAllowedMoves, Player.colors activePlayersColor) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof StraightEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();
				result.add(boardField);
				result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, maxAllowedMoves - 1));
			}
		}// TODO handle blocked fields

		return result;
	}

	/**
	 * Calculates all diagonal fields starting with field.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @return the list of all diagonal fields which are not blocked. Never null.
	 */
	public List<ChessboardField> getDiagonalFields(ChessboardField field, Player.colors activePlayersColor) {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		return result;// TODO
	}

	/**
	 * Calculates diagonal fields, which are a maximum of allowed moves away.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @param maxAllowedMoves the maximum allowed number of moves.
	 * @return the list of all diagonal fields which are not blocked or out of range. Never null.
	 */
	public List<ChessboardField> getDiagonalFields(ChessboardField field, int maxAllowedMoves, Player.colors activePlayersColor) {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		return result;// TODO
	}

	/**
	 * Calculates straight fields, which are exactly distance fields away.
	 * 
	 * This method does not care about blocked fields.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @param distance the distance, the wanted fields are away.
	 * @return the list of all straight fields in exact distance. Never null.
	 */
	public List<ChessboardField> getStraightFieldsExact(ChessboardField field, int distance) {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		return result;// TODO
	}

	/**
	 * Calculates diagonal fields, which are exactly distance fields away.
	 * 
	 * This method does not care about blocked fields
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @param distance the distance, the wanted fields are away.
	 * @return the list of all diagonal fields in exact distance. Never null.
	 */
	public List<ChessboardField> getDiagonalFieldsExact(ChessboardField field, int distance) {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		return result;// TODO
	}

	/**
	 * Calculates fields with straight and diagonal offset at a same time.
	 * 
	 * This method implement "jumping", so it does not care about blocked fields on the way.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @param straightOffset the offset in any straight direction. For classic knight - 1.
	 * @param diagonalOffset the offset in one of two diagonal direction (forward). For classic knight - 1.
	 * @return the list of all such fields. Never null.
	 */
	public List<ChessboardField> getJumpStraightPlusDiagonalFields(ChessboardField field, Player.colors activePlayersColor, int straightOffset, int diagonalOffset) {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		return result;// TODO
	}

	/**
	 * Checks, if a piece can not move for start to target. 
	 * 
	 * Straight edges are never blocked. Diagonal edges are blocked, if both neighbor fields are occupied.
	 * 
	 * @param start
	 * @param target
	 * @return true if the corresponding edge is blocked
	 */
	private boolean isBlocked(ChessboardField start, ChessboardField target) {
		return false; // TODO
	}

	/**
	 * Checks if the given field belongs to the given board.
	 * 
	 * @param board
	 * @param field
	 * @return true if the given field belongs to the given board.
	 */
	public static boolean isValidField(Chessboard board, ChessboardField field) {
		return board.getNode(field.getIdentifier()) == field;
	}
}