

/*
=================================================================================
 + filename: check_signup_input.js
 + info: JavaScript file that checks the user input for html page ui (front-end) 
 + date: 27-08-2016: seperated JavaScript part from user_signup.html file   
         28-08-2016: changed password pattern
 + author : Minju Park (13839910)
=================================================================================
*/


            
/* function for showing a message when 
 * username is invalid: Username must be minimum 3 chracters, maximum 15 chracters. No Special characters. Capital letters not allowed. Alphabets come     first. Can include numbers(0-9).
 */
function checkUsername(usernameInput) {
    var regex = new RegExp("^[a-z0-9]{3,15}$");
    var username = usernameInput.value;
    if(regex.test(username)) {
        // check if alphabet letter comes first
        if (username.charAt(0).match(/[a-z]/i)) {
            // alphabet letters found
            usernameInput.setCustomValidity('');
        } else {
            usernameInput.setCustomValidity('Alphabets come first.');
        }
    } else {
        usernameInput.setCustomValidity('Username must be minimum 3 chracters, maximum 15 chracters. No Special characters. Capital letters not allowed. Alphabets come first. Can include numbers(0-9).');
    }
}
            
/* 
* 28-08-2016: Currently NOT USED (Patterns in the form are checking the user input) because it is not working (reason unknown). 
* function for showing a message when 
* password input is not valid: Password must be minimum 6 characters, maximum 30 characters and combination of each of the following types: alphabet letters and numbers and special characters.
*/
function checkPassword(passwordInput) {
    var regex = new RegExp("^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,30}$");
    if (regex.test(passwordInput.value)) {
        passwordInput.setCustomValidity('');
    } else {               
        passwordInput.setCustomValidity('Password must be minimum 6 characters, maximum 30 characters and combination of each of the following types: alphabet letters and numbers and special characters.');
    }
}

            
/* function for showing a message when 
* introduction is not valid: shorter than 5 characters. 
*/
function checkReason(reasonInput) {
    if (reasonInput.value.length < 5) {
        reasonInput.setCustomValidity('Please write more than 5 characters.');
    } else {
        reasonInput.setCustomValidity('');
    }
}
            
/* function for showing a message when 
*  introduction is not valid: shorter than 5 characters. 
*/
function checkIntroduction(introInput) {
    if (introInput.value.length < 5) {
        introInput.setCustomValidity('Please write more than 5 characters.');
    } else {
        introInput.setCustomValidity('');
    }
}
            
/* function for showing a message when 
* confirm-password is not the same as password  
*/
function confirmPswd(conpswdInput) {
    if (conpswdInput.value != document.getElementById('pswd').value) {
        conpswdInput.setCustomValidity('Password is not matching.');
    } else {
        conpswdInput.setCustomValidity('');
    }
}
            
/*
* function for checking if username is available. Change the username span class depending  on 
* the availability.
*/
function checkAvailability() {
    jQuery.ajax({
        url: "check_username.php",
        data: 'username='+$("#userName").val(),
        type: "POST",
        success:function(data) {
        $("#username-availability-status").html(data);
        },
        error:function() {

        }
    });
}
            
