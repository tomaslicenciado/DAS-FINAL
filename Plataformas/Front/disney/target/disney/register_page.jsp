<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String id = request.getParameter("id"); %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Disney+ - Register</title>
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
        <h2 class="text-center" style="color: white;">Registrarse</h2>
        <form action="" method="post" id="form-register">
            <div>
                <input type="hidden" name="id" id="id" value="<%= id %>">
            </div>
            <div class="form-group">
                <label for="nombres" style="font-size: 12px; margin: 0px;">Nombres</label>
                <input type="text" class="form-control campo" name="nombres" placeholder="Ingresa tu/s nombre/s" required>
            </div>
            <div class="form-group">
                <label for="apellidos" style="font-size: 12px; margin: 0px;">Apellidos</label>
                <input type="text" class="form-control campo" name="apellidos" placeholder="Ingresa tu/s apellido/s" required>
            </div>
            <div class="form-group">
                <label for="email" style="font-size: 12px; margin: 0px;">Correo electrónico</label>
                <input type="email" class="form-control campo" name="r-email" placeholder="Ingresa tu correo electrónico" required>
            </div>
            <div class="form-group">
                <label for="password" style="font-size: 12px; margin: 0px;">Contraseña</label>
                <input type="password" class="form-control campo" id="r-password" name="r-password" placeholder="Ingresa tu contraseña" required>
            </div>
            <div class="form-group">
                <label for="password2" style="font-size: 12px; margin: 0px;">Confirmar Contraseña</label>
                <input type="password" class="form-control campo" id="r-password2" name="r-password2" placeholder="Confirma tu contraseña" required>
            </div>
            <div class="d-flex justify-content-between">
                <button type="button" class="btn btn-primary" id="submit-register" >Confirmar</button>
                <a href="login?id=${id}" class="btn btn-secondary">Volver</a>
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
    $('#submit-register').on('click',function(e){

        var password = document.getElementById("r-password").value;
        var repeatPassword = document.getElementById("r-password2").value;

        if (password !== repeatPassword) {
            alert("Las contraseñas no coinciden. Por favor, inténtalo de nuevo.");
        }
        else{
            console.log($('#password').val);
            $('#loading').removeClass('d-none');
            $('#form-register').submit();
        }
    })
</script>