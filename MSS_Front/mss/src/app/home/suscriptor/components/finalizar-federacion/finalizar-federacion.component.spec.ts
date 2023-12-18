import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalizarFederacionComponent } from './finalizar-federacion.component';

describe('FinalizarFederacionComponent', () => {
  let component: FinalizarFederacionComponent;
  let fixture: ComponentFixture<FinalizarFederacionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FinalizarFederacionComponent]
    });
    fixture = TestBed.createComponent(FinalizarFederacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
