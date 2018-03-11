import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UnoComponent } from '../uno';

export const UNO_ROUTE: Route = {
  path: 'uno',
  component: UnoComponent,
  data: {
    authorities: [],
    pageTitle: 'uno.title'
  },
  canActivate: [UserRouteAccessService]
};
