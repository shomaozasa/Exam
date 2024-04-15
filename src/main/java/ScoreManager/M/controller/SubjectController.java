package ScoreManager.M.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ScoreManager.M.model.Subject;
import ScoreManager.M.service.SchoolService;
import ScoreManager.M.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private SchoolService schoolService;

    // 科目登録ページを表示
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("subject", new Subject());
        List<School> schools = schoolService.getAllSchools();
        // 学校名を表示するために、学校名と学校コードの対応マップを生成
        Map<String, String> schoolMap = new HashMap<>();
        for (School school : schools) {
            schoolMap.put(school.getCd(), school.getName());
        }
        model.addAttribute("schoolMap", schoolMap);
        return "subjectForm"; // 登録画面のテンプレート名を返す
    }

    // 科目登録処理
    @PostMapping("/register")
    public String registerSubject(@ModelAttribute Subject subject) {
        subjectService.registerSubject(subject);
        return "redirect:/subjects/list";
    }

    // 科目一覧ページを表示
    @GetMapping("/list")
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjectList"; // 一覧画面のテンプレート名を返す
    }
    
    @PostMapping("/list")
    public String deleteSubjects(@RequestParam("cd") String cd) {
        subjectService.deleteSubject(cd);
        return "redirect:/subjects/list"; // クラス番号一覧ページにリダイレクト
    }

    // 科目編集ページを表示
    @GetMapping("/edit/{cd}")
    public String editSubject(@PathVariable String cd, Model model) {
        Subject subject = subjectService.getSubject(cd).orElse(null);
        if (subject != null) {
            model.addAttribute("subject", subject);
            List<School> schools = schoolService.getAllSchools();
            // 学校名を表示するために、学校名と学校コードの対応マップを生成
            Map<String, String> schoolMap = new HashMap<>();
            for (School school : schools) {
                schoolMap.put(school.getCd(), school.getName());
            }
            model.addAttribute("schoolMap", schoolMap);
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
