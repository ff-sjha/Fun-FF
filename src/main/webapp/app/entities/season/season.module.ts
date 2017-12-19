import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    SeasonService,
    SeasonPopupService,
    SeasonComponent,
    SeasonDetailComponent,
    SeasonDialogComponent,
    SeasonPopupComponent,
    SeasonDeletePopupComponent,
    SeasonDeleteDialogComponent,
    seasonRoute,
    seasonPopupRoute,
    SeasonResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...seasonRoute,
    ...seasonPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SeasonComponent,
        SeasonDetailComponent,
        SeasonDialogComponent,
        SeasonDeleteDialogComponent,
        SeasonPopupComponent,
        SeasonDeletePopupComponent,
    ],
    entryComponents: [
        SeasonComponent,
        SeasonDialogComponent,
        SeasonPopupComponent,
        SeasonDeleteDialogComponent,
        SeasonDeletePopupComponent,
    ],
    providers: [
        SeasonService,
        SeasonPopupService,
        SeasonResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiSeasonModule {}
