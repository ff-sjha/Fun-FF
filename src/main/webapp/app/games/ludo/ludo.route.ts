import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LudoComponent } from '../ludo';

export const LUDO_ROUTE: Route = {
  path: 'ludo',
  component: LudoComponent,
  data: {
    authorities: [],
    pageTitle: 'ludo.title'
  },
  canActivate: [UserRouteAccessService]
};
