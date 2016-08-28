<?php
/* 
=============================================================================================
 + filename: check_signup_input.php
 + info: This file is for backend check for inputs for required information given by users
 + date: 25-08-2016 / last revised on: 27-08-2016
         25-08-2016: created methods to check pattern for each required input
         27-08-2016: added comments and file info 
         28-08-2016: changed password pattern
 + author : Minju Park (13839910)
=============================================================================================
*/

/* returns whether username input matches the pattern or not */
function checkUsernamePattern($username_input) {
    if(preg_match('/^[a-z0-9]{3,15}$/', $username_input)) {
        return true;
    } else {
        return false;
    } 
}

/* returns whether password input matches the pattern or not */
function checkPasswordPattern($password_input) {
    if(preg_match('/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,30}$/', $password_input)) {
        return true;
    } else {
        return false;
    } 
}


/* returns whether email input matches the pattern or not */
function checkEmailPattern($email_input) {
    if(preg_match('/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/', $email_input)) {
        return true;
    } else {
        return false;
    } 
}

/* returns whether introduction input is more than 5 characters */
function checkIntroduction($introduction_input) {
    if(strlen($introduction_input) > 5 ) {
        return true;
    } else {
        return false;
    } 
}

/* returns whether reason to join input is more than 5 characters */
function checkReason($reason_input) {
    if(strlen($reason_input) > 5 ) {
        return true;
    } else {
        return false;
    } 
}

?>