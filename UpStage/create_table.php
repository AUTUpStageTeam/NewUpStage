
<?php
/* 
=================================================================================
 + filename: create_table.php
 + info: This file contains methods relating to creating signup request table and database for UpStage member registraton
 + date: 27-08-2016 / last revised on: 27-08-2016
         27-08-2016: created the file and added comments
 + author : Minju Park (13839910)
=================================================================================
*/

/* Creates database if there is no database called upstage and return true if query works */
function checkDatabase($dbConnect, $dbnm) {
    if(mysqli_query($dbConnect, "CREATE DATABASE IF NOT EXISTS upstage")) {
        echo "successfully created database";
        return true;
    } else {
        echo "failed to create database";
        return false;
    }
}

/* Check if there is table $tableName, if it exists, return true. if not return false. */
function isTable($dbConnect, $tableName) {
    $checktable = mysqli_query($dbConnect, "SHOW TABLES LIKE '$tableName'");
    $table_exists = mysqli_num_rows($checktable) > 0;
    
    if($table_exists) {
        echo "table exists";
    } else {
        echo "table does not exist";
    }
    
    return $table_exists;
    
    
}


function createTable($dbConnect) {
    
    $createQuery ="CREATE TABLE `upstage_signup_request` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `a_username` varchar(25) NOT NULL,
    `a_password` varchar(100) NOT NULL,
    `a_email` varchar(30) NOT NULL,
    `a_introduction` varchar(100) NOT NULL,
    `a_reason` varchar(100) NOT NULL,
    `submission_time` datetime NOT NULL,
    `approved` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`))";
    
    if(mysqli_query($dbConnect, $createQuery)) {
        return true;
    } else {
        return false;
    }
    

}

?>