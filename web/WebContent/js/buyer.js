$(document).ready(function () {
	
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