import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StudentDeleteDialog } from './student-delete-dialog';

describe('StudentDeleteDialog', () => {
  let component: StudentDeleteDialog;
  let fixture: ComponentFixture<StudentDeleteDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentDeleteDialog]
    }).compileComponents();

    fixture = TestBed.createComponent(StudentDeleteDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
