package gradleProject.shop3.controller;

import gradleProject.shop3.dto.MailDto;
import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.UserDisplayDto;
import gradleProject.shop3.exception.ShopException;
import gradleProject.shop3.service.AdminService;
import gradleProject.shop3.service.UserService; // UserService는 계속 필요 (decryptEmail 등)

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

	private final AdminService adminService;

	private final UserService userService;

	@Value("${spring.mail.username}") // application.properties에서 메일 전송용 사용자 이름 주입
	private String configuredMailUsername;

	public AdminController(AdminService adminService, UserService userService) {
		this.adminService = adminService;
		this.userService = userService;
	}

	@GetMapping("/list")
	public String getUserList(Model model, HttpSession session) {
		List<UserDisplayDto> users = adminService.getAllUsersWithDecryptedEmails();
		model.addAttribute("list", users);
		return "/admin/list";
	}

	@GetMapping("/mail")
	public String mailform(@RequestParam(value = "idchks", required = false) String[] idchks,
						   Model model,
						   HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			throw new ShopException("로그인 정보가 없습니다. 다시 로그인 해주세요.", "/user/login");
		}

		if (idchks == null || idchks.length == 0) {
			throw new ShopException("메일을 보낼 대상자를 선택하세요.", "/admin/list");
		}

		// AdminService를 통해 MailDto 객체를 받아옴 (사용자 조회 및 이메일 조합 로직 포함)
		MailDto mailDto = adminService.getMailDtoForSelectedUsers(idchks);
		// mailDto에 configuredMailUsername을 설정 (폼에 표시될 값)
		mailDto.setGoogleid(configuredMailUsername);
		model.addAttribute("mail", mailDto);

		model.addAttribute("configuredMailUsername", configuredMailUsername);

		return "admin/mail";
	}

	@PostMapping("/mail")
	public String mail(@Valid MailDto mailDto,
					   BindingResult bresult,
					   Model model,
					   HttpSession session) {

		if (bresult.hasErrors()) {
			model.addAttribute("configuredMailUsername", configuredMailUsername);
			return "admin/mail";
		}

		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			throw new ShopException("로그인 정보가 없어 메일을 보낼 수 없습니다.", "/user/login");
		}

		String senderEmail;
		try {
			senderEmail = userService.decryptEmail(loginUser);
		} catch (ShopException e) {
			model.addAttribute("message", "보내는 사람 이메일 복호화 실패: " + e.getMessage());
			model.addAttribute("url", "/admin/list");
			return "error";
		}

		try {
			// AdminService를 사용하여 메일 전송
			boolean isSent = adminService.sendMail(mailDto, senderEmail);

			if (isSent) {
				model.addAttribute("message", "메일 전송이 완료 되었습니다.");
			} else {
				model.addAttribute("message", "메일 전송을 실패 했습니다.");
			}
		} catch (ShopException e) {
			model.addAttribute("message", "메일 전송 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}

		model.addAttribute("url", "/admin/list");
		return "error";
	}
}