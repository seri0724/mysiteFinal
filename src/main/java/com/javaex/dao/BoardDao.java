package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	SqlSession sqlSession;
	
	//게시물 작성
	public int boardInsert(Map<String,String> board) {
		return sqlSession.insert("board.insert", board);
	}
	
	//게시물 수정
	public int boardUpdate(Map<String,String> board) {
		return sqlSession.update("board.update", board);
	}
	
	//게시물 조회수 증가
	public int boardHit(int bno) {
		return sqlSession.update("board.hit", bno);
	}
	
	//view.jsp 를 위한 게시물 검색
	public BoardVo selectByNo(int bno) {
		return sqlSession.selectOne("board.selectByNo", bno);
	}
	
	//게시물 삭제
	public int boardDelete(int bno) {
		return sqlSession.delete("board.delete", bno);
	}
	
	//0))
	//전체 게시물수
	public int boardCount() {
		return sqlSession.selectOne("board.count");
	}
	//list.jsp를 위한 게시물 리스트 검색
	public List<BoardVo> selectPageList(Map<String,Object> boardSearchOption) {
		List<BoardVo> l = sqlSession.selectList("board.selectPageList",boardSearchOption);
		return l;
	}
	
	//1)
	//제목으로 검색한 게시물 수
	public int t_SearchBoardCount(String keyWord) {
		return sqlSession.selectOne("board.t_SearchBoardCount", keyWord);
	}	
	//제목으로 검색
	public List<BoardVo> t_SearchBoardList(Map<String,Object> boardSearchOption) {
		return sqlSession.selectList("board.t_SearchBoardList",boardSearchOption);
	}

	//2)
	//제목+내용으로 검색한 게시물 수
	public int tc_SearchBoardCount(String keyWord) {
		return sqlSession.selectOne("board.tc_SearchBoardCount", keyWord);
	}
	//제목+내용으로 검색
	public List<BoardVo> tc_SearchBoardList(Map<String,Object> boardSearchOption) {
		return sqlSession.selectList("board.tc_SearchBoardList",boardSearchOption);
	}
	
	//3))
	//작성자명으로 검색한 게시물 수
	public int un_SearchBoardCount(String keyWord) {
		return sqlSession.selectOne("board.un_SearchBoardCount", keyWord);
	}
	//작성자명으로 검색
	public List<BoardVo> un_SearchBoardList(Map<String,Object> boardSearchOption) {
		return sqlSession.selectList("board.un_SearchBoardList",boardSearchOption);
	}
	
}
