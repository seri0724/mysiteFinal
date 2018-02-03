package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void insertGuestBook(GuestBookVo vo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into guestbook values(seq_guestbook_no.nextval, ? , ? , ? ,  sysdate)"; 
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, vo.getName()); 
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());
			int result = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("처리 결과 : " + result);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
	}

	public List<GuestBookVo> selectAllGuestBook() {
		// 0. import java.sql.*;
		connect();
		List<GuestBookVo> l = new ArrayList<GuestBookVo>();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select no,name,content,reg_date from guestbook order by reg_date desc, no desc ";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			
			// 4.결과처리
			while(rs.next()) {
				GuestBookVo vo = new GuestBookVo();
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				vo.setNo(no);
				vo.setName(name);
				vo.setContent(content);
				vo.setDate(regDate);
				l.add(vo);
			}
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
		return l;
	}
	
	public GuestBookVo selectGuestBook(int getno) {
		// 0. import java.sql.*;
		connect();
		GuestBookVo vo=null;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select no,name,content,reg_date from guestbook where no = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getno);
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				vo = new GuestBookVo();
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				vo.setNo(no);
				vo.setName(name);
				vo.setContent(content);
				vo.setDate(regDate);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
		return vo;
	}
	
	
	public void updateGuestBook(GuestBookVo avo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update guestbook set name = ? , password = ? , content = ? , reg_date = sysdate where no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, avo.getName());
			pstmt.setString(2, avo.getPassword());
			pstmt.setString(3, avo.getContent());
			pstmt.setInt(4, avo.getNo());
			int result = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("처리 결과 : " + result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error:" + e);
		} finally {
			close();
		}
	}
	
	
	public int deleteGuestBook(int no ,String pw) {
		// 0. import java.sql.*;
		connect();
		int result=0;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from guestbook where no = ? and password = ?";
			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, no);
			pstmt.setString(2, pw);
			result = pstmt.executeUpdate();
			
			// 4.결과처리	
			System.out.println("처리 결과 : " + result);
			

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
		return result;
	}
	


	
	private void connect() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	private void close() {
		// 5. 자원정리

		try {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	
}
