import { Observable } from 'rxjs/Observable';
import { Book } from './book';
import { Http, RequestOptions, Headers } from '@angular/http';
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
    return this.http.get(this.baseUrl + "/book/"+id).map(response => response.json() as Book)
    .toPromise()
    .catch(this.handleError);
  }
}
