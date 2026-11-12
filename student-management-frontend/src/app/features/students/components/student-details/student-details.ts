import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { StudentService } from '../../services/student.service';
import { Student } from '../../models/student';
import { NotificationService } from '../../../../core/services/notification.service';
import { StudentDeleteDialog } from '../student-delete-dialog/student-delete-dialog';

@Component({
  selector: 'app-student-details',
  standalone: true,
  imports: [CommonModule, RouterModule, StudentDeleteDialog],
  templateUrl: './student-details.html',
  styleUrl: './student-details.css'
})
export class StudentDetails implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private studentService = inject(StudentService);
  private notification = inject(NotificationService);

  student: Student | null = null;
  loading = false;
  errorMessage = '';
  showDeleteDialog = false;

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idStr = params.get('id');
      if (idStr) {
        const id = Number(idStr);
        if (!isNaN(id)) {
          this.loadStudent(id);
        } else {
          this.errorMessage = 'Invalid student ID.';
        }
      }
    });
  }

  loadStudent(id: number): void {
    this.loading = true;
    this.errorMessage = '';
    this.studentService.getStudentById(id).subscribe({
      next: (data) => {
        this.student = data;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err?.error?.message || 'Could not load student details.';
        this.notification.error(this.errorMessage);
      }
    });
  }

  confirmDelete(): void {
    if (this.student?.id) {
      this.studentService.deleteStudent(this.student.id).subscribe({
        next: () => {
          this.notification.success('Student deleted successfully');
          this.router.navigate(['/students']);
        }
      });
    }
  }
}
