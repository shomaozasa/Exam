package ScoreManager.M.controller;

import java.util.List;

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

import ScoreManager.M.model.Subject;
import ScoreManager.M.model.Teacher;
import ScoreManager.M.repository.UserRepository;
import ScoreManager.M.service.SchoolService;
import ScoreManager.M.service.StudentService;
import ScoreManager.M.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private UserRepository userRepository;

    // 科目登録ページを表示
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("subject", new Subject());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String schoolCd = teacher.getSchoolCd();
	    model.addAttribute("schoolCd", schoolCd);
        return "subjectForm"; // 登録画面のテンプレート名を返す
    }

    // 科目登録処理
    @PostMapping("/register")
    public String registerSubject(@ModelAttribute Subject subject, Model model) {
    	 
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     	String id = authentication.getName();
 	    Teacher teacher = userRepository.findByIdEquals(id);
 	    String schoolCd = teacher.getSchoolCd();
 	    
        if (subjectService.isSubjectCdNoDuplicate(subject.getCd(), schoolCd)) {
            model.addAttribute("errorMessage", "※科目コードが重複しています※");
            model.addAttribute("schoolCd", schoolCd);
            return "subjectForm";
        }
        
        subjectService.registerSubject(subject);
        return "redirect:/subjects/register_success";
    }

    // 科目一覧ページを表示
    @GetMapping("/list")
    public String listSubjects(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String schoolCd = teacher.getSchoolCd();
	    List<Subject> subjects = subjectService.getSubjectsBySchoolCd(schoolCd);
        model.addAttribute("subjects", subjects);
        return "subjectList"; // 一覧画面のテンプレート名を返す
    }
    
    @PostMapping("/list")
    public String deleteSubjects(@RequestParam("cd") String cd) {
        subjectService.deleteSubject(cd);
        return "redirect:/subjects/list"; // クラス番号一覧ページにリダイレクト
    }
    
    @GetMapping("/register_success")
    public String Success() {
    	return "registerSubjectSuccess";
    }

    // 科目編集ページを表示
    @GetMapping("/edit/{cd}")
    public String editSubject(@PathVariable String cd, Model model) {
        Subject subject = subjectService.getSubject(cd).orElse(null);
        if (subject != null) {
            model.addAttribute("subject", subject);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	String id = authentication.getName();
    	    Teacher teacher = userRepository.findByIdEquals(id);
    	    String schoolCd = teacher.getSchoolCd();
    	    model.addAttribute("schoolCd", schoolCd);
            return "editSubject"; // 編集画面のテンプレート名を返す
        } else {
            return "redirect:/subjects/list";
        }
    }

    // 科目編集処理
    @PostMapping("/edit/{cd}")
    public String processEditSubject(@PathVariable String cd, @ModelAttribute Subject subjectData) {
        subjectService.updateSubject(subjectData);
        return "redirect:/subjects/list";
    }

}
