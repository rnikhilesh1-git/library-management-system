import { Component, OnInit } from '@angular/core';
import { LibraryTransactionService } from '../../services/library-transaction.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transactions.html',
  styleUrl: './transactions.css'
})
export class Transactions implements OnInit {

  transactions: any[] = [];
  error = '';

  constructor(private transactionService: LibraryTransactionService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.error = '';

    this.transactionService.getTransactionHistory().subscribe({
      next: (data) => {
        console.log('Transactions API response:', data);
        this.transactions = data || [];
      },
      error: (err) => {
        console.error(err);
        this.error = err.error?.message || 'Failed to load transactions';
      }
    });
  }
}