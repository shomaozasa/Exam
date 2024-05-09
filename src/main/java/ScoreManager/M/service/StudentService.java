package ScoreManager.M.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    public List<Student> getStudentsBySchoolCd(String schoolCd) {
        return studentRepository.findBySchoolCd(schoolCd);
    }
    
    public List<Integer> getEntYearBySchoolCd(String schoolCd) {
        return studentRepository.findDistinctEntYearBySchoolCd(schoolCd);
    }
    
    public Student getStudentsByStudentNo(String studentNo) {
        return studentRepository.findByNo(studentNo);
    }
    
    // 学生番号の重複チェック
    public boolean isStudentNoDuplicate(String studentNo) {
        Optional<Student> existingStudent = studentRepository.findById(studentNo);
        return existingStudent.isPresent(); // 存在すれば true を返す
    }
    
 // 学生を条件で絞り込み
    public List<Student> filterStudents(Integer entYear, String classNum, Boolean isAttend, String schoolCd) {
        List<Student> students = studentRepository.findAll();

        // 学校コードで絞り込み
        students = students.stream()
                .filter(student -> student.getSchoolCd().equals(schoolCd))
                .collect(Collectors.toList());

        // 入学年度で絞り込み
        if (entYear != null) {
            students = students.stream()
                    .filter(student -> student.getEntYear().equals(entYear))
                    .collect(Collectors.toList());
        }

        // クラス番号で絞り込み
        if (classNum != null && !classNum.isEmpty()) {
            students = students.stream()
                    .filter(student -> student.getClassNum().equals(classNum))
                    .collect(Collectors.toList());
        }

        // 在学状況で絞り込み
        if (isAttend != null) {
            students = students.stream()
                    .filter(student -> student.getIsAttend().equals(isAttend))
                    .collect(Collectors.toList());
        }

        return students;
    }

    public List<Student> filterEntYearAndClassNum(Integer entYear, String classNum, String schoolCd) {
        List<Student> allTests = studentRepository.findAll();

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

            return allTests;
        }



}
