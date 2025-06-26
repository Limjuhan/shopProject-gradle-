package gradleProject.shop3.controller;

import gradleProject.shop3.domain.Sale;
import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.*;
import gradleProject.shop3.exception.ShopException;
import gradleProject.shop3.mapper.UserMapper;
import gradleProject.shop3.service.ShopService;
import gradleProject.shop3.service.UserService;
import gradleProject.shop3.util.CipherUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShopService shopService;

    // BoardController 의존성 제거 (필요 없는 경우)
    /*
    private final BoardController boardController;
    UserController(BoardController boardController) {
        this.boardController = boardController;
    }
    */

    @GetMapping("*")
    public String form(Model model, HttpSession session, HttpServletRequest request) {
        User loginUser = (User) session.getAttribute("loginUser");

        // 로그인 상태인 경우 마이페이지로 리다이렉트
        if (loginUser != null && StringUtils.hasText(loginUser.getUserid())) {
            return "redirect:/user/mypage?userid=" + loginUser.getUserid();
        }

        // 현재 요청 경로에 따라 적절한 뷰 설정 (더 명확하게 분리하는 것이 좋음)
        // 예를 들어, /user/join 요청은 user/join 뷰, /user/login 요청은 user/login 뷰
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/user/join")) {
            model.addAttribute("user", new User()); // 폼 바인딩용 User 객체
            model.addAttribute("title", "회원가입");
            return "user/join-account";
        } else if (requestURI.endsWith("/user/login")) {
            model.addAttribute("user", new User()); // 폼 바인딩용 User 객체
            model.addAttribute("title", "로그인");
            return "user/login";
        } else {
            // 그 외의 * 요청에 대한 기본 처리
            model.addAttribute("user", new User());
            return "";
        }
    }

    @PostMapping("join")
    public String userAdd(@Valid UserDto userDto, BindingResult bresult, Model model) {
        model.addAttribute("title", "회원가입"); // 페이지 제목 설정

        if (bresult.hasErrors()) {
            bresult.reject("error.input.user", "입력 값을 확인해 주세요."); // 전반적인 입력 오류
            return "user/join-account"; // 오류 발생 시 다시 user/join 뷰로
        }

        try {
            String cipherPass = userService.encryptPwd(userDto.getPassword());
            userDto.setPassword(cipherPass);
            userService.encryptEmail(userDto);// 이메일 암호화
            User user = userMapper.toEntity(userDto);
            userService.userInsert(user);
        } catch (DataIntegrityViolationException e) { // 키값 중복된 경우 (아이디 중복)
            e.printStackTrace();
            bresult.reject("error.duplicate.user", "이미 존재하는 아이디입니다.");
            return "user/join-account";
        } catch (Exception e) {
            e.printStackTrace();
            bresult.reject("error.join.failed", "회원가입 중 알 수 없는 오류가 발생했습니다.");
            return "user/join-account";
        }

        return "redirect:/user/login";
    }



    @PostMapping("login")
    public String login(@Valid User user, BindingResult bresult, Model model, HttpSession session) {
        model.addAttribute("title", "로그인"); // 페이지 제목 설정

        // 사용자 ID 및 비밀번호 길이 검증
        if (user.getUserid().trim().length() < 3 || user.getUserid().trim().length() > 10) {
            bresult.rejectValue("userid", "error.required.userid", "아이디는 3~10자리여야 합니다.");
        }
        if (user.getPassword().trim().length() < 3 || user.getPassword().trim().length() > 10) {
            bresult.rejectValue("password", "error.required.password", "비밀번호는 3~10자리여야 합니다.");
        }

        if (bresult.hasErrors()) { // 등록된 오류 존재?
            bresult.reject("error.input.check", "입력 값을 확인해 주세요."); // 전반적인 입력 확인 메시지
            return "user/login"; // 오류 발생 시 다시 user/login 뷰로
        }

        User dbUser = userService.selectUser(user.getUserid());
        if (dbUser == null) { // 아이디 없음
            bresult.reject("error.login.id", "존재하지 않는 아이디입니다.");
            return "user/login";
        }

        try {
            if (CipherUtil.makehash(user.getPassword()).equals(dbUser.getPassword())) { // 비밀번호 일치
                session.setAttribute("loginUser", dbUser);
                return "redirect:/user/mypage?userid=" + user.getUserid();
            } else { // 비밀번호 불일치
                bresult.reject("error.login.password", "비밀번호가 일치하지 않습니다.");
                return "user/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("mypage")
    public String idCheckMypage(Model model, @RequestParam("userid") String userid, HttpSession session) {
        model.addAttribute("title", "내 정보"); // 페이지 제목 설정

        User user = userService.selectUser(userid);
        user.setEmail(userService.decryptEmail(user));
        List<Sale> salelist = shopService.saleList(userid);
        System.err.println("salelist = " + salelist);
        model.addAttribute("user", user);
        model.addAttribute("salelist", salelist);

        return "user/mypage";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/user/login"; // 로그인 페이지로 리다이렉트 (절대 경로)
    }

    //	// 로그인 상태, 본인정보만 조회 검증 => AOP 클래스 (UserLoginAspect.userIdCheck)
    @GetMapping("/update")
    public String idCheckupdateUserForm(Model model, @RequestParam("userid") String userid, HttpSession session) {
        User user = userService.selectUser(userid);
        user.setEmail(userService.decryptEmail(user));
        UserDto userDto =  userMapper.toDto(user);

        model.addAttribute("userDto", userDto);
        model.addAttribute("title", "정보 수정");
        return "user/update-account";
    }

    @PostMapping("update")
    public String idCheckUpdate(@Valid UserDto userDto, BindingResult bresult, Model model, HttpSession session) {
        model.addAttribute("title", "정보 수정"); // 페이지 제목 설정

        if (bresult.hasErrors()) {
            bresult.reject("error.update.user", "회원 정보 수정 중 오류가 발생했습니다."); // 전반적인 수정 오류
            return "user/update-account"; // 오류 발생 시 다시 user/update 뷰로
        }
        // 비밀번호 검증
        User loginUser = (User) session.getAttribute("loginUser");
        try {
            if (loginUser == null || !CipherUtil.makehash(userDto.getPassword()).equals(loginUser.getPassword())) {
                bresult.reject("error.login.password", "비밀번호가 일치하지 않습니다.");
                return "user/update-account";
            }
        } catch (Exception e) {
            throw new RuntimeException("입력한 비밀번호 해쉬값 비교작업중 오류");
        }

        try {
            userService.encryptEmail(userDto);
            User user = userMapper.toEntity(userDto);
            userService.userUpdate(user);
            if (loginUser.getUserid().equals(user.getUserid())) {
                session.setAttribute("loginUser", user); // 수정된 정보로 세션 업데이트
            }
            return "redirect:/user/mypage?userid=" + user.getUserid(); // 마이페이지로 리다이렉트 (절대 경로)
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShopException("고객 정보 수정 실패", "/user/update?userid=" + userDto.getUserid()); // 오류 발생 시 예외 처리
        }
    }

    @GetMapping("/delete")
    public String idCheckdeleteUserForm(Model model, @RequestParam("userid") String userid, HttpSession session) {
        User user = userService.selectUser(userid);
        UserDeleteDto userDeleteDto = new UserDeleteDto();
        userDeleteDto.setUserid(user.getUserid());

        model.addAttribute("user", user);
        model.addAttribute("userDeleteDto", userDeleteDto);
        model.addAttribute("title", "회원 탈퇴");
        return "user/delete-account";
    }

	@PostMapping("delete")
	public String idCheckDelete(@ModelAttribute @Valid UserDeleteDto userDeleteDto,
                                BindingResult bresult,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        String delUserId = userDeleteDto.getUserid();
        User delUser = userService.selectUser(delUserId);// 탈퇴하는 사용자 정보 조회

        if (bresult.hasErrors()) {
            model.addAttribute("user", delUser);
            model.addAttribute("title", "회원 탈퇴 오류");
            bresult.reject("error.input.check", "입력 값을 확인해 주세요.");
            return "user/delete-account";
        }

		// 관리자 탈퇴 불가
		if(delUserId.equals("admin")) {
			throw new ShopException("관리자는 탈퇴할 수 없습니다.", "/user/mypage?userid="+loginUser.getUserid());
		}

		// 비밀번호 불일치 (입력받은 비빌번호와 db저장되어있는 비밀번호 비교)
        try {
            if (!CipherUtil.makehash(userDeleteDto.getPassword()).equals(delUser.getPassword())) {
                bresult.rejectValue("password", "password.mismatch", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("user", delUser);
                model.addAttribute("title", "회원 탈퇴 오류");
                return "user/delete-account";
            }
        } catch (Exception e) {
            e.printStackTrace();
            bresult.reject("error.hashing.password", "비밀번호 처리 중 시스템 오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("user", delUser);
            model.addAttribute("title", "회원 탈퇴 오류");
            return "user/delete-account";
        }

        //탈퇴처리 로직
        try {
            userService.userDelete(delUserId);
        } catch (Exception e) {
            e.printStackTrace();
            bresult.reject("delete.failure", "회원 탈퇴 처리 중 시스템 오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("user", delUser);
            model.addAttribute("title", "회원 탈퇴 오류");
            return "user/userDelete"; // 폼으로 돌아가 오류 메시지 표시
        }

        String redirectUrl;
        if (loginUser != null && loginUser.getUserid().equals("admin")) {
            // 관리자가 다른 사용자를 탈퇴시켰을 경우
            redirectAttributes.addFlashAttribute("message", delUserId + " 사용자가 탈퇴 처리되었습니다.");
            redirectUrl = "redirect:/admin/list"; // 관리자 목록 페이지로
        } else {
            // 본인이 탈퇴한 경우 (세션 무효화 후 로그인 페이지로)
            session.invalidate();
            redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
            redirectUrl = "redirect:/user/login"; // 로그인 페이지로
        }
        return redirectUrl;
	}

    @GetMapping("/idsearch")
    public String idSearchForm(Model model) {
        model.addAttribute("userSearchDto", new UserSearchDto());
        model.addAttribute("title", "아이디 찾기");

        return "user/search-id";
    }

    @PostMapping("/idsearch")
    public String processIdSearch(@ModelAttribute("userSearchDto") @Valid UserSearchDto userSearchDto,
                                  BindingResult bresult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        // 이메일 또는 전화번호 미입력
        if (bresult.hasErrors()) {
            model.addAttribute("title", "아이디 찾기 오류");
            return "user/search-id";
        }

        // 2. 이메일과 전화번호로 사용자 아이디 찾기 서비스 호출
        Optional<String> foundUserId = userService.findUserIdByEmailAndPhoneno(userSearchDto.getEmail(), userSearchDto.getPhoneno());

        if (foundUserId.isPresent()) {
            // 아이디를 찾았을 경우, 모델에 아이디를 담아 같은 페이지를 다시 렌더링
            model.addAttribute("foundUserId", foundUserId.get());
            model.addAttribute("title", "아이디 찾기 성공");
            return "user/idSearch";
        } else { // 아이디를 찾지 못했을 경우, 오류 메시지와 함께 다시 폼으로 리다이렉트
            redirectAttributes.addAttribute("notFound", true);
            redirectAttributes.addFlashAttribute("userSearchDto", userSearchDto);
            return "redirect:/user/idsearch";
        }
    }

    @GetMapping("/pwdsearch")
    public String pwdSearchForm(Model model) {
        model.addAttribute("title", "비밀번호 찾기");
        model.addAttribute("userPwdSearchDto", new UserPwdSearchDto());

        return "user/search-password";
    }

    @PostMapping("/pwdsearch")
    public String processPwSearch(@ModelAttribute @Valid UserPwdSearchDto userPwdSearchDto,
                                  BindingResult bresult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        if (bresult.hasErrors()) {
            model.addAttribute("title", "비밀번호 찾기 오류");
            return "user/search-password";
        }

        // 아이디, 이메일, 전화번호로 사용자 정보 확인 및 비밀번호 재설정 로직
        Optional<User> findUser = userService.findUserByIdEmailAndPhoneno(userPwdSearchDto);

        if (findUser.isPresent()) {
            // 사용자 정보가 확인되면, 임시비밀번호 재설정 및 암호화해서 db에 저장.
            try {
                String newPwd = userService.generateRandomPassword();// 임시비밀번호 발급
                String hashPwd = userService.encryptPwd(newPwd);// 비밀번호 암호화
                findUser.get().setPassword(hashPwd);
                userService.userInsert(findUser.get());// 암호화한 임시비밀번호 DB에 저장.

                model.addAttribute("resetSuccessMessage", "임시 비밀번호 : " + newPwd);
                model.addAttribute("title", "비밀번호 찾기 성공");
                model.addAttribute("userPwSearchDto", userPwdSearchDto);
                return "user/search-password";
            } catch (Exception e) {
                bresult.reject("pwsearch.error", "비밀번호 재설정 중 오류가 발생했습니다. 다시 시도해주세요.");
                model.addAttribute("title", "비밀번호 찾기 오류");
                return "user/search-password";
            }
        } else { // 일치하는 계정을 찾지 못했을 경우
            redirectAttributes.addAttribute("notFound", true);
            return "redirect:/user/search-password";
        }
    }
	@GetMapping("/password")
	public String loginCheckform(Model model) {
		model.addAttribute("title", "비밀번호 변경");
        model.addAttribute("userPwdChgDto", new UserPwdChgDto());

		return "user/change-password";
	}

    @PostMapping("/password")
    public String processPasswordChange(@ModelAttribute @Valid UserPwdChgDto userPwdChgDto,
                                        BindingResult bresult,
                                        Model model,
                                        HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
			throw new ShopException("로그인 후 이용해 주세요.", "/user/login");
		}

        if (bresult.hasErrors()) {
            model.addAttribute("title", "비밀번호 변경 오류");
            return "user/change-password";
        }

        if (!userPwdChgDto.getNewPassword().equals(userPwdChgDto.getConfirmNewPassword())) {
            // 비밀번호 입력 과 비밀번호확인 입력 비교.
            bresult.rejectValue("confirmNewPassword", "UserPwdChgDto.passwordMismatch", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            model.addAttribute("title", "비밀번호 변경 오류");
            return "user/change-password";
        }

        try {
            String hashNewPwd = userService.encryptPwd(userPwdChgDto.getNewPassword());
            String hashCurrentPwd = userService.encryptPwd(userPwdChgDto.getCurrentPassword());
            // 기존 비밀번호 검증
            if (hashCurrentPwd.equals(loginUser.getPassword())) {
                loginUser.setPassword(hashNewPwd);
                userService.userUpdate(loginUser);

                session.setAttribute("loginUser", loginUser);
                model.addAttribute("passwordChangeSuccess", "비밀번호가 성공적으로 변경되었습니다.");
                model.addAttribute("title", "비밀번호 변경 성공");

                return "redirect:/user/mypage?userid=" + loginUser.getUserid();
            } else { // 현재 비밀번호 != DB비밀번호
                bresult.rejectValue("currentPassword", "UserPwdChgDto.invalidCurrentPassword", "현재 비밀번호가 올바르지 않습니다.");
                model.addAttribute("title", "비밀번호 변경 오류");
                return "user/change-password";
            }
        } catch (Exception e) {
            bresult.reject("passwordChangeDto.error", "비밀번호 변경 중 오류가 발생했습니다. 다시 시도해주세요.");
            model.addAttribute("title", "비밀번호 변경 오류");
            return "user/change-password";
        }
    }

//	@PostMapping("password")
//	public String loginCheckPassword(@RequestParam("password") String password, // 현재 비밀번호
//									 @RequestParam("chgpass") String chgpass, // 변경할 비밀번호
//									 Model model, HttpSession session) {
//
//		model.addAttribute("title", "비밀번호 변경");
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



}