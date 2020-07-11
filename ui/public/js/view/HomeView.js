import { Book } from "../model.js";

class HomeView {
//    BASEIMAGE_PATH = "https://vedsangraha-187514.firebaseapp.com/images/";
    BASEIMAGE_PATH = "http://localhost:8000/images/";
    constructor(rootSelector, books) {
        this.root = rootSelector;
        this.books = books;
    }

    render() {
        const booksHtml = this.books.map(book => {
            return `
            <div class="col-md-4">
                <div class="card mt-2">
                    <a href="#books/${book.code}">
                        <img class="card-img-top v-card-image" src="${this.BASEIMAGE_PATH+book.previewUrl}" alt="${book.name}">
                        <div class="card-body p-0">
                            <h5 class="card-title ml-2">${book.name}</h5>
                        </div>
                    </a>
                </div>
            </div>`;
        }).reduce((acc, html) => acc + html, '');
        document.querySelector(this.root).innerHTML = '<div class="row">'+booksHtml+'</div>';
    }
}

export { HomeView };