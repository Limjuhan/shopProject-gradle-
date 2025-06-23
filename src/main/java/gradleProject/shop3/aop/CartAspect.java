//package aop;
//
//import exception.ShopException;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//
//@Component
//@Aspect
//public class CartAspect {
//
//	/*
//	 * pointcut: CartController, 매개변수마지막이 HttpSession, check* 로 시작하는 메서드명
//	 *
//	 *  advice: before로 설정
//	 */
//
//	@Around("execution(* controller.Cart*.check*(..)) && args(.., session)")
//	public Object cartCheck(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable {
//		// 1. 현재 HttpServletRequest 객체를 가져옵니다.
//	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//	    // 2. 요청의 컨텍스트 경로를 가져옵니다. (예: "/shop1" 또는 루트에 배포된 경우 "" 빈 문자열)
//	    String contextPath = request.getContextPath();
//
//		User loginUser = (User)session.getAttribute("loginUser");
//
//		if(loginUser == null || !(loginUser instanceof User)) { // 로그 아웃상태
//			throw new ShopException("회원만 주문이 가능합니다. 로그인 하세요.", contextPath + "/user/login");
//		}
//
//		Cart cart = (Cart) session.getAttribute("CART");
//		if (cart == null || cart.getItemSetList().size() == 0) {
//			throw new ShopException("장바구니에 상품을 추가 하세요.", contextPath + "/item/list");
//		}
//
//		return joinPoint.proceed();
//	}
//}
