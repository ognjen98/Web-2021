$(document).ready(function () {
    isUserLoggedIn();
    var date = new Date();
    date.setDate(date.getDate());
    $('.datepicker').datepicker({
        format: 'dd/mm/yyyy',
        startDate: '-3d'
    });

    let btnSubmit = $("#btnSubmit");
    let btnCancel = $("#btnCancel");
    var apartmentsCol = $("#apartmentsCol");
    let form = $("form[name=searchForm]");



    getAllApartments(apartmentsCol)

    hideNavBarButtons();

    btnCancel.click(function () {
        getAllApartments(apartmentsCol);
        resetForm();


    });

    form.submit(function (event) {
        event.preventDefault();
        searchData = {
            location: $("input[name=place]").val(),
            checkinDate: new Date($("input[name=startDate]").val()),
            checkoutDate: new Date($("input[name=endDate]").val()),
            minPrice: $("input[name=minPrice]").val(),
            maxPrice: $("input[name=maxPrice]").val(),
            numberOfRooms: $("input[name=rooms]").val(),
            numberOfGuests: $("input[name=guests]").val()
        }
        if(checkIsDateEmpty($("input[name=startDate]").val(), $("input[name=endDate]").val()) === false) {
            searchData.checkinDate = new Date("01/01/2020");
            searchData.checkoutDate = new Date("01/01/2020");
        }
        let inputStartDate = $("input[name=startDate]").val();
        let inputEndDate = $("input[name=endDate]").val();
        if (checkFormData(searchData.location, searchData.minPrice, searchData.maxPrice, searchData.numberOfRooms, searchData.numberOfGuests, $("input[name=startDate]").val(), $("input[name=endDate]").val()) !== true) {
            return;
        }
        if (checkForReset(searchData.location, searchData.minPrice, searchData.maxPrice, searchData.numberOfRooms, searchData.numberOfGuests, searchData.checkinDate, searchData.checkoutDate)) {
            getAllApartments(apartmentsCol);
        }

        searchApartments(apartmentsCol);
    });

    sortApartments(apartmentsCol);

});

function hideNavBarButtons() {
    let logoutButton = $("#logoutButton");
    logoutButton.hide();
}



function getAllApartments(apartmentsCol) {
    $.ajax({
        type: "GET",
        url: "rest/apartments/",
        success: function (apartments) {
            createApartmentCards(apartments, apartmentsCol);
        }

    });
}

function searchApartments(apartmentsCol) {

    $.ajax({
        type: "POST",
        url: "rest/apartments/search",
        contentType: "application/json",
        data: JSON.stringify(searchData),
        success: function (apartments) {
            createApartmentCards(apartments, apartmentsCol);
        }

    });
}

function sortApartments(apartmentsCol) {
    let sortSelection = $("#sort");
    sortSelection.change(function() {
       let selectedOptionValue = $("#sort").val();

        $.ajax({
            type: "POST",
            url: "rest/apartments/sort/" + selectedOptionValue,
            success: function(apartments) {
                createApartmentCards(apartments, apartmentsCol);
            } 
        
       });
    });
}

function createApartmentCards(apartments, apartmentsCol) {
    
    apartmentsCol.empty();
            for (let apartment of apartments) {
                apartmentsCol.append('<div class="card card-custom card-custom-apartment" style="width: 50rem; margin-bottom: 100px;">' +
                    '<div class="row no-gutters" style="border-radius: 25px;">' +
                    '<div class="col-sm-5" style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"style="background: #868e96;">' +
                    '<img src="' + apartment.images[0] + '" ' + 'style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"class="card-img-top h-100" alt="..."> ' +
                    '</div>' +
                    '<div class="col-sm-7">' +
                    '<div class="card-body">' +
                    '<h4 class="card-title">' + apartment.location.address.place + '</h4>' +
                    '<h5>' + apartment.location.address.street + " " + apartment.location.address.number + '</h5>' + 
                    '<p class="card-text" style="color: #868e96;">' + apartment.location.latitude + "," + apartment.location.longitude + '</p>' +
                    '<p class="card-text">Number of rooms: ' + apartment.numberOfRooms + '</p>' + 
                    '<p class="card-text">Number of guests: ' + apartment.numberOfGuests + '</p>' +
                    '<h3 class="card-text" style="margin-left: 350px;">' + apartment.price + "e" + '</p>' + 
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<a href="apartmentPage.html?id=' + apartment.id + '&checkInDate=' + $("input[name=startDate]").val() + '&checkoutDate=' + $("input[name=endDate]").val() + '"' + 'class="stretched-link"></a>' +
                    '</div>')


            }
}

function resetForm() {
    $("input[name=place]").val("");
    $("input[name=startDate]").val("");
    $("input[name=endDate]").val("");
    $("input[name=minPrice]").val("");
    $("input[name=maxPrice]").val("");
    $("input[name=rooms]").val("");
    $("input[name=guests]").val();

}

function checkPlace(place) {
    let regex = RegExp(".*");
    if (regex.test(place)) {
        let placeInput = $("input[name=place]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=place]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkMinPrice(minPrice) {
    let regex = RegExp("(^\\s*$)|(^[1-9]+(.)?[0-9]*$)");
    if (regex.test(minPrice)) {
        let placeInput = $("input[name=minPrice]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=minPrice]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkMaxPrice(maxPrice) {
    let regex = RegExp("(^\\s*$)|(^[1-9]+(.)?[0-9]*$)");
    if (regex.test(maxPrice)) {
        let placeInput = $("input[name=maxPrice]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=maxPrice]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkRooms(rooms) {
    let regex = RegExp("(^\\s*$)|(^([1-9]|[1-9][0-9]|100)$)");
    if (regex.test(rooms)) {
        let placeInput = $("input[name=rooms]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=rooms]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkGuests(guests) {
    let regex = RegExp("(^\\s*$)|(^([1-9])$)");
    if (regex.test(guests)) {
        let placeInput = $("input[name=guests]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=guests]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkDate(startDateString, endDateString) {

    
    var checkStartDate = Date.parse(startDateString);
    var checkEndDate = Date.parse(endDateString);

    var startDate = true;
    var startDate = true;

    var startDateInput = $("input[name=startDate]");
    var endDateInput = $("input[name=endDate]");

    if(startDateString === "" && endDateString == "") {
        return true;
    }
 
    if(isNaN(checkStartDate) == true) {
        startDate = false;
        startDateInput.css('border', '1px solid red');
    }
    else {
        startDateInput.css('border', '0');
        startDate = true;
    }

    if(isNaN(checkEndDate) == true) {
        endDate = false;
        endDateInput.css('border', '1px solid red');
    }
    else {
        endDate = true;
        endDateInput.css('border', '0');

    }

    return startDate && endDate;

} 
function checkIsDateEmpty(startDate, endDate) {
  

    if(startDate.toString() === "" || endDate.toString() === "") {
    
        return false;
    }

    return true;
}



function checkFormData(place, minPrice, maxPrice, numberOfRooms, numberOfGuests, startDateString, endDateString) {
    let placeState = false;
    let minPriceState = false;
    let maxPriceState = false;
    let numberOfRoomsState = false;
    let numberOfGuestsState = false;
    let date = false;

    if (checkPlace(place)) {
        placeState = true;
    }
    if (checkMinPrice(minPrice)) {
        minPriceState = true;
    }
    if (checkMaxPrice(maxPrice)) {
        maxPriceState = true;
    }
    if (checkRooms(numberOfRooms)) {
        numberOfRoomsState = true;
    }
    if (checkGuests(numberOfGuests)) {
        numberOfGuestsState = true;
    }
    if(checkDate(startDateString, endDateString)) {
        date = true
    }
    if (placeState && minPriceState && maxPriceState && numberOfRoomsState && numberOfGuestsState && date) {

        return true;
    }

    return false;
}

function checkForReset(place, minPrice, maxPrice, numberOfRooms, numberOfGuests, checkinDate, checkoutDate) {
    if (place === "" && minPrice === "" && maxPrice === "" && numberOfRooms === "" && numberOfGuests === "" && !checkinDate && !checkoutDate) {
        alert(checkinDate.toString());
        return true;
    }
    return false;
}