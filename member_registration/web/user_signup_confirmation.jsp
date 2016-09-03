<%-- 
=================================================================================
 + filename: user_signup_confirmation.jsp
 + author : Minju Park (13839910)
 + info: Web user interface for signing up page for user
 + date: last revised on: 03-09-2016
         03-09-2016: reworked into java web application
=================================================================================


--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% 
   String resultTitle = (String) request.getAttribute("resultTitle");
   String result = (String) request.getAttribute("result"); 
%>
<html>
    
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>UpStage user signup</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
     
      <a class="navbar-brand" href="#">UpStage</a>
	  
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      
      <ul class="nav navbar-nav navbar-right">
	  
	  
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
     
          <button type="submit" class="btn btn-primary">Login</button>
          <a href="user_signup.html" class="btn btn-primary" role= "button">Signup</a>
          <a href="forgotten_password.html" class="btn btn-link" role= "button">Forgotten Password</a>
     
    </div>
      </ul>
    </div>
  </div>
</nav>
    <div class="container">
        <div class ="col-xs-6 col-md-8 col-lg-10">
            <h1><%= resultTitle %></h1>
        </div>
        <div class ="col-xs-6 col-md-8 col-lg-12">
        <div class="well">
            <strong><%= result  %> <a href="mailto:minjupk@hotmail.com">Send Email to Admin</a></strong>
        
    </div>
    </div>
    </div>

    
    
</body>



</html>