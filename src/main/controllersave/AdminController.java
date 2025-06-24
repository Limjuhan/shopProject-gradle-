package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.gdu.exception.ShopException;
import kr.gdu.logic.Mail;
import kr.gdu.logic.User;
import kr.gdu.service.AdminService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@GetMapping("/list")
	public String getUserList(Model model,HttpSession session) {
		List<User> users = adminService.getUserList();
		model.addAttribute("list",users);
		
		return "/admin/list";
	}
	
	@GetMapping("mail")
	public ModelAndView mailform(String[] idchks, HttpSession session) {
		ModelAndView mav = new ModelAndView("admin/mail");
		if (idchks == null || idchks.length == 0) {
			throw new ShopException("메일을 보낼 대상자를 선택하세요", "list");
		}
		
		List<User> list = adminService.getUserList(idchks);
		mav.addObject("list", list);
		Mail mail = new Mail();
		StringBuilder recipient = new StringBuilder();
		for (User u : list) {
			recipient.append(u.getUsername()).append("<").append(u.getEmail()).append(">,");
		}
		mail.setRecipient(recipient.toString());
		mav.addObject("mail",mail);
		
		return mav;
	}
	
	@PostMapping("mail")
	public ModelAndView mail(@Valid Mail mail, 
			BindingResult bresult, 
			HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		if (bresult.hasErrors()) {
			return mav;
		}
		
		mav.setViewName("alert");
		
		if (adminService.mailSend(mail)) {
			mav.addObject("message", "메일 전송이 완료 되었습니다.");
		} else {
			mav.addObject("message", "메일 전송을 실패 했습니다.");
		}
		
		mav.addObject("url", "list");
		// 첨부 파일 제거
		adminService.mailfileDelete(mail);
		return mav;
	}
}





















