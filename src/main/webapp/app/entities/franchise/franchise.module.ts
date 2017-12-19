import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    FranchiseService,
    FranchisePopupService,
    FranchiseComponent,
    FranchiseDetailComponent,
    FranchiseDialogComponent,
    FranchisePopupComponent,
    FranchiseDeletePopupComponent,
    FranchiseDeleteDialogComponent,
    franchiseRoute,
    franchisePopupRoute,
    FranchiseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...franchiseRoute,
    ...franchisePopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FranchiseComponent,
        FranchiseDetailComponent,
        FranchiseDialogComponent,
        FranchiseDeleteDialogComponent,
        FranchisePopupComponent,
        FranchiseDeletePopupComponent,
    ],
    entryComponents: [
        FranchiseComponent,
        FranchiseDialogComponent,
        FranchisePopupComponent,
        FranchiseDeleteDialogComponent,
        FranchiseDeletePopupComponent,
    ],
    providers: [
        FranchiseService,
        FranchisePopupService,
        FranchiseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiFranchiseModule {}
