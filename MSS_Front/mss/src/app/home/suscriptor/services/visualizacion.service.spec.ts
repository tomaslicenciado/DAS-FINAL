import { TestBed } from '@angular/core/testing';

import { VisualizacionService } from './visualizacion.service';

describe('VisualizacionService', () => {
  let service: VisualizacionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VisualizacionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
