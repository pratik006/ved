const sourceSelector = document.querySelector('#sources');
const mainContents = document.querySelector('main');
const navbarTitle = document.querySelector('.navbar-title');
const bookScriptOption = document.querySelector('#book-script-option > a > select');
const context = {};

if ('serviceWorker' in navigator) {
  /* window.addEventListener('load', () =>
    navigator.serviceWorker.register('sw.js')
      .then(registration => console.log('Service Worker registered'))
      .catch(err => 'SW registration failed')); */
}

window.addEventListener('load', e => {
  /* sourceSelector.addEventListener('change', evt => updateNews(evt.target.value));
  updateNewsSources().then(() => {
    sourceSelector.value = defaultSource;
    updateNews();
  }); */
  getBooks();
});

window.addEventListener('online', () => getBooks());

/* async function updateNewsSources() {
  const response = await fetch(`https://newsapi.org/v2/sources?apiKey=${apiKey}`);
  const json = await response.json();
  sourceSelector.innerHTML =
    json.sources
      .map(source => `<option value="${source.id}">${source.name}</option>`)
      .join('\n');
} */

async function getBooks() {
  mainContents.innerHTML = '';
  //const response = await fetch(`http://localhost:8080/rest/books`);
  const response = await fetch(`./sample.json`);
  const books = await response.json();
  mainContents.innerHTML = books.map(showBooks).join('\n');
}

function showBooks(book) {
  return `
  <a title="${book.name}" onclick="selectBook(${book.id})">
    <article>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate itaque consequatur illo quis, delectus, incidunt labore.</p>
      <figure>
        <img src="./images/${book.previewUrl}" alt="${book.name}">
        <figcaption>${book.name}</figcaption>
      </figure>
    </article>
  </a>`;
}

async function selectBook(id) {
  mainContents.innerHTML = '';
  const response = await fetch(`http://localhost:8080/rest/book/${id}`);
  const book = await response.json();
  mainContents.innerHTML = book.chapters.map(showBook).join('\n');
  context.bookId = id;
  navbarTitle.innerHTML = ` | ${book.name}`;

  book.availableLanguages.map(function(lang) {
    var opt = document.createElement('option');
    opt.value = lang.code;
    opt.innerHTML = lang.name;
    bookScriptOption.append(opt);
  });
}

function showBook(chapter) {
  return `
  <a title="${chapter.headline}" onclick="selectChapter(${chapter.chapterNo})">
    <h3>${chapter.name} : ${chapter.headline}</h3>
    <article>
      <p>${chapter.content}</p>
    </article>
  </a>`;
}

async function selectChapter(chapterNo) {
  mainContents.innerHTML = '';
  const response = await fetch(`http://localhost:8080/rest/book/${context.bookId}`);
  const book = await response.json();
  mainContents.innerHTML = book.chapters[chapterNo-1].sutras.map(showSutras).join('\n');
}

function showSutras(sutra) {
  return `
  <a title="${sutra.sutraNo}">
    <article>
      <p>${sutra.content}</p>
    </article>
  </a>`;
}












function openSlideMenu(){
  document.getElementById('side-menu').style.width = '250px';
  document.getElementById('main').style.marginLeft = '250px';
}

function closeSlideMenu(){
  document.getElementById('side-menu').style.width = '0';
  document.getElementById('main').style.marginLeft = '0';
}