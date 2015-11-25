package jchess;

import jchess.core.board.ChessboardTest;
import jchess.core.board.graph.GraphEdgeTest;
import jchess.core.pieces.PawnTest;
import jchess.core.pieces.PieceTest;
import jchess.core.util.ClockTest;
import jchess.core.util.SettingsTest;
import jchess.ui.ThemeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GraphEdgeTest.class, SettingsTest.class, ChessboardTest.class, PawnTest.class, PieceTest.class, ThemeTest.class, ClockTest.class })
public class AllTests {

}
