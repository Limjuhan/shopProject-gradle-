<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>메일 보내기</title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- CKEditor -->
  <script src="https://cdn.ckeditor.com/ckeditor5/34.2.0/super-build/ckeditor.js"></script>
	<script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>

  <style>
    .ck-editor__editable[role="textbox"] {
      min-height: 300px;
    }
  </style>
</head>
<body>
  <div class="container mt-5">
    <div class="card shadow">
      <div class="card-header bg-primary text-white">
        <h4 class="mb-0">메일 보내기</h4>
      </div>
      <div class="card-body">
        <form:form modelAttribute="mail" name="mailform" action="mail" method="post" enctype="multipart/form-data">
          
          <div class="mb-3">
            <label class="form-label">본인 구글 ID</label>
            <form:input path="googleid" cssClass="form-control" value="limjuhan1124"/>
            <form:errors path="googleid" cssClass="text-danger"/>
          </div>

          <div class="mb-3">
            <label class="form-label">본인 구글 비밀번호</label>
            <form:password path="googlepw" cssClass="form-control" value="scij hktq qepg xnyc"/>
            <form:errors path="googlepw" cssClass="text-danger"/>
          </div>

          <div class="mb-3">
            <label class="form-label">보내는 사람</label>
            <input type="text" class="form-control" value="${loginUser.email}" readonly/>
          </div>

          <div class="mb-3">
            <label class="form-label">받는 사람</label>
            <form:input path="recipient" cssClass="form-control"/>
          </div>

          <div class="mb-3">
            <label class="form-label">제목</label>
            <form:input path="title" cssClass="form-control"/>
            <form:errors path="title" cssClass="text-danger"/>
          </div>

          <div class="mb-3">
            <label class="form-label">메시지 형식</label>
            <form:select path="mtype" cssClass="form-select">
              <form:option value="text/html; charset=utf-8">HTML</form:option>
              <form:option value="text/plain; charset=utf-8">TEXT</form:option>
            </form:select>
          </div>

          <div class="mb-3">
            <label class="form-label">첨부파일 1</label>
            <input type="file" name="file1" class="form-control"/>
          </div>

          <div class="mb-3">
            <label class="form-label">첨부파일 2</label>
            <input type="file" name="file1" class="form-control"/>
          </div>

          <div class="mb-3">
            <label class="form-label">내용</label>
            <form:textarea path="contents" cssClass="form-control" rows="10" id="contents"/>
          </div>

          <div class="d-grid">
            <button type="submit" class="btn btn-primary">메일 보내기</button>
          </div>

        </form:form>
      </div>
    </div>
  </div>

  <script>
  CKEDITOR.ClassicEditor.create(document.getElementById("contents"), {
	  	ckfinder: {
			uploadUrl : '/image/upload'
		},
	    toolbar: {
	      items: [
	        //'exportPDF', 'exportWord', '|',
	        'findAndReplace', 'selectAll', '|',
	        'heading', '|',
	        'bold', 'italic', 'strikethrough', 'underline', 'code', 'subscript', 'superscript', 'removeFormat', '|',
	        'bulletedList', 'numberedList', 'todoList', '|',
	        'outdent', 'indent', '|',
	        'undo', 'redo', '|',
	        'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor', 'highlight', '|',
	        'alignment', '|',
	        'link', 'insertImage', 'blockQuote', 'insertTable', 'mediaEmbed', 'codeBlock', 'htmlEmbed', '|',
	        'specialCharacters', 'horizontalLine', 'pageBreak', '|',
	        'textPartLanguage', '|',
	        'sourceEditing'
	      ],
	      shouldNotGroupWhenFull: true
	    },
	    list: {
	      properties: {
	        styles: true,
	        startIndex: true,
	        reversed: true
	      }
	    },
	    heading: {
	      options: [
	        { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
	        { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
	        { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
	        { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
	        { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
	        { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
	        { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
	      ]
	    },
	    placeholder: 'Welcome to CKEditor 5!',
	    fontFamily: {
	      options: [
	        'default',
	        'Arial, Helvetica, sans-serif',
	        'Courier New, Courier, monospace',
	        'Georgia, serif',
	        'Lucida Sans Unicode, Lucida Grande, sans-serif',
	        'Tahoma, Geneva, sans-serif',
	        'Times New Roman, Times, serif',
	        'Trebuchet MS, Helvetica, sans-serif',
	        'Verdana, Geneva, sans-serif'
	      ],
	      supportAllValues: true
	    },
	    fontSize: {
	      options: [10, 12, 14, 'default', 18, 20, 22],
	      supportAllValues: true
	    },
	    htmlSupport: {
	      allow: [
	        {
	          name: /.*/,
	          attributes: true,
	          classes: true,
	          styles: true
	        }
	      ]
	    },
	    htmlEmbed: {
	      showPreviews: true
	    },
	    link: {
	      decorators: {
	        addTargetToExternalLinks: true,
	        defaultProtocol: 'https://',
	        toggleDownloadable: {
	          mode: 'manual',
	          label: 'Downloadable',
	          attributes: {
	            download: 'file'
	          }
	        }
	      }
	    },
	    mention: {
	      feeds: [
	        {
	          marker: '@',
	          feed: [
	            '@apple', '@bears', '@brownie', '@cake', '@cake', '@candy', '@canes', '@chocolate', '@cookie', '@cotton', '@cream',
	            '@cupcake', '@danish', '@donut', '@dragée', '@fruitcake', '@gingerbread', '@gummi', '@ice', '@jelly-o',
	            '@liquorice', '@macaroon', '@marzipan', '@oat', '@pie', '@plum', '@pudding', '@sesame', '@snaps', '@soufflé',
	            '@sugar', '@sweet', '@topping', '@wafer'
	          ],
	          minimumCharacters: 1
	        }
	      ]
	    },
	    removePlugins: [
	      'ExportPdf',
	      'ExportWord',
	      'CKBox',
	      'CKFinder',
	      'EasyImage',
	      'RealTimeCollaborativeComments',
	      'RealTimeCollaborativeTrackChanges',
	      'RealTimeCollaborativeRevisionHistory',
	      'PresenceList',
	      'Comments',
	      'TrackChanges',
	      'TrackChangesData',
	      'RevisionHistory',
	      'Pagination',
	      'WProofreader',
	      'MathType'
	    ]
	  });
  </script>
</body>
</html>
