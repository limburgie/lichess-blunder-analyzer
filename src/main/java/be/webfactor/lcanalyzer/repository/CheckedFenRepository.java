package be.webfactor.lcanalyzer.repository;

import be.webfactor.lcanalyzer.domain.CheckedFen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckedFenRepository extends JpaRepository<CheckedFen, String> {
}
