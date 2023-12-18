import { TestBed } from '@angular/core/testing';

import { MssApiRestResourceService } from './mss-api-rest-resource.service';

describe('MssApiRestResourceService', () => {
  let service: MssApiRestResourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MssApiRestResourceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
