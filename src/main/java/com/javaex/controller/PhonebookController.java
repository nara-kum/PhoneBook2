package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhonebookDAO;
import com.javaex.vo.PersonVO;

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public PhonebookController() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//로직
		System.out.println("PhonebookController");
		
		//action 파라미터의 값을 알아야 함
		
		String action = request.getParameter("action");
		System.out.println(action); //업무구분
		
		if("list".equals(action)) {//리스트
			
			System.out.println("리스트");
			
			//db데이터 가져오기
			PhonebookDAO phonbookDAO = new PhonebookDAO();
			List<PersonVO> personList = phonbookDAO.personSelect();
			
			System.out.println(personList);
			
			//html + List
			
			//jsp에게 넘긴다
			//(1)request객체에 데이터를 넣어준다
			request.setAttribute("personList",personList);
			
			//(2)list.jsp에 request객체와 response객체를 보낸다
			//포워드
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			
		}else if("wform".equals(action)) {//등록폼

			System.out.println("등록폼");
			
			//(1)db 읽어오지 않아도 됨
			
			//(2)jsp에게 넘긴다 화면 그리도록
			//writeForm.jsp에 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);
			

		}else if("write".equals(action)) {//등록

			System.out.println("등록");
			
			//파라미터 받어
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			System.out.println(name+"/"+hp+"/"+company); //입력한 파라미터
			
			//받은 파라미터들 하나로 묶는다
			PersonVO personVO = new PersonVO(name,hp,company);
			
			System.out.println(personVO);

			//db에 DAO를 이용해서 insert
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			phonebookDAO.personInsert(personVO);
			
			//리다이렉트 - list 다시 요청해주세요(url 다시 요청)
			//포워드와는 다름
			response.sendRedirect("http://localhost:8080/phonebook2/pbc?action=list");
			
			/*
			//리스트 받아와
			List<PersonVO> personList = phonebookDAO.personSelect();
			
			request.setAttribute("personList",personList);
			
			//입력받은 데이터를 jsp에게 넘긴다
			// 에 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			*/

		} else if("delete".equals(action)) {//삭제

			System.out.println("삭제");
			
			//파라미터 받어
			int personId = Integer.parseInt(request.getParameter("personId"));
			
			//받은 파라미터들 하나로 묶는다

			//db에 DAO를 이용해서 delete
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			phonebookDAO.personDelete(personId);
			
			//리다이렉트 - list 다시 요청해주세요(url 다시 요청)
			//포워드와는 다름
			response.sendRedirect("http://localhost:8080/phonebook2/pbc?action=list");
			
		} else if("mform".equals(action)) {//수정폼

			System.out.println("수정폼");
			
			//파라미터 받어
			int personId = Integer.parseInt(request.getParameter("personId"));
			
			//받은 파라미터들 하나로 묶는다

			//db에 DAO를 이용해서 select 해온다
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			PersonVO PersonVO = phonebookDAO.personOneSelect(personId);
			
			System.out.println(PersonVO);
			
			//jsp에게 넘긴다
			//(1)request객체에 데이터를 넣어준다
			request.setAttribute("personOne",PersonVO);
			
			//포워드
			RequestDispatcher rd = request.getRequestDispatcher("/modifyForm.jsp");
			rd.forward(request, response);
			
			
		} else if("modify".equals(action)) {//수정

			System.out.println("수정");
			
			//파라미터 받어
			int personId = Integer.parseInt(request.getParameter("personId"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			//받은 파라미터들 하나로 묶는다
			PersonVO personVO = new PersonVO(personId,name,hp,company);

			//db update를 하세요
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			phonebookDAO.personUpdate(personVO);
			
			System.out.println(personVO);
			
			//리다이렉트 - list 다시 요청해주세요(url 다시 요청)
			//포워드와는 다름
			response.sendRedirect("http://localhost:8080/phonebook2/pbc?action=list");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
