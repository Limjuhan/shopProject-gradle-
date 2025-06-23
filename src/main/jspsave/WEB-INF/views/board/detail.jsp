<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세보기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
   <div class="card shadow-sm">
      <div class="card-header bg-primary text-white">
         <h4 class="mb-0">${boardName}</h4>
      </div>
      <div class="card-body">
         <table class="table table-bordered">
            <tr>
               <th class="w-25">글쓴이</th>
               <td>${board.writer}</td>
            </tr>
            <tr>
               <th>제목</th>
               <td>${board.title}</td>
            </tr>
            <tr>
               <th>내용</th>
               <td><div class="bg-light p-3 rounded">${board.content}</div></td>
            </tr>
            <tr>
               <th>첨부파일</th>
               <td>
                  <c:if test="${!empty board.fileurl}">
                     <a href="file/${board.fileurl}" class="text-decoration-underline text-primary">${board.fileurl}</a>
                  </c:if>
               </td>
            </tr>
         </table>

         <div class="text-center mt-4">
            <a href="reply?num=${board.num}&boardid=${board.boardid}" class="btn btn-outline-primary me-2">답변</a>
            <a href="update?num=${board.num}&boardid=${board.boardid}" class="btn btn-outline-success me-2">수정</a>
            <a href="delete?num=${board.num}&boardid=${board.boardid}" class="btn btn-outline-danger me-2">삭제</a>
            <a href="list?boardid=${board.boardid}" class="btn btn-outline-secondary">게시물 목록</a>
         </div>
		
		<%-- /detail?num=1#comment
			#comment: id속성값이 comment 위치로 이동하여 화면이 보여줌 
		 --%>		
         <!-- 댓글 등록 -->
         <hr class="my-4">
         <h5 class="mb-3">댓글 작성</h5>
         <form action="comment" method="post" class="row g-3">
            <input type="hidden" name="num" value="${param.num}">
            
            <div class="col-md-3">
               <input type="text" name="writer" class="form-control" placeholder="작성자">
               <form:errors path="writer" cssClass="text-danger small"/>
            </div>
            <div class="col-md-3">
               <input type="password" name="pass" class="form-control" placeholder="비밀번호">
               <form:errors path="pass" cssClass="text-danger small"/>
            </div>
            <div class="col-md-4">
               <input type="text" name="content" class="form-control" placeholder="댓글 내용">
               <form:errors path="content" cssClass="text-danger small"/>
            </div>
            <div class="col-md-2">
               <button type="submit" class="btn btn-primary w-100">등록</button>
            </div>
         </form>

         <!-- 댓글 목록 -->
         <hr class="my-4">
         <h5 class="mb-3">댓글 목록</h5>
         <table class="table table-sm table-hover align-middle">
            <thead class="table-light">
               <tr>
                  <th>번호</th>
                  <th>작성자</th>
                  <th>내용</th>
                  <th>등록일</th>
                  <th>삭제</th>
               </tr>
            </thead>
            <tbody>
               <c:forEach var="c" items="${commlist}" varStatus="stat">
                  <tr>
                     <td>${c.seq}</td>
                     <td>${c.writer}</td>
                     <td>${c.content}</td>
                     <td><fmt:formatDate value="${c.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
                     <td>
                     	<%-- name=commdel${stat.index} --%>
                        <form action="commdel" method="post" class="d-flex align-items-center">
                           <input type="hidden" name="num" value="${c.num}">
                           <input type="hidden" name="seq" value="${c.seq}">
                           <input type="password" name="pass" class="form-control form-control-sm me-2" placeholder="비밀번호">
                           <button type="submit" class="btn btn-sm btn-danger">삭제</button>
                        </form>
                     </td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
      </div>
   </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
