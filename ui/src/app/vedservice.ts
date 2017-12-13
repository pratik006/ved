import { ParamMap } from '@angular/router';
import { Sutra } from './sutra';
import { Observable } from 'rxjs/Observable';
import { Book } from './book';
import { Http, RequestOptions, URLSearchParams, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/Rx';

@Injectable()
export class VedService {
  baseUrl = "http://localhost:8080/rest";
  options: RequestOptions = new RequestOptions();
  books: Promise<Book[]>;

  constructor(private http: Http) {
    this.options.headers = new Headers();
    this.options.headers.append("Access-Control-Allow-Origin", "*");
  }

  getBooks():Promise<Book[]> {
    this.books = this.http.get(this.baseUrl + "/books").map(response => response.json() as Book[])
    .toPromise()
    .catch(this.handleError);
    return this.books;
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  getBook(id: number | string): Promise<Book> {
    return this.http.get(this.baseUrl + "/books/"+id).map(response => response.json() as Book)
    .toPromise()
    .catch(this.handleError);
  }

  getSutras(bookId: number, chapterNo: number, startIndex: number, len: number): Promise<Sutra[]> {
    console.log("startIndex: "+startIndex+"\len: "+len);
    const options: RequestOptions = new RequestOptions();
    options.params = new URLSearchParams();
    options.params.append("startIndex", ""+startIndex);
    options.params.append("size", ""+len);
    return this.http.get(this.baseUrl + "/"+bookId+"/"+chapterNo+"/sutras", options)
      .map(response => response.json() as Sutra[])
    .toPromise()
    .catch(this.handleError);
  }
}
