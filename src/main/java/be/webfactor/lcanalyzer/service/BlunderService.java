package be.webfactor.lcanalyzer.service;

import be.webfactor.lcanalyzer.domain.Blunder;

import java.util.List;

public interface BlunderService {

	void saveBlunder(Blunder blunder);

	List<Blunder> getBlunders();
}
