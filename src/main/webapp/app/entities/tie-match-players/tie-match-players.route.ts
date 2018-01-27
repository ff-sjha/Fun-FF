import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TieMatchPlayersComponent } from './tie-match-players.component';
import { TieMatchPlayersDetailComponent } from './tie-match-players-detail.component';
import { TieMatchPlayersPopupComponent } from './tie-match-players-dialog.component';
import { TieMatchPlayersDeletePopupComponent } from './tie-match-players-delete-dialog.component';

@Injectable()
export class TieMatchPlayersResolvePagingParams implements Resolve<any> {

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

export const tieMatchPlayersRoute: Routes = [
    {
        path: 'tie-match-players',
        component: TieMatchPlayersComponent,
        resolve: {
            'pagingParams': TieMatchPlayersResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tie-match-players/:id',
        component: TieMatchPlayersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tieMatchPlayersPopupRoute: Routes = [
    {
        path: 'tie-match-players-new',
        component: TieMatchPlayersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-match-players/:id/edit',
        component: TieMatchPlayersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-match-players/:id/delete',
        component: TieMatchPlayersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
