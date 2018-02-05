package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	//게시물 리스트 가져오기 위해 서비스 로직 사용
	@RequestMapping(value = "/list" , method = RequestMethod.GET) 
	public String list(	HttpSession session,
						Model model,
					    @RequestParam(value="selectPage",required=false,defaultValue="1") int selectPage,
					    @RequestParam(value="kwd", required=false, defaultValue="")String kwd,
					    @RequestParam(value="searchType", required=false, defaultValue="title") String searchType) {
		
		Map<String,Object> listPage = boardService.list(selectPage, kwd, searchType);
		model.addAttribute("listPage", listPage);
		return "board/list";
	}
	
	//선택한 게시물의 내용을 보여주기위해 서비스 로직 사용
	@RequestMapping(value = "/view" , method = RequestMethod.GET) 
	public String view(@RequestParam("bno") int bno, Model model) {
		System.out.println("view 진입");
		BoardVo bvo= boardService.view(bno);
		model.addAttribute("bvo", bvo);
		return "board/view";
		
	}
	
	//단순 writeform 페이지 요청으로 서비스 로직 필요없음
	@RequestMapping(value = "/write" , method = RequestMethod.GET) 
	public String write(HttpSession session,
						HttpServletRequest request) {
		
		System.out.println("write 진입");
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		if(loginUser==null) {
			System.out.println("강제 write 진입자 발생");
			check(request);
			return "user/loginform";
		}else {
			return "board/write";
		}
	}

	//로그인한 유저가 작성한 글을 저장하기위해 서비스 로직 사용
	@RequestMapping(value = "/writing", method = RequestMethod.POST) 
	public String writing(HttpSession session,
						  HttpServletRequest request,
						  @RequestParam Map<String,String> board) {
		
		System.out.println("writing 진입");
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		if(loginUser==null) {
			System.out.println("강제 writing 진입자 발생");
			check(request);
			return "redirect:/user/loginform";
		}else {
			board.put("userNo", String.valueOf(loginUser.getNo()));
			int result = boardService.writing(board);
			if(result == 1) {
				System.out.println("사용자 작성글 저장 성공");
				return "redirect:/board/list";
			}else {
				System.out.println("사용자 작성글 저장 실패");
				return "redirect:/board/list";
			}
			
		}
	}
	
	//단순 modifyform 페이지 요청으로 서비스 로직 필요없음
	@RequestMapping(value = "/modify" , method = RequestMethod.POST) 
	public String modify(HttpSession session,
						 HttpServletRequest request) {
		
		System.out.println("modify 진입");
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		if(loginUser==null) {
			System.out.println("강제 modify 진입자 발생");
			check(request);
			return "redirect:/user/loginform";
		}else {
			return "board/modify";
		}
	}
	
	//게시글 수정을 위해서 로그인 유저와 수정할 게시글 작성자와 일치하는지 비교위해 서비스 로직 사용
	@RequestMapping(value = "/modifying" , method = RequestMethod.POST) 
	public String modifying(HttpSession session,
			  				HttpServletRequest request,
			  				@RequestParam Map<String,String> board) {
		
		System.out.println("modifying 진입");
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		if(loginUser==null) {
			System.out.println("강제 modifying 진입자 발생");
			check(request);
			return "redirect:/user/loginform";
		}else {
			int result = boardService.modifying(board,loginUser);
			if(result == 1) {
				System.out.println("사용자 작성글 수정 성공");
				return "redirect:/board/view?bno=" + board.get("bno");
			}else if(result == 0){
				System.out.println("사용자 작성글 수정 실패");
				return "redirect:/board/view?bno=" + board.get("bno");
			}else {
				System.out.println("강제 타사용자 작성글 수정 발생");
				check(request);
				session.removeAttribute("authUser");
				session.invalidate();
				return "redirect:/user/loginform";
			}
		}
	}
	
	//게시글 삭제를 위해서 로그인 유저와 삭제할 게시글 작성자와 일치하는지 비교위해 서비스 로직 사용
	@RequestMapping(value = "/delete" , method = RequestMethod.GET)
	public String delete(HttpSession session,
						 HttpServletRequest request,
						 @RequestParam("bno")int bno) {
		System.out.println("delete 진입");
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		if(loginUser==null) {
			System.out.println("강제 delete 진입자 발생");
			check(request);
			return "redirect:/user/loginform";
		}else {
			int result = boardService.delete(bno,loginUser);
			if(result == 1) {
				System.out.println("사용자 작성글 삭제 성공");
				return "redirect:/board/list";
			}else if(result == 0){
				System.out.println("사용자 작성글 삭제 실패");
				return "redirect:/board/list";
			}else {
				System.out.println("강제 타사용자 작성글 삭제 발생");
				check(request);
				session.removeAttribute("authUser");
				session.invalidate();
				return "redirect:/user/loginform";
			}
		}
		
	}	
	
	
	//강제로 비정상적인 접근이 발생했을때 해당 ip 확인
	private void check(HttpServletRequest request) {
		System.out.println("페이지를 요청한 ip 주소 (Host) : " + request.getRemoteHost());
		System.out.println("페이지를 요청한 ip 주소 (Addr) : " + request.getRemoteAddr());
		System.out.println("LocalPort : " + request.getLocalPort());
		System.out.println("LocalName : " + request.getLocalName());
		System.out.println("Protocol : " + request.getProtocol());
		System.out.println("Scheme : " + request.getScheme());
		System.out.println("ContextPath : " + request.getContextPath());
		System.out.println("URI : " + request.getRequestURI());
	}
}
