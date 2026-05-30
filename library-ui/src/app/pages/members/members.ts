import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MemberService } from '../../services/member.service';

@Component({
  selector: 'app-members',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './members.html',
  styleUrl: './members.css'
})
export class Members implements OnInit {

  members: any[] = [];
  message = '';
  error = '';
  editingMemberId: number | null = null;

  member: any = {
    name: '',
    email: ''
  };

  constructor(private memberService: MemberService) {}

  ngOnInit(): void {
    this.loadMembers();
  }

  loadMembers(): void {
    this.memberService.getAllMembers().subscribe({
      next: data => this.members = data,
      error: err => this.error = this.extractError(err)
    });
  }

  saveMember(): void {
    this.message = '';
    this.error = '';

    if (this.editingMemberId) {
      this.memberService.updateMember(this.editingMemberId, this.member).subscribe({
        next: () => {
          this.message = 'Member updated successfully';
          this.resetForm();
          this.loadMembers();
        },
        error: err => this.error = this.extractError(err)
      });
    } else {
      this.memberService.createMember(this.member).subscribe({
        next: () => {
          this.message = 'Member created successfully';
          this.resetForm();
          this.loadMembers();
        },
        error: err => this.error = this.extractError(err)
      });
    }
  }

  editMember(m: any): void {
    this.editingMemberId = m.id;
    this.member = {
      name: m.name,
      email: m.email
    };
  }

  deactivateMember(id: number): void {
    if (confirm('Are you sure?')) {
      this.memberService.deactivateMember(id).subscribe({
        next: () => {
          this.message = 'Member deactivated successfully';
          this.loadMembers();
        },
        error: err => this.error = this.extractError(err)
      });
    }
  }

  resetForm(): void {
    this.editingMemberId = null;
    this.member = {
      name: '',
      email: ''
    };
  }

  extractError(err: any): string {
    if (err.error?.message) return err.error.message;
    if (typeof err.error === 'object') return Object.values(err.error).join(', ');
    return 'Something went wrong';
  }
}