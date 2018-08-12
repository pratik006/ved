const titleDiv = document.querySelector(".navbar-brand");

function createBookListView(book) {
    return `
    <div class="card mtb-1">
      <a href="#${book.code}?code=${book.code}">
        <img class="card-img-top v-card-image" src="${BASEIMAGE_PATH + book.previewUrl}" alt="Card image">
        <div class="card-body p-0">
            <h5 class="card-title ml-2">${book.name}</h5>
        </div>
      </a>
    </div>`;
}

function createBookView(book) {
    return book.chapterSummaries.reduce((acc, chapter) => {
        return acc + `
        <div class="card mb-4 pb-2 mt-4 pt-2 v-chapter-summary">
            <a href='#${book.code}?code=${book.code}&ch=${chapter.chapterNo}&sutra=1'>
                <div class="col-lg-12">${sentenceCase(chapter.name)} - ${sentenceCase(chapter.headline)}</div>
            </a>
        </div>
        `;
    }, "");
    
}

function createSutraView(sutra) {
    let html = `
    <div class="card mt-4 v-sutra">
        <div class="card-body p-0">
            <h5 class="card-title ml-2">${sutra.content}</h5>
        </div>
    </div>
    <div>`;

   
    // if (gSutraNo > 1) {
    //     html += `<a href='#${gBookCode}?code=${gBookCode}&ch=${gChapterNo}&sutra=${gSutraNo-1}'>Prev</a>`;
    // }
    // if (gBook.sutras.filter(s => s.chapterNo == gChapterNo).length > gSutraNo) {
    //     html += `<a href='#${gBookCode}?code=${gBookCode}&ch=${gChapterNo}&sutra=${gSutraNo+1}'>Next</a>`
    // }
    
    // html += `</div>`;
    return html;
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
            if (gBook.sutras.filter(s => s.chapterNo == gChapterNo).length > gSutraNo) {
                window.location.href=`#${gBookCode}?code=${gBookCode}&ch=${gChapterNo}&sutra=${gSutraNo+1}`;
            }
        } else {
            /* right swipe */
            if (gSutraNo > 1) {
                window.location.href=`#${gBookCode}?code=${gBookCode}&ch=${gChapterNo}&sutra=${gSutraNo-1}`;
            }
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