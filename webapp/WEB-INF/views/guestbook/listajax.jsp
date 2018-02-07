<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import><!-- /header -->	
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import> <!-- navigation -->
		
		<div id="wrapper">
			<div id="content">
				<div id="guestbook">
					
					<form id="form" action="${pageContext.request.contextPath}/gb/api/writingajax" method="post">
						
						<table>
							<tr>
								<td>이름</td><td><input type="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" id="content"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input id="agree" type="submit" VALUE=" 확인 " /></td>
							</tr>
						</table>
					</form>
					
						<ul>
							<li id="gbtable">
								
							</li>
						</ul>
						
						<input id="morebtn" type="button" name ="more" value="더 보기">
				</div><!-- /guestbook -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import><!-- /footer -->
		
	</div> <!-- /container -->

</body>
	<script type="text/javascript">
		var addSize=5;
		var min = 0;
		var max = min+addSize;
		
		$(document).ready(function(){
			
			$.ajax({
s
				url : "${pageContext.request.contextPath }/gb/api/addlistajax",
				type : "post",
				contentType : "application/json", //json 형태로 바디에 담에 보내겠다고 타입 지정
				data : JSON.stringify({min: min, max: max}),
				
				/* dataType : "json", */
				success : function(listajax){ /*성공시 처리해야될 코드 작성*/
					for(var i=0; i<addSize; i++){
						render(listajax[i]);
					}
				},
				
				error : function(XHR, status, error) { /*실패시 처리해야될 코드 작성*/
					console.error(status + " : " + error);
				}
			});
		});
		
		$("#morebtn").on("click",function(){
			min += addSize;
			max += addSize;
			console.log(min);
			console.log(max);
			$.ajax({
				url : "${pageContext.request.contextPath }/gb/api/addlistajax",
				type : "post",
				contentType : "application/json", //json 형태로 바디에 담에 보내겠다고 타입 지정
				data : JSON.stringify({min: min, max: max}),
				
				/* dataType : "json", */
				success : function(listajax){ /*성공시 처리해야될 코드 작성*/
					for(var i=0; i<addSize; i++){
						render(listajax[i]);
					}
				},
				
				error : function(XHR, status, error) { /*실패시 처리해야될 코드 작성*/
					console.error(status + " : " + error);
				}
			});
		});
		
		function render(gb){
			var s = 
						"<table>"+
							"<tr>"+
								"<td>["+gb.no+"]</td>"+
								"<td>"+gb.name+"</td>"+
								"<td>"+gb.regDate+"</td>"+
								"<td><a href=${pageContext.request.contextPath}/gb/deleteform?no="+gb.no+">삭제</a></td>"+
							"</tr>"+
							"<tr>"+
								"<td colspan=4>"+
								gb.content+
								"</td>"+
							"</tr>"+
						"</table>"+
						"<br>";
				
			$("#gbtable").append(s);
		}
	
	</script>


</html>