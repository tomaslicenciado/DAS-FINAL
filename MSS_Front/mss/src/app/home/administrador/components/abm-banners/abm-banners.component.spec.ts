import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbmBannersComponent } from './abm-banners.component';

describe('AbmBannersComponent', () => {
  let component: AbmBannersComponent;
  let fixture: ComponentFixture<AbmBannersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbmBannersComponent]
    });
    fixture = TestBed.createComponent(AbmBannersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
