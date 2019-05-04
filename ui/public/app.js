const sourceSelector = document.querySelector('#sources');
const mainContents = document.querySelector('main');
const navbarTitle = document.querySelector('.navbar-title');
const bookScriptOption = document.querySelector('#book-script-option > a > select');
const commentaryOption = document.querySelector('#commentary-option > a > select');
const context = {
  "baseUrl": "https://vedsangraha-187514.firebaseio.com/ved/",
  "refScripts": "https://vedsangraha-187514.firebaseio.com/ved/"+"ref/scripts.json",
  "booksUrl": "https://vedsangraha-187514.firebaseio.com/ved/"+"books.json",
  "preferences": {
    "script": "dv",
    "commentaries": ["SriNeelkanth", "SwamiSivananda"]
  },
  "xDown": undefined,
  "yDown": undefined,
  "title": "Ved Sangraha",
  "bookName": undefined,
  "chapterName": undefined
};

/* const chapterCount = fetch("https://vedsangraha-187514.firebaseio.com/ved/gita/info/chapters.json")
  .then(resp=>resp.json())
  .then(chapterCount => {
    for (var i=0;i<chapterCount;i++) {
      const url = `https://vedsangraha-187514.firebaseio.com/ved/gita/chapters/${i}/info.json`;
      fetch(url, {
        method: "GET",
      }).then(resp => resp.json()).then((resp) => {
        console.log(resp);
        fetch(`https://vedsangraha-187514.firebaseio.com/ved/gita/chapter-summary/${resp.chapterNo-1}.json`, {
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          method: "PUT",
          body: JSON.stringify(resp)
        });
      }).catch((res) => console.log(res) )
    }
  }); */



if ('serviceWorker' in navigator) {
  window.addEventListener('load', () =>
    navigator.serviceWorker.register('sw.js')
      .then(registration => console.log('Service Worker registered'))
      .catch(err => 'SW registration failed'));
}

window.addEventListener('load', e => {
  //window.location.href = "http://localhost:4200";
  const curUrl = window.location.href;
  if (curUrl.split("#").length>1) {
    //window.location.href = curUrl.split("#")[0];
    !context.books?getBooks():'';
    let path = curUrl.split("#")[1];
    if (path.split("/").length > 1) {
      context.bookCode = path.split("/")[0];
      selectChapter(path.split("/")[1]);
    } else {
      context.bookCode = path.split("/")[0];
      selectBook(path.split("/")[0]);
    }
  } else {
    getBooks();
  }
  navbarTitle.innerHTML = context.title;
  //bookScriptOption.addEventListener('change', evt => updateScript(evt.target.value));
  
  //commentaryOption.addEventListener('change', evt => updateCommentator(1, evt.target.value));
  //curUrl.split("#").length == 1 ? getBooks() : selectBook(curUrl);
  //getBooks();
  loadScripts();
});

//window.addEventListener('online', () => getBooks());
window.addEventListener('hashchange', ()=> {
  if (location.hash.indexOf("#") == 0 && location.hash.length > 1) {
    let code = location.hash.substring(1);
    if (code.split("/").length > 1) {
      let chapterNo = code.split("/")[1];
      const chapterName = context.chapterSummary.find(ch=>ch.chapterNo == chapterNo).name;
      context.chapterName = chapterName;
    } else {
      const bookName = context.books.find(book => book.code == code).name;
      context.bookName = bookName;
    }
  }
  updateTitle();
});

async function loadScripts() {
  const response = await fetch(context.refScripts);
  const refScripts = await response.json();
  context.refScripts = refScripts;
}

async function updateScript(script) {
  context.preferences.script = script;
  const url = window.location.href.split("#");
  if (url.length > 1) {
    if (url[1].split("/").length > 1) {
      var bookCode = url[1].split("/")[0];
      var chapter = url[1].split("/")[1];
      selectChapter(chapter);
    }
  }
}

async function updateCommentator(chapterNo, commentator) {
  document.querySelectorAll("main > .sutra").forEach(item => {
    sutraNo = item.querySelector("a").getAttribute("title");
    const commentary = fetch(context.baseUrl+context.bookCode+"-chapter"+chapterNo+"-sutra"+sutraNo+"-"+commentator.replace(" ","")+".json");
    commentary.then(commentary => commentary.json()).then(commentary => 
      commentary?item.querySelector(".commentary").innerHTML = commentator+"<br>"+commentary:undefined);
  });
  
}

async function updateTitle() {
  navbarTitle.innerHTML = context.chapterName?context.bookName+' | '+context.chapterName:context.bookName?context.bookName:context.title;
}

async function handleError(error) {
  console.log(error);
}

async function getBooks() {
  mainContents.innerHTML = '';
  const response = await fetch(context.booksUrl);
  const books = await response.json();
  context.books = books;
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
  fetch(context.baseUrl+`${code}/chapter-summary.json`).then(resp => resp.json())
    .then(chapter => {
      if (!context.books) {
        fetch(context.booksUrl).then(res=>res.json()).then(res=>context.books = res)
        .then(res=> {
          const bookName = context.books.find(book => book.code == code).name;
          context.bookName = bookName;
          mainContents.innerHTML = chapter.map(showBook).join('\n');
          context.chapterSummary = chapter;
        });
      } else {
        const selBook = context.books.find(book => book.code == code);
        context.bookName = selBook.name;
        mainContents.innerHTML = chapter.map(showBook).join('\n');
        context.chapterSummary = chapter;
      }
    });
  

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

async function selectChapter(chapterNo, sutraNo = 1) {
  mainContents.innerHTML = '';
  fetch(context.baseUrl+context.bookCode+`/chapters/${chapterNo-1}/sutras/${sutraNo-1}/${context.preferences.script}.json`)
    .then(resp => resp.json())
    .then(sutra => {
      //mainContents.innerHTML = sutras.map(showSutras).join('\n');
      mainContents.innerHTML = showSutra(sutra, sutraNo);
      fetch(context.baseUrl+context.bookCode+`/chapters/${chapterNo-1}/sutras/${sutraNo-1}/commentaries.json`)
        .then(resp => resp.json())
        .then(commentaries => {
          var keys = Object.keys(commentaries);
          var commentariesDom = document.querySelector(".commentaries");
          const str = keys.filter(comm=>context.preferences.commentaries.includes(comm)).map((comm, index) => {
            commentariesDom.innerHTML += `<p>Translation by ${comm}</p><div class="commentary sutra${index}">${commentaries[comm]}</div>`;
          });
        });
    });
  

  /* fetch(context.baseUrl+`${context.bookCode}-scripts.json`)
    .then((resp) => resp.json())
    .then(scripts => {
      bookScriptOption.innerHTML = '';
      scripts.map(code => {
        var opt = document.createElement('option');
        opt.value = code;
        opt.innerHTML = context.refScripts.find( script => script.code == code).name;
        if (opt.value == context.preferences.script) {
          opt.selected = true;
        }
        bookScriptOption.append(opt);
      })}
  ).catch(handleError); 

  fetch(context.baseUrl+`${context.bookCode}-commentators.json`).then(data => data.json())
    .then(commentaries => { 
      commentaries.map(commentary => {
        var opt = document.createElement('option');
        opt.value = commentary.commentator+"-"+commentary.language;
        opt.innerHTML = commentary.commentator+" - "+commentary.language;
        commentaryOption.append(opt);      
    });
  }).catch(handleError);*/
}

function showSutra(sutra, index) {
  return `
  <div><a title="${index+1}">
    <article>
      <p>${sutra}</p>
    </article>
  </a>
  <div class="commentaries">
    <div class="commentary sutra${index}"></div>
  </div>
  </div>`;
}












function openSlideMenu(){
  document.getElementById('side-menu').style.width = '250px';
  document.getElementById('main').style.marginLeft = '250px';
}

function closeSlideMenu(){
  document.getElementById('side-menu').style.width = '0';
  document.getElementById('main').style.marginLeft = '0';
}

/** SWIPE starts */
document.querySelector("#main").addEventListener('touchstart', (evt)=> {
  context.xDown = evt.touches[0].clientX;                                      
  context.yDown = evt.touches[0].clientY;
}, false);        
document.querySelector("#main").addEventListener('touchmove', handleTouchMove, false);

var xDown = null;                                                        
var yDown = null;                                               

function handleTouchMove(evt) {
  var xDown = context.xDown;
  var yDown = context.yDown;
  if ( ! xDown || ! yDown ) {
      return;
  }

  var xUp = evt.touches[0].clientX;                                    
  var yUp = evt.touches[0].clientY;

  var xDiff = xDown - xUp;
  var yDiff = yDown - yUp;

  if ( Math.abs( xDiff ) > Math.abs( yDiff ) ) {/*most significant*/
    if ( xDiff > 0 ) {
      /* left swipe */ 
      navigateNext();
    } else {
      /* right swipe */
      navigatePrev();
    }                       
  } else {
    if ( yDiff > 0 ) {
      /* up swipe */ 
    } else { 
      /* down swipe */
    }                                                                 
  }
  /* reset values */
  context.xDown = null;
  context.yDown = null;                                             
};

function navigateNext() {
  navigate(1);
}

function navigatePrev() {
  navigate(-1);
}

function navigate(diff) {
  const curUrl = window.location.href;
  if (curUrl.split("#").length>1) {
    let path = curUrl.split("#")[1];
    if (path.split("/").length > 1) {
      context.bookCode = path.split("/")[0];
      let chapterNo = path.split("/")[1];
      chapterNo = parseInt(chapterNo)+diff;
      window.location.href = curUrl.substring(0, curUrl.length-1)+chapterNo;
    } else {
      context.bookCode = path.split("/")[0];
      selectBook(path.split("/")[0]);
    }
  } else {
    
  }
}

/** SWIPE ends */