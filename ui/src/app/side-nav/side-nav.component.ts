import { Book } from './../book';
import { Component, OnInit, Input } from '@angular/core';
import { VedService } from '../vedservice';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css']
})
export class SideNavComponent implements OnInit {
  books: Book[];
  constructor(private vedService: VedService) { }
  ngOnInit() {
    this.vedService.currentMessage.subscribe(books => this.books = books);
    this.vedService.selectedBook.subscribe(
      book => this.books[this.books.findIndex(item => item.id == book.id)] = book,
      error => console.log(error)
    );
   }

}
