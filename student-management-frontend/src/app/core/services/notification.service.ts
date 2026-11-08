import { Injectable, signal, computed } from '@angular/core';

export interface Notification {
  message: string;
  type: 'success' | 'error' | 'info';
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationSignal = signal<Notification | null>(null);
  readonly currentNotification = computed(() => this.notificationSignal());

  success(message: string): void {
    this.show(message, 'success');
  }

  error(message: string): void {
    this.show(message, 'error');
  }

  info(message: string): void {
    this.show(message, 'info');
  }

  clear(): void {
    this.notificationSignal.set(null);
  }

  private show(message: string, type: 'success' | 'error' | 'info'): void {
    this.notificationSignal.set({ message, type });
    setTimeout(() => {
      this.clear();
    }, 4000);
  }
}
