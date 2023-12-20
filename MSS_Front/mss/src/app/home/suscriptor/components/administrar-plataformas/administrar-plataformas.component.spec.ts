import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarPlataformasComponent } from './administrar-plataformas.component';

describe('AdministrarPlataformasComponent', () => {
  let component: AdministrarPlataformasComponent;
  let fixture: ComponentFixture<AdministrarPlataformasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdministrarPlataformasComponent]
    });
    fixture = TestBed.createComponent(AdministrarPlataformasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
