package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/dashboard")
@Controller
public class DashboardController {
	
	public DashboardController() {
		
	}
	
	@GetMapping
	public String getDashboardForm() {
		return "/admin/dashboard";
	}
	
	
}
