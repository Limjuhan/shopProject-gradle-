<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>인덱스 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body, html {
            height: 100%;
            margin: 0;
        }

        .bg-cover {
            background: url('img/won.jpg') no-repeat center center fixed;
            background-size: cover;
            height: 100%;
            position: relative;
        }

        .overlay {
            background-color: rgba(0, 0, 0, 0.6); /* 반투명 어두운 오버레이 */
            position: absolute;
            top: 0;
            bottom: 0;
            right: 0;
            left: 0;
        }

        .main-content {
            position: relative;
            z-index: 1;
        }

        .glass-card {
            background-color: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(8px);
            border-radius: 1rem;
            padding: 2rem;
            color: #fff;
            text-align: center;
        }

        h1 {
            font-weight: bold;
            font-size: 2.5rem;
        }
    </style>
</head>
<body>
    <div class="bg-cover">
        <div class="overlay"></div>
        <div class="container d-flex align-items-center justify-content-center main-content" style="height: 100vh;">
            <div class="glass-card">
                <h1>환영합니다!</h1>
                <p class="lead">멋진 하루 되세요 :)</p>
                <a href="/user/login" class="btn btn-light btn-lg mt-3">로그인 하러가기</a>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
