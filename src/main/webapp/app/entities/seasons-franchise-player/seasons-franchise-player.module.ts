import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    SeasonsFranchisePlayerService,
    SeasonsFranchisePlayerPopupService,
    SeasonsFranchisePlayerComponent,
    SeasonsFranchisePlayerDetailComponent,
    SeasonsFranchisePlayerDialogComponent,
    SeasonsFranchisePlayerPopupComponent,
    SeasonsFranchisePlayerDeletePopupComponent,
    SeasonsFranchisePlayerDeleteDialogComponent,
    seasonsFranchisePlayerRoute,
    seasonsFranchisePlayerPopupRoute,
    SeasonsFranchisePlayerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...seasonsFranchisePlayerRoute,
    ...seasonsFranchisePlayerPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SeasonsFranchisePlayerComponent,
        SeasonsFranchisePlayerDetailComponent,
        SeasonsFranchisePlayerDialogComponent,
        SeasonsFranchisePlayerDeleteDialogComponent,
        SeasonsFranchisePlayerPopupComponent,
        SeasonsFranchisePlayerDeletePopupComponent,
    ],
    entryComponents: [
        SeasonsFranchisePlayerComponent,
        SeasonsFranchisePlayerDialogComponent,
        SeasonsFranchisePlayerPopupComponent,
        SeasonsFranchisePlayerDeleteDialogComponent,
        SeasonsFranchisePlayerDeletePopupComponent,
    ],
    providers: [
        SeasonsFranchisePlayerService,
        SeasonsFranchisePlayerPopupService,
        SeasonsFranchisePlayerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiSeasonsFranchisePlayerModule {}
