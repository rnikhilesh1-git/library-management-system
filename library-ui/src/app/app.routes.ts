import { Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Books } from './pages/books/books';
import { Members } from './pages/members/members';
import { IssueReturn } from './pages/issue-return/issue-return';
import { Transactions } from './pages/transactions/transactions';

export const routes: Routes = [
  { path: '', component: Dashboard },
  { path: 'books', component: Books },
  { path: 'members', component: Members },
  { path: 'issue-return', component: IssueReturn },
  { path: 'transactions', component: Transactions }
];