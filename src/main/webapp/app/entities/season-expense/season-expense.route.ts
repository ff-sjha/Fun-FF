import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SeasonExpenseComponent } from './season-expense.component';
import { SeasonExpenseDetailComponent } from './season-expense-detail.component';
import { SeasonExpensePopupComponent } from './season-expense-dialog.component';
import { SeasonExpenseDeletePopupComponent } from './season-expense-delete-dialog.component';

@Injectable()
export class SeasonExpenseResolvePagingParams implements Resolve<any> {

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

export const seasonExpenseRoute: Routes = [
    {
        path: 'season-expense',
        component: SeasonExpenseComponent,
        resolve: {
            'pagingParams': SeasonExpenseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonExpense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'season-expense/:id',
        component: SeasonExpenseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonExpense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seasonExpensePopupRoute: Routes = [
    {
        path: 'season-expense-new',
        component: SeasonExpensePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonExpense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'season-expense/:id/edit',
        component: SeasonExpensePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonExpense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'season-expense/:id/delete',
        component: SeasonExpenseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fafiApp.seasonExpense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
