import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BadmintonComponent } from '../badminton';

export const BADMINTON_ROUTE: Route = {
  path: 'badminton',
  component: BadmintonComponent,
  data: {
    authorities: [],
    pageTitle: 'badminton.title'
  },
  canActivate: [UserRouteAccessService]
};
