<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
    <script>
        function confirmar() {
            window.location.href = "${url_retorno}";
        }
    </script>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Prime Video - Login</title>
        <link rel="shorcut icon" href="https://m.media-amazon.com/images/G/01/digital/video/DVUI/favicons/favicon.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <div class="login-form">
            <div style="display: flex; justify-content: center; overflow: hidden;">
                <img style="display: block; width: 120%; border-radius: 10px;" src="https://ds-images.bolavip.com/news/image?src=https%3A%2F%2Fimages.bolavip.com%2Fwebp%2Fspo%2Ffull%2FSPO_20231007_SPO_39583_Diseno-sin-titulo-11.webp&width=740&height=416">
            </div>
            <h2 class="text-center" style="color: white;">Login exitoso</h2>
            <p style="color: white;">Gracias por iniciar sesión en nuestra plataforma.</p>
            <p style="color: white;">Ahora será redirigido al sitio para disfrutar del contenido</p>
            <div style="display: flex; justify-content: center;">
                <!--<a class="btn btn-secondary" id="iniciar-registro" href="${url_retorno }">Confirmar</a>-->
                <a class="btn btn-secondary" id="iniciar-registro" href="#" onclick="confirmar()">Confirmar</a>
            </div>
        </div>
    </body>
</html>