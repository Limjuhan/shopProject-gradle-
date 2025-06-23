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
    <title>로그인</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            height: 100vh;
            background: url('/img/won.jpg') no-repeat center center fixed;
            background-size: cover;
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.4); /* 어두운 반투명 배경 */
            z-index: 0;
        }

        .container {
            position: relative;
            z-index: 1;
            max-width: 500px;
        }

        .card {
            border-radius: 15px;
            background: rgba(255, 255, 255, 0.15);
            box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            color: white;
            border: 1px solid rgba(255, 255, 255, 0.18);
        }

        .page-title {
            margin-bottom: 20px;
            font-weight: bold;
            color: white;
        }

        .btn-custom {
            margin-right: 10px;
        }

        .form-label {
            font-weight: 500;
            color: #f1f1f1;
        }

        .form-control {
            background-color: rgba(255, 255, 255, 0.1);
            color: white;
            border: 1px solid #ccc;
        }

        .form-control::placeholder {
            color: #ddd;
        }

        .error-message {
            color: #ffc9c9;
            font-size: 0.875rem;
        }

        .link-custom {
            font-size: 0.9rem;
            margin-left: 15px;
            color: #cfe2ff;
        }

        .link-custom:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card p-4">
            <h2 class="page-title text-center">사용자 로그인</h2>
            <form:form modelAttribute="user" method="post" action="login" name="loginform">
                <spring:hasBindErrors name="user">
                    <div class="alert alert-danger error-message" role="alert">
                        <c:forEach items="${errors.globalErrors}" var="error">
                            <spring:message code="${error.code}"/><br>
                        </c:forEach>
                    </div>
                </spring:hasBindErrors>

                <div class="mb-3">
                    <label for="userid" class="form-label"><i class="bi bi-person"></i> 아이디</label>
                    <form:input path="userid" class="form-control" id="userid" placeholder="아이디를 입력하세요"/>
                    <form:errors path="userid" cssClass="error-message"/>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label"><i class="bi bi-lock"></i> 비밀번호</label>
                    <form:password path="password" class="form-control" id="password" placeholder="비밀번호를 입력하세요"/>
                    <form:errors path="password" cssClass="error-message"/>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary btn-custom">
                        <i class="bi bi-box-arrow-in-right"></i> 로그인
                    </button>
                    <a href="join" class="btn btn-success btn-custom">
                        <i class="bi bi-person-plus"></i> 회원가입
                    </a>
                </div>

                <div class="text-center mt-2">
                    <a href="javascript:win_open('idsearch')" class="link-custom">
                        <i class="bi bi-search"></i> 아이디찾기
                    </a>
                    <a href="javascript:win_open('pwsearch')" class="link-custom">
                        <i class="bi bi-key"></i> 비밀번호찾기
                    </a>
                </div>
            </form:form>
        </div>
    </div>

    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

    <script type="text/javascript">
        function win_open(page) {
            var op = "width=500, height=350, left=50, top=150";
            window.open(page, "", op);
        }
    </script>
</body>
</html>
