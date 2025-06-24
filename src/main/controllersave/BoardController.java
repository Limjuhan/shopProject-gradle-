package controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.gdu.Shop2Application;
import kr.gdu.exception.ShopException;
import kr.gdu.logic.Board;
import kr.gdu.logic.Comment;
import kr.gdu.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardController {

    private final Shop2Application shop2Application;
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService, Shop2Application shop2Application) {
		this.boardService = boardService;
		this.shop2Application = shop2Application;
	}
	
	@GetMapping("*")
	public ModelAndView write() {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(new Board());
		
		return mav;
	}
	
	/*
	 * Spring에서 파라미터 전달 방식
	 * 	1. 파라미터이름과 매개변수의 이름이 같은 경우 매핑
	 * 	2. Bean 클래스의 프로퍼티명과 파라미터이름이 같은 경우 매핑
	 * 	3. Map객체에 RequestParam 어노테이션을 이용한 매핑
	 * 		
	 * 
	 */
	@RequestMapping("list")
	public ModelAndView list(@RequestParam Map<String, String> param,
			HttpSession session) {
		
		Integer pageNum = null;
		
		for (String key : param.keySet()) {
			if (!StringUtils.hasText(param.get(key))) {
				param.put(key, null);
			}
		}
		
		if (StringUtils.hasText(param.get("pageNum"))) {
		    try {
		        pageNum = Integer.parseInt(param.get("pageNum"));
		    } catch (NumberFormatException e) {
		        // 숫자로 변환할 수 없는 경우, 기본값 1로 설정하거나 에러 처리
		        pageNum = 1;
		    }
		} else {
		    pageNum = 1;
		}
		
		String boardid = param.get("boardid");
		String searchtype = param.get("searchtype");
		String searchcontent = param.get("searchcontent");
		
		ModelAndView mav = new ModelAndView();
		String boardName = null;
		
		getBoardName(mav, boardid);
		
		// 게시판 조회 처리
		int limit = 10;// 페이지당 건수
		int listcount = boardService.boardcount(boardid, searchtype, searchcontent);// boardid 별 전체 게시물 건수
		// boardlist : 해당 페이지출력될 게시물 목록
		List<Board> boardlist = 
				boardService.boardlist(pageNum, limit, boardid, searchtype, searchcontent);
		
		// 페이징 처리를 위한 변수
		int maxpage = (int) ((double) listcount / limit + 0.95);// 게시물 건수에 따른 최대 페이지 값
		int startpage = (int) ((pageNum / 10.0 + 0.9) - 1) * 10 + 1;// 화면에 보여질 시작페이지 값
		int endpage = startpage + 9;// 화면에 보여질 마지막페이지 값
		if (endpage > maxpage) endpage = maxpage;// 마지막페이지가 최대페이지보다 클때 마지막페이지를 최대페이지로 변경
		int boardno = listcount - (pageNum - 1) * limit;
		
		mav.addObject("boardid", boardid);
		mav.addObject("pageNum", pageNum);
		mav.addObject("maxpage", maxpage);
		mav.addObject("startpage", startpage);
		mav.addObject("endpage", endpage);
		mav.addObject("listcount", listcount);
		mav.addObject("boardlist", boardlist);
		mav.addObject("boardno", boardno);
		
		return mav;
	}
	
	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam String num) {
		
		ModelAndView mav = new ModelAndView();
		
		try {
			Board board =  boardService.getBoard(num);
			boardService.addReadCnt(num);
			String boardId = board.getBoardid();
			
			getBoardName(mav, boardId);
			mav.addObject("board", board);
		} catch(Exception e) {
			e.printStackTrace();
			log.warn("페이지 조회시 오류발생: {}", num);
		}
		
		// 댓글등록
		List<Comment> commlist = boardService.commentlist(Integer.parseInt(num));
		
		Comment comment = new Comment();
		comment.setNum(Integer.parseInt(num));
		mav.addObject("commlist", commlist);
		mav.addObject(comment);
		
		return mav;
	}
	
	@PostMapping("write")
	public ModelAndView writePost(@Valid Board board, 
			BindingResult bresult,
			HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		if (bresult.hasErrors()) {
			return mav;
		}
		
		if (!StringUtils.hasText(board.getBoardid())) {
			board.setBoardid("1");
		}
		
		boardService.boardWrite(board, request);
		mav.setViewName("redirect:list?boardid=" + board.getBoardid());
		
		return mav;
	}
	
	@GetMapping({"reply", "update", "delete"})
	public ModelAndView getBoard(String num, String boardid, HttpServletRequest request) {
		
	    ModelAndView mav = new ModelAndView();
    	Board board = boardService.getBoard(num);
	    
	    mav.addObject("board", board);
		getBoardName(mav,boardid);
		
		return mav;
	}
	
	@PostMapping("update")
	public ModelAndView updateBoard(@Valid Board board, 
			BindingResult bresult,
			HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		if (bresult.hasErrors()) {
			return mav;
		}
		
		Board dbBoard = boardService.getBoard(String.valueOf(board.getNum()));
		if (!board.getPass().equals(dbBoard.getPass())) {
			throw new ShopException("비밀번호가 틀립니다.", 
					"update?num=" + board.getNum() + "&boardid=" + dbBoard.getBoardid());
		}
		
		try {
			boardService.updateBoard(board,request);
			mav.setViewName("redirect:detail?num=" + board.getNum());
		} catch(Exception e) {
			e.printStackTrace();
			throw new ShopException("게시글 수정 실패", 
					"update?num=" + board.getNum() + "&boardid=" + dbBoard.getBoardid());
		}
		
		return mav;
	}
	
	@PostMapping("delete")
	public String deleteBoard(@Valid Board board, BindingResult bresult,Model model) {
		
		if (!isValidPassword(board.getPass(), board.getNum())) {
	        bresult.reject("error.login.password");
	        model.addAttribute("num", board.getNum());
	        model.addAttribute("boardid", board.getBoardid());
	        return "/board/delete";
	    }
		
		boardService.deleteBoard(board.getNum());
		
		return "redirect:list?boardid=" + board.getBoardid();
	}
	
	/*
	 * db에 insert => service.boardReply()
	 * 	기존에 등록된 답글들 grpstep변경 : grpstep+1 ->boardDao.grpStepAdd()
	 *  기존글들이 새로등록된 답글보다 밑으로 밀려야되기때문에 기존답글들 step증가시킴.
	 *  
	 *  num : maxNum() + 1
	 *  grp: 원글과 동일
	 *  grplevel : 원글 grplevel++
	 *  grpstep : 원글 grpstep++
	 *  
	 *  등록성공 : list로 redirect
	 *  실패 : "답변 등록시 오류 발생" reply 페이지 이동
	 */
	@PostMapping("reply")
	public ModelAndView replyBoard(@Valid Board board, 
			BindingResult bresult, 
			HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		if (bresult.hasErrors()) {
			return mav;
		}
		
		try {
			
			boardService.boardReply(board, request);
			
			mav.setViewName("redirect:list?boardid=" + board.getBoardid());
		} catch(Exception e) {
			e.printStackTrace();
			String url = "reply?num="+board.getNum() + "&boardid="+board.getBoardid();
			throw new ShopException("답변등록시 오류 발생", url);
		}
		
		return mav;
	}
	
	@RequestMapping("comment")
	public ModelAndView comment(@Valid Comment comm, BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		if (bresult.hasErrors()) {
			return commdetail(comm);
		}
		
		int seq = boardService.commmaxseq(comm.getNum());
		comm.setSeq(++seq);
		boardService.cominsert(comm);
		mav.setViewName("redirect:detail?num="+comm.getNum()+"#comment");
		
		return mav;
	}

	@RequestMapping("commdel")
	public String commdel(Comment comm) {
		Comment dbcomm = boardService.commSelectOne(comm.getNum(), comm.getSeq());
		
		if (comm.getPass().equals(dbcomm.getPass())) {
			boardService.commdel(comm.getNum(), comm.getSeq());
		} else {
			throw new ShopException("댓글 삭제 실패", "detail?num=" + comm.getNum() + "#comment");
		}
		
		return "redirect:detail?num=" + comm.getNum() + "#comment";
	}
	
	private ModelAndView commdetail(Comment comm) {
		
		ModelAndView mav = detail(Integer.toString(comm.getNum()));
		mav.setViewName("board/detail");
		mav.addObject(comm);
		return mav;
	}
	
	private boolean isValidPassword(String pass, int num) {
		
		String getPwd = boardService.getPass(num);
		if (getPwd.equals(pass)) {
			return true;
		} else {
			return false;
		}
	}

	private void getBoardName(ModelAndView mav, String boardId) {
		
		if (StringUtils.hasText(boardId)) {
			switch (boardId) {
			case "1":
				mav.addObject("boardName", "공지사항");
				break;
			case "2":
				mav.addObject("boardName", "자유게시판");
				break;
			case "3":
				mav.addObject("boardName", "QnA");
				break;
			default:
				break;
			}
		}
	}
	
}


















