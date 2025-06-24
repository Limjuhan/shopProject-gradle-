package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.gdu.service.BoardService;

/*
 * @Controller : @Component + Controller 기능
 *    Mapping 메서드의 리턴 타입 : ModelAndView : 뷰이름 + 데이터
 *    Mapping 메서드의 리턴 타입 : String : 뷰이름 
 * 
 * @RestController : @Component + Controller 기능 + 클라이언트로 데이터를 직접 전송
 *    Mapping 메서드의 리턴 타입 : String : 클라이언트로 전달되는 문자열 값 
 *    Mapping 메서드의 리턴 타입 : Object : 클라이언트로 전달되는 되는 값. json 형식 처리
 * 
 *  Spring 4.0 이후에 추가됨
 *  Spring 4.0 이전에는 @ResponseBody 기능으로 사용함
 *  
 *  @ResponseBody의 설정은 메서드 마다 설정함
 */
@RestController
@RequestMapping("ajax")
public class AjaxController {
   
   @Autowired
   BoardService service;
   
   @GetMapping(value="select1", produces="text/plain; charset=utf-8")
   public String sidoSelect1(String si, String gu) {
	   return service.sidoSelect1(si, gu);
   }
   
   @PostMapping("select2")
   public List<String> sidoSelect2(String si, String gu) {
	   
	   return service.sidoSelect2(si, gu);
   }
   
   @GetMapping(value="exchange1", produces="text/html; charset=utf-8")
   public String exchange() {
	   
	   return service.exchange1();
   }
   
   @RequestMapping("exchange2")
   public Map<String, Object> exchange2() {
	   return service.exchange2();
   }
   
   @RequestMapping("graph1")
   public List<Map.Entry<String, Integer>> graph1 (String id) {
	   Map<String, Integer> map = service.graph1(id);
	   List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>();
	   
	   for (Map.Entry<String, Integer> m : map.entrySet()) {
		   list.add(m);
	   }
	   
	   Collections.sort(list, (m1, m2) ->m2.getValue() - m1.getValue());
	   return list;
   }
   
   @RequestMapping("graph2")
   public List<Map.Entry<String, Integer>> graph2 (String id) {
	   Map<String, Integer> map = service.graph2(id);
	   List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>();
	   
	   for (Map.Entry<String, Integer> m : map.entrySet()) {
		   list.add(m);
	   }
	   
	   System.err.println("막대그래프: " + list);
	   return list;
   }
   
   @RequestMapping("logo")
   public Map<String, Object> getLogo() {
	   return service.getLogo();
   }
}



















