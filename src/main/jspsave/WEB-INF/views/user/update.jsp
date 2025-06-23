<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container mt-4 mb-5">
  <h2 class="mb-4 text-center">사용자 정보 수정</h2>
  
  <form:form modelAttribute="user" method="post" action="update" class="needs-validation" novalidate="novalidate">
    
    <spring:hasBindErrors name="user">
      <div class="alert alert-danger">
        <c:forEach items="${errors.globalErrors}" var="error">
          <div><spring:message code="${error.code}" /></div>
        </c:forEach>
      </div>
    </spring:hasBindErrors>
    
    <div class="row mb-3">
      <label for="userid" class="col-sm-2 col-form-label">아이디</label>
      <div class="col-sm-10">
        <form:input path="userid" id="userid" readonly="true" class="form-control-plaintext" />
        <form:errors path="userid" cssClass="text-danger small" />
      </div>
    </div>
    
    <div class="row mb-3">
      <label for="password" class="col-sm-2 col-form-label">비밀번호</label>
      <div class="col-sm-10">
        <form:password path="password" id="password" class="form-control" placeholder="비밀번호를 입력하세요" />
        <form:errors path="password" cssClass="text-danger small" />
      </div>
    </div>
    
    <div class="row mb-3">
      <label for="username" class="col-sm-2 col-form-label">이름</label>
      <div class="col-sm-10">
        <form:input path="username" id="username" class="form-control" placeholder="이름을 입력하세요" />
        <form:errors path="username" cssClass="text-danger small" />
      </div>
    </div>
    
    <div class="row mb-3">
      <label for="phoneno" class="col-sm-2 col-form-label">전화번호</label>
      <div class="col-sm-10">
        <form:input path="phoneno" id="phoneno" class="form-control" placeholder="전화번호를 입력하세요" />
      </div>
    </div>
    
    <div class="row mb-3">
      <label for="postcode" class="col-sm-2 col-form-label">우편번호</label>
      <div class="col-sm-10">
        <form:input path="postcode" id="postcode" class="form-control" placeholder="우편번호를 입력하세요" />
      </div>
    </div>
    
    <div class="row mb-3">
      <label for="address" class="col-sm-2 col-form-label">주소</label>
      <div class="col-sm-10">
        <form:input path="address" id="address" class="form-control" placeholder="주소를 입력하세요" />
      </div>
    </div>
    
    <div class="row mb-3">
      <label for="email" class="col-sm-2 col-form-label">이메일</label>
      <div class="col-sm-10">
        <form:input path="email" id="email" class="form-control" placeholder="이메일을 입력하세요" />
        <form:errors path="email" cssClass="text-danger small" />
      </div>
    </div>
    
    <div class="row mb-3">
      <label for="birthday" class="col-sm-2 col-form-label">생년월일</label>
      <div class="col-sm-10">
        <form:input path="birthday" id="birthday" class="form-control" placeholder="YYYY-MM-DD" />
        <form:errors path="birthday" cssClass="text-danger small" />
      </div>
    </div>
    
    <div class="text-center mt-4">
      <button type="submit" class="btn btn-primary me-2">회원수정</button>
      <button type="reset" class="btn btn-secondary">초기화</button>
    </div>
  </form:form>
</div>
