import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SportInfoComponent } from './sport-info.component';
import { SportInfoDetailComponent } from './sport-info-detail.component';
import { SportInfoPopupComponent } from './sport-info-dialog.component';
import { SportInfoDeletePopupComponent } from './sport-info-delete-dialog.component';

export const sportInfoRoute: Routes = [
    {
        path: 'sport-info',
        component: SportInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.sportInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sport-info/:id',
        component: SportInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.sportInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sportInfoPopupRoute: Routes = [
    {
        path: 'sport-info-new',
        component: SportInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.sportInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sport-info/:id/edit',
        component: SportInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.sportInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sport-info/:id/delete',
        component: SportInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.sportInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
