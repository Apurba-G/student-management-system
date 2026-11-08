import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { StudentService } from '../../../students/services/student.service';
import { DashboardStats } from '../dashboard-stats/dashboard-stats';
import { DashboardSidebar } from '../dashboard-sidebar/dashboard-sidebar';
import { Student } from '../../../students/models/student';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, DashboardStats, DashboardSidebar],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {
  private studentService = inject(StudentService);

  totalStudents = 0;
  activeStudents = 0;
  inactiveStudents = 0;
  recentStudents: Student[] = [];
  loading = false;

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.loading = true;
    this.studentService.getStudents(0, 5, '', 'id', 'desc').subscribe({
      next: (page) => {
        this.totalStudents = page.totalElements || 0;
        this.recentStudents = page.content || [];
        this.activeStudents = (page.content || []).filter(s => s.status === 'ACTIVE').length;
        this.inactiveStudents = (page.content || []).filter(s => s.status !== 'ACTIVE').length;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
