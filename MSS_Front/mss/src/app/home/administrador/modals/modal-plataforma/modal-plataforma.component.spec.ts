import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPlataformaComponent } from './modal-plataforma.component';

describe('ModalPlataformaComponent', () => {
  let component: ModalPlataformaComponent;
  let fixture: ComponentFixture<ModalPlataformaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalPlataformaComponent]
    });
    fixture = TestBed.createComponent(ModalPlataformaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
