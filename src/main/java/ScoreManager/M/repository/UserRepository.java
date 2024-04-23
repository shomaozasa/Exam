package ScoreManager.M.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManager.M.model.Teacher;

public interface UserRepository extends JpaRepository<Teacher, String> {

	Teacher findByIdAndPassword(String id, String password);
	Teacher findByIdEquals(String id);
}