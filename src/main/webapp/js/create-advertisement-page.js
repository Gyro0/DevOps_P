/*
Author: Matteo Piva

Js to manage the creation of an advertisement
 */

document.addEventListener("DOMContentLoaded", function(event) {

    // Load dropdrown menu for type adv
    getTypeAdvList("idType", "Type");

    // Real time validation of some input fields
    document.getElementById("title").addEventListener("focusout", validateTitle);
    document.getElementById("description").addEventListener("focusout", validateDescription);
    document.getElementById("price").addEventListener("focusout", validatePrice);
    document.getElementById("numTotItem").addEventListener("focusout", validateNumTotItem);

    document.getElementById("create-button").addEventListener("click", function(event) {
        event.preventDefault();
        createAdvertisement()
    });
});

// Reads the input field and send a message to the server
function createAdvertisement() {

    let url = new URL(contextPath+"/adv-create");

    let idAdvertisement = 0;
    let title = document.getElementById("title").value;
    let description = document.getElementById("description").value;
    let price = parseInt(document.getElementById("price").value);
    let numTotItem = parseInt(document.getElementById("numTotItem").value);
    let dateStart = document.getElementById("dateStart").value;
    let dateEnd = document.getElementById("dateEnd").value;
    let timeStart = document.getElementById("timeStart").value;
    let timeEnd = document.getElementById("timeEnd").value;
    let idType = parseInt(document.getElementById("idType").value);

    // Sanitize inputs
    title = sanitizeString(title);
    description = sanitizeString(description);

    // Ensure time fields are in HH:mm:ss format (required by backend)
    if (!timeStart || timeStart === "") {
        timeStart = "00:00:00";
    } else if (timeStart.length === 5) {
        timeStart = timeStart + ":00"; // Convert HH:mm to HH:mm:ss
    }
    
    if (!timeEnd || timeEnd === "") {
        timeEnd = "23:59:59";
    } else if (timeEnd.length === 5) {
        timeEnd = timeEnd + ":00"; // Convert HH:mm to HH:mm:ss
    }

    // Create JSON object properly
    let advertisementData = {
        advertisement: {
            idAdvertisement: idAdvertisement,
            title: title,
            description: description,
            price: price,
            score: 0,
            numTotItem: numTotItem,
            dateStart: dateStart,
            dateEnd: dateEnd,
            timeStart: timeStart,
            timeEnd: timeEnd,
            emailCompany: getUserEmail(),
            idType: idType
        }
    };

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(advertisementData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: {
            "Accept": "application/json"
        },
        success: function(data){

            // Parses the JSON obj
            let jsonData = data.advertisement;
            let idAdvertisement = jsonData.idAdvertisement;

            // Sends to the upload images page
            window.location.href = contextPath + "/image-do-upload/" + idAdvertisement;
            },
        error: function(res) {
            console.error("Error creating advertisement:", res);
            if (res.responseJSON && res.responseJSON.message) {
                let resMessage = res.responseJSON.message;
                alert(resMessage.message + "\n" + (resMessage.errorDetails || ""));
            } else {
                alert("Failed to create advertisement. Status: " + res.status + "\nPlease check all fields and try again.");
            }
        }
    });
}
