import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { FranchiseInfoComponent } from './';

export const FRANCHISE_INFO_ROUTE: Route = {
  path: 'franchise-info',
  component: FranchiseInfoComponent,
  data: {
    authorities: [],
    pageTitle: 'franchise-info.title'
  }
};
