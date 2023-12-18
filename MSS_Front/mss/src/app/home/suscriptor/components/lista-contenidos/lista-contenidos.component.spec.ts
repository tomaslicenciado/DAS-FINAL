import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaContenidosComponent } from './lista-contenidos.component';

describe('ListaContenidosComponent', () => {
  let component: ListaContenidosComponent;
  let fixture: ComponentFixture<ListaContenidosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaContenidosComponent]
    });
    fixture = TestBed.createComponent(ListaContenidosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
