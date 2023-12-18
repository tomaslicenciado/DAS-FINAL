<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<c:if test="${empty header['X-Requested-With']}">
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="description" content="Ejemplos de JDBC">
  <meta name="author" content="Lic. Mariela Pastarini">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Mensaje de Error</title>
  <link rel="shorcut icon" href="data:image/png;base64,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">

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: black;
        }
    </style>
</head>
<body class="container">
</c:if>
	<div id="errorModal" class="modal" tabindex="-1">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">${error}</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar" onclick="redirigir()"></button>
	      </div>
	      <div class="modal-body">
	        <p>${mensaje}</p>
	      </div>
	    </div>
	  </div>
	</div>
    <script type="text/javascript">
    $(function(){
        $('#errorModal').modal('show');
    });
	</script>
<c:if test="${empty header['X-Requested-With']}">
</body>
</html>
</c:if>

<script>
    function redirigir() {
        $(location).attr('href','login?id=${id}');
    }
</script>