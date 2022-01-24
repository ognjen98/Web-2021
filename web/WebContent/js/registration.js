$(document).ready(function () {
    hideSignupErrorMessages();
 
    var registerForm = $("form[name=registerForm]");
    
    $("#SignUpModal").on("hidden.bs.modal", function (event) {
        resetSignUpForm();
        hideSignupErrorMessages();
    });
    registerForm.submit(function (event) {
        event.preventDefault();
        hideSignupErrorMessages();
        if (checkSignUpForm() !== true) {
            return;
        }
        
        registerData = {
            username: $("#username").val(),
            password: $("#password").val(),
            name: $("#name").val(),
            surname: $("#surname").val(),
            dateOfBirth: Date.parse($("#datepicker").val()),
            gender: ($("#gender option:selected").text()).toUpperCase()
        }
        $.ajax({
            type: "POST",
            url: "rest/registration/reg",
            contentType: "application/json",
            data: JSON.stringify(registerData),
            success: function (data) {


            }
        });
    });

});

function checkUsername() {
    let usernameInput = $("#username");
    let usernameErrorMEssage = $("#usernameErrorMessage");

    if (usernameInput.val() === "") {
        usernameInput.css("border", "1px solid red");
        usernameErrorMEssage.text("Please enter username");
        usernameErrorMEssage.show();
        return false;
    }
    return true;
}

function checkPassword() {
    let passwordInput = $("#password");
    let passwordControlInput = $("#passwordControl");
    let passwordIndicator = true;
    let passwordControlIndicator = true;

    let passwordControlErrorMessage = $("#passwordControlErrorMessage");
    let passwordErrorMessage = $("#passwordErrorMessage");


    if (passwordInput.val() === "") {
        passwordInput.css("border", "1px solid red");
        passwordErrorMessage.text("Can not be empty");
        passwordErrorMessage.show();
        passwordIndicator = false;
    }
    if (passwordControlInput.val() === "") {
        passwordControlInput.css("border", "1px solid red");
        passwordControlErrorMessage.text("Can not be empty");
        passwordControlErrorMessage.show();
        passwordControlIndicator = false;
    }
    if (passwordInput.val() === passwordControlInput.val() && passwordIndicator === true && passwordControlIndicator === true) {
        return true;
    } else {
        passwordControlInput.css("border", "1px solid red");
        passwordControlErrorMessage.text("Passwords must match");
        passwordControlErrorMessage.show();
        passwordControlIndicator = false;
    }

    return passwordIndicator && passwordControlIndicator;
}

function checkName() {
    let nameInput = $("#name");
    let nameErrorMessage = $("#nameErrorMessage");

    if (nameInput.val() === "") {
        nameInput.css("border", "1px solid red");
        nameErrorMessage.text("Can not be empty");
        nameErrorMessage.show();
        return false;
    }

    var regex = new RegExp("^[a-zA-ZšđčćžŠĐČĆŽ ]+$");
    if (regex.test(nameInput.val())) {
        return true;
    } else {
        nameInput.css("border", "1px solid red");
        nameErrorMessage.text("Please enter correct name");
        nameErrorMessage.show();
        return false;
    }

}

function checkSurname() {
    let surnameInput = $("#surname");
    let surnameErrorMessage = $("#surnameErrorMessage");

    if (surnameInput.val() === "") {
        surnameInput.css("border", "1px solid red");
        surnameErrorMessage.text("Can not be empty");
        surnameErrorMessage.show();
        return false;
    }

    var regex = new RegExp("^[a-zA-ZšđčćžŠĐČĆŽ ]+$");
    if (regex.test(surnameInput.val())) {
        return true;
    } else {
        surnameInput.css("border", "1px solid red");
        surnameErrorMessage.text("Please enter valid surname");
        surnameErrorMessage.show();
    }
}

function checkSignUpForm() {
    let usernameIndicator = false;
    let nameIndicator = false;
    let surnameIndicator = false;
    let passwordIndicator = false;

    if (checkUsername()) {
        usernameIndicator = true;
    }
    if (checkName()) {
        nameIndicator = true;
    }
    if (checkSurname()) {
        surnameIndicator = true;
    }
    if (checkPassword()) {
        passwordIndicator = true;
    }

    return usernameIndicator && nameIndicator && surnameIndicator && passwordIndicator;
}

function hideSignupErrorMessages() {
    $(".error-messages").hide();
    $(".sign-up-input").css("border", "1px solid #ced4da")
}

function resetSignUpForm() {
    $("#username").val("");
    $("#name").val("");
    $("#surname").val("");
    $("#password").val("");
    $("#passwordControl").val("");
    $("#gender").val(1);
}