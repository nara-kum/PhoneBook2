<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.PersonVO" %>

<% 
	PersonVO PersonVO = (PersonVO)request.getAttribute("personOne");
	System.out.println("여기는 jsp");
	System.out.println(PersonVO);
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>주소록</h1>
		<h2>전화번호 수정폼</h2>
		<p>전화번호를 수정하는 폼 입니다.</p>
		<%--폼은 주소를 만들어 준다 --%>
		<form action="http://localhost:8080/phonebook2/pbc" method="get">
			<label>이름(name)</label>
			<input type="text" name="name" value="<%=PersonVO.getName()%>">
			<br>
			
			<label>핸드폰(hp)</label>
			<input type="text" name="hp" value="<%=PersonVO.getHp()%>">
			<br>
			
			<label>회사(company)</label>
			<input type="text" name="company" value="<%=PersonVO.getCompany()%>">
			<br>
			
			<input type="hidden" name="action" value="modify">
			<br>
			
			<input type="hidden" name="personId" value="<%=PersonVO.getPersonId()%>">
			
			<button>수정</button>
		</form>
	</body>
</html>