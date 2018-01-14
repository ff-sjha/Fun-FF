import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MatchUmpireComponent } from './match-umpire.component';
import { MatchUmpireDetailComponent } from './match-umpire-detail.component';
import { MatchUmpirePopupComponent } from './match-umpire-dialog.component';
import { MatchUmpireDeletePopupComponent } from './match-umpire-delete-dialog.component';

@Injectable()
export class MatchUmpireResolvePagingParams implements Resolve<any> {

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

export const matchUmpireRoute: Routes = [
    {
        path: 'match-umpire',
        component: MatchUmpireComponent,
        resolve: {
            'pagingParams': MatchUmpireResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchUmpire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'match-umpire/:id',
        component: MatchUmpireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchUmpire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchUmpirePopupRoute: Routes = [
    {
        path: 'match-umpire-new',
        component: MatchUmpirePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchUmpire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-umpire/:id/edit',
        component: MatchUmpirePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchUmpire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-umpire/:id/delete',
        component: MatchUmpireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchUmpire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
