package ScoreManager.M.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManager.M.model.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
	List<Student> findByEntYearAndClassNumAndIsAttend(Integer entYear, String classNum, boolean isAttend);
	List<Student> findByEntYearAndClassNum(Integer entYear, String classNum);
	List<Student> findByEntYearAndIsAttend(Integer entYear, boolean isAttend);
	List<Student> findByClassNumAndIsAttend(String classNum, boolean isAttend);
	List<Student> findByEntYear(Integer entYear);
	List<Student> findByClassNum(String classNum);
	List<Student> findByIsAttend(boolean isAttend);
	List<Student> findBySchoolCd(String schoolCd);
	Student findByNo(String no);
	Optional<Student> findByNoAndSchoolCd(String studentNo, String schoolCd);
}