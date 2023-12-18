import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPublicidadComponent } from './modal-publicidad.component';

describe('ModalPublicidadComponent', () => {
  let component: ModalPublicidadComponent;
  let fixture: ComponentFixture<ModalPublicidadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalPublicidadComponent]
    });
    fixture = TestBed.createComponent(ModalPublicidadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
