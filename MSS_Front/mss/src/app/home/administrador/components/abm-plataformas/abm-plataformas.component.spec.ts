import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbmPlataformasComponent } from './abm-plataformas.component';

describe('AbmPlataformasComponent', () => {
  let component: AbmPlataformasComponent;
  let fixture: ComponentFixture<AbmPlataformasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbmPlataformasComponent]
    });
    fixture = TestBed.createComponent(AbmPlataformasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
