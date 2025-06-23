<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>상품 삭제 전 확인</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <style>
    .product-img {
      max-width: 250px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
  </style>
</head>
<body class="bg-light">

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow">
        <div class="card-header bg-danger text-white">
          <h4 class="mb-0">⚠️ 상품 삭제 전 확인</h4>
        </div>
        <div class="card-body d-flex gap-4">
          <img class="product-img" src="${pageContext.request.contextPath}/img/${item.pictureUrl}" alt="상품 이미지" />
          <div class="flex-grow-1">
            <table class="table table-borderless">
              <tr>
                <th>상품명</th>
                <td>${item.name}</td>
              </tr>
              <tr>
                <th>가격</th>
                <td><fmt:formatNumber value="${item.price}" type="number" /> 원</td>
              </tr>
              <tr>
                <th>설명</th>
                <td>${item.description}</td>
              </tr>
              <tr>
                <td colspan="2">
                  <form action="delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                    <input type="hidden" name="id" value="${item.id}" />
                    <button type="submit" class="btn btn-danger me-2">상품 삭제</button>
                    <button type="button" class="btn btn-secondary" onclick="location.href='list'">상품 목록</button>
                  </form>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>
