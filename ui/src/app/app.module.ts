import { VedService } from './vedservice';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Http, HttpModule } from '@angular/http';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {MatButtonModule, MatCheckboxModule, MatCardModule, MatGridListModule} from '@angular/material';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule, HttpModule, FormsModule, MatCardModule, MatGridListModule 
  ],
  providers: [ VedService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
