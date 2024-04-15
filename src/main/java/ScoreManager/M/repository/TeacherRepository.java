package ScoreManager.M.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManager.M.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    
}