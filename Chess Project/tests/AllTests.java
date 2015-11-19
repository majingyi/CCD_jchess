import jchess.ui.ThemeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import core.board.ChessboardTest;
import core.pieces.PawnTest;
import core.pieces.PieceTest;

@RunWith(Suite.class)
@SuiteClasses({ ChessboardTest.class, PawnTest.class, PieceTest.class, ThemeTest.class })
public class AllTests {

}
