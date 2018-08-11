const BASEIMAGE_PATH = "https://vedsangraha-187514.firebaseapp.com/images/";
const BASEDATA_PATH = "https://vedsangraha-187514.firebaseio.com/ved/"
const DEFAULT_SCRIPT = "dv";
const viewport = document.querySelector('.viewport');

var gBook;
var gBookCode;
var gChapterNo;
var gSutraNo;

(function() {
    handleUrl(window.location.hash);
    window.addEventListener('hashchange', e => {
        handleUrl(e.newURL);
    });
        
})();

async function handleUrl(url) {
    if (window.location.hash.split("?").length < 2) {
        loadHomePage();
        return;
    }

    let urlParams = new URLSearchParams(window.location.hash.split("?")[1]);
    gBookCode = urlParams.get("code");
    gChapterNo = urlParams.get("ch");
    gSutraNo = urlParams.get("sutra");
    gBook = await getBookByCode(gBookCode);

    if (!gChapterNo) {
        loadBook(gBookCode);
        return;
    }

    gSutraNo = gSutraNo ? parseInt(gSutraNo) : 1;
    loadSutra(gBookCode, gChapterNo, gSutraNo);
}

async function loadHomePage() {
    let books = JSON.parse(localStorage.getItem("books"));
    if (!books) {
        books = await fetch(BASEDATA_PATH + 'books.json').then(resp => resp.json());
    }
    
    books.forEach(book => {
        viewport.innerHTML += createBookListView(book);
    });
}

async function loadBook(bookCode) {
    const book = await getBookByCode(bookCode);
    viewport.innerHTML = createBookView(book);
    setTitle(gBook.name);
}

async function loadSutra(bookCode, chapterNo, sutraNo=1) {
    viewport.innerHTML = "";
    const book = await getBookByCode(bookCode);
    //const chapter = book.chapterSummaries.find(ch => ch.chapterNo == chapterNo);
    viewport.innerHTML = createSutraView(book.sutras.find(s => s.chapterNo == chapterNo && s.sutraNo == sutraNo));
    setTitle(getChapterName(gBook, gChapterNo));
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

function getChapterName(book, chapterNo) {
    return book.chapterSummaries.find(ch => ch.chapterNo == chapterNo).name;
}