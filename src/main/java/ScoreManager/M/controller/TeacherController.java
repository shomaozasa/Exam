package ScoreManager.M.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ScoreManager.M.model.School;
import ScoreManager.M.model.Teacher;
import ScoreManager.M.service.SchoolService;
import ScoreManager.M.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showTeacherRegistrationForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        List<School> schools = schoolService.getAllSchools();
        // 学校名を表示するために、学校名と学校コードの対応マップを生成
        Map<String, String> schoolMap = new HashMap<>();
        for (School school : schools) {
            schoolMap.put(school.getCd(), school.getName());
        }
        model.addAttribute("schoolMap", schoolMap);
        return "teacherForm";
    }

    @PostMapping("/register")
    public String registerTeacher(@ModelAttribute Teacher teacher, Model model) {
    	
    	if (teacherService.isTeacherNoDuplicate(teacher.getId())) {
    		
            model.addAttribute("errorMessage", "※idが重複しています※");
            List<School> schools = schoolService.getAllSchools();
            Map<String, String> schoolMap = new HashMap<>();
            for (School school : schools) {
                schoolMap.put(school.getCd(), school.getName());
            }
            model.addAttribute("schoolMap", schoolMap);;
            
            Teacher newTeacher = new Teacher();
            model.addAttribute("teacher", newTeacher);
            
            return "teacherForm";
        }
    	
    	String hashedPassword = passwordEncoder.encode(teacher.getPassword());
        teacher.setPassword(hashedPassword);
        teacherService.registerTeacher(teacher);
        return "redirect:/teachers/register_success";
    }
    
    @GetMapping("register_success")
    public String Success() {
    	return "registerTeacherSuccess";
    }

    @GetMapping("/list")
    public String listTeachers(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "teacherList";
    }

    @PostMapping("/list")
    public String deleteTeacher(@RequestParam("id") String id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditTeacherForm(@PathVariable String id, Model model) {
        Optional<Teacher> optionalTeacher = teacherService.getTeacher(id);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            model.addAttribute("teacher", teacher);
            List<School> schools = schoolService.getAllSchools();
            // 学校名を表示するために、学校名と学校コードの対応マップを生成
            Map<String, String> schoolMap = new HashMap<>();
            for (School school : schools) {
                schoolMap.put(school.getCd(), school.getName());
            }
            model.addAttribute("schoolMap", schoolMap);
            return "editTeacher";
        } else {
            return "redirect:/teachers/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editTeacher(@PathVariable String id, @ModelAttribute Teacher teacher) {
    	String hashedPassword = passwordEncoder.encode(teacher.getPassword());
    	teacher.setPassword(hashedPassword);
        teacherService.registerTeacher(teacher);
        teacherService.updateTeacher(teacher);
        return "redirect:/teachers/list";
    }

}
