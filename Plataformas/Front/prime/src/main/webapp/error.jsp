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
  <link rel="shorcut icon" href="https://m.media-amazon.com/images/G/01/digital/video/DVUI/favicons/favicon.png">

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