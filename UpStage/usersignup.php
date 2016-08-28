
<?php
/* 
=================================================================================
 + filename: usersignup.php
 + info: contains back-end code for creating database and table for user signup (in case they don’t exist) and also for validating and putting data into the upstage_signup_request table. Sends automatic email using Gmail SMTP after successfully inserting the data. Also displays the result of the application for the user.
 + date: 18-08-2016 / last revised on: 25-08-2016
         18-08-2016: created the file and added database connection 
         19-08-2016: added date column and made code for initialising today's date
         21-08-2016: automatic email function enabled by using swift mailer
         27-08-2016: called functions from creat_table.php so we can create table and database if it doesn't exist
 + author : Minju Park (13839910)
=================================================================================
*/

include'ChromePhp.php';
include'check_signup_input.php';
include'create_table.php';
require_once('swiftmailer-5.x/lib/swift_required.php');

$host="127.0.0.1";
$user="root";
$pswd="";
$dbnm="UpStage";


// get username, password, email, introduction and reason for joining from the html client page.
$username = $_POST['username'];
$password = $_POST['password'];
$email = $_POST['email'];
$introduction = $_POST['introduction'];
$reason = $_POST['reason'];

echo "username: " . $username . " password: " . $password . " email: " . $email . " introduction: " . $introduction . " reason to join: " . $reason;

// check if all the fields are not empty or null 
if(empty($username) || empty($password) || empty($email) || empty($introduction) || empty($reason)) {
    
    $signupResult ="<p>You have not put all the required fields.</p>";
    $signupResultTitle = "Sorry, something went wrong!";
    
} else {
    // validation: check if all the fields are in right pattern
    $validData = checkUsernamePattern($username) && checkPasswordPattern($password) && checkEmailPattern($email) && checkIntroduction($introduction) && checkReason($reason);
    
    
    // if all input fields are valid, proceed
    if($validData) {


        // hash the password value for security (this feature will be implemented after the password encryption research)



        // get signup form submission datetime (today's date)
        date_default_timezone_get();
        $today = new DateTime();
        $todaysDate = $today ->format('Y-m-d H:i:s'); // change the format to MySQL datetime format

        ChromePhp::log($todaysDate);

        // create approved value that identifies if application is approved
        $approved = false;

        // connect to database server 
        $dbConnect = mysqli_connect($host, $user, $pswd);
        if(!$dbConnect) {
            $dbServerConnected = "Sorry! Database server could not be connected!";

        } else {
            $dbServerConnected = "Database server Connected.";

        }

        // connect to database 
        if(mysqli_select_db($dbConnect, $dbnm)) {
            //$dbaseConnected= 
            echo "database connected.";
        } else {
            //$dbaseConnected = 
            echo "Sorry!Can't connect to database."; 
            checkDatabase($dbConnect, $dbnm);
        }
        
        // check if there is table
        $exist_table = isTable($dbConnect, $dbnm, "upstage_signup_request");
        
        // create the table to store signup requests if it does not exist
        if(!$exist_table) {
            createTable($dbConnect) or die("Error creating table.");
        }
        
        // check if username is available
        $result = mysqli_query($dbConnect, "SELECT count(*) FROM upstage_signup_request WHERE a_username='$username'");
        $row = mysqli_fetch_row($result);
        $user_count = $row[0];
        
        // if there is already a username display error message
        if($user_count > 0) {
            $signupResult ="<p>please check your username availability before you submit.</p>";
            $signupResultTitle = "Sorry, your username is not available!";
            
        // if username is available for use, proceed to save user information into database   
        } else {
            
            // insert new row into the signup table
            $insertQuery = "INSERT INTO upstage_signup_request (a_username, a_password, a_email, a_introduction, a_reason, submission_time, approved) VALUES ('$username', '$password', '$email', '$introduction', '$reason', '$todaysDate', 'false')";

            if(mysqli_query($dbConnect, $insertQuery)){
                $signupResult = "Once your account information has been approved by an UpStage adminstrator, you will receive email notification.";
                $signupResultTitle = "Thank you!";

                // send email to notify the admin
                $transport = Swift_smtpTransport::newInstance('smtp.gmail.com', 465, "ssl")
                ->setUsername('upstage.test2016@gmail.com')
                ->setPassword('test2016');

                $mailer = Swift_Mailer::newInstance($transport);

                $message = Swift_Message::newInstance('New audience wants to join UpStage')
                ->setFrom(array('UpStage@example.com' => 'UpStage'))
                ->setTo(array('minjupk@hotmail.com'))
                ->setBody('Hello Admin, an audience, '. $username . ' requested for a player account. Please check the details for application.');

                $result = $mailer->send($message);

                if($result) {
                    echo "successfully sent email";
                } else {
                    echo "failed to send email";
                }


            } else {
                $signupResult ="<p>please contact the administrator of this UpStage server.</p>";
                $signupResultTitle = "Sorry, something went wrong!";
            }
        }
        
        

        
    }
}

?>

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
     
      <form id="signin" class="navbar-form navbar-right" role="form">
	 
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input id="email" type="email" class="form-control" name="email" placeholder="Email Address">                                        
                        </div>

                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="password" type="password" class="form-control" name="password" placeholder="Password">   
                  </div>

                        <button type="submit" class="btn btn-primary">Login</button>
                        <button type="submit" class="btn btn-primary">Signup</button>
                   </form>
     
    </div>
      </ul>
    </div>
  </div>
</nav>
    <div class="container">
        <div class ="col-xs-6 col-md-8 col-lg-10">
            <h1><?php echo $signupResultTitle ?></h1>
        </div>
        <div class ="col-xs-6 col-md-8 col-lg-12">
        <div class="well">
            <strong><?php echo $signupResult ?> <a href="mailto:minjupk@hotmail.com">Send Email to Admin</a></strong>
        
    </div>
    </div>
    </div>

    
    
</body>



</html>