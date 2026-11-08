import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { StudentService } from '../../services/student.service';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './student-form.html',
  styleUrl: './student-form.css'
})
export class StudentForm implements OnInit {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private studentService = inject(StudentService);
  private notification = inject(NotificationService);

  form!: FormGroup;
  isEditMode = false;
  studentId: number | null = null;
  loading = false;

  ngOnInit(): void {
    this.initForm();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.studentId = +id;
      this.loadStudent(this.studentId);
    }
  }

  initForm(): void {
    this.form = this.fb.group({
      firstName: ['', [Validators.required, Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: [''],
      dateOfBirth: [''],
      department: ['', Validators.required],
      enrollmentNumber: ['', Validators.required],
      status: ['ACTIVE', Validators.required]
    });
  }

  loadStudent(id: number): void {
    this.loading = true;
    this.studentService.getStudentById(id).subscribe({
      next: (student) => {
        this.form.patchValue(student);
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.router.navigate(['/students']);
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.loading = true;
    const requestData = this.form.value;

    if (this.isEditMode && this.studentId) {
      this.studentService.updateStudent(this.studentId, requestData).subscribe({
        next: () => {
          this.loading = false;
          this.notification.success('Student updated successfully');
          this.router.navigate(['/students']);
        },
        error: () => { this.loading = false; }
      });
    } else {
      this.studentService.createStudent(requestData).subscribe({
        next: () => {
          this.loading = false;
          this.notification.success('Student created successfully');
          this.router.navigate(['/students']);
        },
        error: () => { this.loading = false; }
      });
    }
  }
}
