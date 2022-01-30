$(document).ready(function () {
    //isUserLoggedIn();
    var date = new Date();
    date.setDate(date.getDate());
    $('.datepicker').datepicker({
        format: 'dd/mm/yyyy',
        startDate: '-3d'
    });

    let resCards = $("#restaurants");
    getWorkingRestaurants(resCards);
    searchRestaurants(resCards);
   // hideNavBarButtons();

    

});

function hideNavBarButtons() {
    let logoutButton = $("#logoutButton");
    logoutButton.hide();
}





function getWorkingRestaurants(resCards){
	$.ajax({
        type: "GET",
        url: "rest/restaurant/getRestaurants",
        success: function (restaurants) {
            createRestaurantCards(restaurants, resCards);
            
        }

    });
}



function searchRestaurants(resCards){
	let searchForm = $("#searchForm");
	searchForm.submit(function (event) {
        event.preventDefault();
        
        searchData = {
            name: $("#resName").val(),
            location: $("#location").val(),
            grade: $("#resGrade").val(),
            status: ($("#resStatus option:selected").text()).toUpperCase(),
            type: ($("#resType option:selected").text()).toUpperCase()
        }

        
        $.ajax({
            type: "POST",
            url: "rest/restaurant/search",
            contentType: "application/json",
            data: JSON.stringify(searchData),
            success: function (data) {

            	createRestaurantCards(data,resCards)
            }
        });
    });
}


function createRestaurantCards(restaurants, resCards) {
    
    resCards.empty();
            for (let restaurant of restaurants) {
            	resCards.append('<div class="card card-custom card-custom-apartment" style="width: 50rem; margin-bottom: 50px;">' +
                    '<div class="row no-gutters" style="border-radius: 25px;">' +
                    '<div class="col-sm-5" style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"style="background: #868e96;">' +
                    '<img src="' + restaurant.image + '" ' + 'style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"class="card-img-top h-100" alt="..."> ' +
                    '</div>' +
                    '<div class="col-sm-7">' +
                    '<div class="card-body">' +
                    '<h4 class="card-title">' + restaurant.name + '</h4>' +
                    '<h5>' + restaurant.location.address.streetName + " " + restaurant.location.address.number + " " + restaurant.location.address.number + '</h5>' + 
                    '<h6>' + restaurant.location.address.city + " " + restaurant.location.address.number + " " + restaurant.location.address.zipCode + '</h6>' + 
                    '<p class="card-text" style="color: #868e96;">' + restaurant.location.latitude + "," + restaurant.location.longitude + '</p>' +
                    '<p class="card-text">Restaurant type: ' + restaurant.type + '</p>' + 
                    '<p class="card-text">Grade: ' + restaurant.grade + '</p>' + 
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<a href="restaurantPage.html?id=' + restaurant.id + '"' + 'class="stretched-link"></a>' +
                    '</div>')


            }
}
