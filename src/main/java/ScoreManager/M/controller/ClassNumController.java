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
import ScoreManager.M.repository.ClassNumRepository;
import ScoreManager.M.repository.UserRepository;
import ScoreManager.M.service.ClassNumService;
import ScoreManager.M.service.SchoolService;

@Controller
@RequestMapping("/classnums")
public class ClassNumController {

    @Autowired
    private ClassNumService classNumService;
    
    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private ClassNumRepository classNumRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("classNum", new ClassNum());
        List<School> schools = schoolService.getAllSchools();
        // 学校名を表示するために、学校名と学校コードの対応マップを生成
        Map<String, String> schoolMap = new HashMap<>();
        for (School school : schools) {
            schoolMap.put(school.getCd(), school.getName());
        }
        model.addAttribute("schoolMap", schoolMap);
        return "classNumForm"; // 登録画面のテンプレート名を返す
    }

    @PostMapping("/register")
    public String registerClassNums(@ModelAttribute("school") ClassNum classNum, Model model
    		) {
    	
    	if (classNumService.isClassNumNoDuplicate(classNum.getClassNum(), classNum.getSchoolCd())) {
    		List<School> schools = schoolService.getAllSchools();
            // 学校名を表示するために、学校名と学校コードの対応マップを生成
            Map<String, String> schoolMap = new HashMap<>();
            for (School school : schools) {
                schoolMap.put(school.getCd(), school.getName());
            }
            model.addAttribute("schoolMap", schoolMap);
            model.addAttribute("classNum", new ClassNum());
            model.addAttribute("errorMessage", "※クラス番号が重複しています※");
            return "classNumForm";
        }
    	
        classNumService.registerClassNum(classNum);
        return "redirect:/classnums/register_success";
    }
    
    @GetMapping("/register_success")
    public String Success() {
    	return "registerClassNumSuccess";
    }
    
    @GetMapping("/list")
    public String listClassNums(Model model) {
        model.addAttribute("classNums", classNumService.getAllClassNums());
        return "classNumList";
    }
    
    @PostMapping("/list")
    public String deleteClassNums(@RequestParam("schoolCd") String schoolCd, @RequestParam("classNum") String classNum) {
        classNumService.deleteClassNum(schoolCd, classNum);
        return "redirect:/classnums/list"; // クラス番号一覧ページにリダイレクト
    }

    @GetMapping("/edit/{schoolCd}/{classNum}")
    public String editClassNum(@PathVariable String schoolCd, @PathVariable String classNum, Model model) {
    	Optional<ClassNum> optionalClassNum = classNumRepository.findById(schoolCd,classNum);
        if (optionalClassNum.isPresent()) {
        	ClassNum classnum = optionalClassNum.get();
            model.addAttribute("classNum", classnum);
            List<School> schools = schoolService.getAllSchools();
            model.addAttribute("schools", schools);
            return "editClassNum";
        } else {
            return "redirect:/classnums/list";
        }
    }

    @PostMapping("/edit/{schoolCd}/{classNum}")
    public String processEditClassNum(@PathVariable String schoolCd, @PathVariable String classNum, @ModelAttribute ClassNum classNumData) {
        classNumService.updateClassNum(classNumData);
        return "redirect:/classnums/list";
    }

}
