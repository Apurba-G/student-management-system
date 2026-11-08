export interface Student {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber?: string;
  dateOfBirth?: string;
  department: string;
  enrollmentNumber: string;
  status: 'ACTIVE' | 'INACTIVE' | 'SUSPENDED' | string;
  createdAt?: string;
  updatedAt?: string;
}
