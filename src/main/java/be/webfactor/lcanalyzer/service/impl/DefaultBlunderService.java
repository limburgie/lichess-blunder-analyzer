package be.webfactor.lcanalyzer.service.impl;

import be.webfactor.lcanalyzer.domain.Blunder;
import be.webfactor.lcanalyzer.repository.BlunderRepository;
import be.webfactor.lcanalyzer.service.BlunderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DefaultBlunderService implements BlunderService {

	@Autowired private BlunderRepository blunderRepository;

	@Override
	public void saveBlunder(Blunder blunder) {
		blunderRepository.save(blunder);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Blunder> getBlunders() {
		return blunderRepository.findAll();
	}
}
