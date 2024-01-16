<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

    <title>Smart school</title>
</head>
<body>
<#if verifySuccess == true>
    <img style="margin: auto" class="logo-image" width="500" height="500" src="../static/images/Smart School.png" alt="">
    <p style="text-align: center">Ваш акаунт успішно активовано</p>
    <a style="text-align: center" href="http://localhost:8080/loginform">Увійти в систему</a>
<#elseif verifySuccess == false>
    <p style="text-align: center">Ваш акаунт не активовано</p>
</#if>
</body>
</html>