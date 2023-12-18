import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministradorMainComponent } from './administrador-main.component';

describe('AdministradorMainComponent', () => {
  let component: AdministradorMainComponent;
  let fixture: ComponentFixture<AdministradorMainComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdministradorMainComponent]
    });
    fixture = TestBed.createComponent(AdministradorMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
