<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문완료</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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
        .card {
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            background-color: white;
        }
        .page-title {
            margin-bottom: 20px;
            font-weight: bold;
            color: #343a40;
        }
        .btn-custom {
            margin-right: 10px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 배송지 정보 -->
        <h2 class="page-title">${sale.user.username}님이 주문하신 정보 입니다.</h2>
        <h2 class="page-title">배송지 정보</h2>
        <div class="card p-4 mb-4">
            <table class="table table-borderless">
                <tr>
                    <th style="width: 30%;">주문아이디</th>
                    <td>${sessionScope.loginUser.userid}</td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>${sessionScope.loginUser.username}</td>
                </tr>
                <tr>
                    <th>우편번호</th>
                    <td>${sessionScope.loginUser.postcode}</td>
                </tr>
                <tr>
                    <th>주소</th>
                    <td>${sessionScope.loginUser.address}</td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>${sessionScope.loginUser.phoneno}</td>
                </tr>
            </table>
        </div>

        <!-- 구매 상품 -->
        <h2 class="page-title">주문 상품</h2>
        <div class="card p-4">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col" style="width: 40%;">상품명</th>
                            <th scope="col" style="width: 20%;">가격</th>
                            <th scope="col" style="width: 15%;">수량</th>
                            <th scope="col" style="width: 25%;">합계</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sale.itemList}" var="saleitem" varStatus="stat">
                            <tr>
                                <td>${saleitem.item.name}</td>
                                <td class="text-end"><fmt:formatNumber value="${saleitem.item.price}" pattern="#,###"/> 원</td>
                                <td class="text-center"><fmt:formatNumber value="${saleitem.quantity}" pattern="#,###"/></td>
                                <td class="text-end"><fmt:formatNumber value="${saleitem.item.price * saleitem.quantity}" pattern="#,###"/> 원</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="4" class="text-end fw-bold">
                                총 주문 금액: <fmt:formatNumber value="${sale.total}" pattern="#,###"/> 원
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
            <div class="d-flex justify-content-between mt-3">
                <a href="../item/list" class="btn btn-primary btn-custom">
                    <i class="bi bi-list-ul"></i> 상품 목록
                </a>
            </div>
        </div>
    </div>
    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>