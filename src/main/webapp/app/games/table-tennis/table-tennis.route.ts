import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TableTennisComponent } from '../table-tennis';

export const TABLE_TENNIS_ROUTE: Route = {
  path: 'table-tennis',
  component: TableTennisComponent,
  data: {
    authorities: [],
    pageTitle: 'table-tennis.title'
  },
  canActivate: [UserRouteAccessService]
};
