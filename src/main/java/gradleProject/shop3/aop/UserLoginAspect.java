package gradleProject.shop3.aop;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.exception.ShopException;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
 1. pointcut : UserController.idCheck 메서드로 시작하고,
 			   마지막 매개변수가 String,HttpSession인 메서드를 설정\
 2. advice : Around로 이용
 */
@Component
@Aspect
public class UserLoginAspect {

    @Around("execution(* gradleProject.shop3.controller.User*.idCheck*(..)) && args(..,userid,session)")
	public Object userIdCheck(ProceedingJoinPoint joinPoint,String userid, HttpSession session) throws Throwable {

		User loginUser = (User)session.getAttribute("loginUser");

		if(loginUser == null || !(loginUser instanceof User)) { // 로그 아웃상태
			throw new ShopException("[idCheck]로그인이 필요합니다", "login");
		}

		if(!loginUser.getUserid().equals("admin") && !loginUser.getUserid().equals(userid)) {
			throw new ShopException("[idCheck]본인 정보만 거래 가능합니다","../item/list");
		}
		return joinPoint.proceed();
	}

    @Around("execution(* gradleProject.shop3.controller.User*.idCheck*(..)) && args(..,session)")
    public Object loginCheck(ProceedingJoinPoint joinPoint,
	                       HttpSession session) throws Throwable {

	      User loginUser = (User) session.getAttribute("loginUser");

	      if (loginUser == null || !(loginUser instanceof User)) {
	         throw new ShopException("[loginCheck]로그인이 필요합니다.", "login");
	      }

	      return joinPoint.proceed();
	   }


}
