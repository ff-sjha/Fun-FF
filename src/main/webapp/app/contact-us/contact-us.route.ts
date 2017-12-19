import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { ContactUsComponent } from './';

export const CONTACT_US_ROUTE: Route = {
  path: 'contact-us',
  component: ContactUsComponent,
  data: {
    authorities: [],
    pageTitle: 'contact-us.title'
  },
  canActivate: [UserRouteAccessService]
};
