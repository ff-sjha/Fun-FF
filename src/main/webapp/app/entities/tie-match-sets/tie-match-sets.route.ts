import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TieMatchSetsComponent } from './tie-match-sets.component';
import { TieMatchSetsDetailComponent } from './tie-match-sets-detail.component';
import { TieMatchSetsPopupComponent } from './tie-match-sets-dialog.component';
import { TieMatchSetsDeletePopupComponent } from './tie-match-sets-delete-dialog.component';

@Injectable()
export class TieMatchSetsResolvePagingParams implements Resolve<any> {

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

export const tieMatchSetsRoute: Routes = [
    {
        path: 'tie-match-sets',
        component: TieMatchSetsComponent,
        resolve: {
            'pagingParams': TieMatchSetsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchSets.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tie-match-sets/:id',
        component: TieMatchSetsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchSets.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tieMatchSetsPopupRoute: Routes = [
    {
        path: 'tie-match-sets-new',
        component: TieMatchSetsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchSets.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-match-sets/:id/edit',
        component: TieMatchSetsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchSets.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tie-match-sets/:id/delete',
        component: TieMatchSetsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.tieMatchSets.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
