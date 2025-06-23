<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 목록</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 30px;
            margin-bottom: 30px;
        }
        .table {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        .table-item {
            table-layout: fixed;
        }
        .table th, .table td {
            vertical-align: middle;
            text-align: center;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .btn-custom {
            margin-right: 10px;
        }
        .page-title {
            margin-bottom: 20px;
            font-weight: bold;
            color: #343a40;
        }
        .table-item th:nth-child(1), .table-item td:nth-child(1) {
            width: 10%;
            min-width: 60px;
        }
        .table-item th:nth-child(2), .table-item td:nth-child(2) {
            width: 45%;
            min-width: 150px;
        }
        .table-item th:nth-child(3), .table-item td:nth-child(3) {
            width: 20%;
            min-width: 100px;
            padding-right: 15px;
        }
        .table-item th:nth-child(4), .table-item td:nth-child(4) {
            width: 12.5%;
            min-width: 80px;
        }
        .table-item th:nth-child(5), .table-item td:nth-child(5) {
            width: 12.5%;
            min-width: 80px;
        }
        .table-item .btn {
            width: 100%;
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="page-title">상품 목록</h2>
        <div class="d-flex justify-content-between mb-3">
            <div>
                <a href="create" class="btn btn-primary btn-custom">
                    <i class="bi bi-plus-circle"></i> 상품 등록
                </a>
            </div>
            <div class="d-flex gap-2">
                <a href="../user/mypage?userid=${sessionScope.loginUser.userid}" class="btn btn-primary btn-custom">
                    <i class="bi bi-person"></i> MyPage
                </a>
                <a href="../cart/cartView" class="btn btn-success btn-custom">
                    <i class="bi bi-cart"></i> 장바구니
                </a>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-item">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">상품ID</th>
                        <th scope="col">상품명</th>
                        <th scope="col">가격</th>
                        <th scope="col">수정</th>
                        <th scope="col">삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${itemList}" var="item">
                        <tr>
                            <td>${item.id}</td>
                            <td>
                                <a href="detail?id=${item.id}" class="text-decoration-none text-primary">
                                    ${item.name}
                                </a>
                            </td>
                            <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> 원</td>
                            <td>
                                <a href="update?id=${item.id}" class="btn btn-sm btn-outline-warning">
                                    <i class="bi bi-pencil"></i> 수정
                                </a>
                            </td>
                            <td>
                                <a href="delete?id=${item.id}" class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('정말 삭제하시겠습니까?');">
                                    <i class="bi bi-trash"></i> 삭제
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>