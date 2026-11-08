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
  showDeleteDialog = false;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadStudent(+id);
    }
  }

  loadStudent(id: number): void {
    this.loading = true;
    this.studentService.getStudentById(id).subscribe({
      next: (data) => {
        this.student = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.router.navigate(['/students']);
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
