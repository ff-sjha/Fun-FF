import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BowlingComponent } from '../bowling';

export const BOWLING_ROUTE: Route = {
  path: 'bowling',
  component: BowlingComponent,
  data: {
    authorities: [],
    pageTitle: 'bowling.title'
  },
  canActivate: [UserRouteAccessService]
};
