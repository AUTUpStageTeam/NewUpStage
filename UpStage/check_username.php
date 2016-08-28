<?php
/* 
======================================================================================
 + filename: check_username.php
 + info: This file will check username availability from jQuery in user_signup.html
 + date: 18-08-2016 / last revised on: 27-08-2016
         18-08-2016: created the file and added functions that checks username availability
         27-08-2016: added extra lines of codes for creating database and table if it doesn't exist
                     added more comments
 + author : Minju Park (13839910)
======================================================================================
*/

include'create_table.php';

$host="127.0.0.1";
$user="root";
$pswd="";
$dbnm="UpStage";


// connect to database server 
$dbConnect = mysqli_connect($host, $user, $pswd);
if(!$dbConnect) {
    $dbServerConnected = "Sorry! Database server could not be connected!";

} else {
    $dbServerConnected = "Database server Connected.";

}

// connect to database 
if(mysqli_select_db($dbConnect, $dbnm)) {
    $dbaseConnected= "database connected.";
} else {
    $dbaseConnected = "Sorry!Can't connect to database."; 
    checkDatabase($dbConnect, $dbnm);
}
        
// check if there is table
$exist_table = isTable($dbConnect, "upstage_signup_request");
        
// create the table to store signup requests if it does not exist
if(!$exist_table) {
    createTable($dbConnect) or die("Error creating table.");
}

if(!empty($_POST["username"])) {
    $result = mysqli_query($dbConnect, "SELECT count(*) FROM upstage_signup_request WHERE a_username='" . $_POST["username"] . "'");
    $row = @mysqli_fetch_row($result);
    $user_count = $row[0];
    if($user_count > 0) {
        echo "<span class = 'status-not-available'> Username is not available.</span>";
    } else {
        echo "<span class = 'status-available'> Username is available </span>";
    }
}



?>