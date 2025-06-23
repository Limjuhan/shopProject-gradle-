<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>에러 발생</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: url('https://i.pinimg.com/736x/1b/ac/fc/1bacfc3f93684972becf9931d0a24874.jpg') no-repeat center center fixed;
            background-size: cover;
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            text-shadow: 1px 1px 3px rgba(0,0,0,0.7);
        }

        .error-card {
            background-color: rgba(0, 0, 0, 0.7);
            padding: 40px;
            border-radius: 15px;
            max-width: 600px;
            width: 100%;
            box-shadow: 0 0 20px rgba(0,0,0,0.6);
            text-align: center;
        }

        .error-card h1 {
            font-size: 3rem;
            margin-bottom: 20px;
        }

        .error-card p {
            font-size: 1.2rem;
            margin-bottom: 30px;
        }

        .btn-home {
            background-color: #ff5e57;
            border: none;
        }

        .btn-home:hover {
            background-color: #ff3f3f;
        }
    </style>
</head>
<body>
    <div class="error-card">
        <i class="bi bi-exclamation-triangle-fill display-1 text-danger mb-3"></i>
        <h1>문제가 발생했어요</h1>
        <p>죄송합니다. 요청하신 페이지를 처리하는 중에 오류가 발생했습니다.</p>
        <a href="/" class="btn btn-home btn-lg text-white">
            <i class="bi bi-house-door-fill"></i> 홈으로 돌아가기
        </a>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
