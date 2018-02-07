<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="navigation">
			<ul>
				<c:choose>
					<c:when test="${!empty sessionScope.authUser }">
						<li><a href="${pageContext.request.contextPath}/main">${sessionScope.authUser.name }</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/main">&nbsp;</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="${pageContext.request.contextPath}/gb/list">방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/gb/listajax?min=0&max=10">방명록(ajax)</a></li>
				<li><a href="${pageContext.request.contextPath}/board/list">게시판</a></li>
			</ul>
		</div> <!-- /navigation -->