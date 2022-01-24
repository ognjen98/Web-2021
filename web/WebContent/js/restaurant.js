$(document).ready(function () {
	let userTable = $("#userTable");
	let resDiv = $("#restaurantDiv");
	let btnRes = $("#btnRestaurant");
	$("#userModal").on("hidden.bs.modal", function (event) {
        resetSignUpForm();
        hideSignupErrorMessages();
    });
	var date = new Date();
    date.setDate(date.getDate());
    $('.datepicker').datepicker({
        format: 'dd/mm/yyyy',
        startDate: '-3d'
    });
	//addRestaurant(resDiv);
    createManagerSupplier();
    
	logout();
	
//	$("#restaurantBtn").click(function(event){
//		   //event.stopImmediatePropagation();
//	       
//	       
//	}) 
	getManagersWithoutRestaurants(resDiv);
	createRestaurant();
	
    $("#usersBtn").click(function(){
        getUsers(userTable);
    })


});

function logout() {
    let navbarUl = $("#navbarUl");

    $("#logoutBtn").click(function () {
        $.ajax({
            type: "GET",
            url: "rest/login/logout",
            success: function (data, textStatus, XMLHttpRequest) {
                
                window.location.href = "index.html";
            }
        })
    })


}

function getUsers(userTable){
  
    
    $.ajax({
        type: "GET",
        url: "rest/user/getAllUsers",
        success: function (getAll) {
            createUserTable(userTable,getAll)
        }
    })
}

function createUserTable(userTable,getAll){

    parent = $("#parent");
    parent.empty();
    parent.append(userTable);
    userTable.empty();
    userTable.append('<thead class="thead-dark"><tr><th>Username</th><th>Name</th><th>Surname</th><th>Role</th></tr></thead>');
    for(let user of getAll){
        userTable.append('<tr><td>'+user.username+'</td>'
        +'<td>'+user.name+'</td>'+'<td>'+user.surname+'</td>'+'<td>'+user.role+'</td></tr>');
    }
    
}


function addRestaurant(resDiv){
   $("#restaurantBtn").click(function(event){
	   //event.stopImmediatePropagation();
       getManagersWithoutRestaurants(resDiv);
       createRestaurant();
   }) 

}

function createManagerSupplier(){
    let createForm = $("#createForm");
    createForm.submit(function (event) {
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
            role: ($("#role option:selected").text()).toUpperCase(),
            gender: ($("#gender option:selected").text()).toUpperCase(),
            dateOfBirth: Date.parse($("#datepicker").val())
        }
        $.ajax({
            type: "POST",
            url: "rest/registration/createManagerSupplier",
            contentType: "application/json",
            data: JSON.stringify(registerData),
            success: function (data) {


            }
        });
    });
}

function createRestaurant(){
    let resForm = $("#restaurantForm");
    let formData = new FormData();
    let logo = $("#logo");
    resForm.submit(function(event){
    	event.preventDefault();
    	//event.stopPropagation();
        resData = {
        	manager: $("#managerSelect").val(),
        	resName: $("#resName").val(),
            location: $("#location").val(),
            type: ($("#type option:selected").text()).toUpperCase()
        }
        for(var x in resData){
        	formData.append(x, resData[x]);
        	
        }
        formData.append('file', logo.prop('files')[0]);
        console.log(formData.get('file'));
        

        $.ajax({
            type: "POST",
            url: "rest/restaurant/",
            processData: false,
            contentType: false,
            data: formData,
            success: function (data) {
              alert($("#managerSelect").val());
    
            },
            failure: function(data){
            	alert($("#managerSelect").val());
            }
        });
       return false
    })
    
}



function createRestaurantForm(resDiv,managers){
    
    
    
    let parent = $("#parent");
    parent.empty();
    parent.append(resDiv);
    resDiv.empty();
    resDiv.append('<form id="restaurantForm" enctype="multipart/form-data"><div class="form-group modal-form-input-selection">' +
    '<select class="form-control" id="managerSelect">' +'</select></div>' +
    '<div class="form-group modal-form-input-selection"><select class="form-control" id="type">' +
                    '<option value="1">Italian</option> <option value="2">Chinese</option><option value="3">Mexican</option>' +
                    '<option value="4">Pizza</option><option value="5">Barbeque</option><option value="6">Fast food</option>' +
                    '</select></div>' +
                        '<div class="form-group modal-form-input" id="nameInput">' +
                        '<input type="text" class="form-control sign-up-input" placeholder="Name" id="resName">' +
                        ' </div><div class="form-group modal-form-input">' +
                        '<input type="text" class="form-control sign-up-input" placeholder="Location" id="location">' +
                        '</div><div class="form-group modal-form-input"><input type="file" id="logo"></div>' +   
                        '<button type="submit" class="btn btn-primary " id="btnRestaurant">Create</button> </form>')

   let managerSelect = $("#managerSelect");
   managerSelect.empty();
   managers.forEach(element => {
       console.log(element);
       managerSelect.append('<option value="' + element+'">'+element + '</option>')
   });
    
    // for(let manager in managers){
    // 	console.log(managers);
    //     managerSelect.append('<option value="' + manager+'">'+manager + '</option>')
    // }
}


function getManagersWithoutRestaurants(resDiv){
    $.ajax({
        type: "GET",
        url: "rest/restaurant/getManagers",
        success: function (managers) {
        	//createRestaurantForm(resDiv,managers)
        	let managerSelect = $("#managerSelect");
        	   managerSelect.empty();
        	   managers.forEach(element => {
        	       console.log(element);
        	       managerSelect.append('<option value="' + element+'">'+element + '</option>')
        	   });
        }
    })
}

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