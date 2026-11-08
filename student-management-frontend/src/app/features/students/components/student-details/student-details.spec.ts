import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StudentDetails } from './student-details';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

describe('StudentDetails', () => {
  let component: StudentDetails;
  let fixture: ComponentFixture<StudentDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentDetails],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])]
    }).compileComponents();

    fixture = TestBed.createComponent(StudentDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
