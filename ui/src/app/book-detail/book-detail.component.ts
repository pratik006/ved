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
  book: Observable<Book>;
  constructor(private route: ActivatedRoute,
    private router: Router,
    private service: VedService) { }

  ngOnInit() {
    this.book = this.route.paramMap
    .switchMap((params: ParamMap) =>
      this.service.getBook(params.get('id')));

  }
}
