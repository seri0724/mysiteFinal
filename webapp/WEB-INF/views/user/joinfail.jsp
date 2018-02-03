<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
</head>
<body>
	
	<div id="container">
	
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import><!-- /header -->
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import><!-- navigation -->
		
		<div id="wrapper">
			<div id="content">
				<div id="user">
					
					<p class="jr-success">
						회원가입 실패<br>
						이미 사용중인 email 입니다.
						<br><br>
						<a href="${pageContext.request.contextPath}/user/joinform">다시 가입하기</a>
					</p>
					
				</div><!-- /user -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import><!-- /footer -->
		
	</div> <!-- /container -->

</body>
</html>