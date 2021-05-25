package be.webfactor.lcanalyzer.service;

public interface CheckedFenService {

	boolean isAlreadyChecked(String fen);

	void add(String fen, String initialPlay);
}
