import { ParamMap, Router } from '@angular/router';
import { Sutra } from './sutra';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AsyncSubject } from 'rxjs/AsyncSubject';
import { Observer } from 'rxjs/Observer';
import { Observable } from 'rxjs/Observable';
import { Book } from './book';
import { Http, RequestOptions, URLSearchParams, Headers, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/Rx';

@Injectable()
export class VedService {
  baseUrl = "http://localhost:8080/rest";
  private startIndex: number = 1;
  private endIndex: number = 10;
  private book: Book;
  private selChapterNo: number = 1;

  options: RequestOptions = new RequestOptions();
  private booksSource = new BehaviorSubject<Book[]>([]);
  books = this.booksSource.asObservable();

  
  private selectedBookSource = new BehaviorSubject<Book>(new Book());
  selectedBook = this.selectedBookSource.asObservable();

  private selectedChapterNo: number = 1;
  private chapterNoSource = new BehaviorSubject<number>(1);
  chapterNo = this.chapterNoSource.asObservable();

  private langCode: string = "ro";
  private langCodeSource = new BehaviorSubject<string>(this.langCode);
  langCodeObservable = this.langCodeSource.asObservable();

  constructor(private http: Http) {
    this.options.headers = new Headers();
    this.options.headers.append("Access-Control-Allow-Origin", "*");
  }

  getBooks():void {
    this.http.get(this.baseUrl + "/books")
      .map(response => response.json() as Book[])
      .subscribe(
        msg => this.booksSource.next(msg),
        msg => console.log("error in /books "+msg)
      );
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  getBook(id: number | string, chapter: number): void {
    this.chapterNoSource.next(chapter);
    this.http.get(this.baseUrl + "/book/"+id+"/"+chapter).map(response => response.json() as Book)
    .subscribe(book => {
        this.book = book; 
        this.selectedBookSource.next(book);
      }
    );
  }

  getSutras(bookId: number, chapterNo: number, startIndex: number, len: number): void {
    this.startIndex = startIndex;
    this.endIndex = this.startIndex + len;

    this.callGetSutras(bookId, chapterNo, startIndex, len).subscribe(response => {
      this.book.chapters[chapterNo-1].sutras = this.book.chapters[chapterNo-1].sutras.concat(response.json());
      this.selectedBookSource.next(this.book);
    });
  }

  callGetSutras(bookId: number, chapterNo: number, startIndex: number, len: number): Observable<Response> {
    console.log("chapter: "+chapterNo+"\tstartIndex: "+startIndex+"\len: "+len);
    const options: RequestOptions = new RequestOptions();
    options.params = new URLSearchParams();
    options.params.append("startIndex", ""+startIndex);
    options.params.append("size", ""+len);
    options.params.append("script", this.langCode);
    return this.http.get(this.baseUrl + "/"+bookId+"/"+chapterNo+"/sutras", options)
  }

  setLangCode(langCode: string): void {
    this.langCode = langCode;
    this.langCodeSource.next(langCode);
    this.callGetSutras(this.book.id, this.selectedChapterNo, 1, this.endIndex).subscribe(
      response => {
        this.book.chapters[this.selChapterNo-1].sutras = response.json();
        this.selectedBookSource.next(this.book);
      }
    );
  }
}
