import { VedService } from './vedservice';
import { Book } from './book';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private vedService?: VedService) { }
  title = 'Vedsangraha';
  books: Book[];

  ngOnInit() {
    // this.vedService.getBooks().then(
    //   responseData=>{console.log(responseData);
    //     this.books = responseData; 
    // });
  }
}
