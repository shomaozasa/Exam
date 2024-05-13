package ScoreManager.M.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManager.M.model.Teacher;
import ScoreManager.M.repository.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // 教師を登録するメソッド
    public Teacher registerTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // 教師を更新するメソッド
    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // 教師を削除するメソッド
    public void deleteTeacher(String id) {
        teacherRepository.deleteById(id);
    }

    // 全ての教師を取得するメソッド
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // 特定の教師を取得するメソッド
    public Optional<Teacher> getTeacher(String id) {
        return teacherRepository.findById(id);
    }

	public Teacher findByUsername(String usernameOrId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean isTeacherNoDuplicate(String id) {
		Optional<Teacher> existingTeacher = teacherRepository.findById(id);
	    return existingTeacher.isPresent(); 
	}
}
