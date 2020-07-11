import { Book } from "../model.js";

class ChapterView {
    constructor(rootSelector, chapterSummary, book) {
        this.root = rootSelector;
        this.chapterSummary = chapterSummary;
        this.book = book;
        this.name = book.name;
    }

    render() {
        if (this.html) {
            document.querySelector(this.root).innerHTML = this.html;
        } else {
            fetch('./js/view/templates/chapters.css').then(resp => resp.text())
            .then(css => {
                fetch('./js/view/templates/chapters-header.html').then(resp => resp.text())
                .then(header => {
                    header = this.template(header, this);
                    fetch('./js/view/templates/chapters.html').then(resp => resp.text())
                    .then(template => {                        
                        const html = this.chapterSummary
                            .map(chapter => {
                                chapter.headline = this.toNormalCase(chapter.headline);
                                chapter.name = this.toNormalCase(chapter.name);
                                return chapter;
                            })
                            .map(chapter => this.template(template, chapter))
                            .reduce((acc, html) => acc + html, '');
                        this.html = `<style>${css}</style>${header}
                        <div class="container" style="padding-bottom: 20px;">          
                            ${html}
                        </div>`;
                        document.querySelector(this.root).innerHTML = this.html;
                    });
                });
            });
        }
    }

    template( html, data ) {
        return html.replace(
            /{(\w*)}/g, // or /{(\w*)}/g for "{this} instead of %this%"
            function( m, key ){
              return data.hasOwnProperty( key ) ? data[ key ] : "";
            }
          );
    }

    toNormalCase(str) {
        str = str.toLowerCase();
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
}

export { ChapterView };