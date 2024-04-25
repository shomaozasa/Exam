package ScoreManager.M.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManager.M.model.Subject;
import ScoreManager.M.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // 科目を登録するメソッド
    public Subject registerSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // 科目を更新するメソッド
    public Subject updateSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // 科目を削除するメソッド
    public void deleteSubject(String cd) {
        subjectRepository.deleteById(cd);
    }

    // 全ての科目を取得するメソッド
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // 特定の科目を取得するメソッド
    public Optional<Subject> getSubject(String cd) {
        return subjectRepository.findById(cd);
    }
    
    public List<Subject> getSubjectsBySchoolCd(String schoolCd) {
        return subjectRepository.findBySchoolCd(schoolCd);
    }
}
