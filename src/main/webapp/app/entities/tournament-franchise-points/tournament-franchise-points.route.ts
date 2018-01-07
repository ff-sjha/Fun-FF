import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TournamentFranchisePointsComponent } from './tournament-franchise-points.component';
import { TournamentFranchisePointsDetailComponent } from './tournament-franchise-points-detail.component';
import { TournamentFranchisePointsPopupComponent } from './tournament-franchise-points-dialog.component';
import { TournamentFranchisePointsDeletePopupComponent } from './tournament-franchise-points-delete-dialog.component';

@Injectable()
export class TournamentFranchisePointsResolvePagingParams implements Resolve<any> {

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

export const tournamentFranchisePointsRoute: Routes = [
    {
        path: 'tournament-franchise-points',
        component: TournamentFranchisePointsComponent,
        resolve: {
            'pagingParams': TournamentFranchisePointsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournamentFranchisePoints.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tournament-franchise-points/:id',
        component: TournamentFranchisePointsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournamentFranchisePoints.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tournamentFranchisePointsPopupRoute: Routes = [
    {
        path: 'tournament-franchise-points-new',
        component: TournamentFranchisePointsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournamentFranchisePoints.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tournament-franchise-points/:id/edit',
        component: TournamentFranchisePointsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournamentFranchisePoints.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tournament-franchise-points/:id/delete',
        component: TournamentFranchisePointsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tournamentFranchisePoints.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
