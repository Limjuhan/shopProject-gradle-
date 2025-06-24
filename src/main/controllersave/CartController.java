package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.gdu.logic.Cart;
import kr.gdu.logic.Item;
import kr.gdu.logic.ItemSet;
import kr.gdu.logic.Sale;
import kr.gdu.logic.User;
import kr.gdu.service.ShopService;

@Controller
@RequestMapping("cart")
public class CartController {

    private final ItemController itemController;
	/*
	 * 1. 문제 
	 * 장바구니에 존재하는 상품의 경우 수량만 증가하기
	 * 장바구니에 존재하는 상품이 아닌 경우 상품 추가하기
	 * 
	 * 2. 문제
	 * 비밀번호 찾기를 비밀번호 초기화로 수정하기
	 * 기존 비밀번호:1234 
	 * 비밀번호 초기화 : 전체 6자리의 대문자/소문자/숫자 임의의 조합으로 변경하기
	 * 				   사용자에게 출력하기
	 */
	@Autowired
	private ShopService service;

    CartController(ItemController itemController) {
        this.itemController = itemController;
    }
	
    @RequestMapping("cartAdd")
    public ModelAndView addToCart(@RequestParam Integer id,
                                  @RequestParam Integer quantity,
                                  HttpSession session) {
    	
        ModelAndView mav = new ModelAndView("cart/cart");

        Item item = service.getItem(id);
        if (item == null) {
            mav.addObject("message", "상품을 찾을 수 없습니다.");
            return mav;
        }

        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("CART", cart);
        }

        cart.addItem(item, quantity);

        mav.addObject("message", item.getName() + " : " + quantity + "개 장바구니에 추가됨");
        mav.addObject("cart", cart);
        
        return mav;
    }
	
    @RequestMapping("cartDelete")
    public ModelAndView delete(int index, HttpSession session) {
    	ModelAndView mav = new ModelAndView("cart/cart"); // jsp주소
    	Cart cart = (Cart) session.getAttribute("CART");
    	/*
    	 * .remove(int타입 매개변수): 삭제후 삭제객체 반환.
    	 * 
    	 * ->int타입 매개변수 받을시 해당 인덱스 제거후 제거된 객체 반환하도록 오버로딩 되어있음
    	 * 
    	 * ->Object매개변수 받을시 객제삭제후 삭제여부를 boolean형 타입 반환으로 오버로딩 되어있음.
    	 */
    	ItemSet removeObj = cart.getItemSetList().remove(index); 
    	
    	mav.addObject("message", removeObj.getItem().getName() + "가(이) 삭제 되었습니다.");
    	mav.addObject("cart",cart);
    	
    	return mav;
    }
    
    @RequestMapping("cartView")
    public ModelAndView view(HttpSession session) {
    	ModelAndView mav = new ModelAndView("cart/cart"); // jsp주소
    	mav.addObject("message", "장바구니 상품 조회");
    	mav.addObject("cart", session.getAttribute("CART"));
    	
    	return mav;
    }
    
    /*
     * 주문전 확인 페이지
     * 1. 장바구니에 상품 존재해야함
     * 	  상품이 없는경우 예외 발생
     * 
     * 2. 로그인 된 상태여야함
     *    로그아웃상태 : 예외 발생
     */
    @RequestMapping("checkout")
    public String checkout(HttpSession session) {
    	return null;
    }
    
    @RequestMapping("end")
    public ModelAndView checkEnd(HttpSession session) {
    	
    	ModelAndView mav = new ModelAndView(); 
    	
    	Cart cart = (Cart) session.getAttribute("CART");
    	User loginUser = (User) session.getAttribute("loginUser");
    	Sale sale = service.checkEnd(loginUser, cart);
    	
    	session.removeAttribute("CART");
    	mav.addObject("sale", sale);
    	
    	return mav;
    }
}






















