import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { VolleyBallComponent } from '../volleyball';

export const BOX_CRICKET_ROUTE: Route = {
  path: 'volleyball',
  component: VolleyBallComponent,
  data: {
    authorities: [],
    pageTitle: 'volleyball.title'
  },
  canActivate: [UserRouteAccessService]
};
