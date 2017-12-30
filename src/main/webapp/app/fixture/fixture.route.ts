import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { FixtureComponent } from './';

export const FIXTURE_ROUTE: Route = {
  path: 'fixture',
  component: FixtureComponent,
  data: {
    authorities: [],
    pageTitle: 'fixture.title'
  },
  canActivate: [UserRouteAccessService]
};
