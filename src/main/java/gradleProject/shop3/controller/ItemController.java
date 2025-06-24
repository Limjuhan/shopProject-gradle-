package gradleProject.shop3.controller;

import gradleProject.shop3.domain.Item;
import gradleProject.shop3.service.ShopService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("item")
public class ItemController {

	private ShopService service;

	public ItemController(ShopService service) {
		this.service = service;
	}

	@GetMapping("list")
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

	@GetMapping("create") //GET 방식 요청
	public String create(Model model) {
	 model.addAttribute("item", new Item());
	 model.addAttribute("title", "상품 등록");
	 return "item/create";
	}

	@PostMapping("create") //Post 방식 요청
	public String register(@Valid Item item, BindingResult bresult,
						   HttpServletRequest request, Model model) { // Model 추가
	 if(bresult.hasErrors()) {
		model.addAttribute("title", "상품 등록 오류"); // 오류 페이지 제목
		return "item/create";
	 }
	 service.itemCreate(item,request);
	 return "redirect:/item/list"; // 리다이렉트 시에는 컨텍스트 경로 주의 (/item/list)
	}

	@PostMapping("update")
	public String update(@Valid Item item, BindingResult bresult,
		HttpServletRequest request, Model model) { // Model 추가
	 if(bresult.hasErrors()) {
		model.addAttribute("title", "상품 수정 오류");
		return "item/update"; // resources/templates/item/update.html
	 }
	 service.itemUpdate(item,request);
	 return "redirect:/item/list";
	}

	@PostMapping("delete")
	public String delete(@RequestParam Integer id) {
	 service.itemDelete(id);
	 return "redirect:/item/list";
	}


}