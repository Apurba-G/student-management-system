export interface ApiError {
  status: number;
  error: string;
  message: string;
  timestamp?: string;
  validationErrors?: Record<string, string>;
}
