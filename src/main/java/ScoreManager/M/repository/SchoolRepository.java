package ScoreManager.M.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManager.M.model.School;

public interface SchoolRepository extends JpaRepository<School, String> {

	School findByCdEquals(String cd);

	School findByCd(String cd);
    
}