import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { StudentService } from '../../services/student.service';
import { Student } from '../../models/student';
import { StudentDeleteDialog } from '../student-delete-dialog/student-delete-dialog';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, StudentDeleteDialog],
  templateUrl: './student-list.html',
  styleUrl: './student-list.css'
})
export class StudentList implements OnInit, OnDestroy {
  private studentService = inject(StudentService);
  private notification = inject(NotificationService);

  students: Student[] = [];
  searchTerm = '';
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;
  loading = false;

  studentToDelete: Student | null = null;

  private searchSubject = new Subject<string>();
  private searchSubscription?: Subscription;

  ngOnInit(): void {
    this.fetchStudents();

    this.searchSubscription = this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe((term) => {
      this.searchTerm = term;
      this.currentPage = 0;
      this.fetchStudents();
    });
  }

  ngOnDestroy(): void {
    this.searchSubscription?.unsubscribe();
  }

  onSearchInput(event: Event): void {
    const val = (event.target as HTMLInputElement).value;
    this.searchSubject.next(val);
  }

  fetchStudents(): void {
    this.loading = true;
    this.studentService.getStudents(this.currentPage, this.pageSize, this.searchTerm).subscribe({
      next: (page) => {
        this.students = page.content || [];
        this.totalPages = page.totalPages || 0;
        this.totalElements = page.totalElements || 0;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onSearch(): void {
    this.currentPage = 0;
    this.fetchStudents();
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.currentPage = 0;
    this.fetchStudents();
  }

  changePage(newPage: number): void {
    if (newPage >= 0 && newPage < this.totalPages) {
      this.currentPage = newPage;
      this.fetchStudents();
    }
  }

  promptDelete(student: Student): void {
    this.studentToDelete = student;
  }

  executeDelete(): void {
    if (this.studentToDelete?.id) {
      this.studentService.deleteStudent(this.studentToDelete.id).subscribe({
        next: () => {
          this.notification.success('Student deleted successfully');
          this.studentToDelete = null;
          this.fetchStudents();
        }
      });
    }
  }
}
