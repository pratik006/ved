import { Book } from './book';
import { Http, RequestOptions } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/Rx';

@Injectable()
export class VedService {
  url = "http://localhost:8080/rest/books";
  constructor(private http: Http) { }

  getBooks():Promise<Book[]> {
    return this.http.get(this.url).map(response => response.json() as Book[])
    .toPromise()
    .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
