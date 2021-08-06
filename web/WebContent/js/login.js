$(document).ready(function () {

    let loginForm = $("#loginForm");
    loginForm.submit(function (event) {
        event.preventDefault();

        loginData = {
            username: $("#loginUsername").val(),
            password: $("#loginPassword").val()
        }

        $.ajax({
            type: "POST",
            url: "rest/login/",
            contentType: "application/json",
            data: JSON.stringify(loginData),
            success: function (data, textStatus, XMLHttpRequest) {
            	console.log(document.cookie);
                
                window.location.href = XMLHttpRequest.responseText;


                
                

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                incorrectUserPassMessage(XMLHttpRequest.responseText);
            }


        });

    });

});

function isAdminLoggedIn() {
    $.ajax({
        type: "GET",
        url: "rest/login/loggedIn/admin",
        cache: false,
        success: function (data, textStatus, XMLHttpRequest) {


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var obj = JSON.parse(XMLHttpRequest.responseText);
            window.location.href = obj.href;
        }

    });
}

function isUserLoggedIn() {
    $.ajax({
        type: "GET",
        url: "rest/login/loggedIn/user",
        cache: false,
        success: function (data, textStatus, XMLHttpRequest) {
            logout();

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            createLoginRegisterButtons();
        }

    });
}

function checkIfUserLoggedIn() {
    $.ajax({
        type: "GET",
        url: "rest/login/loggedIn/user",
        success: function (data, textStatus, XMLHttpRequest) {


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var obj = JSON.parse(XMLHttpRequest.responseText);
            window.location.href = obj.href;
            
        }
    }); 
}
function logout() {
    let navbarUl = $("#navbarUl");

    navbarUl.empty();
    navbarUl.append('<li class=" nav-item nav-item-custom" id="logoutButtonLi">' +
        '<button type="button" class="btn btn-primary navbar-btn-custom" id="logoutButton">Logout</button>' +
        '</li>');

    $("#logoutButton").click(function () {
        $.ajax({
            type: "GET",
            url: "rest/login/logout",
            success: function (data, textStatus, XMLHttpRequest) {
                createLoginRegisterButtons();
                window.location.href = "index.html";
            }
        })
    })


}


function createLoginRegisterButtons() {
    let navbarUl = $("#navbarUl");
    navbarUl.empty();
    navbarUl.append('<li class="nav-item nav-item-custom" id="registerButtonLi">' +
        '<button type="button" class="btn btn-primary navbar-btn-custom" data-toggle="modal" data-target="#SignUpModal" id="registerButton">Register</button>' +
        '</li>');
    navbarUl.append('<li class=" nav-item nav-item-custom" id="loginButtonLI">' +
        '<button type="button" class="btn btn-primary navbar-btn-custom" data-toggle="modal" data-target="#LoginModal" id="loginButton">Login</button>' +
        '</li>'
    );
}

function incorrectUserPassMessage(message) {
    let loginUsername = $("#loginUsername");
    let loginPassword = $("#loginPassword");
    let errorMessage = $("#loginErrorMessage");

    loginUsername.css("border", "1px solid red");
    loginPassword.css("border", "1px solid red");
    errorMessage.text(message);
    errorMessage.show();
}