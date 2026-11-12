import { Routes } from '@angular/router';
import { StudentList } from './components/student-list/student-list';
import { StudentForm } from './components/student-form/student-form';
import { StudentDetails } from './components/student-details/student-details';
import { authGuard } from '../../core/guards/auth.guard';

export const STUDENTS_ROUTES: Routes = [
  { path: '', component: StudentList, canActivate: [authGuard] },
  { path: 'new', component: StudentForm, canActivate: [authGuard] },
  { path: ':id', component: StudentDetails, canActivate: [authGuard] },
  { path: ':id/edit', component: StudentForm, canActivate: [authGuard] }
];
