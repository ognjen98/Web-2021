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
	    
    console.log(role === 'ADMIN');
    
    let res = $("#restInfo");
    console.log(res)
    if(getUrlParameter("id")){
    	getRestaurantById(res,role);
    	
    }
    else if(getUrlParameter("username")){
    	getRestaurantByManager(res,role);
    	$("#artBtn").css("display","block");
    	addArticle();
    	
    }
    
    
    
});


function createBasket(){
	
	
	$("#basketBtn").click(function(event){
    	event.preventDefault();
    	i = 0;
    	quants = 0;
    	artData = {
            	items: [],
            	buyer: $("#usernameInfo").val(),
                resId: $("#restaurantId").text(),
                
            }
    	
    	$("tr.article").each(function() {
    		if($("#quan"+i).val() != 0){
	            artData.items.push({
	            	name: $(this).find("#articleName"+i).html(),
	            	price: $(this).find("#articlePrice"+i).html(),
	            	type: $(this).find("#articleType"+i).html(),
	            	qType: $(this).find("#articleQuan"+i).html(),
	            	description: $(this).find("#articleDesc"+i).html(),
	            	
	            	quantity: $("#quan"+i).val(),
	            	image: $(this).find("#articleImage"+i).prop('src')
	            	
	            });
    		}
            quants += $("#quan"+i).val();
            i++;
    	});

    	console.log(artData)
    	if(quants == 0){
    		alert("Ukupna kolicina ne sme biti nula");
    		return;
    	}
        $.ajax({
            type: "POST",
            url: "rest/order/createBasket",
            contentType: "application/json",
            data: JSON.stringify(artData),
            success: function (data) {
              //alert($("#managerSelect").val());
            	window.location.href = "basket.html";
            }
//            failure: function(data){
//            	alert($("#managerSelect").val());
//            }
        });
//        return false
    })
}


function getRestaurantById(res,role){
	table = $("#artTable");
	$.ajax({
        type: "GET",
        url: "rest/restaurant/getById/" + getUrlParameterName("id"),
        success: function (data, textStatus, XMLHttpRequest) {
        	console.log(data);
        	createRestaurantPage(res,data);
        	getArticlesByRestaurant(table,role);
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
        
    });
}

function getRestaurantByManager(res,role){
	table = $("#artTable");
	$.ajax({
        type: "GET",
        url: "rest/restaurant/getByManager/" + getUrlParameterName("username"),
        success: function (data, textStatus, XMLHttpRequest) {
        	console.log(data);
        	createRestaurantPage(res,data);
        	
            
            getArticlesByRestaurant(table,role);
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
        
    });
}


function getArticlesByRestaurant(table,role){
	let id = $("#restaurantId");
	console.log(id.val())
	$.ajax({
        type: "GET",
        url: "rest/restaurant/getArticlesByRestaurant/" + id.text(),
        success: function (data, textStatus, XMLHttpRequest) {
        	console.log(data);
        	createArticleTable(table,data,role);
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
        
    });
}


function addArticle(){
	
	let artForm = $("#artForm");
    let formData = new FormData();
    let image = $("#artImage");
	artForm.submit(function(event){
    	event.preventDefault();
    	//event.stopPropagation();
        artData = {
        	resId: $("#restaurantId").text(),
        	artName: $("#artName").val(),
        	price: $("#artPrice").val(),
            description: $("#artDesc").val(),
            quantity: ($("#artQua option:selected").text()).toUpperCase(),
            artType: ($("#artType option:selected").text()).toUpperCase()
        }
        for(var x in artData){
        	formData.append(x, artData[x]);
        	
        }
        formData.append('file', image.prop('files')[0]);
        console.log(formData.get('file'));
        

        $.ajax({
            type: "POST",
            url: "rest/restaurant/addArticle",
            processData: false,
            contentType: false,
            data: formData,
            success: function (data) {
              //alert($("#managerSelect").val());
    
            }
//            failure: function(data){
//            	alert($("#managerSelect").val());
//            }
        });
//        return false
    })
}


function createRestaurantPage(res,data){
	console.log("cAOaoaodadaow");
	console.log(data)
	//console.log(res)
	console.log("dnawdwakd")
	res.empty();
	res.append('<div><img src="' + data.image + '" height=300 width=300 style="float:left;"></div>' +
			'<h4>Restaurant name: ' + data.name + '</div>' +
			'<h5>' + data.location.address.streetName + " " + data.location.address.number + '</h5>' + 
            '<h6>' + data.location.address.city + " "  + data.location.address.zipCode + '</h6>' + 
            '<p class="card-text" style="color: #868e96;">' + data.location.latitude + "," + data.location.longitude + '</p>' +
            '<p class="card-text">Restaurant type: ' + data.type + '</p>' + 
            '<p class="card-text">Grade: ' + data.grade + '</p>' + 
            '<p class="card-text">Status: ' + data.status + '</p>' +
            '<p class="card-text" id="restaurantId" style="visibility:hidden">' + data.id + '</p>');
}


function createArticleTable(table,articles,role){
	i = 0;
    table.empty();
    table.append('<thead class="thead-dark"><tr><th>Image</th><th>Name</th><th>Price</th><th>Type</th><th>Description</th><th>Measurement</th><th class="inputCol" ></th></tr></thead>');
    for(let article of articles){
        table.append('<tr class="article"><td><img src="' + article.image + '" height="50" width="50" id="articleImage'+i+'">'+'</td>'
        +'<td id="articleName'+i+'">'+article.name+'</td>'+'<td id="articlePrice'+i+'">'+article.price+'</td>'+'<td id="articleType'+i+'">'+article.type+'</td>' +'<td id="articleDesc'+i+'">'+article.description+'</td>' +'<td id="articleQuan'+i+'">'+article.quantity+'</td>'+'<td class="inputCol" ><input style="display:none" type="text" id="quan'+i+'" value="0">'+'</td></tr>');
        if(role == "BUYER"){
        	$("#quan" + i ).css("display","block");
        }
        i++;
       
    }
    
}


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
