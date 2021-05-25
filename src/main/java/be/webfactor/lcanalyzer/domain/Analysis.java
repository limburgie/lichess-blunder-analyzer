package be.webfactor.lcanalyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Analysis {

	List<MoveRating> moveRatings;

	public List<MoveRating> getMoves() {
		return moveRatings;
	}

	public void setMoves(List<MoveRating> moveRatings) {
		this.moveRatings = moveRatings;
	}

	public Optional<MoveRating> getMostPopularMove() {
		return moveRatings.stream().max(Comparator.comparing(MoveRating::getTotal));
	}

	public List<MoveRating> getMovesWithAtLeastXPlays(long x) {
		return moveRatings.stream().filter(moveRating -> moveRating.getTotal() >= x).collect(Collectors.toList());
	}
}
