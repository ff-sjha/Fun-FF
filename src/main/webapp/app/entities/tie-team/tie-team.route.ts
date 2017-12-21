import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TieTeamComponent } from './tie-team.component';
import { TieTeamDetailComponent } from './tie-team-detail.component';
import { TieTeamPopupComponent } from './tie-team-dialog.component';
import { TieTeamDeletePopupComponent } from './tie-team-delete-dialog.component';

@Injectable()
export class TieTeamResolvePagingParams implements Resolve<any> {

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

export const tieTeamRoute: Routes = [
    {
        path: 'tie-team',
        component: TieTeamComponent,
        resolve: {
            'pagingParams': TieTeamResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieTeam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tie-team/:id',
        component: TieTeamDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieTeam.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tieTeamPopupRoute: Routes = [
    {
        path: 'tie-team-new',
        component: TieTeamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieTeam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-team/:id/edit',
        component: TieTeamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieTeam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-team/:id/delete',
        component: TieTeamDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieTeam.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
