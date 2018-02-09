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
								<td>이름</td><td><input type="text" class="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" class="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea id="content" class="content" name="content" ></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input id="addbtn" type="button" VALUE=" 확인 " /></td>
							</tr>
						</table>
						<!-- 삽입 테이블/ -->
					
						<ul id="gbtable">
							
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
				<div class="modal-body" align="center">
					<table >
						<tr>
							<td align="right"><label>게시글 번호 : </label></td>
							<td align="right"><input type="text" id="modalgbNo" value="" style="border-width: 0px;" readonly="readonly"></td>
						</tr>
						<tr>
							<td align="right"><label>비밀번호 : </label></td>
							<td align="right"><input type="password" id="modalPassword" name="modalPassword" ></td>
						</tr>
					</table>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger" id="delbtn">삭제</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

</body>
	<script type="text/javascript">
		var addSize = 5;
		var minNo = 0;
		var maxNo = 0;
		var fetchUrl = {
						 select : "/gb/api/fetchlistajax",
						 write : "/gb/api/gbwritingajax",
						 del : "/gb/api/gbdeletingajax"
					   };
		
		//페이지 맨처음 로딩시
		$(document).ready(function(){
			fetchListAjax(fetchUrl.select,addSize,minNo);
		});
		
		//패치 리스트
		//1.최초에 기준을 잡을 게시물의 번호 값이 없기 때문에 임의 값 0 을 넘겨서 가장 최신글을 rownum을 사용해서 로드해온다
		//2.받아온 리스트로 부터 게시물의 최저번호,최고 번호를 설정한다.
		function fetchListAjax(stringUrl,addSize,sendMinNo){
			$.ajax({
				url : "${pageContext.request.contextPath}"+stringUrl,
				type : "post",
				
				//parameter 로 담아 보냄
				data : {addSize : addSize, minNo : sendMinNo},
				
				dataType : "json",
				success : function(listajax){ /*성공시 처리해야될 코드 작성*/
					if(listajax.length!=0){
						minNo = listajax[listajax.length-1].no;
						if(maxNo==0){//맨처음 한번 로드해올시에만 max값을 세팅하도록...
							maxNo = listajax[0].no;
						}
						console.log("minNo : " + minNo);
						console.log("maxNo : " + maxNo);
						for(var i=0; i<listajax.length; i++){
							render(listajax[i],"append");
						}
					}else{
						console.log("게시물 더이상 존재 하지 않음");
					}
				},
				
				error : function(XHR, status, error) { /*실패시 처리해야될 코드 작성*/
					console.error(status + " : " + error);
				}
			});
		}
		
		$("#morebtn").on("click",function(){
			fetchListAjax(fetchUrl.select,addSize,minNo);
		});
		
		
		//보낼때는 header,body 선택 가능
		//게시물 추가
		//json 사용시 : body
		//json 미사용시 : header
		//현재 보여준 리스트의 마지막번호에서 사용자가 게시글을 작성하는동안 추가된 게시글까지 리스트를 최신화
		$("#addbtn").on("click",function(){
			var gvo = {
				name : $("[name=name]").val(),
				password : $("[name=password]").val(),
				content :$("[name=content]").val()
			};
			
			if(gvo.name !="" && gvo.password!="" && gvo.content!=""){
				$.ajax({
					url : "${pageContext.request.contextPath}"+fetchUrl.write,
					type : "post",
					
					contentType : "application/json", //json 형태로 바디에 담에 보내겠다고 타입 지정
					data : JSON.stringify({gvo : gvo, maxNo : maxNo}),
					
					dataType : "json",
					success : function(listajax){ /*성공시 처리해야될 코드 작성*/
						if(listajax.length!=0){
							maxNo = listajax[0].no;
							for(var i=0; i<listajax.length; i++){
								render(listajax[i],"prepend");
							}
						}else{
							console.log("작성 게시물 추가 실패");
						}
					},
					
					error : function(XHR, status, error) { /*실패시 처리해야될 코드 작성*/
						console.error(status + " : " + error);
					}
				});
				$(".text").val("");
				$(".password").val("");
				$(".content").val("");
				
			}else{
				alert("게시글 입력 정보를 빠짐없이 모두 기입해주십시오");
			}
				
		});
				
		
		//게시글 삭제 버튼
		$("#gbtable").on("click",".modalopen" ,function(){
			var no = $(this).data("no");
			console.log(no);
			$("#modalgbNo").val(no); //modal의 텍스트 필드에 넘버값 삽입
			$("#del-pop").modal(); // modal 오픈
		});
		
		
		//modal 삭제 버튼을 이용한 실질적인 삭제
		$("#delbtn").on("click", function(){
			var no = $("#modalgbNo").val();
			var password = $("#modalPassword").val();
			$.ajax({
				url : "${pageContext.request.contextPath}"+fetchUrl.del,
				type : "post",
				
				data : {no : no , password : password},
				
				dataType : "json",
				success : function(result){ /*성공시 처리해야될 코드 작성*/
					if(result==1){
						$("#gb"+no).remove();
					}else{
						console.log("작성 게시물 삭제 실패");
					}
				},
				
				error : function(XHR, status, error) { /*실패시 처리해야될 코드 작성*/
					console.error(status + " : " + error);
				}
			});
			$("#modalgbNo").val("");
			$("#modalPassword").val("");
			$("#del-pop").modal("hide");
			
		});
		
		
		//패치 리스트 실행 결과 토대로 태그 추가
		function render(gb,type){
			var s = 
					"<li id='gb"+gb.no+"'>"+
						"<table>"+
							"<tr>"+
								"<td>["+gb.no+"]</td>"+
								"<td>"+gb.name+"</td>"+
								"<td>"+gb.regDate+"</td>"+
								"<td><input type='button' data-no='"+gb.no +"' class='modalopen' value='삭제'></td>"+
							"</tr>"+
							"<tr>"+
								"<td colspan=4>"+
								gb.content+
								"</td>"+
							"</tr>"+
						"</table>"+
					"</li>";
			
			if(type == "append"){
				$("#gbtable").append(s);
			}else{
				$("#gbtable").prepend(s);
			}
		}
	
	</script>


</html>