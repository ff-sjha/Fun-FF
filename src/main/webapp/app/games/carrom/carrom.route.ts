import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CarromComponent } from '../carrom';

export const CARROM_ROUTE: Route = {
  path: 'carrom',
  component: CarromComponent,
  data: {
    authorities: [],
    pageTitle: 'carrom.title'
  },
  canActivate: [UserRouteAccessService]
};
