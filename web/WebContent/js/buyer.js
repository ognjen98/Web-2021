$(document).ready(function () {
    let infoForm = $("#infoForm")
	getPersonalInfo();
    
    infoForm.submit(function(event){
        event.preventDefault();
        changeInfo();
    })
	
	logout();

  
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

function getPersonalInfo(){
    let username = $("#username");
    let name = $("#name");
    let surname = $("#surname");
    let gender = $("#gender");
    
    $.ajax({
        type: "GET",
        url: "rest/user/getInfo",
        success: function (getInfo, textStatus, XMLHttpRequest) {
            username.val(getInfo.username);
            name.val(getInfo.name);
            surname.val(getInfo.surname);
            if(getInfo.gender === "MALE")
                gender.val('1');
            else if(getInfo.gender === "FEMALE")
                gender.val('2');
            else
                gender.val('3')
            console.log(gender);
        }
    })
}

function changeInfo(){
    // let username = $("#username").val();
    // let name = $("#name").val();
    // let surname = $("#surname").val();
    // let gender = $("#gender").val();

    let submitInfo = {
        username: $("#username").val(),
        name: $("#name").val(),
        surname: $("#surname").val(),
        gender: $("#gender").val(),
    }
    
    $.ajax({
        type: "POST",
        url: "rest/user/changeInfo",
        contentType: "application/json",
        data: JSON.stringify(submitInfo),
        success: function (getInfo, textStatus, XMLHttpRequest) {
            
        }
    })
}

