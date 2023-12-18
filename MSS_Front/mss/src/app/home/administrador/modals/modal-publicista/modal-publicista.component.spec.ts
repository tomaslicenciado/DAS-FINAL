import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPublicistaComponent } from './modal-publicista.component';

describe('ModalPublicistaComponent', () => {
  let component: ModalPublicistaComponent;
  let fixture: ComponentFixture<ModalPublicistaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalPublicistaComponent]
    });
    fixture = TestBed.createComponent(ModalPublicistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
