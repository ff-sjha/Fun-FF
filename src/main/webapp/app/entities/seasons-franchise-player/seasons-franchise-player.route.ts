import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SeasonsFranchisePlayerComponent } from './seasons-franchise-player.component';
import { SeasonsFranchisePlayerDetailComponent } from './seasons-franchise-player-detail.component';
import { SeasonsFranchisePlayerPopupComponent } from './seasons-franchise-player-dialog.component';
import { SeasonsFranchisePlayerDeletePopupComponent } from './seasons-franchise-player-delete-dialog.component';

@Injectable()
export class SeasonsFranchisePlayerResolvePagingParams implements Resolve<any> {

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

export const seasonsFranchisePlayerRoute: Routes = [
    {
        path: 'seasons-franchise-player',
        component: SeasonsFranchisePlayerComponent,
        resolve: {
            'pagingParams': SeasonsFranchisePlayerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonsFranchisePlayer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'seasons-franchise-player/:id',
        component: SeasonsFranchisePlayerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonsFranchisePlayer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seasonsFranchisePlayerPopupRoute: Routes = [
    {
        path: 'seasons-franchise-player-new',
        component: SeasonsFranchisePlayerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonsFranchisePlayer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seasons-franchise-player/:id/edit',
        component: SeasonsFranchisePlayerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonsFranchisePlayer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seasons-franchise-player/:id/delete',
        component: SeasonsFranchisePlayerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonsFranchisePlayer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
