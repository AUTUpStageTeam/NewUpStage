<%-- 

=================================================================================
 + filename: user_signup.jsp
 + author : Minju Park (13839910)
 + info: Web user interface for signing up page for user
 + date: 18-08-2016 / last revised on: 21-08-2016
 +       18-08-2016: created the html file
         20-08-2016: added nav bar 
         27-08-2016: seperated javascript file and included them using the src link
         28-08-2016: made another jumbotron for describing username and password policy and changed password pattern & policy
         03-09-2016: reworked into java web application
=================================================================================


pattern regex used.

^: Start of the string
(?=\S+$): no white space allowed
(?=.*\d): a digit must occur at least once
(?=.*[a-z]): a lower case letter must occur at least once
.{3,15}: minimum 3 characters, maximum 15 characters
$: end of string

--%>

<html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <style>
            form {margin: 0 auto; width:250px;}
            div.form {background-color: #eee; border: 1px solid #888; border-radius: 3px; margin: -1px;}
            .status-available{color:#2FC332;}
            .status-not-available{color:#D60202;}
            p {font-size: 10px;}
        </style>

        <title>UpStage user signup</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="check_input.js"></script>
        
    </head>

    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>                        
                    </button>

                    <a class="navbar-brand" href="user_signup.html">UpStage</a>

                </div>
                <div class="collapse navbar-collapse" id="myNavbar">

                    <ul class="nav navbar-nav navbar-right">


                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">


                            <form id="signin" class="navbar-form navbar-right" role="form" action="login.php" method="POST">

                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input id="nav_username" type="text" class="form-control" name="login_username" placeholder="Username" required/>                                        
                                </div>

                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input id="nav_password" type="password" class="form-control" name="login_password" placeholder="Password" required/>   
                                </div>


                                <button type="submit" class="btn btn-primary">Login</button>
                                <a href="user_signup.html" class="btn btn-primary" role= "button">Signup</a>
                                <a href="forgotten_password.html" class="btn btn-link" role= "button">Forgotten Password</a>
                            
                            </form>
                        </div>
                    </ul>
                </div>
            </div>
        </nav>
        
        <div class="container">
            <div class ="col-xs-12 col-md-8 col-lg-10">
                <h1>Create a Player Account</h1>
                <div class="alert alert-info">
                    <strong>Once your account information has been approved by an UpStage administrator, you will receive email notification. Check out how you can make your password more secure by clicking this <a href="">link </a>.</strong>
                </div>
                <div class ="row">
                <div class ="col-xs-12 col-md-8 col-lg-12">
                    <div class="jumbotron text-center col-md-6">

                        <form id ="signup-request" action= "<%=request.getContextPath()%>/signup_request" method="post">

                            <span class="required">* : Required Field </span> <br />

                            <div class="form-group">
                                <label for="userName">Username: <span class="required">* </span> </label>
                                <input type="text" class ="form-control" id="userName" name="username" minlength="3" maxlength="15" placeholder="Enter username" oninput="checkUsername(this)" onBlur="checkUsernameAvailability()" required/> 
                                
                                <span id="username-availability-status"></span><br/>


                                <label for="pswd">Password: <span class="required">* </span></label>
                                <input type="password" class="form-control" id="pswd" name="password" pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,30}$" title="Password must be minimum 6 characters, maximum 30 characters and combination of each of the following types: alphabet letters and numbers and special characters." placeholder="Enter password" required/> <br/>

                                <label for="conpswd">Confirm Password: <span class="required">* </span></label>
                                <input type="password" class="form-control" id="conpswd" name="confirm_password" oninput="confirmPswd(this)" pattern=".{6,30}" placeholder="Confirm your password" required/> <br/>
                                
                                <label for="e-mail">Email: <span class="required">* </span> </label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" title="This field should be in email format" required/> <br/>

                                <label for="introd">Introduction: <span class="required">* </span></label>
                                <textarea class="form-control" id="introd" rows="5" name="introduction"  pattern=".{5,100}" oninput="checkIntroduction(this)" title="Please write more than 5 characters to introduce yourself." placeholder="Please introduce yourself" required></textarea> <br/>

                                <label for="reasonwhy">Why do you want to join UpStage: <span class="required">* </span> </label>
                                <textarea class="form-control" id="reasonwhy" rows="5" name="reason" pattern=".{5,100}" title="Please write more than 5 characters for reason to join UpStage." oninput="checkReason(this)" placeholder="please tell us why you want to join UpStage" required></textarea> <br/>
                                
                                <input type="hidden" value="signup_request" name="formName" />
                                
                                <button type="submit" class="btn btn-default">Submit</button>
                            </div>
                        </form>
                    </div>
                    <div class="jumbotron text-center col-md-6">
                        <h4>UpStage Username and Password Policy</h4><br/>
                        
                        Username must be minimum 3 chracters, maximum 15 chracters. No Special characters. Capital letters not allowed. Alphabets come first. Can include numbers(0-9). <br/>
                        Password must be minimum 6 characters, maximum 30 characters and combination of each of the following types: alphabet letters and numbers and special characters.
                    
                    </div>
                </div>
                    </div>
            </div>
        </div>
    </body>
</html>
