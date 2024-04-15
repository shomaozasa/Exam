package ScoreManager.M.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManager.M.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {
    
}