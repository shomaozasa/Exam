package ScoreManager.M.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManager.M.model.ClassNum;
import ScoreManager.M.model.ClassNumId;
import ScoreManager.M.repository.ClassNumRepository;

@Service
public class ClassNumService {

    @Autowired
    private ClassNumRepository classNumRepository;

    // クラス番号を登録するメソッド
    public ClassNum registerClassNum(ClassNum classNum) {
        return classNumRepository.save(classNum);
    }

    // クラス番号を更新するメソッド
    public ClassNum updateClassNum(ClassNum classNum) {
        return classNumRepository.save(classNum);
    }

    // クラス番号を削除するメソッド
    public void deleteClassNum(String schoolCd, String classNum) {
        classNumRepository.deleteById(new ClassNumId(schoolCd, classNum));
    }

    // 全てのクラス番号を取得するメソッド
    public List<ClassNum> getAllClassNums() {
        return classNumRepository.findAll();
    }

}


