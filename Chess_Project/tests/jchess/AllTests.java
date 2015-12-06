package jchess;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jchess.core.board.ChessboardTest;
import jchess.core.board.HexagonChessFieldGraphInitializerTest;
import jchess.core.board.graph.GraphEdgeTest;
import jchess.core.pieces.BishopMoveBehaviorTest;
import jchess.core.pieces.KingMoveBehaviorTest;
import jchess.core.pieces.KnightMoveBehaviorTest;
import jchess.core.pieces.PawnMoveBehaviorTest;
import jchess.core.pieces.PawnTest;
import jchess.core.pieces.PieceTest;
import jchess.core.pieces.QueenMoveBehaviorTest;
import jchess.core.pieces.RookMoveBehaviorTest;
import jchess.core.util.ClockTest;
import jchess.core.util.GameTest;
import jchess.core.util.SettingsTest;
import jchess.ui.ThemeTest;

@RunWith(Suite.class)
@SuiteClasses({ ChessboardTest.class, GameTest.class, HexagonChessFieldGraphInitializerTest.class, GraphEdgeTest.class, SettingsTest.class, PawnTest.class,
		PieceTest.class, RookMoveBehaviorTest.class, BishopMoveBehaviorTest.class, QueenMoveBehaviorTest.class, KnightMoveBehaviorTest.class,
		KingMoveBehaviorTest.class, PawnMoveBehaviorTest.class, ThemeTest.class, ClockTest.class })
public class AllTests {

}
