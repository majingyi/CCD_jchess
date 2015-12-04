package jchess.core.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.core.board.graph.DiagonalEdge;
import jchess.core.board.graph.DirectedGraphEdge;
import jchess.core.board.graph.DirectedGraphEdge.EdgeDirection;
import jchess.core.board.graph.GraphEdge;
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
import jchess.core.util.Player.PlayerColor;
import jchess.ui.GameTab;
import jchess.ui.MoveHistoryUI;
import jchess.ui.lang.Language;

/**
 * 
 * Class representing a chess board for three player chess. This class manages the whole board data structure. 
 * 
 */
public class Chessboard extends HexagonChessboardFieldGraph {

	private GameTab												m_GameUI				= null;
	private ChessboardField								m_ActiveField		= null;

	private MoveHistoryUI									m_Moves_history	= null;
	private Map<Player.PlayerColor, King>	m_KingsMap			= new HashMap<Player.PlayerColor, King>();

	public Chessboard(GameTab ui, MoveHistoryUI movesHistory) throws Exception {
		m_GameUI = ui;
		m_Moves_history = movesHistory;
	}

	/**
	 * Initializes the chessboard. All fields are created and linked together. 
	 * All necessary check pieces are placed on the chessboard.
	 * 
	 * @param player
	 * @throws Exception
	 */
	public void initChessBoard(Map<PlayerColor, Player> player) throws Exception {
		HexagonChessFieldGraphInitializer.initialise(this, player);
	}

	/**
	 * Method selecting piece in chessboard
	 * 
	 * @param field
	 *          chess board field to select (when clicked))
	 */
	public void select(ChessboardField field) {
		this.m_ActiveField = field;
		m_GameUI.getChessboardUI().repaint();
	}

	/**
	 * Deselects the currently selected filed, if one is selected. 
	 */
	public void unselect() {
		this.m_ActiveField = null;
		if (m_GameUI.getChessboardUI() != null) {
			m_GameUI.getChessboardUI().repaint();
		}
	}

	/**
	 * Redo the last undone move.
	 * 
	 * @return true if redo worked, false otherwise.
	 * @throws Exception
	 */
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
	 * Performs a move from begin to end.
	 * 
	 *  If clearForwardHistory is true, the redo history is cleared. 
	 *  Redo impossible afterwards until a undo is performed.
	 * 
	 * @param begin
	 * @param end
	 * @param clearForwardHistory
	 * @throws Exception
	 */
	private void move(ChessboardField begin, ChessboardField end, boolean clearForwardHistory) throws Exception {
		castling wasCastling = MoveHistory.castling.none;
		Piece promotedPiece = null;
		boolean wasEnPassant = false;
		if (end.getPiece() != null) {
			end.getPiece().setField(null, this);
		}

		ChessboardField tempBegin = begin.copy();
		ChessboardField tempEnd = end.copy();

		begin.getPiece().setField(end, this);
		begin.setPiece(null);
		end.setPiece(begin.getPiece());

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

	/**
	 * The last move done by a player is undone. 
	 * 
	 * @return true, if undo worked, false otherwise.
	 * @throws Exception
	 */
	public synchronized boolean undo() throws Exception {
		boolean result = false;
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
				Logging.log(exc);
			} catch (java.lang.NullPointerException exc) {
				Logging.log(exc);
			}

			result = true;
		}

		return result;
	}

	/**
	 * 
	 * @param color
	 * @return the king of the given color.
	 */
	public King getKingForColor(Player.PlayerColor color) {
		return m_KingsMap.get(color);
	}

	/**
	 * Adds a new King to the kings map. 
	 * It is checked, if a king of the kings color is already existing.
	 * 
	 * @param king
	 * @throws Exception if a king with the same color already exists
	 */
	public void addKing(King king) throws Exception {
		if (m_KingsMap.containsKey(king.getPlayer().getColor()) == false) {
			m_KingsMap.put(king.getPlayer().getColor(), king);
		} else {
			throw new Exception("King with color " + king.getPlayer().getColor().toString().toLowerCase() + " is already existing on this borad.");
		}
	}

	/**
	 * 
	 * This method tries to perform a move. If it passes, the move is executed. 
	 * If not this method does not fail, but return false.
	 * 
	 * @param begin
	 * @param end
	 * 
	 * @return true, if the move could be performed
	 * 
	 * @throws Exception
	 */
	public boolean tryMove(ChessboardField begin, ChessboardField end) throws Exception {
		boolean result = false;

		select(begin);
		if (m_ActiveField.getPiece().allMoves().contains(end)) {
			move(begin, end, true);
			unselect();
			m_GameUI.nextMove();
			result = true;
		} else {
			Logging.log(Language.getString("Game.29"));
		}

		return result;
	}

	/**
	 * 
	 * @return the active filed of this chess board
	 */
	public ChessboardField getActiveField() {
		return m_ActiveField;
	}

	/**
	 * Performs a move from begin to end.
	 * 
	 * @param begin
	 * @param end
	 * @throws Exception
	 */
	public void move(ChessboardField begin, ChessboardField end) throws Exception {
		move(begin, end, true);
	}

	/**
	 * Calculates all straight fields starting with field.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chess board field, which is the starting point for this calculation.
	 * @return the list of all straight fields which are not blocked. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getStraightFields(ChessboardField field, PlayerColor activePlayersColor) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof StraightEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();

				/*
				 * The next field can be moved to if:
				 * 
				 * - We did not specify color, than we want simply have all fields.
				 * 
				 * - There is no piece on this field.
				 * 
				 * - The piece on this field is an opponents piece
				 */
				if (activePlayersColor == null || boardField.getPiece() == null || boardField.getPiece().getPlayer().getColor() != activePlayersColor) {
					result.add(boardField);
				}

				/*
				 * If there is a piece on this field, we can not move on.
				 */
				if (boardField.getPiece() == null) {
					result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, activePlayersColor));
				}
			}
		}

		return result;
	}

	private List<ChessboardField> getNodesInSpecificDirection(ChessboardField field, DirectedGraphEdge edge, PlayerColor color) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		ChessboardField next = field.getNextField(edge.getDirection(), edge.getEdgeType());

		if (next != null) {
			/*
			 * Diagonal edge can be blocked, if both fields left and right from that
			 * edge is occupied by an piece, regardless which color.
			 * 
			 * Straight edges are never blocked.
			 */
			boolean edgeBlocked = isBlocked(field, next);

			/*
			 * The next field can be moved to if:
			 * 
			 * - We did not specify color, than we want simply have all fields.
			 * 
			 * - There is no piece on this field.
			 * 
			 * - The piece on this field is an opponents piece
			 */
			if ((edgeBlocked == false) && (color == null || next.getPiece() == null || next.getPiece().getPlayer().getColor() != color)) {
				result.add(next);
			}

			/*
			 * If there is a piece on this field, we can not move on.
			 */
			if ((edgeBlocked == false) && (next.getPiece() == null)) {
				result.addAll(getNodesInSpecificDirection(next, edge, color));
			}
		}

		return result;
	}

	private List<ChessboardField> getNodesInSpecificDirection(ChessboardField field, DirectedGraphEdge edge, int maxDepth, PlayerColor color) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		if (maxDepth > 0) {
			ChessboardField next = field.getNextField(edge.getDirection(), edge.getEdgeType());

			if (next != null) {
				/*
				 * Diagonal edge can be blocked, if both fields left and right from that
				 * edge is occupied by an piece, regardless which color.
				 * 
				 * Straight edges are never blocked.
				 */
				boolean edgeBlocked = isBlocked(field, next);

				/*
				 * The next field can be moved to if:
				 * 
				 * - We did not specify color, than we want simply have all fields.
				 * 
				 * - There is no piece on this field.
				 * 
				 * - The piece on this field is an opponents piece
				 */
				if ((edgeBlocked == false) && (color == null || next.getPiece() == null || next.getPiece().getPlayer().getColor() != color)) {
					result.add(next);
				}

				/*
				 * If there is a piece on this field, we can not move on.
				 */
				if ((edgeBlocked == false) && (next.getPiece() == null)) {
					result.addAll(getNodesInSpecificDirection(next, edge, maxDepth - 1, color));
				}
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
	 * @param field a valid chess board field, which is the starting point for this calculation.
	 * @param maxAllowedMoves the maximum allowed number of moves.
	 * @return the list of all straight fields which are not blocked or out of range. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getStraightFields(ChessboardField field, int maxAllowedMoves, Player.PlayerColor activePlayersColor) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof StraightEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();
				/*
				 * The next field can be moved to if:
				 * 
				 * - We did not specify color, than we want simply have all fields.
				 * 
				 * - There is no piece on this field.
				 * 
				 * - The piece on this field is an opponents piece
				 */
				if (activePlayersColor == null || boardField.getPiece() == null || boardField.getPiece().getPlayer().getColor() != activePlayersColor) {
					result.add(boardField);
				}

				/*
				 * If there is a piece on this field, we can not move on.
				 */
				if (boardField.getPiece() == null) {
					result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, maxAllowedMoves - 1, activePlayersColor));
				}
			}
		}

		return result;
	}

	/**
	 * Calculates all diagonal fields starting with field.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chess board field, which is the starting point for this calculation.
	 * @return the list of all diagonal fields which are not blocked. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getDiagonalFields(ChessboardField field, Player.PlayerColor activePlayersColor) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof DiagonalEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();

				/*
				 * The next field can be moved to if:
				 * 
				 * - We did not specify color, than we want simply have all fields.
				 * 
				 * - There is no piece on this field.
				 * 
				 * - The piece on this field is an opponents piece
				 * 
				 * AND the edge is not blocked.
				 */
				if ((isBlocked(field, boardField) == false)
						&& (activePlayersColor == null || boardField.getPiece() == null || boardField.getPiece().getPlayer().getColor() != activePlayersColor)) {
					result.add(boardField);
				}

				/*
				 * If there is a piece on this field, we can not move on.
				 */
				if ((isBlocked(field, boardField) == false) && (boardField.getPiece() == null)) {
					result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, activePlayersColor));
				}
			}
		}

		return result;
	}

	/**
	 * Calculates diagonal fields, which are a maximum of allowed moves away.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chess board field, which is the starting point for this calculation.
	 * @param maxAllowedMoves the maximum allowed number of moves.
	 * @return the list of all diagonal fields which are not blocked or out of range. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getDiagonalFields(ChessboardField field, int maxAllowedMoves, Player.PlayerColor activePlayersColor) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof DiagonalEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();

				/*
				 * The next field can be moved to if:
				 * 
				 * - We did not specify color, than we want simply have all fields.
				 * 
				 * - There is no piece on this field.
				 * 
				 * - The piece on this field is an opponents piece
				 * 
				 * AND the edge is not blocked.
				 */
				if ((isBlocked(field, boardField) == false)
						&& (activePlayersColor == null || boardField.getPiece() == null || boardField.getPiece().getPlayer().getColor() != activePlayersColor)) {
					result.add(boardField);
				}

				/*
				 * If there is a piece on this field, we can not move on.
				 */
				if ((isBlocked(field, boardField) == false) && (boardField.getPiece() == null)) {
					result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, maxAllowedMoves - 1, activePlayersColor));
				}
			}
		}

		return result;
	}

	/**
	 * Calculates straight fields, which are exactly distance fields away.
	 * 
	 * This method does not care about blocked fields.
	 * 
	 * @param field a valid chess board field, which is the starting point for this calculation.
	 * @param distance the distance, the wanted fields are away.
	 * @return the list of all straight fields in exact distance. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getStraightFieldsExact(ChessboardField field, int distance) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof StraightEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();
				result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, distance - 1));
			}
		}

		return result;
	}

	private List<ChessboardField> getNodesInSpecificDirection(ChessboardField field, DirectedGraphEdge edge, int distance) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		if (distance > 0) {
			ChessboardField next = field.getNextField(edge.getDirection(), edge.getEdgeType());

			if (next != null) {
				result.addAll(getNodesInSpecificDirection(next, edge, distance - 1));
			}
		} else {
			result.add(field);
		}

		return result;
	}

	/**
	 * Calculates diagonal fields, which are exactly distance fields away.
	 * 
	 * This method does not care about blocked fields
	 * 
	 * @param field a valid chess board field, which is the starting point for this calculation.
	 * @param distance the distance, the wanted fields are away.
	 * @return the list of all diagonal fields in exact distance. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getDiagonalFieldsExact(ChessboardField field, int distance) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof DiagonalEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();
				result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, distance - 1));
			}
		}

		return result;
	}

	/**
	 * Calculates fields with straight and diagonal offset at a same time.
	 * 
	 * This method implement "jumping", so it does not care about blocked fields on the way.
	 * 
	 * @param field a valid chess board field, which is the starting point for this calculation.
	 * @param straightOffset the offset in any straight direction. For classic knight - 1.
	 * @param diagonalOffset the offset in one of two diagonal direction (forward). For classic knight - 1.
	 * @return the list of all such fields. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getJumpStraightPlusDiagonalFields(ChessboardField field, PlayerColor activePlayersColor, int straightOffset, int diagonalOffset)
			throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();

		List<GraphEdge> edges = field.getEdges();
		for (GraphEdge edge : edges) {
			if (edge instanceof StraightEdge) {
				ChessboardField boardField = (ChessboardField) ((DirectedGraphEdge) edge).getEndNode();
				List<ChessboardField> first = getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, straightOffset - 1);
				if (first.size() == 1) {
					ChessboardField firstStep = first.get(0);

					switch (((StraightEdge) edge).getDirection()) {
						case LEFT:
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.LEFT_DOWN));
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.LEFT_UP));
							break;
						case LEFT_DOWN:
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.DOWN));
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.LEFT_DOWN));
							break;
						case LEFT_UP:
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.UP));
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.LEFT_UP));
							break;
						case RIGHT:
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.RIGHT_DOWN));
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.RIGHT_UP));
							break;
						case RIGHT_DOWN:
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.DOWN));
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.RIGHT_DOWN));
							break;
						case RIGHT_UP:
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.UP));
							result.addAll(performSecondStep(activePlayersColor, diagonalOffset, firstStep, EdgeDirection.RIGHT_UP));
							break;
						default:
							break;
					}
				} else if (first.size() > 1) {
					throw new Exception("We should get a maximum of one field here.");
				}
			}
		}

		return result;
	}

	private List<ChessboardField> performSecondStep(PlayerColor color, int diagonalOffset, ChessboardField firstStep, EdgeDirection direction) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		List<ChessboardField> second = getNodesInSpecificDirection(firstStep, new DiagonalEdge(null, null, direction), diagonalOffset);
		if (second.size() == 1) {
			ChessboardField secondStep = second.get(0);
			if ((secondStep.getPiece() == null) || (secondStep.getPiece().getPlayer().getColor() != color)) {
				result.add(secondStep);
			}
		}

		return result;
	}

	/**
	 * Checks, if a piece can not move for start to target. 
	 * 
	 * Straight edges are never blocked. Diagonal edges are blocked, if both neighbor fields are occupied.
	 * 
	 * @param start
	 * @param target
	 * @return true if the corresponding edge is blocked
	 * @throws Exception 
	 */
	private boolean isBlocked(ChessboardField start, ChessboardField target) throws Exception {
		boolean result = false;

		List<GraphEdge> edges = start.getEdges();

		for (GraphEdge edge : edges) {
			if ((edge.getOtherNode(start) == target) && (edge instanceof DiagonalEdge)) {
				/*
				 * The two nodes, connected to both of the start and target node are the
				 * two nodes right and left from the edge.
				 */
				List<ChessboardField> neighborsA = start.getStraightNeighbors();
				List<ChessboardField> neighborsB = target.getStraightNeighbors();

				List<ChessboardField> inBothLists = new ArrayList<ChessboardField>();

				for (ChessboardField node : neighborsA) {
					if (neighborsB.contains(node)) {
						inBothLists.add(node);
					}
				}

				/*
				 * There are always two fields surrounding a diagonal edge.
				 */
				if (inBothLists.size() == 2) {
					result = (inBothLists.get(0).getPiece() != null) && (inBothLists.get(1).getPiece() != null);
				}
				break;
			}
		}

		return result;
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

	/**
	 * Return the chess board field identified with the given identifier.
	 * 
	 * @param identifier
	 * @return the chess board field identified with the given identifier or null;
	 */
	public ChessboardField getField(String identifier) {
		return (ChessboardField) getNode(identifier);
	}

	/**
	 * Return the direction from the player's point of view.
	 * 
	 * @param color player's color
	 * @direction direction from player's POV
	 * @return absolute direction of the chesboard;
	 */
	public EdgeDirection getDirectionFromPlayersPOV(Player.PlayerColor color, EdgeDirection direction) {
		switch (color) {
			case WHITE:
				switch (direction) {
					case LEFT:
						return EdgeDirection.RIGHT;
					case RIGHT:
						return EdgeDirection.LEFT;
					case UP:
						return EdgeDirection.DOWN;
					case DOWN:
						return EdgeDirection.UP;
					case LEFT_UP:
						return EdgeDirection.RIGHT_DOWN;
					case LEFT_DOWN:
						return EdgeDirection.RIGHT_UP;
					case RIGHT_UP:
						return EdgeDirection.LEFT_DOWN;
					case RIGHT_DOWN:
						return EdgeDirection.LEFT_UP;
					case UNDEFINED:
						return EdgeDirection.UNDEFINED;
				}
				break;
			case BLACK:
				switch (direction) {
					case LEFT:
						return EdgeDirection.LEFT_UP;
					case RIGHT:
						return EdgeDirection.RIGHT_DOWN;
					case UP:
						return EdgeDirection.RIGHT_UP;
					case DOWN:
						return EdgeDirection.LEFT_DOWN;
					case LEFT_UP:
						return EdgeDirection.RIGHT_UP;
					case LEFT_DOWN:
						return EdgeDirection.LEFT;
					case RIGHT_UP:
						return EdgeDirection.RIGHT;
					case RIGHT_DOWN:
						return EdgeDirection.LEFT_DOWN;
					case UNDEFINED:
						return EdgeDirection.UNDEFINED;
				}
				break;
			case RED:
				switch (direction) {
					case LEFT:
						return EdgeDirection.LEFT_DOWN;
					case RIGHT:
						return EdgeDirection.RIGHT_UP;
					case UP:
						return EdgeDirection.LEFT_UP;
					case DOWN:
						return EdgeDirection.RIGHT_DOWN;
					case LEFT_UP:
						return EdgeDirection.LEFT;
					case LEFT_DOWN:
						return EdgeDirection.RIGHT_DOWN;
					case RIGHT_UP:
						return EdgeDirection.LEFT_UP;
					case RIGHT_DOWN:
						return EdgeDirection.RIGHT;
					case UNDEFINED:
						return EdgeDirection.UNDEFINED;
				}
				break;
		}
		return EdgeDirection.UNDEFINED;
	}
}