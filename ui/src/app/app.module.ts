import { VedService } from './vedservice';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Http, HttpModule } from '@angular/http';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {MatButtonModule, MatCheckboxModule, MatCardModule, MatGridListModule} from '@angular/material';
import { routes } from './app.router';

import { AppComponent } from './app.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    BookDetailComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule, HttpModule, FormsModule, MatCardModule, MatGridListModule, routes
  ],
  providers: [ VedService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
