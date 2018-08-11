const BASEIMAGE_PATH = "https://vedsangraha-187514.firebaseapp.com/images/";
const BASEDATA_PATH = "https://vedsangraha-187514.firebaseio.com/ved/"
const viewport = document.querySelector('.viewport');

var gBook;
var gBookCode;
var gChapterNo;
var gChapterName;
var gSutraNo;

(function() {
    handleUrl(window.location.hash);
    window.addEventListener('hashchange', e => {
        //const bookCode = e.newURL.split("#")[1];
        handleUrl(e.newURL);
    });
        
})();

function handleUrl(url) {
    if (window.location.hash.split("#").length < 2) {
        loadHomePage();
        return;
    }

    let suburl = window.location.hash.split("#")[1];
    gBookCode = suburl.split("?")[0];
    gBook = getBookByCode(gBookCode);

    let urlParams = new URLSearchParams(suburl.split("?")[1]);
    gChapterName = urlParams.get("ch");
    gSutraNo = parseInt( urlParams.get("sutra") );

    if (!gChapterName) {
        loadBook(gBookCode);
        return;
    }

    loadChapter(gBookCode, gChapterName, gSutraNo);
}

function loadHomePage() {
    fetch(BASEDATA_PATH + 'books.json').then(resp => resp.json())
        .then(books => {
            books.forEach(book => {
                viewport.innerHTML += createBookListView(book);
            });
        });
}

async function loadBook(bookCode) {
    const book = await getBookByCode(bookCode);
    viewport.innerHTML = createBookView(book);
}

async function loadChapter(bookCode, chapterName, sutraNo=1) {
    viewport.innerHTML = "";
    const book = await getBookByCode(bookCode);
    const chapter = book.chapters.find(ch => ch.info.name == chapterName);
    viewport.innerHTML = createSutraView(chapter.sutras[sutraNo-1]);
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
        book = await fetch(BASEDATA_PATH + bookCode + ".json").then(resp => resp.json());
        localStorage.setItem(bookCode, JSON.stringify(book));
    }
    return book;    
}