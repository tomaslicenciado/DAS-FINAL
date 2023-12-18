import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';

export const authGuard: CanActivateFn = (route, state) => {
  const mssService = inject(MssApiService)
  if (mssService.isLogged())
    return true;
  const tree: UrlTree = inject(Router).parseUrl('/auth/login');
  return tree;
};
