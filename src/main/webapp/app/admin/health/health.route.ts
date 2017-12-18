import { Route } from '@angular/router';

import { FafiHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'fafi-health',
    component: FafiHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
