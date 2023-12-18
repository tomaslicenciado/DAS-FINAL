<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String id = request.getParameter("id"); %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HBO Max - Register</title>
    <link rel="shorcut icon" href="https://i.pinimg.com/originals/c3/d8/36/c3d8361dfab10491c15ae272684b7bd7.jpg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="login-form">
        <div style="display: flex; justify-content: center; overflow: hidden;">
            <img style="display: block; border-radius: 10px;" src="data:image/jpeg;base64,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">
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