package com.javaex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	BoardDao boardDao;

	public Map list(int sp, String kwd, String st) {
		// TODO Auto-generated method stub
		//페이징 세팅 값 2가지
		//1.
		int pageSize = 10; // 한페이지당 게시물 수
		//2.
		//선택한 페이지 번호를 가운데 정렬 할 것이기 때문에 무조건 pageBundleSize 를 홀수로 지정
		int pageBundleSize = 9; // 표시할 페이지 개수
		
		
		//--)이하 : 페이징 세팅 값을 설정하면 자동 세팅 되도록 설계
		
		//--0)사용자가 요청한 검색 스펙 받기
		//처음 list.jsp 페이지가 열릴때는 사용자가 페이지 넘버를 선택하지 않은 상태라 가장 최신 페이지인 1로 초기화
		int selectPage=sp;
		System.out.println("selectPage : " + selectPage);
		String keyWord = kwd; //검색 키워드를 받아온다.
		System.out.println("keyWord : " + kwd);
		String searchType = st; //검색 타입을 받아온다.
		System.out.println("searchType : " + st);
		if(keyWord==null) { //키워드가 null이면 "" 로 세팅
			keyWord="";
		}
		
		//--1)DB 검색 시작
		//DB 검색결과 게시물 개수를 담을 변수 선언
		int boardCount;	
		//DB 검색결과 게시물 리스트를 담을 변수 선언
		List<BoardVo> l ;
		
		//DAO 객체를 이용해서 사용자가 클릭한 페이지에 해당하는 게시물들을 가져온다
		//만약 2페이지를 누르면 11번~20번까지 게시물을 가져와야 하기때문에 (사용자가 선택한 페이지 번호에서 -1)을 한다.
		if(keyWord.equals("")) {
			boardCount = boardDao.boardCount();
			System.out.println("전체 게시물 수 : " + boardCount);
			l = boardDao.selectPageList((selectPage-1)*pageSize,selectPage*pageSize); 
		}else {
			String copyKeyWord = keyWord; //키워드 가공을 위한 복사
			copyKeyWord.replace(" ", "%"); //띄어 쓰기 한곳을 전부 %로 변경
			copyKeyWord = "%".concat(keyWord).concat("%"); //DB 검색을 위해서 앞뒤에 %를 붙이도록 변경
			if(searchType==null) {
				System.out.println("검색 타입이 없습니다.");
				boardCount = 0;
				l = new ArrayList<BoardVo>();
			}else if(searchType.equals("title")) {
				System.out.println("검색 타입 : 제목");
				System.out.println("키워드 확인 : " + copyKeyWord);
				boardCount = boardDao.t_SearchBoardCount(copyKeyWord);
				System.out.println("키워드 검색 일치 게시물 수 : " + boardCount);
				l = boardDao.t_SearchList((selectPage-1)*pageSize,selectPage*pageSize,copyKeyWord);
			}else if(searchType.equals("title,content")) {
				System.out.println("검색 타입 : 제목+내용");
				System.out.println("키워드 확인 : " + copyKeyWord);
				boardCount = boardDao.tc_SearchBoardCount(copyKeyWord);
				System.out.println("키워드 검색 일치 게시물 수 : " + boardCount);
				l = boardDao.tc_SearchList((selectPage-1)*pageSize,selectPage*pageSize,copyKeyWord);
			}else if(searchType.equals("username")) {
				System.out.println("검색 타입 : 작성자명");
				System.out.println("키워드 확인 : " + copyKeyWord);
				boardCount = boardDao.un_SearchBoardCount(copyKeyWord);
				System.out.println("키워드 검색 일치 게시물 수 : " + boardCount);
				l = boardDao.un_SearchList((selectPage-1)*pageSize,selectPage*pageSize,copyKeyWord);
			}else {
				System.out.println("잘못된 검색 타입 입니다.");
				boardCount = 0;
				l = new ArrayList<BoardVo>();
			}
		}
		//DB 검색 결과로 얻은 게시물 개수를 이용하여 몇페이지를 만들수 있는지 계산
		int pageCount = ((boardCount-1)/pageSize)+1; // 페이지 총 갯수
		 // 게시물이 10개 이하일때도 1페이지라도 무조건 나와야 하기 때문에 페이지 개수를 구할때 올림 기능을 만들어 내야해서 무조건 결과에 1을 더해줬는데
		 // 게시물이 10개가 될때는 올림 해줄 필요가없어서 2페이지라는 오류가 발생하기 때문에 강제적으로 게시물 수에서 -1을 해주어 pageSize 로 나누었다
		
		
		//--2)사용자에게 표시할 페이지의 정상 작동을 위한 페이지 이동 및 범위 관리 로직
		int movePage = (pageBundleSize/2)+1;//화살표 클릭시 몇페이지씩 옆으로 이동시킬 것인지 지정
		int minPage=1; // 페이지의 최소 범위 지정
		int maxPage=pageCount; // 페이지 최대 범위 지정
		
		//사용자가 선택한 페이지의 넘버가 페이지의 최소, 최대범위값을 벗어나는 것을 방지
		if(selectPage<minPage) {
			selectPage = minPage;
		}else if(selectPage>maxPage) {
			selectPage = maxPage;
		}	
		
		System.out.println("페이지 최소 최대 범위");
		System.out.println("minPage : " + minPage);
		System.out.println("maxPage : " + maxPage);
		
		//보여줄 페이지의 최소,최대 범위 지정
		int showMinPage = selectPage-(pageBundleSize/2); 
		int showMaxPage = selectPage+(pageBundleSize/2);
		
		System.out.println("표시할 페이지 범위");
		System.out.println("showMinPage : " + showMinPage);
		System.out.println("showMaxPage : " + showMaxPage);
		
		//보여줄 페이지가 최소,최대 범위를 벗어나는 것을 방지
		if(showMinPage<minPage) { // 보여줄 페이지의 범위가 페이지의 최소 범위를 벗어나면
			showMinPage = minPage;
			showMaxPage = (showMinPage-1)+pageBundleSize;
			System.err.println("표시할 페이지 범위가 페이지 최소 범위 초과 : 재설정");
			System.err.println("showMinPage : " + showMinPage);
			System.err.println("showMaxPage : " + showMaxPage);		
		}else if(showMaxPage>maxPage) { // 보여줄 페이지의 범위가 페이지의 최대 범위를 벗어나면
			if(maxPage<pageBundleSize) { //DB에 저장되어있는 데이터로 만들수 있는 리스트 크기가 페이지 묶음 크기보다 작으면
				showMinPage = minPage;
				showMaxPage = pageBundleSize;
				System.err.println("표시할 페이지 범위가 페이지 최대 범위 초과 : 재설정");
				System.err.println("showMinPage : " + showMinPage);
				System.err.println("showMaxPage : " + showMaxPage);	
			}else { //리스트 크기가 페이지 묶음 크기보다 작지 않을때
				showMaxPage = maxPage;
				showMinPage = (showMaxPage+1)-pageBundleSize;
				System.err.println("표시할 페이지 범위가 페이지 최대 범위 초과 : 재설정");
				System.err.println("showMinPage : " + showMinPage);
				System.err.println("showMaxPage : " + showMaxPage);		
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("keyWord", keyWord);
		map.put("searchType", searchType);
		map.put("minPage", minPage);
		map.put("maxPage", maxPage);
		map.put("showMinPage", showMinPage);
		map.put("showMaxPage", showMaxPage);
		map.put("selectPage", selectPage);
		map.put("movePage", movePage);
		map.put("pageCount", pageCount);
		map.put("l", l);
		
		return map;
	}

}
