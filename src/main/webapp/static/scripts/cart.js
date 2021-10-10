let allQuantityInputFields = document.querySelectorAll(".quantity-input");

let cartEndpoint = "/cart"

function addQuantityChangeListeners(){
    for (let i = 0; i < allQuantityInputFields.length; i++){
        allQuantityInputFields[i].addEventListener('change', changeItemQuantity)
    }
}

function changeItemQuantity() {
    let inputField = event.target;
    let formField = event.target.parentElement;
    let changedQuantity = inputField.value;
    let productName = inputField.name;
    formField.submit();
}


addQuantityChangeListeners();