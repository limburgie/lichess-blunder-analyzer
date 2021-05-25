package be.webfactor.lcanalyzer.domain;

import com.github.bhlangonijr.chesslib.move.MoveList;

public class Play {

	private static final String STANDARD_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -";

	private final MoveList moveList;

	public Play() {
		this("");
	}

	public Play(String san) {
		moveList = new MoveList();
		moveList.loadFromSan(san);
	}

	public Play addMove(String san) {
		String newSan = moveList.isEmpty() ? san : moveList.toSan() + " " + san;

		return new Play(newSan);
	}

	public String getFen() {
		if (moveList.isEmpty()) {
			return STANDARD_FEN;
		}

		return moveList.getFen(moveList.size(), false);
	}

	public String toString() {
		return moveList.toSanWithMoveNumbers();
	}
}
