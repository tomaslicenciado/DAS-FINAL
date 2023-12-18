import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemContenidoComponent } from './item-contenido.component';

describe('ItemContenidoComponent', () => {
  let component: ItemContenidoComponent;
  let fixture: ComponentFixture<ItemContenidoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ItemContenidoComponent]
    });
    fixture = TestBed.createComponent(ItemContenidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
