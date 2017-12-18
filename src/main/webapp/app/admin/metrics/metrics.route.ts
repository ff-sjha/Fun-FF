import { Route } from '@angular/router';

import { FafiMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
    path: 'fafi-metrics',
    component: FafiMetricsMonitoringComponent,
    data: {
        pageTitle: 'metrics.title'
    }
};
