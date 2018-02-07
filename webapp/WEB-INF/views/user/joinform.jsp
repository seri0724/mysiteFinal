<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import><!-- /header -->
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import><!-- navigation -->
			
		<div id="wrapper">
			<div id="content">
				<div id="user">
	
					<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath}/user/join">
						<label class="block-label" for="name">이름</label>
						<input id="name" name="name" type="text" value="">
	
						<label class="block-label" for="email">이메일</label>
						<input id="email" name="email" type="text" value="">
						<input id="emailcheckbtn" type="button" value="id 중복체크">
						<input id="emailcheckresult" type="text" style="border-width : 0px ; font-weight:bold;" readonly="readonly" value="중복 체크가 필요합니다.">
					
						<label class="block-label">패스워드</label>
						<input name="password" type="password" value="">
						
						<fieldset>
							<legend>성별</legend>
							<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
							<label>남</label> <input type="radio" name="gender" value="male">
						</fieldset>
						
						<fieldset>
							<legend>약관동의</legend>
							<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
							<label>서비스 약관에 동의합니다.</label>
						</fieldset>
						
						<input type="submit" value="가입하기">
						
					</form>
					
				</div><!-- /user -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import><!-- /footer -->
		
	</div> <!-- /container -->

</body>

	<script type="text/javascript">
		$("#emailcheckbtn").on("click",function(){
			var email = $("#email").val();
			console.log("이메일 체크 버튼");
			
			//ajax 를 사용 하기위해서 필요한 포맷
			$.ajax({

				url : "${pageContext.request.contextPath }/user/api/emailcheck",
				type : "post",
				contentType : "application/json", //json 형태로 바디에 담에 보내겠다고 타입 지정
				data : JSON.stringify({email: email}),
				
				/* dataType : "json", */
				success : function(result){ /*성공시 처리해야될 코드 작성*/
					if(result == true){
						$("#emailcheckresult").css("color","blue");
						$("#emailcheckresult").val("사용가능한 이메일입니다.");
					}else{
						$("#emailcheckresult").css("color","red");
						$("#emailcheckresult").val("사용중인 이메일입니다.");
					}
				},
				
				
				error : function(XHR, status, error) { /*실패시 처리해야될 코드 작성*/
					/* console.error(status + " : " + error); */
				}
			});
			
		});
		
		$("#email").keydown(function(){
			$("#emailcheckresult").css("color","black");
			$("#emailcheckresult").val("중복 체크가 필요합니다.");
		});
		
		
		
	</script>
	
</html>