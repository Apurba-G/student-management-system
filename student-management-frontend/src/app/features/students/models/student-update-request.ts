export interface StudentUpdateRequest {
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber?: string;
  dateOfBirth?: string;
  department: string;
  enrollmentNumber: string;
  status: string;
}
