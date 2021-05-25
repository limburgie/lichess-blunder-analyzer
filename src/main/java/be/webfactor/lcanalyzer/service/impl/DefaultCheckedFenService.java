package be.webfactor.lcanalyzer.service.impl;

import be.webfactor.lcanalyzer.domain.CheckedFen;
import be.webfactor.lcanalyzer.repository.CheckedFenRepository;
import be.webfactor.lcanalyzer.service.CheckedFenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultCheckedFenService implements CheckedFenService {

	@Autowired private CheckedFenRepository checkedFenRepository;

	@Override
	@Transactional(readOnly = true)
	public boolean isAlreadyChecked(String fen) {
		return checkedFenRepository.existsById(fen);
	}

	@Override
	public void add(String fen, String initialPlay) {
		checkedFenRepository.save(new CheckedFen(fen, initialPlay));
	}
}
