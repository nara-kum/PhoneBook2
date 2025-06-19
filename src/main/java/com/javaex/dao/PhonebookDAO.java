package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVO;

public class PhonebookDAO {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/phonebook_db";
	private String id = "phonebook";
	private String pw = "phonebook";
	
	//생성자
	public void PhonebookDAO() {}
	
	//메소드-gs
	
	//메소드일반
	private void connect() {

		try {
			// 1. JDBC 드라이버 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 종료-클리어
	private void close() {
		// 5. 자원정리
		try {
			if(rs != null) {
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
	
	//전체 리스트 가져오기
	public List<PersonVO> personSelect() {
		
		List<PersonVO> personList = new ArrayList<PersonVO>();
		
		connect();
		
		try {
			
			// 3. SQL준비 / 바인딩 / 실행
			
			String query= "";
			query+="select person_id ";
			query+="	, name ";
			query+="	, hp ";
			query+="	, company ";
			query+="from person ";
			query+="order by person_id desc ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행
			rs = pstmt.executeQuery();
			
			// 4. 결과처리
			while(rs.next()) {
				//resultSet에서 각각의 값을 꺼내서 변수에 담는다
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String ph = rs.getString("hp");
				String company = rs.getString("company");
				
				//1개의 VO로 묶는다
				PersonVO personVO = new PersonVO(personId,name,ph,company);
				//VO를 리스트에 추가한다
				personList.add(personVO);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return personList;
	}
	
	//사람(주소)등록
	public int personInsert(PersonVO personVO) {
		System.out.println("db등록");
		
		int count = -1;
		
		connect();

		// 3. SQL준비 / 바인딩 / 실행
		try {
			
			String query="";
			query+="insert into person values(null,?,?,?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVO.getName());
			pstmt.setString(2, personVO.getHp());
			pstmt.setString(3, personVO.getCompany());
			
			//실행		
			count = pstmt.executeUpdate();
					
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	
		close();
		
		return count;
	}
	
	//사람(주소)삭제
	public int personDelete(int personId) {
		System.out.println("db삭제");

		int count = -1;
		connect();
		
		try {
			//sql준비
			String query="";
			query+="delete ";
			query+="from person ";
			query+="where person_id = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			
			//실행
			count = pstmt.executeUpdate();

			//결과처리
			System.out.println(personId+ "번 삭제 : "+count+"건");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	
		close();
		
		return count;
		
	}
	//수정폼
	public PersonVO personOneSelect(int personId) {
		System.out.println("dao수정폼");

		PersonVO PersonVO = null;
		
		int count = -1;
		
		connect();
		
		try {
			//sql준비
			String query="";
			query+="select name ";
			query+="	, hp ";
			query+="	, company ";
			query+="	, person_id ";
			query+="from person ";
			query+="where person_id = ? ";
						
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			
			//실행
			rs = pstmt.executeQuery();

			//결과처리
			rs.next();

			//변수에 담어
			int personOneId = rs.getInt("person_id");
			String name = rs.getString("name");
			String ph = rs.getString("hp");
			String company = rs.getString("company");
			
			//1개의 VO로 묶는다
			PersonVO = new PersonVO(personOneId,name,ph,company);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	
		close();
		
		return PersonVO;
		
	}//사람(주소)등록
	public int personUpdate(PersonVO personVO) {
		System.out.println("db수정");
		
		int count = -1;
		
		connect();

		// 3. SQL준비 / 바인딩 / 실행
		try {
			
			String query="";
			query+="update person ";
			query+="set name = ? ";
			query+="	, hp = ? ";
			query+="    , company = ? ";
			query+="where person_id = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVO.getName());
			pstmt.setString(2, personVO.getHp());
			pstmt.setString(3, personVO.getCompany());
			pstmt.setInt(4, personVO.getPersonId());
			
			//실행		
			count = pstmt.executeUpdate();

			//결과처리
			System.out.println(count+"건 수정");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	
		close();
		
		return count;
	}
	
}
