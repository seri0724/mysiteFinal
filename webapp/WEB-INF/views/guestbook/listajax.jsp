<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
	
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import><!-- /header -->	
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import> <!-- navigation -->
		
		<div id="wrapper">
			<div id="content">
				<div id="guestbook">
					

						<!-- 삽입 테이블 -->
						<table>
							<tr>
								<td>이름</td><td><input type="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" id="content"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input id="addbtn" type="button" VALUE=" 확인 " /></td>
							</tr>
						</table>
						<!-- 삽입 테이블/ -->
					
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


	<!-- 삭제팝업(모달)창 -->
	<div class="modal fade" id="del-pop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">방명록삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label>
					<input type="password" name="modalPassword" id="modalPassword"><br>	
					<input type="text" name="modalPassword" value="" id="modalNo"> <br>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger" id="btn_del">삭제</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<input id="delbtn" type="button" value="삭제">

</body>
	<script type="text/javascript">
		var addSize = 5;
		var finalNo = 0;
		var fetchUrl = {select : "/gb/api/fetchlistajax",
						 write : "/gb/api/gbwritingajax",
						 del : "/gb/api/gbdeletingajax"
					   };
		
		$(document).ready(function(){
			fetchListAjax(addSize,finalNo);
		});
		
		//게시물 추가
		//보낼때는 header,body 선택 가능
		//json 사용시 : header
		//json 미사용시 : body
		//현재 보여준 리스트의 마지막번호에서 사용자가 게시글을 작성하는동안 추가된 게시글까지 리스트를 최신화
		$("#addbtn").on("click",function(){
			var gvo = {
				name : $("[name=name]").val(),
				password : $("[name=password]").val(),
				content : $("[name=content]").val()
			};
			
			$.ajax({
				url : "${pageContext.request.contextPath}"+fetchUrl.write,
				type : "post",
				
				data : {gvo : gvo, no : finalNo},  //왜인지 도무지 이유도 모르겠지만 finalNo 값에 음수를 넣으면 계속 콘솔에 타입 오류가 뜸 하지만 실행하는데 문제가 없이 실행됨
				
				dataType : "json",
				success : function(listajax){ /*성공시 처리해야될 코드 작성*/
					for(var i=0; i<addSize; i++){
						render(listajax[i],type);
					}
					return listajax[0].no;
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
			moreFetchList();
		});
		
		$("#delbtn").on("click",function(){
			$("#del-pop").modal();
		});
		
		//1.처음에는 기준을 잡을 게시물의 번호 값이 없기 때문에 임의 값 -1 을 넘겨서 가장 최신글을 rownum을 사용해서 로드해온다
		//2.기준잡을 번호 잡혔을때 
		function fetchListAjax(addSize,finalNo){
			fetchListExecute(fetchUrl.select,"append",addSize,finalNo);
			
		}
		
		//패치 리스트 실행
		function fetchListExecute(stringUrl,type,addSize,finalNo){
			$.ajax({
				url : "${pageContext.request.contextPath}"+stringUrl,
				type : "post",
				/* contentType : "application/json", //json 형태로 바디에 담에 보내겠다고 타입 지정
				data : JSON.stringify({addSize: addSize, no: finalNo}), */
				
				data : {addSize : addSize, noo : finalNo},
				
				dataType : "json",
				success : function(listajax){ /*성공시 처리해야될 코드 작성*/
					finalNo = listajax[0].no;
					console.log(finalNo);
					for(var i=0; i<addSize; i++){
						render(listajax[i],type);
					}
				},
				
				error : function(XHR, status, error) { /*실패시 처리해야될 코드 작성*/
					console.error(status + " : " + error);
				}
			});
		}
		
		//패치 리스트 실행 결과 토대로 태그 추가
		function render(gb,type){
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
			
			if(type == "append"){
				$("#gbtable").append(s);
			}else{
				$("#gbtable").prepend(s);
			}
		}
	
	</script>


</html>