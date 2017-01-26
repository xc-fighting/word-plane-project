
<!-- <form name="form2" method="post" action="register.php">
输入新的用户名<input type="text" name="user_name_reg">
输入用户的密码<input type="text" name="password_reg">
再次输入用户密码<input type="text">
<input type="submit" value="注册用户">
</form>-->
<?php ?>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <meta charset="utf-8">
        <title>单词战机app--用户登录</title>
		<meta name="keywords" content="网站模板,手机网站模板,手机登录页面,登录页面HTML,免费网站模板下载" />
		<meta name="description" content="JS代码网提供高质量手机网站模板下载" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel="stylesheet" href="assets/css/reset.css">
        <link rel="stylesheet" href="assets/css/supersized.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

    </head>

    <body>

        <div class="page-container">
            <h1>register</h1>
            <form action="register.php" method="post" name='form2'>
                <input type="text" name="user_name_reg" class="username" placeholder="username">
                <input type="password" name="password_reg" class="password" placeholder="input your password">
                 <input type="password" name="password_again" class="password" placeholder="input password again">
                <button type="submit" onclick='check'>submit</button>
                <div class="error"><span>+</span></div>
            </form>
            <div class="connect">
                <p>Or connect with:</p>
                <p>
                    <a class="facebook" href=""></a>
                    <a class="twitter" href=""></a>
                </p>
            </div>
        </div>
		
        <!-- Javascript -->
        <script src="assets/js/jquery-1.8.2.min.js"></script>
        <script src="assets/js/supersized.3.2.7.min.js"></script>
        <script src="assets/js/supersized-init.js"></script>
        <script src="assets/js/scripts.js"></script>
       
    </body>

</html>