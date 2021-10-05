function toggleCategories() {
    let categories = document.getElementsByClassName("category-title");
    for (let i = 0; i < categories.length; i++) {
        let title = categories[i];
        title.addEventListener("click", toggleHidden);
    }
}

function toggleHidden(event) {
    event.preventDefault();
    event.target.parentElement.parentNode.children[1].classList.toggle("hidden");
}

toggleCategories();