import { TestBed } from '@angular/core/testing';

import { MssApiService } from './mss-api.service';

describe('MssApiResolverService', () => {
  let service: MssApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MssApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
