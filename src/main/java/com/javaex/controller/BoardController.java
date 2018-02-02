package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService guestBookService;
	
	@RequestMapping("/list")
	public String list(	HttpSession session,
						Model model,
					    @RequestParam(value="selectPage",required=false,defaultValue="1") int selectPage,
					    @RequestParam(value="kwd", required=false, defaultValue="")String kwd,
					    @RequestParam(value="searchType", required=false, defaultValue="title") String searchType) {
		Map<String,Object> map = guestBookService.list(selectPage, kwd, searchType);
		model.addAttribute("map", map);
		return "board/list";
	}
	
//	@RequestMapping("/view")
//	public String view() {
//		guestBookService.view();
//		return "board/view";
//	}
//	
//	@RequestMapping("/write")
//	public String write() {
//		
//		return "board/write";
//	}
//
//	@RequestMapping("/writing")
//	public String writing() {
//		
//		return "board/writing";
//	}
//	
//	@RequestMapping("/modify")
//	public String modify() {
//		
//		return "board/modify";
//	}
//	
//	@RequestMapping("/modifying")
//	public String modifying() {
//		
//		return "board/modifying";
//	}
//	
//	@RequestMapping("/delete")
//	public String delete() {
//		
//		return "board/delete";
//	}
//	
//	public static void main(String[] args) {
//		
//		
//		
//		
//
//		System.out.println("board 진입");
//		request.setCharacterEncoding("UTF-8");
//		
//		System.out.println("페이지를 요청한 ip 주소 : " + request.getLocalAddr());
//		System.out.println("접근 포트 : " + request.getLocalPort());
//		System.out.println("ip 주소 지역 위치 : " + request.getLocale());
//		
//		HttpSession session = request.getSession();
//		UserVO vo = (UserVO) session.getAttribute("authUser");
//		
//		BoardDAO dao = new BoardDAO(); //정상 로그인 상태일 시에 사용할 dao 객체를만든다
//		String actionName = request.getParameter("a"); //사용자의 요청 action 값을 받아온다.
//		
//		//1.리스트 보기 , 2.자세히 보기
//		if(actionName.equals("list")) { //게시물 리스트 페이지 선택 요청시
//			System.out.println("list 진입");
//			
//			
//			
//		}else if(actionName.equals("view")){ //게시물 자세히 보기 요청시
//			System.out.println("view 진입");
//			int getNo = Integer.parseInt(request.getParameter("bno")); //사용자가 요청한 게시물의 번호를 받아와
//			dao.boardHit(getNo); //조회수를 하나 올린다
//			BoardVO bvo = dao.select(getNo); //그번호를 DAO 객체에 이용해서 게시물의 정보를 받아온다.
//			request.setAttribute("bvo", bvo); //검색해온 게시물의 정보를 가지고 view.jsp 완성해 사용자에게 넘겨준다.
//			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
//			
//		}else {
//			
//			//로그인 상태라면
//			if(vo!=null){
//				//3.게시물 작성폼, 4.게시물 삽입 
//				if(actionName.equals("write")){ //게시물 작성 폼 요청시
//					System.out.println("write 진입");
//					WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp"); //단순 작성폼 요청이기에 로그인만 되어있다면 그냥 페이지를 준다.
//					
//				}else if(actionName.equals("writing")){ //작성한 게시물 DB 삽입 요청시
//					System.out.println("writing 진입");
//					String title = request.getParameter("title"); //사용자가 작성한 title 을 받아온다.
//					String content = request.getParameter("content"); //사용자가 작성한 content 를 받아온다.
//					BoardVO bvo = new BoardVO(); //정보가 비어있는 게시물 객체를 하나 생성한다.
//					bvo.setTitle(title); //비어있는 게시물의 제목에 사용자가 작성한 title 을 넣어준다.
//					bvo.setContent(content); //비어있는 게시물의 내용에 사용자가 작성한 content 를 넣어준다
//					bvo.setUserNo(vo.getNo()); //현재 로그인 되어있는 사용자의 번호를 받아와서 게시물을 작성한 사람의 번호라고 적어준다.
//					dao.insert(bvo); //DAO 객체를 통해서 DB 에 작성된 게시물을 삽입한다.
//					WebUtil.redirect(request, response, "/mysite/board?a=list"); // 사용자의 브라우저 새로고침으로 인한 insert 요청의 중복 발생을 방지 하기위하여 redirect 로 사용자의 브라우저 url 을 바꿔준다. 
//					
//				// 5.게시물 수정폼, 6.게시물 수정요청, 7.삭제 요청시 :따로 분리한 이유는 위의 select 과 insert 와는 다르게 DB에 이미 기록된 정보를 수정, 삭제 요청 하는 것이기 때문에 로그인한 유저와 해당 게시물의 작성자가 동일 한지 검사가 필요하다.
//				}else if(actionName.equals("modify") || actionName.equals("modifying") || actionName.equals("delete")){
//					int loginUserNo = vo.getNo(); //로그인유저의 번호를 받아오고
//					int getBoardNo = Integer.parseInt(request.getParameter("bno")); // 어떤 글을 수정을 원하는지 번호를 받아온다.
//					BoardVO bvo = dao.select(getBoardNo); //dao 객체를 이용해서 사용자가 선택한 게시글의 정보를 검색해온다.
//					int boardUserNo = bvo.getUserNo(); //게시물 을 작성한 유저의 번호를 변수로 만든다
//					if(loginUserNo == boardUserNo) { //로그인 한 유저와 게시물을 작성한 유저가 같은지 비교하고 같을때 실행 되게 만든다.
//						if(actionName.equals("modify")){ //글 수정 폼 요청시
//							System.out.println("modify 진입");
//							request.setAttribute("bvo", bvo); //검색해온 게시물의 정보를 가지고 modify.jsp 완성해 사용자에게 넘겨준다.
//							WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
//							
//						}else if(actionName.equals("modifying")){ //수정한 글 DB에 업데이트 요청시
//							System.out.println("modifying 진입");
//							String title = request.getParameter("title"); //사용자가 작성한 수정한 게시글 제목을 받아온다.
//							String content = request.getParameter("content"); //사용자가 작성한 수정한 게시글 내용을 받아온다.
//							BoardVO updateVo = new BoardVO(); //업데이트에 사용할 게시물 객체를 생성한다.
//							updateVo.setNo(getBoardNo); //비어있는 게시물 번호에 위에 아까 사용자가 선택한 게시물의 번호를 기입한다.
//							updateVo.setTitle(title); //비어있는 게시물 제목에 사용자가 작성한 수정한 게시글 title 을 넣어준다.
//							updateVo.setContent(content); //비어있는 게시물 내용에 사용자가 작성한 수정한 게시글 cotent 를 넣어준다.
//							dao.update(updateVo); //DAO 객체를 이용해서 게시물을 update 한다.
//							WebUtil.redirect(request, response, "/mysite/board?a=list"); // 사용자의 브라우저 새로고침으로 인한 update 요청의 중복 발생을 방지 하기위하여 redirect 로 사용자의 브라우저 url 을 바꿔준다. 
//							
//						}else if(actionName.equals("delete")){ //선택한 글 DB에서 삭제 요청시
//							System.out.println("delete 진입");
//							dao.delete(getBoardNo); //DAO 객체를 이용해서 해당 번호 게시물을 삭제한다.
//							WebUtil.redirect(request, response, "/mysite/board?a=list"); // 사용자의 브라우저 새로고침으로 인한 delete 요청의 중복 발생을 방지 하기위하여 redirect 로 사용자의 브라우저 url 을 바꿔준다. 
//						}	
//					}
//				}
//			}else {
//				System.out.println("잘못된 접근입니다.");
//				WebUtil.redirect(request, response, "/mysite/board?a=list");
//			}
//			
//		}
//		
//		
//	}
}
