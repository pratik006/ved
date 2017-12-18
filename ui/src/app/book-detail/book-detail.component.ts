import { Chapter } from './../chapter';
import { VedService } from './../vedservice';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Book } from './../book';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Params } from '@angular/router/src/shared';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {
  private  LEN: number = 10;
  book: Book;
  startIndex: number = 0;
  endIndex: number = this.startIndex + this.LEN;
  chapterNo: number = 0;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private vedService: VedService) { }

  ngOnInit() {
    this.vedService.selectedBook.subscribe(
      book => this.book = book,
      book => console.log(book)
    );
    this.route.paramMap.subscribe((params: Params) => {
      this.chapterNo = params.get('chapterId')?params.get('chapterId'):this.chapterNo;
      this.vedService.getBook(params.get('id'), this.chapterNo);
    });
    
  }

  loadMore(): void {
    this.vedService.getSutras(this.book.id, this.chapterNo, this.endIndex, this.LEN);
  }
}
