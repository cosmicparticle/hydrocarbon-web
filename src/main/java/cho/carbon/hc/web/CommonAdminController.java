package cho.carbon.hc.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
//@RequestMapping("/admin")
public class CommonAdminController {
	@RequestMapping("/login")
	public String login(@RequestParam(name="error",required=false) String error, Model model){
		model.addAttribute("error", error);
		return "/admin/common/login.jsp";
	}
	
	@RequestMapping({"/", ""})
	public String index(){
		return "/admin/index.jsp";
	}
		
}
