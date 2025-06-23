<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title><sitemesh:write property="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1" />

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<sitemesh:write property="head" />

<style>
html, body {
  height: 100%;
  margin: 0;
  display: flex;
  flex-direction: column;
  background: url('${path}/img/won.jpg') no-repeat center center fixed;
  background-size: cover;
}

body {
  padding-top: 56px;
}

#main-wrapper {
  flex: 1 0 auto;
  display: flex;
  flex-direction: row;
  overflow: hidden;
}

/* 네비게이션 바 */
.navbar {
  background-color: rgba(0, 0, 0, 0.6) !important;
}

/* 사이드바 */
.sidebar {
  width: 250px;
  position: fixed;
  top: 56px;
  bottom: 0;
  left: 0;
  background-color: rgba(248, 249, 250, 0.7);
  overflow-y: auto;
  z-index: 1000;
  transition: all 0.3s ease;
  padding-top: 1rem;
}

.sidebar.collapsed {
  width: 80px;
}

.sidebar .list-group-item {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.main-content {
  flex: 1;
  overflow-y: auto;
  padding: 2rem;
  margin-left: 250px;
  background-color: rgba(255,255,255,0.2);
  backdrop-filter: blur(6px);
  border-radius: 1rem;
  padding-bottom: 170px;
}

.sidebar.collapsed ~ .main-content {
  margin-left: 80px;
}

/* 투명 강제 적용 */
#mainContent * {
  background-color: transparent !important;
}

/* 푸터 고정 */
/* .footer {
  flex-shrink: 0;
  background-color: rgba(0, 0, 0, 0.6);
  padding: 2rem 0;
  color: white;
} 
*/
.footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 150px;  /* 높이 조절 (원하는 만큼 조절 가능) */
  background-color: rgba(0, 0, 0, 0.6); /* 반투명 배경 */
  color: white;
  padding: 0.75rem 0;  /* 위아래 패딩 줄임 */
  
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer .form-select {
  max-width: 180px;
  background-color: #495057;
  color: white;
  border-color: #6c757d;
}

.footer .form-select:focus {
  background-color: #495057;
  border-color: #adb5bd;
  box-shadow: 0 0 0 0.25rem rgba(173, 181, 189, 0.25);
}

.footer a {
  transition: transform 0.2s;
}
.footer a:hover {
  transform: scale(1.2);
}

#footer-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

#footer-info img {
  height: 20px;
  vertical-align: middle;
}

@media (max-width: 576px) {
  .footer .d-flex {
    flex-direction: column;
    gap: 0.5rem;
  }

  .footer .form-select {
    max-width: 100%;
  }
}

/* 채팅 버튼 */
#chat-button {
  position: fixed;
  bottom: 25px;
  right: 25px;
  width: 60px;
  height: 60px;
  font-size: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1050;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}
</style>
</head>
<body>
  <!-- 네비게이션 -->
  <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
    <div class="container-fluid">
      <button class="btn btn-outline-light me-2" id="toggleSidebar">☰</button>
      <a class="navbar-brand" href="/">하나도너츠</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item"><a class="nav-link" href="/user/login">홈</a></li>
          <li class="nav-item"><a class="nav-link" href="#">설정</a></li>
          <li class="nav-item"><a class="nav-link" href="/user/logout">로그아웃</a></li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- 메인 컨테이너 -->
  <div id="main-wrapper">
    <div id="sidebar" class="sidebar">
      <div class="list-group">
        <a href="/admin/dashboard" class="list-group-item list-group-item-action">📊 대시보드</a>
        <a href="/user/mypage?userid=${loginUser.userid}" class="list-group-item list-group-item-action">🧑‍💼 마이페이지</a>
        <c:if test="${loginUser.userid == 'admin'}">
          <a href="/admin/list" class="list-group-item list-group-item-action">👥 사용자 관리</a>
        </c:if>
        <a href="/board/list?boardid=1" class="list-group-item list-group-item-action">📌 공지사항</a>
        <a href="/board/list?boardid=2" class="list-group-item list-group-item-action">💬 자유게시판</a>
        <a href="/board/list?boardid=3" class="list-group-item list-group-item-action">❓ Q&A</a>
        <a href="/naver/search" class="list-group-item list-group-item-action">🔎 검색</a>
        <a href="#" class="list-group-item list-group-item-action">⚙️ 설정</a>
      </div>
    </div>

    <div id="mainContent" class="main-content">
      <sitemesh:write property="body" />
    </div>
  </div>

  <!-- 푸터 -->
  <footer class="footer">
    <div class="container">
      <div class="mb-3 text-center">
        <div class="d-flex flex-wrap gap-2 justify-content-center">
          <select name="si" class="form-select form-select-sm" onchange="getText('si')">
            <option value="">시도를 선택하세요</option>
          </select>
          <select name="gu" class="form-select form-select-sm" onchange="getText('gu')">
            <option value="">구군을 선택하세요</option>
          </select>
          <select name="dong" class="form-select form-select-sm">
            <option value="">동리를 선택하세요</option>
          </select>
        </div>
      </div>
      <div class="text-center mb-3">
        <p class="mb-0">
          <i class="fas fa-donut-bite me-2"></i>하나도너츠 | 대표:  | 고객센터: 1234-5678
        </p>
      </div>
      <hr class="bg-secondary my-3">
      <div class="row align-items-center">
        <div class="col-md-6 text-center text-md-start" id="footer-info">
          <p class="mb-0">© 2025 하나도너츠. 석범아 도넛 사와.</p>
        </div>
        <div class="col-md-6 text-center text-md-end">
          <a href="#" class="text-white mx-2"><i class="fab fa-facebook-f"></i></a>
          <a href="#" class="text-white mx-2"><i class="fab fa-twitter"></i></a>
          <a href="#" class="text-white mx-2"><i class="fab fa-instagram"></i></a>
        </div>
      </div>
    </div>
  </footer>

  <button type="button" id="chat-button" class="btn btn-primary rounded-circle">
    <i class="fas fa-comments"></i>
  </button>
  <div id="chat-box-container" class="position-fixed bottom-0 end-0 m-4" style="z-index:1060;"></div>

  <script>
    const toggleBtn = document.getElementById("toggleSidebar");
    const sidebar = document.getElementById("sidebar");
    const mainContent = document.getElementById("mainContent");

    toggleBtn.addEventListener("click", () => {
      sidebar.classList.toggle("collapsed");
      mainContent.classList.toggle("collapsed");
    });

    $(function() {
      getSido1();
      getLogo();

      let chatOpen = false;
      $("#chat-button").on("click", function(){
        if (!chatOpen) {
          $("#chat-box-container").load("/chat/chat");
        } else {
          $("#chat-box-container").empty();
        }
        chatOpen = !chatOpen;
      });
    });

    function getSido1() {
      $.ajax({
        url: "${path}/ajax/select1",
        success: function(data) {
          let arr = data.substring(data.indexOf('[')+1, data.indexOf(']')).split(",");
          $.each(arr, function(i, item) {
            $("select[name=si]").append(`<option>\${item}</option>`);
          });
        }
      });
    }

    function getText(name) {
      let city = $("select[name='si']").val();
      let gu = $("select[name='gu']").val();
      let disname;
      let toptext = "구군을 선택하세요.";
      let params = "";

      if (name == "si") {
        params = "si=" + city.trim();
        disname = "gu";
      } else if (name == "gu") {
        params = "si=" + city.trim() + "&gu=" + gu.trim();
        disname = "dong";
        toptext = "동리를 선택하세요.";
      } else {
        return;
      }

      $.ajax({
        url: "/ajax/select2",
        type: "post",
        data: params,
        success: function(arr) {
          $("select[name=" + disname + "] option").remove();
          $("select[name=" + disname + "]").append(`<option value=''>\${toptext}</option>`);
          $.each(arr, function(i, item) {
            $("select[name=" + disname + "]").append(`<option>\${item}</option>`);
          });
        }
      });
    }

    function getLogo() {
      $.ajax({
        url: "/ajax/logo",
        success: function(json) {
          var html = '<img src=' + json.logo + ' />';
          $("#footer-info").prepend(html);
        },
        error: function(e) {
          alert("로고 조회 오류: " + e.status);
        }
      });
    }
  </script>
</body>
</html>
