package ScoreManager.M.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManager.M.model.Test;
import ScoreManager.M.repository.TestRepository;
import jakarta.transaction.Transactional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    // テストを登録するメソッド
    public Test registerTest(Test test) {
        return testRepository.save(test);
    }

    // テストを更新するメソッド
    public Test updateTest(Test test) {
        return testRepository.save(test);
    }

    // テストを削除するメソッド
    @Transactional
    public void deleteByStudentNoAndSubjectCdAndSchoolCdAndNo(String studentNo, String subjectCd, String schoolCd, Integer no) {
        testRepository.deleteByStudentNoAndSubjectCdAndSchoolCdAndNo(studentNo, subjectCd, schoolCd, no);
    }

    // 全てのテストを取得するメソッド
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // 特定のテストを取得するメソッド
    public Optional<Test> getTest(String studentNo, String subjectCd, String schoolCd, Integer no) {
        return testRepository.findById(studentNo, subjectCd, schoolCd, no);
    }
}
