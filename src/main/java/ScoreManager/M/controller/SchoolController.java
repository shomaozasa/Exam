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

import ScoreManager.M.model.School;
import ScoreManager.M.repository.SchoolRepository;
import ScoreManager.M.service.SchoolService;

@Controller
@RequestMapping("/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private SchoolRepository schoolRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("school", new School());
        return "schoolForm"; // 登録画面のテンプレート名を返す
    }

    @PostMapping("/register")
    public String registerSchool(@ModelAttribute("school") School school, Model model) {
    	
    	if (schoolService.isSchoolNoDuplicate(school.getCd()) != null) {
            model.addAttribute("errorMessage", "※学校コードが重複しています※");
            return "schoolForm";
        }
    	
        schoolService.registerSchool(school);
        return "redirect:/schools/register_success";
    }
    
    @GetMapping("register_success")
    public String Success() {
    	return "registerSchoolSuccess";
    }

    @GetMapping("/list")
    public String showSchoolList(Model model) {
        List<School> schools = schoolService.getAllSchools();
        model.addAttribute("schools", schools);
        return "schoolList"; // 一覧画面のテンプレート名を返す
    }
    
    @PostMapping("/list")
    public String deleteSchool(@RequestParam("cd") String cd) {
    	schoolService.deleteSchool(cd);
        return "redirect:/schools/list"; // 役職一覧ページにリダイレクト
    }
    
    @GetMapping("/edit/{cd}")
    public String editSchool(@PathVariable String cd, Model model) {
        // 学校情報の取得
        Optional<School> optionalSchool = schoolRepository.findById(cd);
        if (optionalSchool.isPresent()) {
            School school = optionalSchool.get();
            // モデルへの追加
            model.addAttribute("school", school);
            return "editSchool";
        } else {
            // 学校が見つからなかった場合は一覧画面にリダイレクトする
            return "redirect:/schools/list";
        }
    }

    @PostMapping("/edit/{cd}")
    public String processEditSchool(
        @PathVariable String cd,
        @ModelAttribute School school) {
        // 学校情報の更新
        schoolRepository.save(school);
        return "redirect:/schools/list";
    }

}
