import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    TieMatchSetsService,
    TieMatchSetsPopupService,
    TieMatchSetsComponent,
    TieMatchSetsDetailComponent,
    TieMatchSetsDialogComponent,
    TieMatchSetsPopupComponent,
    TieMatchSetsDeletePopupComponent,
    TieMatchSetsDeleteDialogComponent,
    tieMatchSetsRoute,
    tieMatchSetsPopupRoute,
    TieMatchSetsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tieMatchSetsRoute,
    ...tieMatchSetsPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TieMatchSetsComponent,
        TieMatchSetsDetailComponent,
        TieMatchSetsDialogComponent,
        TieMatchSetsDeleteDialogComponent,
        TieMatchSetsPopupComponent,
        TieMatchSetsDeletePopupComponent,
    ],
    entryComponents: [
        TieMatchSetsComponent,
        TieMatchSetsDialogComponent,
        TieMatchSetsPopupComponent,
        TieMatchSetsDeleteDialogComponent,
        TieMatchSetsDeletePopupComponent,
    ],
    providers: [
        TieMatchSetsService,
        TieMatchSetsPopupService,
        TieMatchSetsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiTieMatchSetsModule {}
