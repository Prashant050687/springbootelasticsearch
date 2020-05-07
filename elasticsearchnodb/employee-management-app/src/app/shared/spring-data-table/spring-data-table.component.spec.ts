import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpringDataTableComponent } from './spring-data-table.component';

describe('SpringDataTableComponent', () => {
  let component: SpringDataTableComponent;
  let fixture: ComponentFixture<SpringDataTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SpringDataTableComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpringDataTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
