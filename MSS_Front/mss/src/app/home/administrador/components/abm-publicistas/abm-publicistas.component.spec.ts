import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbmPublicistasComponent } from './abm-publicistas.component';

describe('AbmPublicistasComponent', () => {
  let component: AbmPublicistasComponent;
  let fixture: ComponentFixture<AbmPublicistasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbmPublicistasComponent]
    });
    fixture = TestBed.createComponent(AbmPublicistasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
