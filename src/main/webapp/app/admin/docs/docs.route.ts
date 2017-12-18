import { Route } from '@angular/router';

import { FafiDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: FafiDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
