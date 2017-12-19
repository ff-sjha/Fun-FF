import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { PointsTableComponent } from './';

export const POINTS_TABLE_ROUTE: Route = {
  path: 'points-table',
  component: PointsTableComponent,
  data: {
    authorities: [],
    pageTitle: 'points-table.title'
  },
  canActivate: [UserRouteAccessService]
};
