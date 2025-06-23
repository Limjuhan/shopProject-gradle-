<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<script src="https://cdn.ckeditor.com/ckeditor5/34.2.0/super-build/ckeditor.js"></script>
<script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>
<style>
  .ck-editor__editable[role="textbox"] {
    min-height: 300px;
  }

  .ck-content .image {
    max-width: 80%;
    margin: 20px auto;
  }
</style>
</head>
<body class="bg-light">

	<div class="container mt-5">
		<div class="card shadow">
			<div class="card-header bg-success text-white">
				<h4 class="mb-0">게시글 작성</h4>
			</div>
			<div class="card-body">
				<form:form modelAttribute="board" action="write" enctype="multipart/form-data" name="f" class="needs-validation">
					<input type="hidden" name="boardid" value="${param.boardid}">
					<div class="mb-3">
						<label class="form-label fw-bold">글쓴이</label>
						<form:input path="writer" cssClass="form-control"/>
						<form:errors path="writer" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">비밀번호</label>
						<form:input path="pass" cssClass="form-control" type="password"/>
						<form:errors path="pass" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">제목</label>
						<form:input path="title" cssClass="form-control"/>
						<form:errors path="title" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">내용</label>
						<form:textarea path="content" id="editor" cssClass="form-control" rows="5"/>
						<form:errors path="content" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">첨부파일</label>
						<input type="file" name="file1" class="form-control">
					</div>

					<div class="d-flex justify-content-between">
						<button type="submit" class="btn btn-primary">게시글 등록</button>
						<a href="list?boardid=${param.boardid}" class="btn btn-secondary">게시글 목록</a>
					</div>

				</form:form>
			</div>
		</div>
	</div>
	
<script>
  CKEDITOR.ClassicEditor.create(document.getElementById("editor"), {
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
