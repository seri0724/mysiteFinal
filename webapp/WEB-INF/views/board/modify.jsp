<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">

		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import><!-- /header -->	
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import> <!-- navigation -->
		
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.request.contextPath}/board?a=modifying">
					<input type="hidden" name ="bno" value="${requestScope.bvo.no}">
					<input type="hidden" name= "buserno" value="${requestScope.bvo.userNo}">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="${requestScope.bvo.title}"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content">${requestScope.bvo.content}</textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="">취소</a>
						<input type="submit" value="수정">
					</div>
				</form>				
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import><!-- /footer -->
		
		
	</div><!-- /container -->
</body>
</html>