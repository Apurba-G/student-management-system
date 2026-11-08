import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-empty-state',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './empty-state.html',
  styleUrl: './empty-state.css'
})
export class EmptyState {
  @Input() title = 'No Data Found';
  @Input() description = 'There are currently no items to display.';
  @Input() actionLabel?: string;
  @Output() actionClicked = new EventEmitter<void>();
}
