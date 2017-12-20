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
  selectedBook: Book;
  selectedChapterNo: number;

  chapterExpand: boolean = true;

  constructor(private vedService: VedService) { }
  ngOnInit() {
    this.vedService.books.subscribe(books => this.books = books);
    this.vedService.selectedBook.subscribe(
      book => {
        this.selectedBook = book;
        this.books[this.books.findIndex(item => item.id == book.id)] = book;      
      },
      error => console.log(error)
    );
    this.vedService.chapterNo.subscribe(chapterNo => this.selectedChapterNo = chapterNo);
   }

   toggleChapter(): void {
    this.chapterExpand = !this.chapterExpand;
   }

}
