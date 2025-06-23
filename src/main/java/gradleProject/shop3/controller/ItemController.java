package gradleProject.shop3.controller;

import gradleProject.shop3.logic.Item;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ModelAndView 대신 Model 사용
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.ModelAndView; // 이제 필요 없음
import gradleProject.shop3.service.ShopService;

import javax.sql.DataSource; // jakarta.sql.DataSource 대신 javax.sql.DataSource 그대로 사용
import java.util.List;

@Controller
@RequestMapping("item")
public class ItemController {

	private final DataSource dataSource; // 현재 사용되지 않으므로 필요에 따라 제거 가능

	@Autowired //ShopService 객체 주입
	private ShopService service;

	// DataSource는 생성자 주입을 유지하되, 현재 사용하지 않으면 제거를 고려
	ItemController(DataSource dataSource) {
		this.dataSource = dataSource; // 생성자 필드 주입
	}

	// http://localhost:8083/item/list 요청시 호출되는 메서드 (기본 컨텍스트 경로 가정)
	@GetMapping("list") // GET 방식 요청시 호출
	public String list(Model model, HttpSession session) {

		// itemList : item 테이블의 모든 정보를 저장 객체
		List<Item> itemList = service.itemList();
		model.addAttribute("itemList", itemList); // view 에 전달할 객체 저장
		model.addAttribute("title", "상품 목록"); // 레이아웃에 전달할 페이지 제목

		Object loginUser = session.getAttribute("loginUser");
		if (loginUser != null) {
			model.addAttribute("loginUser", loginUser);
		}
		return "item/list";
	}

	@GetMapping("detail")
	public String detail(@RequestParam Integer id, Model model) {
		Item item = service.getItem(id);
		model.addAttribute("item", item);
		model.addAttribute("title", item.getName() + " - 상세 보기"); // 레이아웃에 전달할 페이지 제목

		System.err.println("상세보기창.item객체 확인" + item);

		return "item/detail";
	}

	@GetMapping("update")
	public String update(@RequestParam Integer id, Model model) {
		Item item = service.getItem(id);
		model.addAttribute("item", item);
		model.addAttribute("title", item.getName() + " - 수정"); // 레이아웃에 전달할 페이지 제목

		return "item/update";
	}
	@GetMapping("delete")
	public String delete(@RequestParam Integer id, Model model) {
		Item item = service.getItem(id);
		model.addAttribute("item", item);
		model.addAttribute("title", item.getName() + " - 삭제"); // 레이아웃에 전달할 페이지 제목

		return "item/delete";
	}





	// 아래 주석 처리된 메서드들도 Model을 사용하고 템플릿 이름 또는 redirect 문자열을 반환하도록 수정해야 합니다.

//  @GetMapping("create") //GET 방식 요청
//  public String create(Model model) {
//     model.addAttribute("item", new Item());
//     model.addAttribute("title", "상품 등록");
//     return "item/create"; // resources/templates/item/create.html
//  }
//
//  @PostMapping("create") //Post 방식 요청
//  public String register(@Valid Item item, BindingResult bresult,
//        HttpServletRequest request, Model model) { // Model 추가
//     if(bresult.hasErrors()) {
//        model.addAttribute("title", "상품 등록 오류"); // 오류 페이지 제목
//        return "item/create";
//     }
//     service.itemCreate(item,request);
//     return "redirect:/item/list"; // 리다이렉트 시에는 컨텍스트 경로 주의 (/item/list)
//  }
//
//  @PostMapping("update")
//  public String update(@Valid Item item, BindingResult bresult,
//        HttpServletRequest request, Model model) { // Model 추가
//     if(bresult.hasErrors()) {
//        model.addAttribute("title", "상품 수정 오류");
//        return "item/update"; // resources/templates/item/update.html
//     }
//     service.itemUpdate(item,request);
//     return "redirect:/item/list";
//  }
//
//  @PostMapping("delete")
//  public String delete(@RequestParam Integer id) {
//     service.itemDelete(id);
//     return "redirect:/item/list";
//  }


}