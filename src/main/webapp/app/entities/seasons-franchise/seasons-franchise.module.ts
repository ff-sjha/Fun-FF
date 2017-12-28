import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    SeasonsFranchiseService,
    SeasonsFranchisePopupService,
    SeasonsFranchiseComponent,
    SeasonsFranchiseDetailComponent,
    SeasonsFranchiseDialogComponent,
    SeasonsFranchisePopupComponent,
    SeasonsFranchiseDeletePopupComponent,
    SeasonsFranchiseDeleteDialogComponent,
    seasonsFranchiseRoute,
    seasonsFranchisePopupRoute,
    SeasonsFranchiseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...seasonsFranchiseRoute,
    ...seasonsFranchisePopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SeasonsFranchiseComponent,
        SeasonsFranchiseDetailComponent,
        SeasonsFranchiseDialogComponent,
        SeasonsFranchiseDeleteDialogComponent,
        SeasonsFranchisePopupComponent,
        SeasonsFranchiseDeletePopupComponent,
    ],
    entryComponents: [
        SeasonsFranchiseComponent,
        SeasonsFranchiseDialogComponent,
        SeasonsFranchisePopupComponent,
        SeasonsFranchiseDeleteDialogComponent,
        SeasonsFranchiseDeletePopupComponent,
    ],
    providers: [
        SeasonsFranchiseService,
        SeasonsFranchisePopupService,
        SeasonsFranchiseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiSeasonsFranchiseModule {}
