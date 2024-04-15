package ScoreManager.M.controller;

import java.util.List;
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
import ScoreManager.M.model.Student;
import ScoreManager.M.service.ClassNumService;
import ScoreManager.M.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ClassNumService classNumService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        List<ClassNum> classnums = classNumService.getAllClassNums();
        model.addAttribute("classNums", classnums);
        return "studentForm"; // 登録画面のテンプレート名を返す
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student) {
        studentService.registerStudent(student);
        return "redirect:/students/list";
    }
    
    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "studentList";
    }
    
    @PostMapping("/list")
    public String deleteStudent(@RequestParam("no") String studentNo) {
        studentService.deleteStudent(studentNo);
        return "redirect:/students/list"; // クラス番号一覧ページにリダイレクト
    }

    @GetMapping("/edit/{studentNo}")
    public String editStudent(@PathVariable String studentNo, Model model) {
        Optional<Student> optionalStudent = studentService.getStudent(studentNo);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            model.addAttribute("student", student);
            List<ClassNum> classnums = classNumService.getAllClassNums();
            model.addAttribute("classNums", classnums);
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
