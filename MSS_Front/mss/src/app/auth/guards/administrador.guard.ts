import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';

export const administradorGuard: CanActivateFn = (route, state) => {
  const mssService = inject(MssApiService);
  const logged = mssService.isLogged();
  const nivel = mssService.getUser().id_nivel;
  let url = '/auth/login';
  if (logged){
    switch (nivel){
      case 1:
        url = '/home/suscriptor';
        break;
      case 2:
        url = '/home/publicista';
        break;
      case 3:
        return true;
      default:
        break;
    }
  }
  const tree: UrlTree = inject(Router).parseUrl(url);
  return tree;
};