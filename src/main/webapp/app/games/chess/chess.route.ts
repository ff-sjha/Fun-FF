import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ChessComponent } from '../chess';

export const CHESS_ROUTE: Route = {
  path: 'chess',
  component: ChessComponent,
  data: {
    authorities: [],
    pageTitle: 'chess.title'
  },
  canActivate: [UserRouteAccessService]
};
