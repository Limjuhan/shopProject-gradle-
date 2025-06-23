<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원 목록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
<script type="text/javascript">
   function allchkbox(allChk) {
       document.querySelectorAll('.idchks').forEach(chk => {
           chk.checked = allChk.checked;
       });
   }
</script>
</head>
<body>

<div class="container my-5">
    <h2 class="mb-4"><i class="bi bi-people-fill"></i> 회원 목록</h2>

    <form action="mail">
        <div class="card">
            <div class="card-body">
                <table class="table table-hover align-middle text-center">
                    <thead class="table-dark">
                        <tr>
                            <th style="width: 5%;"><input class="form-check-input" type="checkbox" onchange="allchkbox(this)"></th>
                            <th style="width: 15%;">아이디</th>
                            <th style="width: 15%;">이름</th>
                            <th style="width: 20%;">이메일</th>
                            <th style="width: 15%;">전화번호</th>
                            <th style="width: 10%;">생년월일</th>
                            <th style="width: 20%;">관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="user">
                        <tr>
                            <td><input class="form-check-input idchks" type="checkbox" name="idchks" value="${user.userid}"></td>
                            <td>${user.userid}</td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>${user.phoneno}</td>
                            <td><fmt:formatDate value="${user.birthday}" pattern="yy-MM-dd" /></td>
                            <td>
                                <a href="../user/mypage?userid=${user.userid}" class="btn btn-sm btn-outline-secondary">정보</a>
                                <a href="../user/update?userid=${user.userid}" class="btn btn-sm btn-primary">수정</a>
                                <a href="../user/delete?userid=${user.userid}" class="btn btn-sm btn-danger">강제탈퇴</a>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="mt-3 text-end">
            <button type="submit" class="btn btn-success"><i class="bi bi-envelope-fill"></i> 선택 회원에게 메일 보내기</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
