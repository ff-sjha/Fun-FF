import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { VolleyballComponent } from '../volleyball';

export const VOLLEY_BALL_ROUTE: Route = {
  path: 'volleyball',
  component: VolleyballComponent,
  data: {
    authorities: [],
    pageTitle: 'volleyball.title'
  },
  canActivate: [UserRouteAccessService]
};
