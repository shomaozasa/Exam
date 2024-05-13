package ScoreManager.M.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ScoreManager.M.model.ClassNum;
import ScoreManager.M.model.ClassNumId;

public interface ClassNumRepository extends JpaRepository<ClassNum, ClassNumId> {
	 @Query("DELETE FROM ClassNum c WHERE c.schoolCd = ?1 AND c.classNum = ?2")
	    void deleteById(String schoolCd, String classNum);
	 @Query("SELECT c FROM ClassNum c WHERE c.schoolCd = ?1 AND c.classNum = ?2")
	    Optional<ClassNum> findById(String schoolCd, String classNum);
	List<ClassNum> findBySchoolCd(String schoolCd);
	Optional<ClassNum> findByClassNumAndSchoolCd(String classNum, String schoolCd);
}