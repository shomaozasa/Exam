package ScoreManager.M.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ScoreManager.M.model.ClassNum;
import ScoreManager.M.model.Student;
import ScoreManager.M.model.Teacher;
import ScoreManager.M.repository.UserRepository;
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
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String schoolCd = teacher.getSchoolCd();
	    model.addAttribute("schoolCd", schoolCd);
	    
	    List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
	    model.addAttribute("classNums", classNums);
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
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String schoolCd = teacher.getSchoolCd();
	    List<Student> students = studentService.getStudentsBySchoolCd(schoolCd);
        model.addAttribute("students", students);
        return "studentList";
    }
    
    @PostMapping("/list")
    public String handleListActions(
            @RequestParam(name = "entYear", required = false) Integer entYear,
            @RequestParam(name = "classNum", required = false) String classNum,
            @RequestParam(name = "isAttend", required = false) Boolean isAttend,
            @RequestParam(name = "schoolCd", required = true) String schoolCd,
            Model model) {
        
        // 検索操作
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String schoolcd = teacher.getSchoolCd();
	    model.addAttribute("schoolCd", schoolcd);
	    
        List<Student> students = studentService.filterStudents(entYear, classNum, isAttend, schoolCd);
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

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	String id = authentication.getName();
    	    Teacher teacher = userRepository.findByIdEquals(id);
    	    String schoolCd = teacher.getSchoolCd();
    	    model.addAttribute("schoolCd", schoolCd);
    	    
    	    List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
    	    model.addAttribute("classNums", classNums);
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
