package be.webfactor.lcanalyzer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MoveRating {

	private String san;
	private long white;
	private long draws;
	private long black;

	public long getTotal() {
		return white + draws + black;
	}

	public int getWhitePct() {
		return Math.round(100f * white / getTotal());
	}

	public int getDrawsPct() {
		return 100 - getWhitePct() - getBlackPct();
	}

	public int getBlackPct() {
		return Math.round(100f * black / getTotal());
	}

	public int getMaxPct() {
		return Math.max(getWhitePct(), getBlackPct());
	}
}
