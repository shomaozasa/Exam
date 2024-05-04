package ScoreManager.M.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ScoreManager.M.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

	void deleteByStudentNoAndSubjectCdAndSchoolCdAndNo(String studentNo, String subjectCd, String schoolCd, Integer no);


    @Query("SELECT t FROM Test t WHERE t.studentNo = :studentNo AND t.subjectCd = :subjectCd AND t.schoolCd = :schoolCd AND t.no = :no")
    Optional<Test> findById(@Param("studentNo") String studentNo, @Param("subjectCd") String subjectCd, @Param("schoolCd") String schoolCd, @Param("no") Integer no);


	List<Test> findBySchoolCd(String schoolCd);


}
