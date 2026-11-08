import { Student } from './student';

export interface StudentPage {
  content: Student[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}
