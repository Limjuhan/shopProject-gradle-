<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>사용자 등록</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow">
        <div class="card-header bg-primary text-white">
          <h4 class="mb-0">사용자 등록</h4>
        </div>
        <div class="card-body">

          <form:form modelAttribute="user" method="post" action="join">

            <spring:hasBindErrors name="user">
              <div class="alert alert-danger">
                <c:forEach items="${errors.globalErrors}" var="error">
                  <spring:message code="${error.code}" /><br/>
                </c:forEach>
              </div>
            </spring:hasBindErrors>

            <div class="mb-3">
              <label class="form-label">아이디</label>
              <form:input path="userid" cssClass="form-control" />
              <form:errors path="userid" cssClass="text-danger" />
            </div>

            <div class="mb-3">
              <label class="form-label">비밀번호</label>
              <form:password path="password" cssClass="form-control" />
              <form:errors path="password" cssClass="text-danger" />
            </div>

            <div class="mb-3">
              <label class="form-label">이름</label>
              <form:input path="username" cssClass="form-control" />
              <form:errors path="username" cssClass="text-danger" />
            </div>

            <div class="mb-3">
              <label class="form-label">전화번호</label>
              <form:input path="phoneno" cssClass="form-control" />
            </div>

            <div class="mb-3">
              <label class="form-label">우편번호</label>
              <form:input path="postcode" cssClass="form-control" />
            </div>

            <div class="mb-3">
              <label class="form-label">주소</label>
              <form:input path="address" cssClass="form-control" />
            </div>

            <div class="mb-3">
              <label class="form-label">이메일</label>
              <form:input path="email" cssClass="form-control" />
              <form:errors path="email" cssClass="text-danger" />
            </div>

            <div class="mb-3">
              <label class="form-label">생년월일</label>
              <form:input path="birthday" type="date" cssClass="form-control" />
              <form:errors path="birthday" cssClass="text-danger" />
            </div>

            <div class="d-flex justify-content-end">
              <button type="submit" class="btn btn-primary me-2">회원가입</button>
              <button type="reset" class="btn btn-secondary">초기화</button>
            </div>

          </form:form>

        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>
