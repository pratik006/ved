import { BookDetailComponent } from './book-detail/book-detail.component';
import { HomeComponent } from './home/home.component';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

export const router: Routes = [
    { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
    { path: 'detail/:id', component: BookDetailComponent }
];

export const routes: ModuleWithProviders = RouterModule.forRoot(router);