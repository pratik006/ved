import { Book } from "../model.js";

class BookView {
    constructor(rootSelector, code, chapter, sutras, book) {
        this.root = rootSelector;
        this.code = code;
        this.chapter = chapter;
        this.sutras = sutras;        
        this.nextCh = Number(this.chapter) + 1;
        this.prevCh = Number(this.chapter) - 1;
        this.book = book;
        this.name = book.name;
    }

    render() {
        if (this.html) {
            document.querySelector(this.root).innerHTML = '';
            document.querySelector(this.root).insertAdjacentHTML('afterbegin', this.css);
            document.querySelector(this.root).insertAdjacentHTML('afterbegin', this.header);
            document.querySelector(this.root).insertAdjacentHTML('beforeend', this.html);
            document.querySelector(this.root).insertAdjacentHTML('beforeend', this.footer);
        } else {
            fetch('./js/view/templates/book.css').then(resp => resp.text())
            .then(css => {
                this.css = `<style>${css}</style>`;
                fetch('./js/view/templates/book-header.html').then(resp => resp.text())
                .then(header => {
                    this.header = this.template(header, this);
                    fetch('./js/view/templates/book.html').then(resp => resp.text())
                    .then(template => {
                        fetch('./js/view/templates/footer.html').then(resp => resp.text())
                        .then(footer => {                        
                            this.footer = this.template(footer, this);
                            this.sutras.splice(0, 1);
                            const sutraHtml = this.sutras.map(sutra => template.replace('{text}', sutra))
                                .reduce((acc, html) => acc + html, '');
                            this.html = `
                            <div class="container" style="padding-bottom: 20px;">          
                                <div class='row'>
                                    <div class='scrollable col-md-12'>${sutraHtml}</div>
                                </div>
                            </div>`;
                            document.querySelector(this.root).innerHTML = '';
                            document.querySelector(this.root).insertAdjacentHTML('afterbegin', this.css);
                            document.querySelector(this.root).insertAdjacentHTML('afterbegin', this.header);
                            document.querySelector(this.root).insertAdjacentHTML('beforeend', this.html);
                            document.querySelector(this.root).insertAdjacentHTML('beforeend', this.footer);
                        });                    
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
}

export { BookView };