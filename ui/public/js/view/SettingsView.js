class SettingsView {
    constructor(rootSelector, languages) {
        this.root = rootSelector;
        this.languages = languages;
    }

    render() {
        fetch('./js/view/templates/index-header.html').then(resp => resp.text())
        .then(header => {
            fetch('./js/view/templates/settings.html').then(resp => resp.text())
            .then(html => {
                const langHtml = this.languages.map(lang => this.template(html, lang)).reduce((acc, arg) => acc+arg, '');                
                const root = document.querySelector(this.root);
                root.innerHTML = '';
                root.insertAdjacentHTML('afterbegin', header);
                root.insertAdjacentHTML('beforeend', `<div class="container" style="padding-bottom: 20px;"></div`);
                const container = root.querySelector('body > .container');
                container.innerHTML = `<p>Select Language</p>`+langHtml+`<button type="button" class="btn btn-outline-primary">Save</button>`;
                container.querySelector('button').addEventListener('click', evt => {
                    const langs = [];
                    container.querySelectorAll('input[type="checkbox"]')
                        .forEach(elem => {
                            if (elem.checked) {
                                langs.push(elem.id);
                            }
                        });
                    console.log(langs)
                    document.cookie = "languages="+langs
                });
            });
        });
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

export { SettingsView };