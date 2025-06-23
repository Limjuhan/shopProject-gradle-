<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>상품 등록</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <style>
    #preview {
      max-width: 200px;
      max-height: 200px;
      margin-top: 1rem;
      border: 1px solid #ccc;
      border-radius: 5px;
      object-fit: contain;
    }
  </style>
</head>
<body class="bg-light">

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow">
        <div class="card-header bg-success text-white">
          <h4 class="mb-0">📦 상품 등록</h4>
        </div>
        <div class="card-body">

          <form:form modelAttribute="item" action="create" method="post" enctype="multipart/form-data">

            <div class="mb-3">
              <label class="form-label">상품명</label>
              <form:input path="name" cssClass="form-control" />
              <form:errors path="name" cssClass="text-danger" />
            </div>

            <div class="mb-3">
              <label class="form-label">상품 가격</label>
              <form:input path="price" type="number" cssClass="form-control" />
              <form:errors path="price" cssClass="text-danger" />
            </div>

            <div class="mb-3">
              <label class="form-label">상품 이미지</label>
              <input type="file" name="picture" id="pictureInput" class="form-control" accept="image/*" />
              <img id="preview" src="#" alt="미리보기 이미지" class="d-none" />
            </div>

            <div class="mb-3">
              <label class="form-label">상품 설명</label>
              <form:textarea path="description" cssClass="form-control" rows="5" />
              <form:errors path="description" cssClass="text-danger" />
            </div>

            <div class="d-flex justify-content-end">
              <button type="submit" class="btn btn-success me-2">상품 등록</button>
              <button type="button" class="btn btn-secondary" onclick="location.href='list'">상품 목록</button>
            </div>

          </form:form>

        </div>
      </div>
    </div>
  </div>
</div>

<!-- JS: 이미지 미리보기 -->
<script>
  const input = document.getElementById("pictureInput");
  const preview = document.getElementById("preview");

  input.addEventListener("change", function () {
    const file = this.files[0];
    if (file && file.type.startsWith("image/")) {
      const reader = new FileReader();
      reader.onload = function (e) {
        preview.src = e.target.result;
        preview.classList.remove("d-none");
      };
      reader.readAsDataURL(file);
    } else {
      preview.src = "#";
      preview.classList.add("d-none");
    }
  });
</script>

</body>
</html>
