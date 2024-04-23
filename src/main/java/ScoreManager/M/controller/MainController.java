package ScoreManager.M.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ScoreManager.M.model.Teacher;
import ScoreManager.M.repository.UserRepository;
import ScoreManager.M.service.SchoolService;

@Controller
public class MainController {
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private SchoolService schoolService;
  
  @GetMapping("/")
  public String top(Model model) {
	  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String id = authentication.getName();
	    Teacher teacher = userRepository.findByIdEquals(id);
	    String name = teacher.getName();
	    String schoolCd = teacher.getSchoolCd(); // 学校コードの取得
	    String schoolName = ""; // 学校名

	    if (schoolCd != null) {
	        schoolName = schoolService.getSchoolNameByCode(schoolCd);
	    }

	    model.addAttribute("userName", name);
	    model.addAttribute("schoolName", schoolName);
	    return "top";
      }
}