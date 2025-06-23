<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="card" style="width: 350px; height: 450px;">
  <div class="card-header d-flex justify-content-between align-items-center bg-primary text-white">
    <span>💬 실시간 채팅</span>
    <button class="btn btn-sm btn-light" onclick="$('#chat-box-container').empty();">✖</button>
  </div>
  <div class="card-body d-flex flex-column">
    <textarea name="chatMsg" class="form-control mb-2" rows="10" readonly></textarea>
    <input type="text" name="chatInput" class="form-control" placeholder="메시지를 입력하세요..." />
  </div>
</div>

<script>
$(function(){
   let ws = new WebSocket("ws://localhost:8080/chatting");

   ws.onopen = function(){
      $("input[name=chatInput]").on("keydown", function(evt){
         if(evt.keyCode == 13){
            let msg = $(this).val();
            ws.send(msg);
            $(this).val("");
         }
      });
   };

   ws.onmessage = function(event){
      let area = $("textarea[name=chatMsg]");
      area.val(event.data + "\n" + area.val());
   };

   ws.onclose = function(){
      console.log("웹소켓 연결 종료");
   };
});
</script>
