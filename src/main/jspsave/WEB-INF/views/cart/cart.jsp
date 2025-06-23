<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>장바구니</title>
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
        .table {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .btn-custom {
            margin-right: 10px;
        }
        .page-title {
            margin-bottom: 20px;
            font-weight: bold;
            color: #343a40;
        }
        .alert {
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="page-title">장바구니</h2>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th colspan="4" scope="col" class="text-center">장바구니 상품 목록</th>
                    </tr>
                    <tr>
                        <th scope="col" style="width: 40%;">상품명</th>
                        <th scope="col" style="width: 20%;">가격</th>
                        <th scope="col" style="width: 15%;">수량</th>
                        <th scope="col" style="width: 25%;">합계</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cart.itemSetList}" var="set" varStatus="stat">
                        <tr>
                            <td>${set.item.name}</td>
                            <td class="text-end"><fmt:formatNumber value="${set.item.price}" pattern="#,###"/> 원</td>
                            <td class="text-center"><fmt:formatNumber value="${set.quantity}" pattern="#,###"/></td>
                            <td class="text-end">
                                <fmt:formatNumber value="${set.quantity * set.item.price}" pattern="#,###"/> 원
                                <a href="cartDelete?index=${stat.index}" 
                                   class="btn btn-sm btn-outline-danger ms-2" 
                                   onclick="return confirm('이 상품을 장바구니에서 삭제하시겠습니까?');"
                                   aria-label="상품 삭제">
                                    <i class="bi bi-trash"></i> 삭제
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4" class="text-end fw-bold">
                            총 구매 금액: <fmt:formatNumber value="${cart.total}" pattern="#,###"/> 원
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <c:if test="${not empty message}">
            <div class="alert alert-info" role="alert">
                ${message}
            </div>
        </c:if>
        <div class="d-flex justify-content-between mt-3">
            <a href="../item/list" class="btn btn-primary btn-custom">
                <i class="bi bi-list-ul"></i> 상품 목록
            </a>
            <a href="checkout" class="btn btn-success btn-custom">
                <i class="bi bi-credit-card"></i> 주문하기
            </a>
        </div>
    </div>
    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>