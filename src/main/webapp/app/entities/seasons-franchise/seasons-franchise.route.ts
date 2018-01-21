import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SeasonsFranchiseComponent } from './seasons-franchise.component';
import { SeasonsFranchiseDetailComponent } from './seasons-franchise-detail.component';
import { SeasonsFranchisePopupComponent } from './seasons-franchise-dialog.component';
import { SeasonsFranchiseDeletePopupComponent } from './seasons-franchise-delete-dialog.component';

@Injectable()
export class SeasonsFranchiseResolvePagingParams implements Resolve<any> {

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

export const seasonsFranchiseRoute: Routes = [
    {
        path: 'seasons-franchise',
        component: SeasonsFranchiseComponent,
        resolve: {
            'pagingParams': SeasonsFranchiseResolvePagingParams
        },
        data: {
            authorities: [],
            pageTitle: 'fafiApp.seasonsFranchise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'seasons-franchise/:id',
        component: SeasonsFranchiseDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'fafiApp.seasonsFranchise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seasonsFranchisePopupRoute: Routes = [
    {
        path: 'seasons-franchise-new',
        component: SeasonsFranchisePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fafiApp.seasonsFranchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seasons-franchise/:id/edit',
        component: SeasonsFranchisePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fafiApp.seasonsFranchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seasons-franchise/:id/delete',
        component: SeasonsFranchiseDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'fafiApp.seasonsFranchise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
