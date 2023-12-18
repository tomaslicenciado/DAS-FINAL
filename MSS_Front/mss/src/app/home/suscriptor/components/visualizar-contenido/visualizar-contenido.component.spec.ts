import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizarContenidoComponent } from './visualizar-contenido.component';

describe('VisualizarContenidoComponent', () => {
  let component: VisualizarContenidoComponent;
  let fixture: ComponentFixture<VisualizarContenidoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizarContenidoComponent]
    });
    fixture = TestBed.createComponent(VisualizarContenidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
