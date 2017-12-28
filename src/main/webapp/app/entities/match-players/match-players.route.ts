import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MatchPlayersComponent } from './match-players.component';
import { MatchPlayersDetailComponent } from './match-players-detail.component';
import { MatchPlayersPopupComponent } from './match-players-dialog.component';
import { MatchPlayersDeletePopupComponent } from './match-players-delete-dialog.component';

@Injectable()
export class MatchPlayersResolvePagingParams implements Resolve<any> {

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

export const matchPlayersRoute: Routes = [
    {
        path: 'match-players',
        component: MatchPlayersComponent,
        resolve: {
            'pagingParams': MatchPlayersResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'match-players/:id',
        component: MatchPlayersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchPlayersPopupRoute: Routes = [
    {
        path: 'match-players-new',
        component: MatchPlayersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-players/:id/edit',
        component: MatchPlayersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'match-players/:id/delete',
        component: MatchPlayersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.matchPlayers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
