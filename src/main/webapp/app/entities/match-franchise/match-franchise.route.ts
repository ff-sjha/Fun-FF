import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MatchFranchiseComponent } from './match-franchise.component';
import { MatchFranchiseDetailComponent } from './match-franchise-detail.component';
import { MatchFranchisePopupComponent } from './match-franchise-dialog.component';
import { MatchFranchiseDeletePopupComponent } from './match-franchise-delete-dialog.component';

@Injectable()
export class MatchFranchiseResolvePagingParams implements Resolve<any> {

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

export const matchFranchiseRoute: Routes = [
    {
        path: 'match-franchise',
        component: MatchFranchiseComponent,
        resolve: {
            'pagingParams': MatchFranchiseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchFranchise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'match-franchise/:id',
        component: MatchFranchiseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchFranchise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchFranchisePopupRoute: Routes = [
    {
        path: 'match-franchise-new',
        component: MatchFranchisePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchFranchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-franchise/:id/edit',
        component: MatchFranchisePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchFranchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-franchise/:id/delete',
        component: MatchFranchiseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchFranchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
