
$(document).ready(function () {
    let infoForm = $("#infoForm")
	getPersonalInfo();
    
    infoForm.submit(function(event){
        event.preventDefault();
        changeInfo();
    })
	
    orderCards = $("#orders");
    
    getOrders(orderCards);
	logoutUser();
	
	console.log($(".buttons"))
	//cancelOrder();
});



function changeOrderMan(){
	$(".buttonsCha").click(function(){
		let id = this.id.substring(9);
		console.log(id);
		$.ajax({
	        type: "POST",
	        url: "rest/order/changeOrderManager/"+id,
	        contentType: "application/json",
	        
	        success: function (data) {
	            
	        },
	        error:function(data){
	        	alert("Nije moguce azurirati narudzbinu")
	        }
	    })
	})
}


function cancelOrder(){
	$(".buttonsCan").click(function(){
		let id = this.id.substring(9);
		console.log(id);
		$.ajax({
	        type: "POST",
	        url: "rest/order/cancelOrder/"+id,
	        contentType: "application/json",
	        
	        success: function (data) {
	            
	        },
	        error:function(data){
	        	alert("Nije moguce ponistiti narudzbinu")
	        }
	    })
	})
	
}


function logoutUser() {
    let navbarUl = $("#navbarUl");

    $("#logout").click(function () {
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
    
	let username = $("#usernameInfo");
    let name = $("#name");
    let surname = $("#surname");
    let gender = $("#gender");
    
    $.ajax({
        type: "GET",
        url: "rest/user/getInfo",
        success: function (getInfo, textStatus, XMLHttpRequest) {
            
        	username.val(getInfo.username);
        	console.log(getInfo.username)
            name.val(getInfo.name);
            surname.val(getInfo.surname);
            if(getInfo.gender === "MALE")
                gender.val('1');
            else if(getInfo.gender === "FEMALE")
                gender.val('2');
            else
                gender.val('3')
            console.log(gender);
        },
    	failure: function(data){
    		console.log("kitaaaaa")
    	}
    })
}

function changeInfo(){

    let submitInfo = {
        
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


function getOrders(orderCards){
	ordersDiv = $("#orderCards");
	$.ajax({
        type: "GET",
        url: "rest/order/getOrders/" + getUrlParameterName("username"),
        success: function (data, textStatus, XMLHttpRequest) {
        	console.log(data);
        	createOrderCards(data,orderCards);
        	cancelOrder();
        	changeOrderMan();
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
        
    });
}



function createOrderCards(orders, orderCards) {
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
	    	$("#basketBtn").css("display","block");
	    	createBasket();
	    }
	    else if(role == "MANAGER"){
	    	$("#navbarUlManager").css("display", "block");
	    	
	    }
	    else if(role == "SUPPLIER"){
	    	$("#navbarUlSupplier").css("display", "block");
	    }
    }
    else{
    	$("#navbarUlNoUser").css("display", "block");
    }
    orderCards.empty();
    let k = 0;
            for (let order of orders) {
            	let orderDate = new Date(order.dateOfOrder);
            	let date = $.date(orderDate);
            	let id = order.uniqueId;
            	orderCards.append('<div class="card card-custom card-custom-apartment" style="width: 50rem; margin-bottom: 50px;">' +
                    '<div class="row no-gutters" style="border-radius: 25px;">'+ 
                    
                    '<div class="col-sm-7">' +
                    '<div class="card-body">' +
                    '<h4 class="card-title">Restaurant name: ' + order.restaurant.name + '</h4>' +
                    '<h5>Buyer: ' + order.buyer.name + " " + order.buyer.surname + '</h5>' + 
                    '<p class="card-text" >Price: ' + order.price + '</p>' +
                    '<p class="card-text">Status: ' + order.status + '</p>' + 
                    '<p class="card-text" style="color: #868e96;">' + date + '</p>' + 
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<span style="visibility:hidden" id="id'+k+'">' +order.uniqueId+'</span>'+
                    '<span>' +
                    '<button type="submit" class="btn btn-primary buttonsCan" id="btnCancel'+id+'"' + ' style="margin-bottom:20px;display:none">Cancel order</button>'+
                    '</span>' + 
                    '<span>' +
                    '<button type="submit" class="btn btn-primary buttonsCha" id="btnChange'+id+'"' + ' style="margin-bottom:20px;display:none">Change order</button>'+
                    '</span>' +
                    '<table id="articleTable'+k+'"><thead class="thead-dark"><tr><th>Article</th><th>Price</th><th>Quantity</th></tr></thead>'+ '</table>'+
                    '</div>');
                    let table = $("#articleTable"+k)
                    for(let art of order.articles){
                    	table.append('<tr>'+
                    	        '<td>'+art.article.name+'</td>'+'<td>'+art.article.price+'</td>'+'<td>'+ art.quantity+'</td></tr>');
                    	        
                    	
                    }
                    
                    if(user != ""){
                	    if(role == "ADMIN"){
                	    	//$("#navbarUlAdmin").css("display", "block");
                	    }
                	    else if(role == "BUYER"){
////                	    	$("#navbarUlUser").css("display", "block");
////                	    	$("#basketBtn").css("display","block");
//                	    	createBasket();
                	    	$("#btnCancel"+id).css("display","block")
                	    	
                	    }
                	    else if(role == "MANAGER"){
                	    	//$("#navbarUlManager").css("display", "block");
                	    	$("#btnChange"+id).css("display","block")
                	    }
                	    else if(role == "SUPPLIER"){
                	    	//$("#navbarUlSupplier").css("display", "block");
                	    }
                    }
                    else{
                    	//$("#navbarUlNoUser").css("display", "block");
                    }
                    k++;
            }
          
}


$.date = function(dateObject) {
    var d = new Date(dateObject);
    var day = d.getDate();
    var month = d.getMonth() + 1;
    var year = d.getFullYear();
    if (day < 10) {
        day = "0" + day;
    }
    if (month < 10) {
        month = "0" + month;
    }
    var date = day + "/" + month + "/" + year;

    return date;
};


function getUrlParameterName(param) {
    var pageUrl = window.location.search.substring(1);
    var urlVariables = pageUrl.split('&');
    for (let i = 0; i < urlVariables.length; i++) {
        var parameterName = urlVariables[i].split("=");
        if (parameterName[0] === param) {
            return parameterName[1];
        }
    }
}

function getUrlParameter(param) {
    var pageUrl = window.location.search.substring(1);
    var urlVariables = pageUrl.split('&');
    for (let i = 0; i < urlVariables.length; i++) {
        var parameterName = urlVariables[i].split("=");
        if (parameterName[0] === param) {
            return parameterName[0];
        }
    }
}

