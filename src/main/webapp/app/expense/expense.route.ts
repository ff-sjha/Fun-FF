import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { ExpenseComponent } from './';

export const EXPENSE_ROUTE: Route = {
  path: 'expense',
  component: ExpenseComponent,
  data: {
    authorities: [],
    pageTitle: 'expense.title'
  },
  canActivate: [UserRouteAccessService]
};
