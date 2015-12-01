package jchess.core.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.core.board.graph.DiagonalEdge;
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

	public static final int						top									= 0;
	public static final int						bottom							= 7;

	private GameTab										gameUI							= null;

	private ChessboardField						activeField					= null;
	private Pawn											twoSquareMovedPawn	= null;

	private MoveHistoryUI							moves_history				= null;
	private Map<Player.colors, King>	m_KingsMap					= new HashMap<Player.colors, King>();

	public Chessboard(GameTab ui, MoveHistoryUI movesHistory) throws Exception {
		gameUI = ui;
		moves_history = movesHistory;
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
		this.activeField = field;
		gameUI.getChessboardUI().repaint();
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
			// if (begin.pozX + 2 == end.pozX) {
			// move(getFields()[7][begin.pozY], getFields()[end.pozX - 1][begin.pozY],
			// false);
			// wasCastling = MoveHistory.castling.shortCastling;
			// } else if (begin.pozX - 2 == end.pozX) {
			// move(getFields()[0][begin.pozY], getFields()[end.pozX + 1][begin.pozY],
			// false);
			// wasCastling = MoveHistory.castling.longCastling;
			// }
		} else if (end.getPiece().getSymbol() == Rook.SYMBOL) {
			if (((Rook) end.getPiece()).wasMotion == false) {
				((Rook) end.getPiece()).wasMotion = true;
			}
		} else if (end.getPiece().getSymbol() == Pawn.SYMBOL) {
			if (getTwoSquareMovedPawn() != null && end == getTwoSquareMovedPawn().getField()) // en
			// passant
			{
				tempEnd.setPiece(end.getPiece()); // ugly
				end.setPiece(null);
				wasEnPassant = true;
			}

			// TODO two square
			// if (begin.pozY - end.pozY == 2 || end.pozY - begin.pozY == 2) // moved
			// // two
			// // square
			// {
			// setTwoSquareMovedPawn((Pawn) end.getPiece());
			// } else {
			// setTwoSquareMovedPawn(null); // erase last saved move (for En
			// // passant)
			// }
			ChessboardField field = end.getPiece().getField();
			// TODO pawn promotion
			// if (((Square) field).pozY == 0 || ((Square) field).pozY == 7) //
			// promote
			// // Pawn
			// {
			// if (clearForwardHistory) {
			// String newPiece =
			// JChessApp.jcv.showPawnPromotionBox(end.getPiece().getPlayer().getColor());
			// Pawn pawn = (Pawn) end.getPiece();
			// pawn.promote(newPiece);
			// promotedPiece = end.getPiece();
			// }
			// }
		} else if (end.getPiece().getSymbol() == Pawn.SYMBOL == false) {
			setTwoSquareMovedPawn(null); // erase last saved move (for En passant)
		}

		this.unselect();// unselect square
		gameUI.getChessboardUI().repaint();

		if (clearForwardHistory) {
			this.moves_history.clearMoveForwardStack();
			this.moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
		} else {
			this.moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
		}
	}

	public synchronized boolean undo() throws Exception // undo
																											// last
																											// move
	{
		Move last = this.moves_history.undo();

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
					// TODO castling
					// if (last.getCastlingMove() == castling.shortCastling) {
					// rook = end.getPiece();
					// this.getFields()[7][begin.pozY].setPiece(rook);
					// rook.setField(this.getFields()[7][begin.pozY], this);
					// this.getFields()[end.pozX - 1][end.pozY].setPiece(null);
					// } else {
					// rook = this.getFields()[end.pozX + 1][end.pozY].getPiece();
					// this.getFields()[0][begin.pozY].setPiece(rook);
					// rook.setField(this.getFields()[0][begin.pozY], this);
					// this.getFields()[end.pozX + 1][end.pozY].setPiece(null);
					// }
					((King) moved).wasMotion = false;
					((Rook) rook).wasMotion = false;
				} else if (moved.getSymbol() == Rook.SYMBOL) {
					((Rook) moved).wasMotion = false;
																						// was moved before
				} else if (moved.getSymbol() == Pawn.SYMBOL && last.wasEnPassant()) {
					// TODO enpassant
					// Pawn pawn = (Pawn) last.getTakenPiece();
					// this.getFields()[end.pozX][begin.pozY].setPiece(pawn);
					// pawn.setField(this.getFields()[end.pozX][begin.pozY], this);

				} else if (moved.getSymbol() == Pawn.SYMBOL && last.getPromotedPiece() != null) {
					Piece promoted = end.getPiece();
					promoted.setField(null, this);
					end.setPiece(null);
				}

				// check one more move back for en passant
				Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
				if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove()) {
					Piece canBeTakenEnPassant = oneMoveEarlier.getTo().getPiece();
					if (canBeTakenEnPassant.getSymbol() == Pawn.SYMBOL) {
						this.setTwoSquareMovedPawn((Pawn) canBeTakenEnPassant);
					}
				}

				if (taken != null && !last.wasEnPassant()) {
					end.setPiece(taken);
					taken.setField(end, this);
				} else {
					end.setPiece(null);
				}

				this.unselect();// unselect square
				gameUI.getChessboardUI().repaint();

			} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
				Logging.log(exc);
				return false;
			} catch (java.lang.NullPointerException exc) {
				Logging.log(exc);
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method is useful for out of bounds protection
	 * 
	 * @param x
	 *          x position on chessboard
	 * @param y
	 *          y position on chessboard
	 * @return true if parameters are out of bounds (array)
	 * @deprecated will not be need due to graph implementation
	 * */
	public static boolean isout(int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			return true;
		}
		return false;
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
		return activeField;
	}

	public Pawn getTwoSquareMovedPawn() {
		return twoSquareMovedPawn;
	}

	public void setTwoSquareMovedPawn(Pawn twoSquareMovedPawn) {
		this.twoSquareMovedPawn = twoSquareMovedPawn;
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
	public List<ChessboardField> getStraightFields(ChessboardField field, Player.colors activePlayersColor) {
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
				result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge));
				}
			}
		}

		return result;
	}

	private List<ChessboardField> getNodesInSpecificDirection(ChessboardField field, DirectedGraphEdge edge) throws Exception {
		List<ChessboardField> result = new ArrayList<ChessboardField>();
		ChessboardField next = field.getNextField(edge.getDirection(), edge.getEdgeType());

		if (next != null) {
			if (color == null || next.getPiece() == null || next.getPiece().getPlayer().getColor() != color) {
			result.add(next);
			}

			/*
			 * If there is a piece on this field, we can not move on.
			 */
			if (next.getPiece() == null) {
			result.addAll(getNodesInSpecificDirection(field, edge));
			}
		}

		return result;
	}

	private List<ChessboardField> getNodesInSpecificDirection(ChessboardField field, DirectedGraphEdge edge, int maxDepth) throws Exception {
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
				if (color == null || next.getPiece() == null || next.getPiece().getPlayer().getColor() != color) {
				result.add(next);
				}

				/*
				 * If there is a piece on this field, we can not move on.
				 */
				if (next.getPiece() == null) {
				result.addAll(getNodesInSpecificDirection(field, edge, maxDepth - 1));
				}
			}
		}

		return result;// TODO
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
	public List<ChessboardField> getStraightFields(ChessboardField field, int maxAllowedMoves, Player.colors activePlayersColor) {
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
				result.addAll(getNodesInSpecificDirection(boardField, (DirectedGraphEdge) edge, maxAllowedMoves - 1));
			}
		}// TODO handle blocked fields

		return result;// TODO
	}

	/**
	 * Calculates all diagonal fields starting with field.
	 * 
	 * This method is returning fields only, which can be reached by a check piece. 
	 * If a field is blocked, calculation stops.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @return the list of all diagonal fields which are not blocked. Never null.
	 * @throws Exception 
	 */
	public List<ChessboardField> getDiagonalFields(ChessboardField field, Player.colors activePlayersColor) {
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
	 * @throws Exception 
	 */
	public List<ChessboardField> getDiagonalFields(ChessboardField field, int maxAllowedMoves, Player.colors activePlayersColor) {
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
	 * This method implement "jumping", so it does not care about blocked fields on the way, only goal fields can be blocked.
	 * 
	 * @param field a valid chessboard field, which is the starting point for this calculation.
	 * @param straightOffset the offset in any straight direction. For classic knight - 1.
	 * @param diagonalOffset the offset in one of two diagonal direction (forward). For classic knight - 1.
	 * @return the list of all such fields. Never null.
	 */
	public List<ChessboardField> getJumpStraightPlusDiagonalFields(ChessboardField field, Player.colors activePlayersColor, int straightOffset,
			int diagonalOffset) {
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
	 * @throws Exception 
	 */
	private boolean isBlocked(ChessboardField start, ChessboardField target) throws Exception {
		boolean result = true;

		List<GraphEdge> edges = start.getEdges();

		for (GraphEdge edge : edges) {
			if (edge.getOtherNode(start) == target) {
				if (edge instanceof DiagonalEdge) {
					/*
					 * The two nodes, connected to both of the start and target node are
					 * the two node right and left from the edge.
					 */
					List<ChessboardField> neighborsA = start.getStraightNeighbors();
					List<ChessboardField> neighborsB = target.getStraightNeighbors();

					List<ChessboardField> inBothLists = new ArrayList<ChessboardField>();

					for (ChessboardField node : neighborsA) {
						if (neighborsB.contains(node)) {
							inBothLists.add(node);
						}
					}

					if (inBothLists.size() == 2) {
						for (ChessboardField node : inBothLists) {
							result &= node.getPiece() != null;
						}
					}
				} else {
					/*
					 * A Straight Edge is never blocked.
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

	/**
	 * Return the chessboard field identified with the given identifier.
	 * 
	 * @param identifier
	 * @return the chessboard field identified with the given identifier or null;
	 */
	public ChessboardField getField(String identifier) {
		return (ChessboardField) getNode(identifier);
	}
}