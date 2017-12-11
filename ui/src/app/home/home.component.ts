import { VedService } from './../vedservice';
import { Book } from './../book';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  books: Book[];
  constructor(private vedService: VedService) { }

  ngOnInit() {
    this.vedService.getBooks().then(
      responseData=>{
        this.books = responseData; 
    });
  }

}
