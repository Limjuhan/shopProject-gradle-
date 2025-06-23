<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${boardName}</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <style>
        body {
            padding-top: 20px;
            padding-bottom: 20px;
        }
        .container {
            max-width: 960px;
        }
        .pagination-container {
            display: flex;
            justify-content: center;
            margin-top: 1.5rem;
        }

        .search-form-row .form-control,
        .search-form-row .form-select,
        .search-form-row .btn {
            margin-bottom: 0.5rem;
        }

        .btn-group.w-100-sm {
            width: 100%;
        }
        @media (min-width: 768px) {
            .search-form-row .form-control,
            .search-form-row .form-select,
            .search-form-row .btn {
                margin-bottom: 0;
            }
            .btn-group.w-100-sm {
                width: auto;
            }
            .search-form-row > .col-auto:first-child {
                margin-right: 0.5rem;
            }
            .search-form-row > .col-auto.flex-grow-1 {
                margin-right: 0.5rem;
            }
            .search-form-row > .col-auto:last-child {
                margin-right: 0;
            }
        }

        .table th:first-child,
        .table td:first-child {
            width: 50px;
            min-width: 50px;
            text-align: center;
        }
        /* 공지사항 제목에 추가적인 상단 마진을 부여 */
        /* .board-title {
            margin-top: 1rem; // 예를 들어 1rem 더 내리고 싶을 때
        } */
    </style>
    <script type="text/javascript">
        function listpage(page) {
            document.searchform.pageNum.value = page;
            document.searchform.submit();
        }
    </script>
</head>
<body>
    <div class="container">
        <h2 class="mb-4 mt-4">${boardName}</h2>

        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <form action="list" method="post" name="searchform" class="row g-2 align-items-center flex-nowrap search-form-row">
                    <input type="hidden" name="pageNum" value="1">
                    <input type="hidden" name="boardid" value="${param.boardid}">

                    <div class="col-auto col-md-3">
                        <label for="searchType" class="visually-hidden">검색 유형</label>
                        <select id="searchType" name="searchtype" class="form-select form-select-sm">
                            <option value="">선택하세요</option>
                            <option value="title">제목</option>
                            <option value="writer">작성자</option>
                            <option value="content">내용</option>
                        </select>
                        <script type="text/javascript">
                            document.searchform.searchtype.value = "${param.searchtype}";
                        </script>
                    </div>

                    <div class="col-auto flex-grow-1">
                        <label for="searchContent" class="visually-hidden">검색 내용</label>
                        <input type="text" id="searchContent" name="searchcontent"
                               value="${param.searchcontent}" class="form-control form-control-sm">
                    </div>

                    <div class="col-auto">
                        <div class="btn-group w-100-sm" role="group">
                            <button type="submit" class="btn btn-primary btn-sm">검색</button>
                            <button type="button" class="btn btn-secondary btn-sm"
                                    onclick="location.href='list?boardid=${param.boardid}'">전체게시물보기</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${listcount > 0}">
            <div class="d-flex justify-content-end mb-2">
                <span class="text-muted">글 개수: ${listcount}</span>
            </div>

            <div class="table-responsive">
                <table class="table table-hover table-striped border">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col" class="text-center">번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">글쓴이</th>
                            <th scope="col">날짜</th>
                            <th scope="col" class="text-center">조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="board" items="${boardlist}">
                            <tr>
                                <td class="text-center">${boardno}</td>
                                <c:set var="boardno" value="${boardno - 1}" />
                                <td>
                                    <c:if test="${! empty board.fileurl}">
                                        <a href="file/${board.fileurl}" class="text-decoration-none me-1" title="첨부파일">
                                            <i class="bi bi-paperclip"></i>
                                        </a>
                                    </c:if>
                                    <c:if test="${empty board.fileurl}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
                                    <c:forEach begin="1" end="${board.grplevel}">&nbsp;&nbsp;</c:forEach>
                                    <c:if test="${board.grplevel > 0}">└</c:if>
                                    <a href="detail?num=${board.num}" class="text-decoration-none">${board.title}</a>
                                </td>
                                <td>${board.writer}</td>
                                <td>
                                    <fmt:formatDate value="${board.regdate}" pattern="yyyyMMdd" var="rdate" />
                                    <c:if test="${today == rdate}">
                                        <fmt:formatDate value="${board.regdate}" pattern="HH:mm:ss" />
                                    </c:if>
                                    <c:if test="${today != rdate}">
                                        <fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm" />
                                    </c:if>
                                </td>
                                <td class="text-center">${board.readcnt}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="pagination-container">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li class="page-item <c:if test="${pageNum <= 1}">disabled</c:if>">
                            <a class="page-link" href="javascript:listpage('${pageNum - 1}')" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach var="a" begin="${startpage}" end="${endpage}">
                            <li class="page-item <c:if test="${a == pageNum}">active</c:if>">
                                <a class="page-link" href="javascript:listpage('${a}')">${a}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item <c:if test="${pageNum >= maxpage}">disabled</c:if>">
                            <a class="page-link" href="javascript:listpage('${pageNum + 1}')" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </c:if>

        <c:if test="${listcount == 0}">
            <div class="alert alert-info text-center" role="alert">
                등록된 게시물이 없습니다.
            </div>
        </c:if>

        <div class="d-flex justify-content-end mt-3">
            <a href="write?boardid=${param.boardid}" class="btn btn-success">글쓰기</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>