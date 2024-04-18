package ScoreManager.M.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ScoreManager.M.model.ClassNum;
import ScoreManager.M.model.School;
import ScoreManager.M.model.Student;
import ScoreManager.M.service.ClassNumService;
import ScoreManager.M.service.SchoolService;
import ScoreManager.M.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ClassNumService classNumService;
    
    @Autowired
    private SchoolService schoolService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        List<ClassNum> classnums = classNumService.getAllClassNums();
        model.addAttribute("classNums", classnums);
        List<School> schools = schoolService.getAllSchools();
        Map<String, String> schoolMap = new HashMap<>();
        for (School school : schools) {
            schoolMap.put(school.getCd(), school.getName());
        }
        model.addAttribute("schoolMap", schoolMap);
        return "studentForm"; // 登録画面のテンプレート名を返す
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, Model model) {
        // 学生番号の重複をチェック
        if (studentService.isStudentNoDuplicate(student.getNo())) {
            // 重複している場合はエラーメッセージを設定して登録画面に戻る
        	List<ClassNum> classnums = classNumService.getAllClassNums();
            model.addAttribute("classNums", classnums);
            model.addAttribute("errorMessage", "※学生番号が重複しています※");
            return "studentForm"; // エラーメッセージを含んだ登録画面のテンプレート名を返す
        }
        
        // 重複していない場合は学生を登録し、一覧画面にリダイレクトする
        studentService.registerStudent(student);
        return "redirect:/students/list";
    }

    
    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "studentList";
    }
    
    @PostMapping("/list")
    public String handleListActions(
            @RequestParam(name = "entYear", required = false) Integer entYear,
            @RequestParam(name = "classNum", required = false) String classNum,
            @RequestParam(name = "isAttend", required = false) Boolean isAttend,
//            @RequestParam(name = "no", required = false) String studentNo,
            Model model) {

//        // 削除操作の場合
//        if (studentNo != null) {
//            studentService.deleteStudent(studentNo);
//            return "redirect:/students/list"; // クラス番号一覧ページにリダイレクト
//        }
        
        // 検索操作の場合
        List<Student> students = studentService.filterStudents(entYear, classNum, isAttend);
        System.out.println("検索結果: " + students); 
        model.addAttribute("searchedStudents", students);
        return "studentList"; // 検索結果のテンプレート名を返す
    }


    @GetMapping("/edit/{studentNo}")
    public String editStudent(@PathVariable String studentNo, Model model) {
        Optional<Student> optionalStudent = studentService.getStudent(studentNo);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            model.addAttribute("student", student);
            List<ClassNum> classnums = classNumService.getAllClassNums();
            model.addAttribute("classNums", classnums);
            List<School> schools = schoolService.getAllSchools();
            Map<String, String> schoolMap = new HashMap<>();
            for (School school : schools) {
                schoolMap.put(school.getCd(), school.getName());
            }
            model.addAttribute("schoolMap", schoolMap);
            return "editStudent";
        } else {
            return "redirect:/students/list";
        }
    }

    @PostMapping("/edit/{studentNo}")
    public String processEditStudent(@PathVariable String studentNo, @ModelAttribute Student studentData) {
        studentData.setNo(studentNo);
        studentService.registerStudent(studentData);
        return "redirect:/students/list";
    }

}
