import { Book } from "./model.js";
import { HomeView } from "./view/HomeView.js";
import { BookView } from "./view/BookView.js";
import { ChapterView } from "./view/ChapterView.js";
import { SettingsView } from "./view/SettingsView.js";

export class Controller {
    
    BASEDATA_PATH = "https://vedsangraha-187514.firebaseio.com/ved/"
    views = [];
    books = [];    
    constructor(router) {
        this.router = router;
    }

    showView(path) {
        console.log('path: '+path);
        if (path == 'home') {
            if (!this.views[path]) {
                fetch(this.BASEDATA_PATH + '/books.json').then(resp => resp.json())
                    .then(books => {
                        const view = new HomeView('body > .container', books)
                        this.views[path] = view;
                        view.render();
                    });
            } else {
                this.views[path].render();
            }
        } else if (path == 'book') {            
            
        }
    }

    showChaptersView(code) {
        if (!this.views[code]) {
            if (this.books[code]) {
                const book = this.books[code];
                fetch(this.BASEDATA_PATH + `/${code}/chapter-summary.json`).then(resp => resp.json())
                .then(chapterSummary => {
                    const view = new ChapterView('body', chapterSummary, book)
                    this.views[code] = view;
                    view.render();
                });                
            } else {
                fetch(this.BASEDATA_PATH + `${code}/info.json`).then(resp => resp.json()).then(data => {
                    const book = new Book(data.name, data.code, data.desc, data.id, data.authorName);
                    book.chapters = data.chapters;
                    this.books[code] = book;
                    fetch(this.BASEDATA_PATH + `/${code}/chapter-summary.json`).then(resp => resp.json())
                    .then(chapterSummary => {
                        const view = new ChapterView('body', chapterSummary, book)
                        this.views[code] = view;
                        view.render();
                    });
                });
                
            }
        } else {
            this.views[code].render();
        }
    }

    showBookView(code, ch) {
        const key = code+"/"+ch;
        if (this.views[key]) {
            this.views[key].render();
        } else {            
            if (this.books[code]) {
                const book = this.books[code];
                const langs = this.getCookie('languages') ? this.getCookie('languages') : 'dv';
                
                fetch(this.BASEDATA_PATH + `${code}/sutras/dv/${ch}.json`).then(resp => resp.json())
                .then(sutras => {
                    const view = new BookView('body', code, ch, sutras, book)
                    this.views[key] = view;
                    view.render();
                });
            } else {
                fetch(this.BASEDATA_PATH + `${code}/info.json`).then(resp => resp.json()).then(data => {
                    const book = new Book(data.name, data.code, data.desc, data.id, data.authorName);
                    book.chapters = data.chapters;
                    this.books[code] = book;
                    fetch(this.BASEDATA_PATH + `/${code}/sutras/dv/${ch}.json`).then(resp => resp.json())
                    .then(sutras => {
                        const view = new BookView('body', code, ch, sutras, book)
                        this.views[key] = view;
                        view.render();
                    });
                });                
            }
        }
    }

    showSettingsView() {
        fetch(this.BASEDATA_PATH + 'gita/availableLanguages.json').then(resp => resp.json())
        .then(languages => {
            const view = new SettingsView('body', languages)
            this.views['settings'] = view;
            view.render();
        });
    }

    getCookie(cname) {
        var name = cname + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        for(var i = 0; i <ca.length; i++) {
          var c = ca[i];
          while (c.charAt(0) == ' ') {
            c = c.substring(1);
          }
          if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
          }
        }
        return "";
      }
}
