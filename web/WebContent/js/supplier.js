$(document).ready(function () {
    let infoForm = $("#infoForm")
	getPersonalInfo();
    
    infoForm.submit(function(event){
        event.preventDefault();
        changeInfo();
    })
	
	logoutSup();

  
});


function logoutSup() {
    let navbarUl = $("#navbarUl");

    $("#logoutSup").click(function () {
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
    
    let name = $("#name");
    let surname = $("#surname");
    let gender = $("#gender");
    
    $.ajax({
        type: "GET",
        url: "rest/user/getInfo",
        success: function (getInfo, textStatus, XMLHttpRequest) {
            
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