package be.webfactor.lcanalyzer.repository;

import be.webfactor.lcanalyzer.domain.Blunder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlunderRepository extends JpaRepository<Blunder, String> {
}
