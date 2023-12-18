import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarraFiltroComponent } from './barra-filtro.component';

describe('BarraFiltroComponent', () => {
  let component: BarraFiltroComponent;
  let fixture: ComponentFixture<BarraFiltroComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BarraFiltroComponent]
    });
    fixture = TestBed.createComponent(BarraFiltroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
