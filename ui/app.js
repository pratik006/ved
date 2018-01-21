const sourceSelector = document.querySelector('#sources');
const mainContents = document.querySelector('main');
const navbarTitle = document.querySelector('.navbar-title');
const bookScriptOption = document.querySelector('#book-script-option > a > select');
const commentaryOption = document.querySelector('#commentary-option > a > select');
const context = {
  "script": "dv",
  "baseUrl": "https://vedsangraha-187514.firebaseio.com/ved/",
  "refScripts": "https://vedsangraha-187514.firebaseio.com/ved/"+"ref/scripts.json",
  "books": "https://vedsangraha-187514.firebaseio.com/ved/"+"books.json"
};

if ('serviceWorker' in navigator) {
  /* window.addEventListener('load', () =>
    navigator.serviceWorker.register('sw.js')
      .then(registration => console.log('Service Worker registered'))
      .catch(err => 'SW registration failed')); */
}

window.addEventListener('load', e => {
  /*updateNewsSources().then(() => {
    sourceSelector.value = defaultSource;
    updateNews();
  }); */
  const curUrl = window.location.href;
  //curUrl.split("#").length == 1 ? getBooks() : selectBook(curUrl);
  getBooks();
  loadScripts();
  bookScriptOption.addEventListener('change', evt => updateScript(evt.target.value));
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

async function loadScripts() {
  const response = await fetch(context.refScripts);
  const refScripts = await response.json();
  context.refScripts = refScripts;
}

async function updateScript(script) {
  context.script = script;
  const url = window.location.href.split("#");
  if (url.length > 1) {
    if (url[1].split("/").length > 1) {
      var bookCode = url[1].split("/")[0];
      var chapter = url[1].split("/")[1];
      selectChapter(chapter);
    }
  }
}

async function handleError(error) {
  console.log(error);
}

async function getBooks() {
  mainContents.innerHTML = '';
  const response = await fetch(context.books);
  const books = await response.json();
  mainContents.innerHTML = books.map(showBooks).join('\n');
}

function showBooks(book) {
  return `
  <a href="#${book.code}" title="${book.name}" onclick="selectBook('${book.code}')">
    <article>
      <p>${book.desc}</p>
      <figure>
        <img src="./images/${book.previewUrl}" alt="${book.name}">
        <figcaption>${book.name}</figcaption>
      </figure>
    </article>
  </a>`;
}

async function selectBook(code) {
  context.bookCode = code;
  mainContents.innerHTML = '';
  const response = await fetch(context.baseUrl+`${code}.json`);
  const book = await response.json();
  mainContents.innerHTML = book.chapters.map(showBook).join('\n');
  navbarTitle.innerHTML = ` | ${book.name}`;

  

  fetch(context.baseUrl+`${code}-commentators.json`).then(data => data.json())
    .then(commentaries => { 
      commentaries.map(lang => {
      //todo
    });
  }).catch(handleError);
  
}

function showBook(chapter) {
  return `
  <a href="#${context.bookCode}/${chapter.chapterNo}" title="${chapter.headline}" onclick="selectChapter(${chapter.chapterNo})">
    <h3>${chapter.name} : ${chapter.headline}</h3>
    <article>
      <p>${chapter.content}</p>
    </article>
  </a>`;
}

async function selectChapter(chapterNo) {
  mainContents.innerHTML = '';
  const response = await fetch(context.baseUrl+context.bookCode+`-chapter${chapterNo}-sutras-${context.script}.json`);
  const sutras = await response.json();
  mainContents.innerHTML = sutras.map(showSutras).join('\n');

  fetch(context.baseUrl+`${context.bookCode}-scripts.json`)
    .then((resp) => resp.json())
    .then(scripts => {
      bookScriptOption.innerHTML = '';
      scripts.map(code => {
        var opt = document.createElement('option');
        opt.value = code;
        opt.innerHTML = context.refScripts.find( script => script.code == code).name;
        if (opt.value == context.script) {
          opt.selected = true;
        }
        bookScriptOption.append(opt);
      })}
  ).catch(handleError);

  fetch(context.baseUrl+`${context.bookCode}-commentators.json`).then(data => data.json())
    .then(commentaries => { 
      commentaries.map(commentary => {
        var opt = document.createElement('option');
        opt.value = commentary.id;
        opt.innerHTML = commentary.commentator+" - "+commentary.language;
        commentaryOption.append(opt);      
    });
  }).catch(handleError);
}

function showSutras(sutra, index) {
  return `
  <a title="${index+1}">
    <article>
      <p>${sutra}</p>
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