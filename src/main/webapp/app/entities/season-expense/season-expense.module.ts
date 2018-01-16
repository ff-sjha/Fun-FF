import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    SeasonExpenseService,
    SeasonExpensePopupService,
    SeasonExpenseComponent,
    SeasonExpenseDetailComponent,
    SeasonExpenseDialogComponent,
    SeasonExpensePopupComponent,
    SeasonExpenseDeletePopupComponent,
    SeasonExpenseDeleteDialogComponent,
    seasonExpenseRoute,
    seasonExpensePopupRoute,
    SeasonExpenseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...seasonExpenseRoute,
    ...seasonExpensePopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SeasonExpenseComponent,
        SeasonExpenseDetailComponent,
        SeasonExpenseDialogComponent,
        SeasonExpenseDeleteDialogComponent,
        SeasonExpensePopupComponent,
        SeasonExpenseDeletePopupComponent,
    ],
    entryComponents: [
        SeasonExpenseComponent,
        SeasonExpenseDialogComponent,
        SeasonExpensePopupComponent,
        SeasonExpenseDeleteDialogComponent,
        SeasonExpenseDeletePopupComponent,
    ],
    providers: [
        SeasonExpenseService,
        SeasonExpensePopupService,
        SeasonExpenseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiSeasonExpenseModule {}
