import { VedService } from './vedservice';
import { Book } from './book';
import {MediaMatcher} from '@angular/cdk/layout';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Ved Sangraha';
  books: Book[];                   
  ngOnInit() { 
    this.vedService.getBooks().then(
      responseData=>{
        this.books = responseData; 
    });
  }

  mobileQuery: MediaQueryList;  
  fillerNav = Array(10).fill(0).map((_, i) => `Nav Item ${i + 1}`);

  private _mobileQueryListener: () => void;

  constructor(private vedService: VedService,
    changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  shouldRun = [/(^|\.)plnkr\.co$/, /(^|\.)stackblitz\.io$/].some(h => h.test(window.location.host));
}
