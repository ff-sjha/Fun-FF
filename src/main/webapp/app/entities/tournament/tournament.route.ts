import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TournamentComponent } from './tournament.component';
import { TournamentDetailComponent } from './tournament-detail.component';
import { TournamentPopupComponent } from './tournament-dialog.component';
import { TournamentDeletePopupComponent } from './tournament-delete-dialog.component';

@Injectable()
export class TournamentResolvePagingParams implements Resolve<any> {

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

export const tournamentRoute: Routes = [
    {
        path: 'tournament',
        component: TournamentComponent,
        resolve: {
            'pagingParams': TournamentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tournament/:id',
        component: TournamentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tournamentPopupRoute: Routes = [
    {
        path: 'tournament-new',
        component: TournamentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tournament/:id/edit',
        component: TournamentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tournament/:id/delete',
        component: TournamentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournament.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
