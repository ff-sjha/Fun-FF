import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TieMatchComponent } from './tie-match.component';
import { TieMatchDetailComponent } from './tie-match-detail.component';
import { TieMatchPopupComponent } from './tie-match-dialog.component';
import { TieMatchDeletePopupComponent } from './tie-match-delete-dialog.component';

@Injectable()
export class TieMatchResolvePagingParams implements Resolve<any> {

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

export const tieMatchRoute: Routes = [
    {
        path: 'tie-match',
        component: TieMatchComponent,
        resolve: {
            'pagingParams': TieMatchResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatch.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tie-match/:id',
        component: TieMatchDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatch.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tieMatchPopupRoute: Routes = [
    {
        path: 'tie-match-new',
        component: TieMatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-match/:id/edit',
        component: TieMatchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-match/:id/delete',
        component: TieMatchDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
