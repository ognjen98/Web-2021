$(document).ready(function () {

	
    var user = $.ajax({
        type: "GET",
        url: "rest/login/user",
        cache: false,
        async:false,
        success: function (data, textStatus, XMLHttpRequest) {
        	return data;
        	//console.log(user.role);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
        
    }).responseText;
    
    //let role = user.role;
    console.log(user);
    let userObj;
    let role;
    
    if(user != ""){
    	userObj = JSON.parse(user);
    	role = userObj.role;
        console.log(role);
    }

    
    if(user != ""){
	    if(role == "ADMIN"){
	    	$("#navbarUlAdmin").css("display", "block");
	    }
	    else if(role == "BUYER"){
	    	$("#navbarUlUser").css("display", "block");
	    }
	    else if(role == "MANAGER"){
	    	$("#navbarUlAdmin").css("display", "block");
	    }
	    else if(role == "SUPPLIER"){
	    	$("#navbarUlAdmin").css("display", "block");
	    }
    }
    else{
    	$("#navbarUlNoUser").css("display", "block");
    }
	    
    console.log(role === 'ADMIN');
    
    let res = $("#restInfo");
    console.log(res)
    getRestaurantById(res);
    
});


function getRestaurantById(res){
	
	$.ajax({
        type: "GET",
        url: "rest/restaurant/getById/" + getUrlParameter("id"),
        success: function (data, textStatus, XMLHttpRequest) {
        	console.log(data);
        	createRestaurantPage(res,data);
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
        
    });
}



function createRestaurantPage(res,data){
	console.log("cAOaoaodadaow");
	console.log(data)
	//console.log(res)
	console.log("dnawdwakd")
	res.empty();
	res.append('<div><img src="' + data.image + '" height=300 width=300 style="float:left;"></div>' +
			'<div>' + data.name + '</div>' +
			'<div>' + data.type + '</div>' +
			'<div>' + data.status + '</div>' +
			'<div>' + data.location.address.number + '</div>' +
			'<div>' + data.location.address.streetName + '</div>');
}


function getUrlParameter(param) {
    var pageUrl = window.location.search.substring(1);
    var urlVariables = pageUrl.split('&');
    for (let i = 0; i < urlVariables.length; i++) {
        var parameterName = urlVariables[i].split("=");
        if (parameterName[0] === param) {
            return parameterName[1];
        }
    }
}
