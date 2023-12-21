<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String id = request.getParameter("id"); %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Disney+ - Login</title>
    <link rel="shorcut icon" href="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQ53DyHwn_fo2nkn0CHu8W1mKsWqYDV6-IJg&usqp=CAU">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="login-form">
        <div style="display: flex; justify-content: center; overflow: hidden;">
            <img style="display: block; width: 100%; border-radius: 10px;" src="https://static-assets.bamgrid.com/product/disneyplus/images/share-default.14fadd993578b9916f855cebafb71e62.png">
        </div>
        <h2 class="text-center" style="color: white;">Iniciar sesión</h2>
        <form action="" method="post" id="form-login">
            <div>
                <input type="hidden" name="id" id="id" value="<%= id %>">
            </div>
            <div class="form-group">
                <label for="email" style="font-size: 12px; margin: 0px;">Correo electrónico</label>
                <input type="email" class="form-control campo" name="email" placeholder="Ingresa tu correo electrónico" required>
            </div>
            <div class="form-group">
                <label for="password" style="font-size: 12px; margin: 0px;">Contraseña</label>
                <input type="password" class="form-control campo" name="password" placeholder="Ingresa tu contraseña" required>
            </div>
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-primary" id="submit-login">Ingresar</button>
                <a class="btn btn-secondary" id="iniciar-registro" href="registrarse?id=${id}">Registrarse</a>
            </div>
        </form>
        <div id="loading" style="display: flex; justify-content: center;" class="d-none">
            <div class="lds-ring">
                <div></div><div></div><div></div><div></div>
            </div>
        </div>
    </div>
</body>
</html>

<script>
    $('#form-login').on('submit',function(){
        $('#loading').removeClass('d-none');
    })
</script>