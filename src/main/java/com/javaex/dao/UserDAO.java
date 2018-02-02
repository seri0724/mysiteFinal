package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVO;

@Repository
public class UserDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void insert(UserVO vo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into users values(seq_users_no.nextval, ? , ? , ? , ?)"; 
			pstmt = conn.prepareStatement(query); 
			pstmt.setString(1, vo.getName()); 
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			int result = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("처리 결과 : " + result);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
	}

	public List<UserVO> selectAll() {
		// 0. import java.sql.*;
		connect();
		List<UserVO> l = new ArrayList<UserVO>();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select no ,name, email, password, gender from users";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				UserVO vo = new UserVO();
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String gender = rs.getString("gender");
				
				vo.setNo(no);
				vo.setName(name);
				vo.setEmail(email);
				vo.setPassword(password);
				vo.setGender(gender);
				l.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}
		return l;
	}
	
	public UserVO select(String getEmail, String getPassword) {
		// 0. import java.sql.*;
		connect();
		UserVO vo = null;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select no , name, gender from users where email = ? and password = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, getEmail);
			pstmt.setString(2, getPassword);
			rs = pstmt.executeQuery();
			
			
			// 4.결과처리
			while(rs.next()) {
				vo = new UserVO();
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				vo.setNo(no);
				vo.setName(name);
				vo.setEmail(getEmail);
				vo.setPassword(getPassword);
				vo.setGender(gender);
				
			}
				
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}

		return vo;
	}
	
	public UserVO select(int getno) {
		// 0. import java.sql.*;
		connect();
		UserVO vo = null;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select name, email, password, gender from users where no = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getno);
			rs = pstmt.executeQuery();
			
			
			// 4.결과처리
			while(rs.next()) {
				vo = new UserVO();
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String gender = rs.getString("gender");
				
				vo.setNo(getno);
				vo.setName(name);
				vo.setEmail(email);
				vo.setPassword(password);
				vo.setGender(gender);
				
			}
				
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			close();
		}

		return vo;
	}
	
	
	public void update(UserVO vo) {
		// 0. import java.sql.*;
		connect();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update users set name = ? , password = ? , gender = ? where no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setInt(4, vo.getNo());
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
	
	
	public int delete(int no ,String pw) {
		// 0. import java.sql.*;
		connect();
		int result=0;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from users where no = ? and password = ?";
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
