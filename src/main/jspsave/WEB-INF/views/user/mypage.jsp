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
    <title>마이페이지</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
            text-align: center;
        }
        .nav-tabs .nav-link {
            border-radius: 8px 8px 0 0;
        }
        .nav-tabs .nav-link.active {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }
        .nav-tabs .nav-link {
            color: #343a40;
        }
        .table-order {
            table-layout: fixed;
        }
        .table-order th, .table-order td {
            padding: 10px;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .table-order th:nth-child(1), .table-order td:nth-child(1) {
            width: 20%;
            min-width: 80px;
        }
        .table-order th:nth-child(2), .table-order td:nth-child(2) {
            width: 30%;
            min-width: 100px;
        }
        .table-order th:nth-child(3), .table-order td:nth-child(3) {
            width: 50%;
            min-width: 120px;
            padding-right: 15px;
        }
        .table-sub th, .table-sub td {
            padding: 8px;
        }
        .table-sub th:nth-child(1), .table-sub td:nth-child(1) {
            width: 40%;
            min-width: 120px;
        }
        .table-sub th:nth-child(2), .table-sub td:nth-child(2) {
            width: 20%;
            min-width: 80px;
        }
        .table-sub th:nth-child(3), .table-sub td:nth-child(3) {
            width: 15%;
            min-width: 60px;
        }
        .table-sub th:nth-child(4), .table-sub td:nth-child(4) {
            width: 25%;
            min-width: 100px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="page-title">마이페이지</h2>
        <!-- 탭 내비게이션 -->
        <ul class="nav nav-tabs mb-3">
            <li class="nav-item">
                <a class="nav-link active" id="tab1" href="javascript:disp_div('minfo','tab1')">회원정보</a>
            </li>
                <li class="nav-item">
                    <a class="nav-link" id="tab2" href="javascript:disp_div('oinfo','tab2')">주문정보</a>
                </li>
        </ul>

        <!-- 주문 정보 -->
        <div id="oinfo" class="info card p-4" style="display: none;">
            <div class="table-responsive">
                <table class="table table-hover table-order">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">주문번호</th>
                            <th scope="col">주문일자</th>
                            <th scope="col">주문금액</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${salelist}" var="sale" varStatus="stat">
                            <tr>
                                <td>
                                    <a href="javascript:list_disp('saleLine${stat.index}')" class="text-decoration-none text-primary">
                                        ${sale.saleid}
                                    </a>
                                </td>
                                <td>
                                    <fmt:formatDate value="${sale.saledate}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${sale.total}" pattern="#,###"/> 원
                                </td>
                            </tr>
                            <tr id="saleLine${stat.index}" class="saleLine" style="display: none;">
                                <td colspan="3">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-sub">
                                            <thead>
                                                <tr>
                                                    <th>상품명</th>
                                                    <th>상품가격</th>
                                                    <th>주문수량</th>
                                                    <th>상품총액</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${sale.itemList}" var="saleItem">
                                                    <tr>
                                                        <td>${saleItem.item.name}</td>
                                                        <td><fmt:formatNumber value="${saleItem.item.price}" pattern="#,###"/> 원</td>
                                                        <td><fmt:formatNumber value="${saleItem.quantity}" pattern="#,###"/></td>
                                                        <td><fmt:formatNumber value="${saleItem.item.price * saleItem.quantity}" pattern="#,###"/> 원</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- 회원 정보 -->
        <div id="minfo" class="info card p-4">
            <table class="table table-borderless">
                <tr>
                    <th style="width: 30%;">아이디</th>
                    <td>${user.userid}</td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <th>우편번호</th>
                    <td>${user.postcode}</td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>${user.phoneno}</td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <th>생년월일</th>
                    <td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td>
                </tr>
            </table>
            <div class="d-flex justify-content-between mt-3">
                <div>
                    <a href="logout" class="btn btn-outline-danger btn-custom">
                        <i class="bi bi-box-arrow-right"></i> 로그아웃
                    </a>
                    <a href="update?userid=${user.userid}" class="btn btn-outline-warning btn-custom">
                        <i class="bi bi-pencil"></i> 회원정보 수정
                    </a>
                    <a href="password" class="btn btn-outline-info btn-custom">
                        <i class="bi bi-key"></i> 비밀번호 수정
                    </a>
                    <c:if test="${loginUser.userid != 'admin'}">
                        <a href="delete?userid=${user.userid}" class="btn btn-outline-danger btn-custom" onclick="return confirm('정말 회원탈퇴 하시겠습니까?');">
                            <i class="bi bi-person-x"></i> 회원탈퇴
                        </a>
                    </c:if>
                    <c:if test="${loginUser.userid == 'admin'}">
                        <a href="../admin/list" class="btn btn-outline-primary btn-custom">
                            <i class="bi bi-person-lines-fill"></i> 회원목록
                        </a>
                    </c:if>
                </div>
                <a href="../item/list" class="btn btn-primary btn-custom">
                    <i class="bi bi-list-ul"></i> 상품 보기
                </a>
            </div>
        </div>
    </div>
    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(function () {
            $("#minfo").show();
            $("#oinfo").hide();
            $(".saleLine").each(function() {
                $(this).hide();
            });
            $("#tab1").addClass("nav-link active");
        });
        function disp_div(id, tab) {
            $(".info").each(function() {
                $(this).hide();
            });
            $(".nav-link").each(function() {
                $(this).removeClass("active");
            });
            $("#" + id).show();
            $("#" + tab).addClass("nav-link active");
        }
        function list_disp(id) {
            $("#" + id).toggle();
        }
    </script>
</body>
</html>