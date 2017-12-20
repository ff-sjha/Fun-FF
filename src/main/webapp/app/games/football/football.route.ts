import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FootballComponent } from '../football';

export const FOOTBALL_ROUTE: Route = {
  path: 'football',
  component: FootballComponent,
  data: {
    authorities: [],
    pageTitle: 'football.title'
  },
  canActivate: [UserRouteAccessService]
};
