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
	    
	    List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
	    model.addAttribute("classNums", classNums);
        
	    List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
        Map<String, String> subjectMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getCd(), subject.getName());
        }
        model.addAttribute("subjectMap", subjectMap);
        
        return "testForm";
    }
    
//    @PostMapping("/register")
//    public String registerTest(
//            @RequestParam(name = "entYear", required = false) Integer entYear,
//            @RequestParam(name = "classNum", required = false) String classNum,
//            @ModelAttribute Test test,
//            @ModelAttribute("tests") ArrayList<Test> tests,
//            Model model
//    ) {
//   
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String id = authentication.getName();
//        Teacher teacher = userRepository.findByIdEquals(id);
//        String schoolCd = teacher.getSchoolCd();
//
//        if (entYear != null && classNum != null) {
//            // 入学年度とクラス番号で学生リストをフィルタリングして表示
//            List<Test> filteredTests = testService.filterEntYearAndClassNum(entYear, classNum, schoolCd);
//            System.out.println("検索結果: " + filteredTests);
//            model.addAttribute("tests", filteredTests);
//
//            List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
//            model.addAttribute("classNums", classNums);
//
//            List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
//            Map<String, String> subjectMap = new HashMap<>();
//            for (Subject subject : subjects) {
//                subjectMap.put(subject.getCd(), subject.getName());
//            }
//            model.addAttribute("subjectMap", subjectMap);
//            return "testForm";
//        }
//
//        else {
//        	
//        	for (Test seiseki : tests) {
//                // データベースに保存する前に、必要な処理を実行
//                // 例: 学校コードの設定
//                seiseki.setSchoolCd(schoolCd);
//                seiseki.setStudentNo("101");
//                seiseki.setClassNum("101");
//                seiseki.setPoint(10);
//                System.out.print("成績データ:" + seiseki.getStudentNo() + seiseki.getSubjectCd() + seiseki.getSchoolCd()
//                + seiseki.getNo() + seiseki.getPoint() + seiseki.getClassNum());
//
//                // データベースに保存
//                testService.registerTest(seiseki);
//            }
//        	
//        	return "redirect:/tests/list";
//        }
//    }
    
    @PostMapping("/register/search")
    public String searchTests(
            @RequestParam(name = "entYear", required = false) Integer entYear,
            @RequestParam(name = "classNum", required = false) String classNum,
            Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Teacher teacher = userRepository.findByIdEquals(id);
        String schoolCd = teacher.getSchoolCd();

          	List<Student> filteredTests = studentService.filterEntYearAndClassNum(entYear, classNum, schoolCd);
            System.out.println("検索結果: " + filteredTests);
            model.addAttribute("students", filteredTests);

            List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
            model.addAttribute("classNums", classNums);

            List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
            Map<String, String> subjectMap = new HashMap<>();
            for (Subject subject : subjects) {
                subjectMap.put(subject.getCd(), subject.getName());
            }
            model.addAttribute("subjectMap", subjectMap);

            return "testForm";
        
    }
   
    @PostMapping("/register/save")
    public String saveTest(@RequestParam(name = "studentNo", required = false)  List<String> studentNos,
            @RequestParam(name = "classNum", required = false) List<String> classNums,
            @RequestParam(name = "subjectCd", required = false) String subjectCd,
            @RequestParam(name = "no", required = false) Integer no,
            @RequestParam(name = "point", required = false) List<Integer> points,
    		@ModelAttribute("testsave") Test tests,
    		Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Teacher teacher = userRepository.findByIdEquals(id);
        String schoolCd = teacher.getSchoolCd();
        
        if (studentNos == null || studentNos.isEmpty()) {
        	List<ClassNum> classNum = classNumService.getClassNumsBySchoolCd(schoolCd);
            model.addAttribute("classNums", classNum);

            List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
            Map<String, String> subjectMap = new HashMap<>();
            for (Subject subject : subjects) {
                subjectMap.put(subject.getCd(), subject.getName());
            }
            model.addAttribute("subjectMap", subjectMap);
            model.addAttribute("errorMessage", "表が見つかりません。");
            return "testForm"; // エラーページにリダイレクトするなど
        }
        
        for (int i = 0; i < studentNos.size(); i++) {
            Test test = new Test();
            test.setStudentNo(studentNos.get(i));
            test.setSubjectCd(subjectCd);
            test.setSchoolCd(schoolCd);
            test.setNo(no);
            test.setPoint(points.get(i));
            test.setClassNum(classNums.get(i));
            
            System.out.println("SQLクエリ: INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) VALUES (" + 
                    test.getStudentNo() + ", " +
                    test.getSubjectCd() + ", " +
                    test.getSchoolCd() + ", " +
                    test.getNo() + ", " +
                    test.getPoint() + ", " +
                    test.getClassNum() + ")");
            
            testService.registerTest(test);
        }
        
        // 成績データの保存が完了したらリダイレクト
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
        
        List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
        Map<String, String> subjectMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getCd(), subject.getName());
        }
        model.addAttribute("subjectMap", subjectMap);
        
        List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
	    model.addAttribute("classNums", classNums);
        return "testList";
    }
    
    @PostMapping("/list/search")
    public String searchedTests(
            @RequestParam(name = "entYear", required = false) Integer entYear,
            @RequestParam(name = "classNum", required = false) String classNum,
            @RequestParam(name = "subjectCd", required = false) String subjectCd,
            Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Teacher teacher = userRepository.findByIdEquals(id);
        String schoolCd = teacher.getSchoolCd();

        List<Test> filteredTests = testService.filterTests(entYear, classNum, subjectCd, schoolCd);
        System.out.println("検索結果: " + filteredTests); 
        model.addAttribute("tests", filteredTests);
        
        List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
        Map<String, String> subjectMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getCd(), subject.getName());
        }
        model.addAttribute("subjectMap", subjectMap);
        
        List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
	    model.addAttribute("classNums", classNums);

        return "testList"; // 成績一覧ページに遷移
    }
    
    @PostMapping("/list/search/student")
    public String searchedTestsByStudentNo(
            @RequestParam(name = "studentNo", required = false) String studentNo,
            Model model
    ) {
    	
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Teacher teacher = userRepository.findByIdEquals(id);
        String schoolCd = teacher.getSchoolCd();

        Student student = studentService.getStudentsByStudentNo(studentNo);
        if (student != null && student.getSchoolCd().equals(schoolCd)) {
        	String studentName = student.getName();
        	String studentNum = student.getNo();
        	model.addAttribute("studentName", studentName);
        	model.addAttribute("studentNum", studentNum);
        }
        
        List<Test> filteredTests = testService.filterTestsByStudentNo(studentNo, schoolCd);
        System.out.println("検索結果: " + filteredTests); 
        model.addAttribute("tests", filteredTests);
        
        List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
        Map<String, String> subjectMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getCd(), subject.getName());
        }
        model.addAttribute("subjectMap", subjectMap);
        
        List<ClassNum> classNums = classNumService.getClassNumsBySchoolCd(schoolCd);
	    model.addAttribute("classNums", classNums);

        return "testList"; // 成績一覧ページに遷移
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
