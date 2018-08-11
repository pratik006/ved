const titleDiv = document.querySelector(".navbar-brand");

function createBookListView(book) {
    return `
    <div class="card mtb-1">
      <a href="#${book.code}">
        <img class="card-img-top v-card-image" src="${BASEIMAGE_PATH + book.previewUrl}" alt="Card image">
        <div class="card-body p-0">
            <h5 class="card-title ml-2">${book.name}</h5>
        </div>
      </a>
    </div>`;
}

function createBookView(book) {
    return book.chapters.reduce((acc, chapter) => {
        return acc + `
        <a href='#${book.info.code}?ch=${chapter.info.name}&sutra=1'><div class="card mt-3" style='box-shadow: 2px 2px'>
            <div class="col-lg-12">${sentenceCase(chapter.info.name)} - ${sentenceCase(chapter.info.headline)}</div>
        </div></a>
        `;
    }, "");
    
}

function createSutraView(sutra) {
    return `
    <div class="card mtb-2 sutra">
        <div class="card-body p-0">
            <h5 class="card-title ml-2">${sutra["dv"]}</h5>
        </div>
    </div>
    <div>
    <a href='#${gBookCode}?ch=${gChapterName}&sutra=${gSutraNo-1}'>Prev</a>
    <a href='#${gBookCode}?ch=${gChapterName}&sutra=${gSutraNo+1}'>Next</a>
    </div>
    `;
}

function setTitle(title) {
    titleDiv.innerHTML = title;
}

document.addEventListener('touchstart', handleTouchStart, false);        
document.addEventListener('touchmove', handleTouchMove, false);

var xDown = null;                                                        
var yDown = null;                                                        

function handleTouchStart(evt) {                                         
    xDown = evt.touches[0].clientX;                                      
    yDown = evt.touches[0].clientY;                                      
};                                                

function handleTouchMove(evt) {
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
            window.location.href=`#${gBookCode}?ch=${gChapterName}&sutra=${gSutraNo+1}`;
        } else {
            /* right swipe */
            window.location.href=`#${gBookCode}?ch=${gChapterName}&sutra=${gSutraNo-1}`;
        }                       
    } else {
        if ( yDiff > 0 ) {
            /* up swipe */ 
        } else { 
            /* down swipe */
        }                                                                 
    }
    /* reset values */
    xDown = null;
    yDown = null;                                             
};