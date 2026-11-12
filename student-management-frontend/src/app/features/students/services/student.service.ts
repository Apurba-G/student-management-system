import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { Student } from '../models/student';
import { StudentCreateRequest } from '../models/student-create-request';
import { StudentUpdateRequest } from '../models/student-update-request';
import { StudentPage } from '../models/student-page';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private api = inject(ApiService);
  private endpoint = 'students';

  getStudents(page = 0, size = 10, search = '', sortBy = 'id', sortDir = 'asc'): Observable<StudentPage> {
    return this.api.get<StudentPage>(this.endpoint, { page, size, search, sortBy, sortDir });
  }

  getStats(): Observable<{ totalStudents: number; activeStudents: number; inactiveStudents: number; suspendedStudents: number }> {
    return this.api.get<{ totalStudents: number; activeStudents: number; inactiveStudents: number; suspendedStudents: number }>(this.endpoint + '/stats');
  }

  getStudentById(id: number): Observable<Student> {
    return this.api.get<Student>(this.endpoint + '/' + id);
  }

  createStudent(student: StudentCreateRequest): Observable<Student> {
    return this.api.post<Student>(this.endpoint, student);
  }

  updateStudent(id: number, student: StudentUpdateRequest): Observable<Student> {
    return this.api.put<Student>(this.endpoint + '/' + id, student);
  }

  deleteStudent(id: number): Observable<void> {
    return this.api.delete<void>(this.endpoint + '/' + id);
  }
}
