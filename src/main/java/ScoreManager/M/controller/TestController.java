package ScoreManager.M.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import ScoreManager.M.model.School;
import ScoreManager.M.model.Student;
import ScoreManager.M.model.Subject;
import ScoreManager.M.model.Teacher;
import ScoreManager.M.model.Test;
import ScoreManager.M.repository.UserRepository;
import ScoreManager.M.service.ClassNumService;
import ScoreManager.M.service.SchoolService;
import ScoreManager.M.service.StudentService;
import ScoreManager.M.service.SubjectService;
import ScoreManager.M.service.TestService;

@Controller
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;
    
    @Autowired
    private ClassNumService classNumService;
    
    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private UserRepository userRepository; 

    @GetMapping("/register")
    public String showTestRegistrationForm(Model model) {
        model.addAttribute("test", new Test());
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String schoolCd = teacher.getSchoolCd();
	    model.addAttribute("schoolCd", schoolCd);
	    
	    List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
	    model.addAttribute("classNums", classNums);
        
	    List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
        Map<String, String> subjectMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getCd(), subject.getName());
        }
        model.addAttribute("subjectMap", subjectMap);
        
        List<Student> students = studentService.getStudentsBySchoolCd(schoolCd);
        Map<String, String> studentMap = new HashMap<>();
        for (Student student : students) {
            studentMap.put(student.getNo(), student.getName());
        }
        model.addAttribute("studentMap", studentMap);
        
        return "testForm";
    }

    @PostMapping("/register")
    public String registerTest(@ModelAttribute Test test) {
        testService.registerTest(test);
        return "redirect:/tests/list";
    }

    @GetMapping("/list")
    public String listTests(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String schoolCd = teacher.getSchoolCd();
        List<Test> tests = testService.getTestsBySchoolCd(schoolCd);
        model.addAttribute("tests", tests);
        return "testList";
    }
    
    @PostMapping("/list")
    public String deleteClassTests(@RequestParam("studentNo") String studentNo, @RequestParam("subjectCd") String subjectCd, 
    		@RequestParam("schoolCd") String schoolCd, @RequestParam("no") Integer no) {
        testService.deleteByStudentNoAndSubjectCdAndSchoolCdAndNo(studentNo, subjectCd, schoolCd, no);
        return "redirect:/tests/list"; // クラス番号一覧ページにリダイレクト
    }

    @GetMapping("/edit/{studentNo}/{subjectCd}/{schoolCd}/{no}")
    public String showEditTestForm(@PathVariable String studentNo, @PathVariable String subjectCd,
            @PathVariable String schoolCd, @PathVariable Integer no, Model model) {
        Optional<Test> optionalTest = testService.getTest(studentNo, subjectCd, schoolCd, no);
        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            model.addAttribute("test", test);
            List<ClassNum> classnums = classNumService.getAllClassNums();
            model.addAttribute("classNums", classnums);
            
            List<School> schools = schoolService.getAllSchools();
            Map<String, String> schoolMap = new HashMap<>();
            for (School school : schools) {
                schoolMap.put(school.getCd(), school.getName());
            }
            model.addAttribute("schoolMap", schoolMap);
            
            List<Subject> subjects = subjectService.getAllSubjects();
            Map<String, String> subjectMap = new HashMap<>();
            for (Subject subject : subjects) {
                subjectMap.put(subject.getCd(), subject.getName());
            }
            model.addAttribute("subjectMap", subjectMap);
            
            List<Student> students = studentService.getAllStudents();
            Map<String, String> studentMap = new HashMap<>();
            for (Student student : students) {
                studentMap.put(student.getNo(), student.getName());
            }
            model.addAttribute("studentMap", studentMap);
            return "editTest";
        } else {
            return "redirect:/tests/list";
        }
    }

    @PostMapping("/edit/{studentNo}/{subjectCd}/{schoolCd}/{no}")
    public String editTest(@PathVariable String studentNo, @PathVariable String subjectCd,
            @PathVariable String schoolCd, @PathVariable Integer no, @ModelAttribute Test test) {
        testService.updateTest(test);
        return "redirect:/tests/list";
    }

}
