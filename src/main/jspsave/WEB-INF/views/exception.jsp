<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%-- isErrorPage = "true" : ShopExcecption 객체가 전달됨 --%>
<script type="text/javascript">
	alert("${exception.message}")
	location.href="${exception.url}"
</script>