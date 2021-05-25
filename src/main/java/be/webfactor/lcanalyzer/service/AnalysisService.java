package be.webfactor.lcanalyzer.service;

import be.webfactor.lcanalyzer.domain.Analysis;
import be.webfactor.lcanalyzer.domain.Blunder;
import be.webfactor.lcanalyzer.domain.MoveRating;
import be.webfactor.lcanalyzer.domain.Play;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AnalysisService {

	private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);

	private static final String BASE_URL = "https://explorer.lichess.ovh/lichess?fen={fen}&play={play}&variant=standard&speeds[]=rapid&speeds[]=classical&ratings[]=1600&ratings[]=1800&ratings[]=2000&ratings[]=2200&ratings[]=2500";

	private static final int WAIT_MILLIS = 1000;

	@Autowired private RestTemplate restTemplate;
	@Autowired private CheckedFenService checkedFenService;
	@Autowired private BlunderService blunderService;

	@Value("${status.path}")
	private String statusPath;

	@Value("${initial.sans}")
	private String initialSans;

	@Value("${min.plays}")
	private int minPlays;

	@Value("${fail.pct}")
	private int failPct;

	public void go() {
		for (String initialSan : initialSans.split(",")) {
			LocalDateTime startTime = LocalDateTime.now();

			List<Play> plays = new ArrayList<>();

			plays.add(new Play(initialSan));

			AtomicLong playsChecked = new AtomicLong();
			AtomicLong blundersFound = new AtomicLong();

			while (!plays.isEmpty()) {
				Play play = plays.remove(0);

				List<MoveRating> selectedMoveRatings = tryAnalyze(play).getMovesWithAtLeastXPlays(minPlays);

				selectedMoveRatings.forEach(moveRating -> {
					Play newPlay = play.addMove(moveRating.getSan());
					String fen = newPlay.getFen();

					if (!checkedFenService.isAlreadyChecked(fen)) {
						if (moveRating.getMaxPct() >= failPct) {
							Blunder blunder = new Blunder(fen, initialSan, newPlay.toString(), moveRating.getTotal(), moveRating.getWhitePct(), moveRating.getDrawsPct(), moveRating.getBlackPct());
							blundersFound.incrementAndGet();

							blunderService.saveBlunder(blunder);
						} else {
							plays.add(newPlay);
						}

						checkedFenService.add(fen, initialSan);
					}
				});

				writeStatus(play, playsChecked.incrementAndGet(), blundersFound.get(), plays.size(), startTime);
				doWait(WAIT_MILLIS);
			}

		}
		System.exit(0);
	}

	private Analysis tryAnalyze(Play play) {
		try {
			return restTemplate.getForObject(BASE_URL, Analysis.class, play.getFen(), "");
		} catch (Exception e) {
			log.error("Error retrieving analysis", e);
			doWait(60000);
			return tryAnalyze(play);
		}
	}

	private void doWait(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void writeStatus(Play play, long playsChecked, long blundersFound, long queue, LocalDateTime startTime) {
		String status = "Time spent: " + getReadableTimeDiff(startTime) + "\nMove lists checked: " + playsChecked + "\nCurrently checking: " + play + "\nBlunders found: " + blundersFound + "\nTo be checked: " + queue;

		Path path = Paths.get(statusPath);
		try {
			Files.write(path, status.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getReadableTimeDiff(LocalDateTime from) {
		LocalDateTime now = LocalDateTime.now();

		Duration duration = Duration.between(from, now);

		String result = "";

		if (duration.isZero()) {
			return "0m";
		} else {
			long days = duration.toDays();
			if (days != 0) {
				result = days + "d ";
				duration = duration.minusDays(days);
			}
			long hours = duration.toHours();
			if (hours != 0) {
				result += hours + "h ";
				duration = duration.minusHours(hours);
			}
			long minutes = duration.toMinutes();
			if (minutes != 0) {
				result += minutes + "m ";
				duration = duration.minusMinutes(minutes);
			}
			long seconds = duration.getSeconds();
			if (seconds != 0) {
				result += seconds + "s ";
			}
		}

		return result;
	}
}
