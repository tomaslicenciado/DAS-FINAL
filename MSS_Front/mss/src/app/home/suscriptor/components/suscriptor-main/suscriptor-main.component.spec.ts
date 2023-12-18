import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuscriptorMainComponent } from './suscriptor-main.component';

describe('SuscriptorMainComponent', () => {
  let component: SuscriptorMainComponent;
  let fixture: ComponentFixture<SuscriptorMainComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuscriptorMainComponent]
    });
    fixture = TestBed.createComponent(SuscriptorMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
