<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tic Tac Toe</title>
        <?php include "interfaces/ICheckSession.php"; ?>
        <link rel="stylesheet" href="css/indexStylesheet.css" />
        <script src="scripts/index.js"></script>
    </head>
    <body class="body">
<!--    https://www.w3schools.com/howto/howto_css_register_form.asp-->
<!--We used the guide above to create the nice login and register page as seen below-->

        <div class="header">
            <center><h1>Welcome to Tic Tac Toe!</h1></center>
            <center><h3>Team Serbonne</h3></center>
            <div class="header-right">
            </div>
        </div>
        <!-- Buttons for log on and register     -->
        <!--The forms below collect user inputs and sends them to the corresponding php interfaces such as ILogin-->
        <center><button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Login</button></center>
        <center><button onclick="document.getElementById('id02').style.display='block'" style="width:auto;">Register</button></center>
        <div id="id01" class="modal">
            <form id="login-form" class="modal-content animate" action="/EE4023/interfaces/ILogin.php" method="POST">
                <div class="container">
                    <label for="uname"><b>Username</b></label>
                    <input id="username-input" class="input" type="text" name="username" tabindex="1" required />
                    <label for="psw"><b>Password</b></label>
                    <input id="password-input" class="input" type="password" name="password" minlength="6" tabindex="2" required />
                    <input id="login-submit" class="form-button" type="submit" value="Login" />
                </div>
                <div class="container" style="background-color:#f1f1f1">
                    <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
                </div>
            </form>
        </div>
        <div id="id02" class="modal">
            <form id="register-form" class="modal-content animate" action="/EE4023/interfaces/IRegister.php" method="POST">
                <div class="container">
                    <label id="register-name" class="label">Name:</label>
                    <input id="name-input" class="input" type="text" name="name" tabindex="1" required/>

                    <label id="register-surname" class="label">Surname:</label>
                    <input id="surname-input" class="input" type="text" name="surname" tabindex="2" required/>

                    <label id="register-username" class="label">Username:</label>
                    <input id="username-input" class="input" type="text" name="username" tabindex="3" required/>

                    <label id="register-password" class="label">Password:</label>
                    <label id="register-password" class="label">Password:</label>
                    <input id="password-input" class="input" type="password" name="password" minlength="6" tabindex="4" required />
                    <input id="register-submit" class="form-button" type="submit" value="Register" />
                </div>
                <div class="container" style="background-color:#f1f1f1">
                    <button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn">Cancel</button>
                </div>
            </form>
        </div>
    </body>
</html>
