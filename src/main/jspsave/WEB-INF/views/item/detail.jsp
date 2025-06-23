<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 상세보기</title>
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
        }
        .page-title {
            margin-bottom: 20px;
            font-weight: bold;
            color: #343a40;
        }
        .btn-custom {
            margin-right: 10px;
        }
        .product-img {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .form-select-sm {
            width: auto;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="page-title">상품 상세보기</h2>
        <div class="card p-4">
            <div class="row g-4">
                <!-- 상품 이미지 -->
                <div class="col-md-6">
                    <img src="../img/${item.pictureUrl}" alt="${item.name}" class="product-img">
                </div>
                <!-- 상품 정보 -->
                <div class="col-md-6">
                    <table class="table table-borderless">
                        <tr>
                            <th style="width: 30%;">상품명</th>
                            <td>${item.name}</td>
                        </tr>
                        <tr>
                            <th>가격</th>
                            <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> 원</td>
                        </tr>
                        <tr>
                            <th>상품설명</th>
                            <td>${item.description}</td>
                        </tr>
                    </table>
                    <!-- 장바구니 폼 -->
                    <form action="../cart/cartAdd" method="post" onsubmit="return confirm('이 상품을 장바구니에 추가하시겠습니까?');">
                        <input type="hidden" name="id" value="${item.id}">
                        <div class="d-flex align-items-center mt-3">
                            <select name="quantity" class="form-select form-select-sm me-2">
                                <c:forEach begin="1" end="10" var="i">
                                    <option value="${i}">${i}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-success btn-custom">
                                <i class="bi bi-cart-plus"></i> 장바구니
                            </button>
                            <a href="list" class="btn btn-primary btn-custom">
                                <i class="bi bi-list-ul"></i> 상품 목록
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>