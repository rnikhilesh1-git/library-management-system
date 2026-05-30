import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueReturn } from './issue-return';

describe('IssueReturn', () => {
  let component: IssueReturn;
  let fixture: ComponentFixture<IssueReturn>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssueReturn],
    }).compileComponents();

    fixture = TestBed.createComponent(IssueReturn);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
