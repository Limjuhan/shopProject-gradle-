<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>네이버 검색</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container mt-4">
    <form id="searchForm" onsubmit="event.preventDefault(); naversearch(1);" class="mb-3">
        <div class="row g-3">
            <div class="col-md-3">
                <label for="type" class="form-label">검색 유형</label>
                <select id="type" class="form-select" aria-label="검색 유형 선택">
                    <option value="blog">블로그</option>
                    <option value="news">뉴스</option>
                    <option value="book">책</option>
                    <option value="encyc">백과사전</option>
                    <option value="cafearticle">카페글</option>
                    <option value="kin">지식인</option>
                    <option value="local">지역</option>
                    <option value="webkr">웹문서</option>
                    <option value="image">이미지</option>
                    <option value="shop">쇼핑</option>
                    <option value="doc">전문자료</option>
                </select>
            </div>
            <div class="col-md-3">
                <label for="display" class="form-label">페이지당 결과 수</label>
                <select id="display" class="form-select" aria-label="페이지당 결과 수 선택">
                    <option>10</option>
                    <option>20</option>
                    <option>50</option>
                </select>
            </div>
            <div class="col-md-4">
                <label for="data" class="form-label">검색어</label>
                <input type="text" id="data" class="form-control" placeholder="제시어" aria-label="검색어 입력">
            </div>
            <div class="col-md-2 d-flex align-items-end">
                <button type="submit" class="btn btn-primary w-100">검색</button>
            </div>
        </div>
    </form>
    <div id="result" tabindex="-1"></div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    
    <script type="text/javascript">
    
        var $searchForm = $("#searchForm");
        var $type = $("#type");
        var $display = $("#display");
        var $data = $("#data");
        var $result = $("#result");

        function renderPagination(start, maxpage) {
            var startpage = Math.floor((start - 1) / 10) * 10 + 1;
            var endpage = Math.min(startpage + 9, maxpage);
            var html = '<nav aria-label="페이지 내비게이션"><ul class="pagination justify-content-center">';
            if (start > 1) {
                html += '<li class="page-item"><a class="page-link" href="javascript:naversearch(' + (start - 1) + ')" aria-label="이전 페이지">이전</a></li>';
            }
            for (var i = startpage; i <= endpage; i++) {
                html += '<li class="page-item' + (i === start ? ' active' : '') + '"><a class="page-link" href="javascript:naversearch(' + i + ')" aria-label="페이지 ' + i + '">' + i + '</a></li>';
            }
            if (maxpage > start) {
                html += '<li class="page-item"><a class="page-link" href="javascript:naversearch(' + (start + 1) + ')" aria-label="다음 페이지">다음</a></li>';
            }
            html += '</ul></nav>';
            return html;
        }

        function naversearch(start) {
            if (!$data.val().trim()) {
                $result.html('<div class="alert alert-warning" role="alert">검색어를 입력해주세요.</div>');
                return;
            }

            $.ajax({
                type: 'post',
                url: 'naversearch',
                data: {
                    data: $data.val(),
                    display: $display.val(),
                    start: start,
                    type: $type.val()
                },
                success: function(json) {
                    if (!json.total || !json.items) {
                        $result.html('<div class="alert alert-info" role="alert">검색 결과가 없습니다.</div>');
                        return;
                    }

                    var total = json.total;
                    var num = (start - 1) * parseInt($display.val()) + 1;
                    var maxpage = Math.ceil(total / parseInt($display.val()));
                    var html = '<div class="table-responsive">' +
                        '<table class="table table-striped table-hover" aria-label="검색 결과 테이블">' +
                        '<thead>' +
                        '<tr>' +
                        '<th colspan="4" class="text-center">' +
                        '전체 조회 건수: ' + total + ', 현재 페이지: ' + start + '/' + maxpage +
                        '</th>' +
                        '</tr>' +
                        '<tr>' +
                        '<th scope="col">번호</th>' +
                        '<th scope="col">제목</th>' +
                        '<th scope="col">링크</th>' +
                        '<th scope="col">설명</th>' +
                        '</tr>' +
                        '</thead>' +
                        '<tbody>';

                    $.each(json.items, function(i, item) {
                        html += '<tr>' +
                            '<td>' + num + '</td>' +
                            '<td>' + item.title + '</td>' +
                            '<td>';
                        if ($type.val() === 'image') {
                            html += '<a href="' + item.link + '"><img src="' + item.thumbnail + '" class="img-fluid" style="max-width: 100px;" alt="검색 이미지"></a>';
                        } else {
                            html += '<a href="' + item.link + '">' + item.link + '</a>';
                        }
                        html += '</td>' +
                            '<td>' + ($type.val() === 'image' ? '' : item.description) + '</td>' +
                            '</tr>';
                        num++;
                    });

                    html += '</tbody>' +
                        '<tfoot>' +
                        '<tr>' +
                        '<td colspan="4">' + renderPagination(start, maxpage) + '</td>' +
                        '</tr>' +
                        '</tfoot>' +
                        '</table></div>';

                    $result.html(html).focus();
                },
                error: function(xhr, status, error) {
                    $result.html('<div class="alert alert-danger" role="alert">검색 중 오류가 발생했습니다: ' + error + '</div>');
                }
            });
        }
    </script>
</body>
</html>