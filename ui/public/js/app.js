const BASEIMAGE_PATH = "https://vedsangraha-187514.firebaseapp.com/images/";
const BASEDATA_PATH = "https://vedsangraha-187514.firebaseio.com/ved/"
const DEFAULT_SCRIPT = "dv";

const APP_NAME = "Vedsangraha";

const viewport = document.querySelector('.viewport');
const footerMenu = document.querySelector('.v-footer');
const nextBtn = document.querySelector('.v-footer .next');
const prevBtn = document.querySelector('.v-footer .prev');
const homeBtn = document.querySelector('.v-footer .home');
const scriptsDiv = document.querySelector('.scripts > ul');
const commentariesDiv = document.querySelector('.commentaries > ul');
const btnMenu = document.querySelector('#menu');
const btnSettings = document.querySelector('#settings');
const navbarMenu = document.querySelector("#navbarResponsiveMenu");
const navbarSettingsMenu = document.querySelector("#navbarResponsiveSettings");

var gBook;
var gBookCode;
var gChapterNo;
var gSutraNo;
var gPreferences = new Object();

(function() {
    gPreferences = JSON.parse(localStorage.getItem('gPreferences'));
    if (!gPreferences) {
        gPreferences = new Object();
        gPreferences.languages = ['dv'];
        gPreferences.commentaries = [];
    }

    handleUrl(window.location.hash);
    window.addEventListener('hashchange', e => {
        handleUrl(e.newURL);
    });

    nextBtn.addEventListener('click', evt => nextSutra());
    prevBtn.addEventListener('click', evt => prevSutra());
    homeBtn.addEventListener('click', evt => {});    
    document.querySelector("body").addEventListener('click', evt => {
        if (navbarMenu.classList.contains("show")) {
            btnMenu.click();
        }
        if (navbarSettingsMenu.classList.contains("show")) {
            btnSettings.click();
        }
    });
})();

async function handleUrl(url) {
    if (window.location.hash.split("?").length < 2) {
        loadHomePage();
        nextBtn.querySelector("a").removeAttribute("href");
        btnMenu.style.display = "none";
        return;
    }

    let urlParams = new URLSearchParams(window.location.hash.split("?")[1]);
    gBookCode = urlParams.get("code");
    gChapterNo = urlParams.get("ch");
    gSutraNo = urlParams.get("sutra");
    gBook = await getBookByCode(gBookCode);
    btnMenu.style.display = gBook ? "block" : "none";
    navbarMenu.querySelector("ul").innerHTML = createBookNavMenu(gBook);
    handleTopMenu();

    if (!gChapterNo) {
        loadBook(gBookCode);
        return;
    }

    gSutraNo = gSutraNo ? parseInt(gSutraNo) : 1;
    loadSutra(gBookCode, gChapterNo, gSutraNo);
}

async function handleTopMenu() {
    scriptsDiv.innerHTML = createScriptsView(gBook.availableLanguages);    
    scriptsDiv.addEventListener('change', evt => {
        if (evt.target.type == "checkbox") {
            if (evt.target.checked) {
                if (!gPreferences.languages.includes(evt.target.name)) {
                    gPreferences.languages.push(evt.target.name);
                }
            } else {
                const index = gPreferences.languages.indexOf(evt.target.name);
                if (index != -1)
                    gPreferences.languages.splice(index, 1);
            }
            
            updatePreference();
            location.reload();
        }
    });

    commentariesDiv.innerHTML = createCommentariesView(gBook.availableCommentaries);
    commentariesDiv.addEventListener('change', evt => {
        if (evt.target.type == "checkbox") {
            if (evt.target.checked) {
                if (!gPreferences.commentaries.includes(evt.target.name)) {
                    gPreferences.commentaries.push(evt.target.name);
                }
            } else {
                const index = gPreferences.commentaries.indexOf(evt.target.name);
                if (index != -1)
                    gPreferences.commentaries.splice(index, 1);
            }
            
            updatePreference();
            location.reload();
        }
    });
}

async function loadHomePage() {
    let books = JSON.parse(localStorage.getItem("books"));
    if (!books) {
        books = await fetch(BASEDATA_PATH + 'books.json').then(resp => resp.json());
    }
    
    viewport.innerHTML = "";
    books.forEach(book => {
        viewport.innerHTML += createBookListView(book);
    });
    setTitle(APP_NAME);
}

async function loadBook(bookCode) {
    const book = await getBookByCode(bookCode);
    gBook = book;
    viewport.innerHTML = createBookView(book);
    setTitle(gBook.name);
}

async function loadSutra(bookCode, chapterNo, sutraNo=1) {
    viewport.innerHTML = "";
    const book = await getBookByCode(bookCode);
    gPreferences.languages.forEach(lang => {
        getSutrasByLanguage(lang).then(sutras => {
            viewport.innerHTML += createSutraView(sutras.find(s => s.chapterNo == chapterNo && s.sutraNo == sutraNo));    
        });
    });
    //viewport.innerHTML = createSutraView(book.sutras.find(s => s.chapterNo == chapterNo && s.sutraNo == sutraNo));
    setTitle(getChapterName(gBook, gChapterNo)+" | Verse "+gSutraNo);
}

function prevSutra() {
    const url = `#${gBookCode}?code=${gBookCode}&ch=${gChapterNo}&sutra=${gSutraNo-1}`;
    window.location.href = url;
}

function nextSutra() {
    const url = `#${gBookCode}?code=${gBookCode}&ch=${gChapterNo}&sutra=${gSutraNo+1}`;
    window.location.href = url;
}


function sentenceCase(str) {
	str = str.toLowerCase().split(' ');
	for (var i = 0; i < str.length; i++) {
		str[i] = str[i].charAt(0).toUpperCase() + str[i].slice(1);
	}
	return str.join(' ');
}

async function getBookByCode(bookCode) {
    let book = JSON.parse(localStorage.getItem(bookCode));
    if (!book) {
        book = await fetch(BASEDATA_PATH + bookCode +"-"+ DEFAULT_SCRIPT + ".json").then(resp => resp.json());
        localStorage.setItem(bookCode, JSON.stringify(book));
    }
    return book;    
}

async function getSutrasByLanguage(language) {
    const key = gBookCode+"-sutras-"+language;
    let sutras = JSON.parse(localStorage.getItem(key));

    if (!sutras) {
        sutras = await fetch(BASEDATA_PATH + key + ".json").then(resp => resp.json());
        localStorage.setItem(key, JSON.stringify(sutras));
    }

    return sutras;
}

function updatePreference() {
    localStorage.setItem('gPreferences', JSON.stringify(gPreferences));
}

function getChapterName(book, chapterNo) {
    return book.chapterSummaries.find(ch => ch.chapterNo == chapterNo).name;
}