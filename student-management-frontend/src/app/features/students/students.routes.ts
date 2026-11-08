import { Routes } from '@angular/router';
import { StudentList } from './components/student-list/student-list';
import { StudentForm } from './components/student-form/student-form';
import { StudentDetails } from './components/student-details/student-details';

export const STUDENTS_ROUTES: Routes = [
  { path: '', component: StudentList },
  { path: 'new', component: StudentForm },
  { path: ':id', component: StudentDetails },
  { path: ':id/edit', component: StudentForm }
];
