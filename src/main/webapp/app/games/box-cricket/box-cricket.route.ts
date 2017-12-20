import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BoxCricketComponent } from '../box-cricket';

export const BOX_CRICKET_ROUTE: Route = {
  path: 'box-cricket',
  component: BoxCricketComponent,
  data: {
    authorities: [],
    pageTitle: 'box-cricket.title'
  },
  canActivate: [UserRouteAccessService]
};
