package ScoreManager.M.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManager.M.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {

	List<Subject> findBySchoolCd(String schoolCd);
    
}