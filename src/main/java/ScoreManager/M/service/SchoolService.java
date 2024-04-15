package ScoreManager.M.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManager.M.model.School;
import ScoreManager.M.repository.SchoolRepository;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    // 学校を登録するメソッド
    public School registerSchool(School school) {
        return schoolRepository.save(school);
    }

    // 学校を更新するメソッド
    public School updateSchool(School school) {
        return schoolRepository.save(school);
    }

    // 学校を削除するメソッド
    public void deleteSchool(String cd) {
        schoolRepository.deleteById(cd);
    }

    // 全ての学校を取得するメソッド
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

}

