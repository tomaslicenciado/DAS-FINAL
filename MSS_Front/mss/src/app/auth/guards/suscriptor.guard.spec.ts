import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { suscriptorGuard } from './suscriptor.guard';

describe('suscriptorGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => suscriptorGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
