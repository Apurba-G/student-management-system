import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-student-delete-dialog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student-delete-dialog.html',
  styleUrl: './student-delete-dialog.css'
})
export class StudentDeleteDialog {
  @Input() studentName = '';
  @Output() confirmed = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  onConfirm(): void {
    this.confirmed.emit();
  }

  onCancel(): void {
    this.cancelled.emit();
  }
}
