import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbmPublicidadesComponent } from './abm-publicidades.component';

describe('AbmPublicidadesComponent', () => {
  let component: AbmPublicidadesComponent;
  let fixture: ComponentFixture<AbmPublicidadesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbmPublicidadesComponent]
    });
    fixture = TestBed.createComponent(AbmPublicidadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
