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
    
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

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
    
    public boolean isStudentNoDuplicate(String studentNo) {
        // 学生番号が重複しているかどうかをデータベースでチェックするロジックを実装する
        // 例えば、学生番号をキーとしてデータベースから学生を検索し、存在すれば重複と判定する
        Optional<Student> existingStudent = studentRepository.findById(studentNo);
        return existingStudent.isPresent(); // 存在すれば true を返す
    }
    
    public List<Student> filterStudents(Integer entYear, String classNum, Boolean isAttend) {
        List<Student> students = studentRepository.findAll();

        // 入学年度で絞り込み
        if (entYear != null) {
            students = studentRepository.findByEntYear(entYear);
        }

        // クラス番号で絞り込み
        if (classNum != null && !classNum.isEmpty()) {
            List<Student> classNumStudents = studentRepository.findByClassNum(classNum);
            students.retainAll(classNumStudents);
        }

        // 在学状況で絞り込み
        if (isAttend != null) {
            List<Student> isAttendStudents = studentRepository.findByIsAttend(isAttend);
            students.retainAll(isAttendStudents);
        }

        return students;
    }



}
