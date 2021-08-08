$(document).ready(function () {
	let userTable = $("#userTable");
	logout();
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

    userTable.empty();
    userTable.append('<thead class="thead-dark"><tr><th>Username</th><th>Name</th><th>Surname</th><th>Role</th></tr></thead>');
    for(let user of getAll){
        userTable.append('<tr><td>'+user.username+'</td>'
        +'<td>'+user.name+'</td>'+'<td>'+user.surname+'</td>'+'<td>'+user.role+'</td></tr>');
    }
    
}