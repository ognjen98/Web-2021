$(document).ready(function () {
    let infoForm = $("#infoForm")
	getPersonalInfo();
    
    infoForm.submit(function(event){
        event.preventDefault();
        changeInfo();
    })
	
	logoutUser();

    table = $("#artTable");
    getBasket(table);
});


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


function createOrder(){
	
	
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
            	//window.location.href = "basket.html";
            }
//            failure: function(data){
//            	alert($("#managerSelect").val());
//            }
        });
//        return false
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


function getBasket(table){
	
	$.ajax({
        type: "GET",
        url: "rest/order/getBasket",
        success: function (data, textStatus, XMLHttpRequest) {
        	console.log(data);
        	createArticleTable(table,data);
        	
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
        
    });
}


function createArticleTable(table,basket){
	i = 0;
    table.empty();
    table.append('<thead class="thead-dark"><tr><th>Image</th><th>Name</th><th>Price</th><th>Type</th><th>Description</th><th>Measurement</th><th class="inputCol" ></th></tr></thead>');
    for(let item of basket.items){
        table.append('<tr class="article"><td><img src="' + item.article.image + '" height="50" width="50" id="articleImage'+i+'">'+'</td>'
        +'<td id="articleName'+i+'">'+item.article.name+'</td>'+'<td id="articlePrice'+i+'">'+item.article.price+'</td>'+'<td id="articleType'+i+'">'+item.article.type+'</td>' +'<td id="articleDesc'+i+'">'+item.article.description+'</td>' +'<td id="articleQuan'+i+'">'+item.article.quantity+'</td>'+'<td class="inputCol" ><input type="text" id="quan'+i+'" value="'+item.quantity+'">'+'</td></tr>');
        
        i++;
       
    }
    let buyer = $("#buyer");
    let res = $("#rest");
    buyer.text(basket.buyer.username);
    console.log(basket.buyer.username)
    res.text(basket.resId);
    
    console.log(basket.resId)
    
    console.log(res.text())
    
    
}

