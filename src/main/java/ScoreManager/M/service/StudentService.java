package ScoreManager.M.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManager.M.model.Student;
import ScoreManager.M.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // 学生を登録するメソッド
    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }
    // 更新
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    // 学生を削除するメソッド
    public void deleteStudent(String studentNo) {
        studentRepository.deleteById(studentNo);
    }

    // 全ての学生を取得するメソッド
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 特定の学生を取得するメソッド
    public Optional<Student> getStudent(String studentNo) {
        return studentRepository.findById(studentNo);
    }
}
