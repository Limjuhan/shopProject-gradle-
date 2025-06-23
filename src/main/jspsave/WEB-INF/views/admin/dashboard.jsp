<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>대시보드</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
<style>
body {
    padding-top: 20px;
}

/* 카드 스타일 */
.card {
    border-radius: 0.5rem;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 라디오 버튼 스타일 */
.form-check-inline {
    margin-right: 1rem;
}

/* 차트 컨테이너 */
#piecontainer, #barcontainer {
    width: 100%;
}

/* 환율 컨테이너 */
.exchange-container {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
}

#exchange {
    width: 100%;
    font-size: 1rem;
    line-height: 1.4;
    word-wrap: break-word;
    overflow-x: hidden;
    box-sizing: border-box;
}

#exchange table {
    width: 100%;
    table-layout: fixed;
    border-collapse: collapse;
}

#exchange table td, #exchange table th {
    padding: 4px;
    font-size: 0.9rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

/* 반응형 조정 */
@media (max-width: 576px) {
    .form-check-inline {
        margin-bottom: 0.5rem;
    }

    #exchange table td, #exchange table th {
        font-size: 0.8rem;
    }
}
</style>
</head>
<body>

<div class="container">
    <!-- 차트 섹션 -->
    <div class="row g-3 mb-4">
        <!-- 파이 차트 -->
        <div class="col-md-6">
            <div class="card p-3 text-center">
                <div class="d-flex justify-content-center mb-3">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="pie" id="pieFree" value="2" onchange="piegraph(2)" checked>
                        <label class="form-check-label" for="pieFree">자유게시판</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="pie" id="pieQnA" value="3" onchange="piegraph(3)">
                        <label class="form-check-label" for="pieQnA">QnA</label>
                    </div>
                </div>
                <div id="piecontainer"></div>
            </div>
        </div>
        <!-- 바 차트 -->
        <div class="col-md-6">
            <div class="card p-3 text-center">
                <div class="d-flex justify-content-center mb-3">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="barline" id="barFree" value="2" onchange="barlinegraph(2)" checked>
                        <label class="form-check-label" for="barFree">자유게시판</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="barline" id="barQnA" value="3" onchange="barlinegraph(3)">
                        <label class="form-check-label" for="barQnA">QnA</label>
                    </div>
                </div>
                <div id="barcontainer"></div>
            </div>
        </div>
    </div>
    <!-- 환율 정보 섹션 -->
    <div class="card p-3 mb-4">
        <div class="exchange-container">
            <div id="exchange"></div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"></script>
<script type="text/javascript">
	$(function() {
	    exchangeRate2();
	    piegraph(2); // 글쓴이별 게시글 건수를 파이그래프로 출력.
	    barlinegraph(2);
	});
	
	let randomColorFactor = function() {
	    return Math.round(Math.random() * 255);
	};
	
	let randomColor = function(opa) {
	    return "rgba(" + randomColorFactor() + "," + randomColorFactor() + "," + randomColorFactor() + "," + (opa || '.3') + ")";
	};
	
	function piegraph(id) {
	    $.ajax("/ajax/graph1?id=" + id, {
	        success: function(json) {
	            let canvas = '<canvas id="canvas1" style="width:100%"></canvas>';
	            $("#piecontainer").html(canvas);
	            peiGraphPrint(json, id);
	        },
	        error: function(e) {
	            alert("서버오류: " + e.status);
	        }
	    });
	}
	
	function peiGraphPrint(arr, id) {
	    let colors = [];
	    let writers = [];
	    let datas = [];
	    
	    $.each(arr, function(index) {
	        colors[index] = randomColor(0.5);
	        for (key in arr[index]) {
	            writers.push(key);
	            datas.push(arr[index][key]);
	        }
	    });
	
	    let title = (id == 2) ? "자유게시판" : "QnA";
	    let config = {
	        type: 'pie',
	        data: {
	            datasets: [{
	                data: datas,
	                backgroundColor: colors
	            }],
	            labels: writers
	        },
	        options: {
	            responsive: true,
	            legend: { display: true, position: "right" },
	            title: {
	                display: true,
	                text: '글쓴이 별 ' + title + " 등록건수",
	                position: 'bottom'
	            }
	        }
	    };
	    
	    let ctx = document.getElementById("canvas1").getContext('2d');
	    new Chart(ctx, config);
	}

	function barlinegraph(id){
	      $.ajax("/ajax/graph2?id="+id,{
	         success : function(arr) {
		        console.log('막대그래프 정보:', arr);
	            let canvas = "<canvas id='canvas2' style='width:100%'></canvas>"
	            $("#barcontainer").html(canvas)
	            barlinegraphPrint(arr,id)
	         },
	         error : function(e) {
	            alert("서버오류 : " + e.status)
	         }
	      })
	    }

	 function barlinegraphPrint(arr,id) {
	      let colors = []
	      let regdates = []
	      let datas = []
	      $.each(arr,function(index) {
	         colors[index] = randomColor(0.5)
	         for(key in arr[index]) {
	            regdates.push(key)
	            datas.push(arr[index][key])
	         }
	      })
	      let title = (id == 2) ? "자유게시판" : "Q&A"
	      let config = {
	         type : 'bar',
	         data : {
	            datasets : [
	               {
	                  type : "line",
	                  borderWidth : 2,
	                  borderColor : colors,
	                  label : '건수',
	                  fill : false,
	                  data : datas
	               },
	               {
	                  type : "bar",
	                  backgroundColor : colors,
	                  label : '건수',
	                  data : datas
	               }
	            ],
	            labels : regdates,
	         },
	         options : {
	            responsive : true,
	            legend : {
	               display : false
	            },
	            title : {
	               display : true,
	               text : '최근 7일 ' + title +" 등록 건수",
	               position : 'bottom'
	            },
	            scales : {
	               xAxes : [{
	                  display : true,
	                  scaleLabel : {
	                     display : true,
	                     labelString : "작성일자"
	                  }
	               }],
	               yAxes : [{
	                  scaleLabel : {
	                     display : true,
	                     labelString : "게시물 등록 건수"
	                  },
	                  ticks : {beginAtZero : true}
	               }]
	            }
	         }
	      }
	      let ctx = document.getElementById("canvas2")
	      new Chart(ctx,config)
	    }
	    
	// 환율정보
	function exchangeRate2() {
	    $.ajax({
	        url: "/ajax/exchange2",
	        success: function(json) {
	            console.log(json);
	            var html = '<h4 class="text-center mb-3">수출입은행<br>' + json.exdate + '</h4>';
	            html += '<table class="table table-bordered table-sm me-2">';
	            html += '<thead><tr>';
	            html += '<th>통화</th>';
	            html += '<th>기준율</th>';
	            html += '<th>받으실때</th>';
	            html += '<th>보내실때</th>';
	            html += '</tr></thead>';
	            html += '<tbody>';
	            $.each(json.trlist, function(i, tds) {
	                html += '<tr>';
	                html += '<td>' + tds[0] + '<br>' + tds[1] + '</td>';
	                html += '<td>' + tds[4] + '</td>';
	                html += '<td>' + tds[2] + '</td>';
	                html += '<td>' + tds[3] + '</td>';
	                html += '</tr>';
	            });
	            html += '</tbody>';
	            html += '</table>';
	            $("#exchange").html(html);
	        },
	        error: function(e) {
	            alert("환율 조회 오류: " + e.status);
	        }
	    });
	}

	
</script>
</body>
</html>