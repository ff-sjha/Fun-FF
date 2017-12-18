import { Route } from '@angular/router';

import { FafiConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'fafi-configuration',
    component: FafiConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
