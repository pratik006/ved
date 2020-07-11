export class Book {
    constructor(name, code, desc, id, author, previewUrl) {
        this.name = name;
        this.code = code;
        this.desc = desc;
        this.id = id;
        this.author = author;
        this.previewUrl = previewUrl;
    }

    chapters = [];
}

export class Chapter {
    constructor(chapterNo, name, headline) {
        this.chapterNo = chapterNo;
        this.name = name;
        this.headline = headline;
    }
}

export class Sutra {
    constructor(script, sutraNo, text) {
        this.script = script;
        this.sutraNo = sutraNo;
        this.text = text;
    }
}

export class Commentary {
    constructor(sutraNo, commentator, text) {
        this.sutraNo = sutraNo;
        this.commentator = commentator;
        this.text = text;
    }
}