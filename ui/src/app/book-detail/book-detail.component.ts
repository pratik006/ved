import { Chapter } from './../chapter';
import { VedService } from './../vedservice';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Book } from './../book';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {
  private  LEN: number = 10;
  book: Observable<Book>;
  selectedBook: Book;
  chapter: Chapter;
  startIndex: number = 0;
  endIndex: number = this.startIndex + this.LEN;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private service: VedService) { }

  ngOnInit() {
    this.book = this.route.paramMap
    .switchMap((params: ParamMap) =>
      this.service.getBook(params.get('id')));
    this.book.subscribe(book => {
      this.selectedBook = book;
      this.chapter = book.chapters? book.chapters[0] : null;
    });
  }

  loadMore(): void {
    this.service.getSutras(this.selectedBook.id, this.chapter.id, this.endIndex, this.LEN)
      .then( responseData=>{ 
        console.log(responseData);
        this.chapter.sutras = this.chapter.sutras.concat(responseData);
        this.startIndex = this.endIndex;
        this.endIndex = this.startIndex + this.LEN;
        console.log(this.chapter.sutras.length);
      });
  }
}
