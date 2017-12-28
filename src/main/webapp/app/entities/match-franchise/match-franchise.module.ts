import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    MatchFranchiseService,
    MatchFranchisePopupService,
    MatchFranchiseComponent,
    MatchFranchiseDetailComponent,
    MatchFranchiseDialogComponent,
    MatchFranchisePopupComponent,
    MatchFranchiseDeletePopupComponent,
    MatchFranchiseDeleteDialogComponent,
    matchFranchiseRoute,
    matchFranchisePopupRoute,
    MatchFranchiseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...matchFranchiseRoute,
    ...matchFranchisePopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MatchFranchiseComponent,
        MatchFranchiseDetailComponent,
        MatchFranchiseDialogComponent,
        MatchFranchiseDeleteDialogComponent,
        MatchFranchisePopupComponent,
        MatchFranchiseDeletePopupComponent,
    ],
    entryComponents: [
        MatchFranchiseComponent,
        MatchFranchiseDialogComponent,
        MatchFranchisePopupComponent,
        MatchFranchiseDeleteDialogComponent,
        MatchFranchiseDeletePopupComponent,
    ],
    providers: [
        MatchFranchiseService,
        MatchFranchisePopupService,
        MatchFranchiseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiMatchFranchiseModule {}
