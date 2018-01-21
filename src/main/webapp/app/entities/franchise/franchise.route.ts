import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FranchiseComponent } from './franchise.component';
import { FranchiseDetailComponent } from './franchise-detail.component';
import { FranchisePopupComponent } from './franchise-dialog.component';
import { FranchiseDeletePopupComponent } from './franchise-delete-dialog.component';

@Injectable()
export class FranchiseResolvePagingParams implements Resolve<any> {

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

export const franchiseRoute: Routes = [
    {
        path: 'franchise',
        component: FranchiseComponent,
        resolve: {
            'pagingParams': FranchiseResolvePagingParams
        },
        data: {
            authorities: [],
            pageTitle: 'fafiApp.franchise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'franchise/:id',
        component: FranchiseDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'fafiApp.franchise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const franchisePopupRoute: Routes = [
    {
        path: 'franchise-new',
        component: FranchisePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.franchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'franchise/:id/edit',
        component: FranchisePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.franchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'franchise/:id/delete',
        component: FranchiseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.franchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
