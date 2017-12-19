import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SeasonComponent } from './season.component';
import { SeasonDetailComponent } from './season-detail.component';
import { SeasonPopupComponent } from './season-dialog.component';
import { SeasonDeletePopupComponent } from './season-delete-dialog.component';

@Injectable()
export class SeasonResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const seasonRoute: Routes = [
    {
        path: 'season',
        component: SeasonComponent,
        resolve: {
            'pagingParams': SeasonResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.season.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'season/:id',
        component: SeasonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.season.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seasonPopupRoute: Routes = [
    {
        path: 'season-new',
        component: SeasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.season.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'season/:id/edit',
        component: SeasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.season.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'season/:id/delete',
        component: SeasonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.season.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
