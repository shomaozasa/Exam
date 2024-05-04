package ScoreManager.M.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManager.M.model.Test;
import ScoreManager.M.repository.StudentRepository;
import ScoreManager.M.repository.TestRepository;
import jakarta.transaction.Transactional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    // 登録するメソッド
    public Test registerTest(Test test) {
        return testRepository.save(test);
    }

    // 更新するメソッド
    public Test updateTest(Test test) {
        return testRepository.save(test);
    }

    // 削除するメソッド
    @Transactional
    public void deleteByStudentNoAndSubjectCdAndSchoolCdAndNo(String studentNo, String subjectCd, String schoolCd, Integer no) {
        testRepository.deleteByStudentNoAndSubjectCdAndSchoolCdAndNo(studentNo, subjectCd, schoolCd, no);
    }

    // 全てを取得するメソッド
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // 特定の成績を取得するメソッド
    public Optional<Test> getTest(String studentNo, String subjectCd, String schoolCd, Integer no) {
        return testRepository.findById(studentNo, subjectCd, schoolCd, no);
    }
    
    public List<Test> getTestsBySchoolCd(String schoolCd) {
        return testRepository.findBySchoolCd(schoolCd);
    }
    
    public List<Test> filterTests(Integer entYear, String classNum, String subjectCd, String schoolCd) {
        // 全てのテストデータをデータベースから取得する
        List<Test> allTests = testRepository.findAll();

            // 学校コードで絞り込み
            allTests = allTests.stream()
                    .filter(student -> student.getSchoolCd().equals(schoolCd))
                    .collect(Collectors.toList());

            // 入学年度で絞り込み
            if (entYear != null) {
                allTests = allTests.stream()
                        .filter(student -> student.getEntYear().equals(entYear))
                        .collect(Collectors.toList());
            }

            // クラス番号で絞り込み
            if (classNum != null && !classNum.isEmpty()) {
                allTests = allTests.stream()
                        .filter(student -> student.getClassNum().equals(classNum))
                        .collect(Collectors.toList());
            }
            
            // 科目番号で絞り込み
            if (subjectCd != null && !subjectCd.isEmpty()) {
                allTests = allTests.stream()
                        .filter(student -> student.getSubjectCd().equals(subjectCd))
                        .collect(Collectors.toList());
            }
            

            return allTests;
        }
    
    public List<Test> filterTestsByStudentNo(String studentNo, String schoolCd) {
        // 全てのテストデータをデータベースから取得する
        List<Test> allTests = testRepository.findAll();

            // 学校コードで絞り込み
            allTests = allTests.stream()
                    .filter(student -> student.getSchoolCd().equals(schoolCd))
                    .collect(Collectors.toList());

            // 学生番号で絞り込み
            if (studentNo != null && !studentNo.isEmpty()) {
                allTests = allTests.stream()
                        .filter(student -> student.getStudentNo().equals(studentNo))
                        .collect(Collectors.toList());
            }

            return allTests;
        }
    
}
