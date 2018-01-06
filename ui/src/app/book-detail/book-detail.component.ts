import { Chapter } from './../chapter';
import { VedService } from './../vedservice';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Book } from './../book';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Params } from '@angular/router/src/shared';
import { Directive, HostListener } from '@angular/core';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
// @Directive({
//   selector: 'div'
// })
export class BookDetailComponent implements OnInit {
  private  LEN: number = 10;
  book: Book;
  startIndex: number = 1;
  chapterNo: number = 1;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private vedService: VedService) { }

  ngOnInit() {
    this.vedService.selectedBook.subscribe(book => {
      this.book = book;
      console.log(book);
    });
    this.route.paramMap.subscribe((params: Params) => {
      this.chapterNo = params.get('chapterId')?params.get('chapterId'):this.chapterNo;
      this.vedService.getBook(params.get('id'), this.chapterNo);
    });
    
  }

  // @HostListener('click')
  // onScroll(): void {
  //   console.log("scrolling...");
  // }

  next(): void {
    this.vedService.getSutras(this.book.id, this.chapterNo, this.startIndex+this.LEN, this.LEN);
    this.startIndex = this.startIndex + this.LEN;
  }

  prev(): void {
    console.log("prev clicked"+this.startIndex);
  }
}
