<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- /WEB-INF/views/board/delete.jsp --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <title>게시판 삭제 화면</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Bootstrap 5 JS (with Popper.js) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <!-- Custom CSS for additional styling -->
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
        }
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #007bff;
            color: white;
            border-radius: 10px 10px 0 0;
            text-align: center;
            font-size: 1.25rem;
            padding: 1rem;
        }
        .form-control {
            border-radius: 5px;
        }
        .btn-primary {
            width: 100%;
            padding: 10px;
            font-size: 1.1rem;
            border-radius: 5px;
        }
        .error-message {
            color: #dc3545;
            font-size: 0.9rem;
            margin-bottom: 1rem;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header">
                ${boardName} 글 삭제
            </div>
            <div class="card-body">
                <form action="delete" method="post" name="f">
                    <spring:hasBindErrors name="board">
                        <div class="error-message">
                            <c:forEach items="${errors.globalErrors}" var="error">
                                <spring:message code="${error.code}" /><br>
                            </c:forEach>
                        </div>
                    </spring:hasBindErrors>
                    <input type="hidden" name="num" value="${param.num}">
                    <input type="hidden" name="boardid" value="${param.boardid}">
                    <div class="mb-3">
                        <label for="pass" class="form-label">게시글 비밀번호</label>
                        <input type="password" name="pass" class="form-control" id="pass" placeholder="비밀번호를 입력하세요" required>
                    </div>
                    <button type="submit" class="btn btn-primary">게시글 삭제</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
