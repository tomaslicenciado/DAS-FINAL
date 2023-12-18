import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { publicistaGuard } from './publicista.guard';

describe('publicistaGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => publicistaGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
