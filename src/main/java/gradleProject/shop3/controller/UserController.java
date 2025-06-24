//package gradleProject.shop3.controller;
//
//import gradleProject.shop3.service.ShopService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.SecureRandom;
//import java.util.List;
//
//@Controller
//@RequestMapping("user")
//public class UserController {
//
//	@Autowired
//	private UserService service;
//
//	@Autowired
//	private ShopService shopService;
//
//	// BoardController 의존성 제거 (필요 없는 경우)
//    /*
//    private final BoardController boardController;
//    UserController(BoardController boardController) {
//        this.boardController = boardController;
//    }
//    */
//
//	@GetMapping("*") // GET 방식 모든 요청시 호출 (회원가입/로그인 폼 등)
//	public String form(Model model, HttpSession session, HttpServletRequest request) {
//		User loginUser = (User) session.getAttribute("loginUser");
//
//		// 로그인 상태인 경우 마이페이지로 리다이렉트
//		if (loginUser != null && StringUtils.hasText(loginUser.getUserid())) {
//			return "redirect:/user/mypage?userid=" + loginUser.getUserid();
//		}
//
//		// 현재 요청 경로에 따라 적절한 뷰 설정 (더 명확하게 분리하는 것이 좋음)
//		// 예를 들어, /user/join 요청은 user/join 뷰, /user/login 요청은 user/login 뷰
//		String requestURI = request.getRequestURI();
//		if (requestURI.endsWith("/user/join")) {
//			model.addAttribute("user", new User()); // 폼 바인딩용 User 객체
//			model.addAttribute("title", "회원가입");
//			return "user/join";
//		} else if (requestURI.endsWith("/user/login")) {
//			model.addAttribute("user", new User()); // 폼 바인딩용 User 객체
//			model.addAttribute("title", "로그인");
//			return "user/login";
//		} else {
//			// 그 외의 * 요청에 대한 기본 처리
//			model.addAttribute("user", new User());
//			model.addAttribute("title", "회원가입"); // 기본적으로 회원가입 폼을 보여주는 것으로 가정
//			return "user/join";
//		}
//	}
//
//	@PostMapping("join")
//	public String userAdd(@Valid User user, BindingResult bresult, Model model) {
//		model.addAttribute("title", "회원가입"); // 페이지 제목 설정
//
//		if(bresult.hasErrors()) {
//			bresult.reject("error.input.user", "입력 값을 확인해 주세요."); // 전반적인 입력 오류
//			return "user/join"; // 오류 발생 시 다시 user/join 뷰로
//		}
//
//		try {
//			service.userInsert(user);
//		} catch(DataIntegrityViolationException e) { // 키값 중복된 경우 (아이디 중복)
//			e.printStackTrace();
//			bresult.reject("error.duplicate.user", "이미 존재하는 아이디입니다."); // 아이디 중복 오류
//			return "user/join";
//		} catch(Exception e) { // 기타 예외
//			e.printStackTrace();
//			bresult.reject("error.join.failed", "회원가입 중 알 수 없는 오류가 발생했습니다."); // 일반적인 회원가입 실패 메시지
//			return "user/join";
//		}
//
//		return "redirect:/user/login"; // 성공 시 로그인 페이지로 리다이렉트 (절대 경로)
//	}
//
//	@PostMapping("login")
//	public String login(@Valid User user, BindingResult bresult, Model model, HttpSession session) {
//		model.addAttribute("title", "로그인"); // 페이지 제목 설정
//
//		// 사용자 ID 및 비밀번호 길이 검증
//		if(user.getUserid().trim().length() < 3 || user.getUserid().trim().length() > 10) {
//			bresult.rejectValue("userid", "error.required.userid", "아이디는 3~10자리여야 합니다.");
//		}
//		if(user.getPassword().trim().length() < 3 || user.getPassword().trim().length() > 10) {
//			bresult.rejectValue("password", "error.required.password", "비밀번호는 3~10자리여야 합니다.");
//		}
//
//		if(bresult.hasErrors()) { // 등록된 오류 존재?
//			bresult.reject("error.input.check", "입력 값을 확인해 주세요."); // 전반적인 입력 확인 메시지
//			return "user/login"; // 오류 발생 시 다시 user/login 뷰로
//		}
//
//		User dbUser = service.selectUser(user.getUserid());
//		if(dbUser == null) { // 아이디 없음
//			bresult.reject("error.login.id", "존재하지 않는 아이디입니다.");
//			return "user/login";
//		}
//
//		if(user.getPassword().equals(dbUser.getPassword())) { // 비밀번호 일치
//			session.setAttribute("loginUser", dbUser);
//			return "redirect:/user/mypage?userid=" + user.getUserid(); // 마이페이지로 리다이렉트 (절대 경로)
//		} else { // 비밀번호 불일치
//			bresult.reject("error.login.password", "비밀번호가 일치하지 않습니다.");
//			return "user/login";
//		}
//	}
//
//	@RequestMapping("mypage")
//	public String idCheckMypage(@RequestParam("userid") String userid, Model model) {
//		model.addAttribute("title", "내 정보"); // 페이지 제목 설정
//
//		User user = service.selectUser(userid);
//		List<Sale> salelist = shopService.saleList(userid);
//
//		model.addAttribute("user", user);
//		model.addAttribute("salelist", salelist);
//
//		return "user/mypage"; // user/mypage 뷰 반환
//	}
//
//	@RequestMapping("logout")
//	public String logout(HttpSession session) {
//		session.invalidate(); // 세션 무효화
//		return "redirect:/user/login"; // 로그인 페이지로 리다이렉트 (절대 경로)
//	}
//
//	// 로그인 상태, 본인정보만 조회 검증 => AOP 클래스 (UserLoginAspect.userIdCheck)
//	// /user/update, /user/delete 요청을 각각 별도의 GetMapping으로 분리하는 것이 더 명확합니다.
//	// 기존의 * 방식 처리에서 특정 뷰를 반환하기 위해 HttpServletRequest를 사용하는 예시
//	@GetMapping({"update", "delete"})
//	public String idCheckUser(@RequestParam("userid") String userid, Model model, HttpServletRequest request) {
//		User user = service.selectUser(userid);
//		model.addAttribute("user", user);
//
//		// 요청 경로에 따라 뷰 이름 설정
//		String requestURI = request.getRequestURI(); // 현재 요청의 URI 가져오기
//		if (requestURI.endsWith("/user/update")) {
//			model.addAttribute("title", "정보 수정");
//			return "user/update";
//		} else if (requestURI.endsWith("/user/delete")) {
//			model.addAttribute("title", "회원 탈퇴");
//			return "user/delete";
//		} else {
//			// 예외 처리 또는 기본 에러 페이지로
//			model.addAttribute("title", "페이지 오류");
//			return "error/error";
//		}
//	}
//
//	@PostMapping("update")
//	public String idCheckUpdate(@Valid User user, BindingResult bresult, Model model, HttpSession session) {
//		model.addAttribute("title", "정보 수정"); // 페이지 제목 설정
//
//		if(bresult.hasErrors()) {
//			bresult.reject("error.update.user", "회원 정보 수정 중 오류가 발생했습니다."); // 전반적인 수정 오류
//			return "user/update"; // 오류 발생 시 다시 user/update 뷰로
//		}
//
//		// 비밀번호 검증
//		User loginUser = (User) session.getAttribute("loginUser");
//		if(loginUser == null || !loginUser.getPassword().equals(user.getPassword())) {
//			bresult.reject("error.login.password", "비밀번호가 일치하지 않습니다.");
//			return "user/update";
//		}
//
//		try {
//			service.userUpdate(user);
//			if(loginUser.getUserid().equals(user.getUserid())) {
//				session.setAttribute("loginUser", user); // 수정된 정보로 세션 업데이트
//			}
//			return "redirect:/user/mypage?userid=" + user.getUserid(); // 마이페이지로 리다이렉트 (절대 경로)
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ShopException("고객 정보 수정 실패", "/user/update?userid=" + user.getUserid()); // 오류 발생 시 예외 처리
//		}
//	}
//
//	@PostMapping("delete")
//	public String idCheckDelete(@RequestParam("password") String password,
//								@RequestParam("userid") String userid,
//								Model model, HttpSession session) {
//		model.addAttribute("title", "회원 탈퇴"); // 페이지 제목 설정
//
//		User loginUser = (User) session.getAttribute("loginUser");
//
//		// 관리자 탈퇴 불가
//		if(userid.equals("admin")) {
//			throw new ShopException("관리자는 탈퇴할 수 없습니다.", "/user/mypage?userid="+userid);
//		}
//
//		// 비밀번호 불일치 (탈퇴하려는 사용자 본인의 비밀번호와 비교)
//		User userToDelete = service.selectUser(userid); // 탈퇴하려는 사용자 정보 조회
//		if(userToDelete == null || !password.equals(userToDelete.getPassword())) {
//			throw new ShopException("비밀번호를 확인하세요.", "/user/delete?userid="+userid);
//		}
//
//		try {
//			service.userDelete(userid);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ShopException("회원 탈퇴 중 오류 발생", "/user/delete?userid="+userid);
//		}
//
//		String redirectUrl;
//		if(loginUser != null && loginUser.getUserid().equals("admin")) {
//			redirectUrl = "redirect:/admin/list"; // 관리자라면 관리자 목록 페이지로 (절대 경로)
//		} else {
//			session.invalidate(); // 본인 탈퇴 시 세션 무효화
//			redirectUrl = "redirect:/user/login"; // 로그인 페이지로 리다이렉트 (절대 경로)
//		}
//		return redirectUrl;
//	}
//
//	@GetMapping("password")
//	public String loginCheckform(Model model) {
//		model.addAttribute("title", "비밀번호 변경"); // 페이지 제목 설정
//		return "user/password"; // user/password 뷰 반환
//	}
//
//	@PostMapping("password")
//	public String loginCheckPassword(@RequestParam("password") String password, // 현재 비밀번호
//									 @RequestParam("chgpass") String chgpass, // 변경할 비밀번호
//									 Model model, HttpSession session) {
//		model.addAttribute("title", "비밀번호 변경"); // 페이지 제목 설정
//
//		User loginUser = (User) session.getAttribute("loginUser");
//
//		if (loginUser == null) {
//			throw new ShopException("로그인 후 이용해 주세요.", "/user/login");
//		}
//
//		// 현재 입력받은 비밀번호와 로그인된 사용자의 실제 비밀번호 비교
//		if(!password.equals(loginUser.getPassword())) {
//			throw new ShopException("현재 비밀번호가 일치하지 않습니다. 다시 확인해주세요.", "/user/password");
//		}
//
//		try {
//			service.updatePassword(loginUser.getUserid(), chgpass); // 서비스 호출
//			loginUser.setPassword(chgpass); // 세션에 저장된 사용자 정보 업데이트
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ShopException("비밀번호 변경 중 오류가 발생했습니다.", "/user/password");
//		}
//
//		return "redirect:/user/mypage?userid=" + loginUser.getUserid(); // 마이페이지로 리다이렉트 (절대 경로)
//	}
//
//	@PostMapping("{url}search") // url은 'id' 또는 'pw'가 될 것
//	public String search(@Valid User user, BindingResult bresult, @PathVariable String url, Model model) {
//		model.addAttribute("title", "검색 결과"); // 페이지 제목 기본 설정
//
//		boolean isPasswordSearch = "pw".equals(url); // 비밀번호 찾기인지 여부
//		String title = isPasswordSearch ? "비밀번호 찾기" : "아이디 찾기";
//		String errorCode = isPasswordSearch ? "error.password.search" : "error.userid.search";
//		String errorMessage = isPasswordSearch ? "입력하신 정보로 비밀번호를 찾을 수 없습니다." : "입력하신 정보로 아이디를 찾을 수 없습니다.";
//
//		model.addAttribute("title", title); // 실제 페이지 제목 설정 (아이디 찾기/비밀번호 찾기)
//
//		// 사용자 입력 유효성 검사
//		validateUserInput(user, bresult, isPasswordSearch);
//
//		if (bresult.hasErrors()) {
//			bresult.reject("error.input.check", "입력 값을 확인해 주세요."); // 전반적인 입력 확인 메시지
//			return "user/search"; // 오류 시 다시 user/search 뷰로
//		}
//
//		// userid가 빈 문자열이면 null 처리 (id 찾기 시 userid는 필요 없음)
//		if (!isPasswordSearch && StringUtils.isEmpty(user.getUserid())) {
//			user.setUserid(null);
//		}
//
//		String result = service.getSearch(user); // DB에서 검색
//		if (result == null) {
//			bresult.reject(errorCode, errorMessage); // 검색 결과 없음 오류
//			return "user/search"; // 오류 시 다시 user/search 뷰로
//		}
//
//		// 비밀번호 초기화 로직 (비밀번호 찾기인 경우)
//		if (isPasswordSearch) {
//			String newPassword = generateRandomPassword(); // 새 임시 비밀번호 생성
//			service.updatePassword(user.getUserid(), newPassword); // DB에 새 비밀번호 업데이트
//			model.addAttribute("result", "임시 비밀번호가 발급되었습니다. 새로운 비밀번호: " + newPassword);
//		} else {
//			model.addAttribute("result", "찾으시는 아이디: " + result);
//		}
//
//		return "user/search"; // 검색 결과 뷰 반환
//	}
//
//	// 사용자 입력 유효성 검사 (search 메서드에서 호출)
//	private void validateUserInput(User user, BindingResult bresult, boolean isPasswordSearch) {
//		if (isPasswordSearch && !StringUtils.hasText(user.getUserid())) {
//			bresult.rejectValue("userid", "error.required.userid", "아이디를 입력해 주세요.");
//		}
//		if (!StringUtils.hasText(user.getEmail())) {
//			bresult.rejectValue("email", "error.required.email", "이메일을 입력해 주세요.");
//		}
//		if (!StringUtils.hasText(user.getPhoneno())) {
//			bresult.rejectValue("phoneno", "error.required.phoneno", "전화번호를 입력해 주세요.");
//		}
//	}
//
//	// 6자리 임시 비밀번호 생성
//	private String generateRandomPassword() {
//		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//		SecureRandom random = new SecureRandom();
//		StringBuilder sb = new StringBuilder(6);
//		for (int i = 0; i < 6; i++) {
//			sb.append(characters.charAt(random.nextInt(characters.length())));
//		}
//		return sb.toString();
//	}
//}