import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    TieMatchService,
    TieMatchPopupService,
    TieMatchComponent,
    TieMatchDetailComponent,
    TieMatchDialogComponent,
    TieMatchPopupComponent,
    TieMatchDeletePopupComponent,
    TieMatchDeleteDialogComponent,
    tieMatchRoute,
    tieMatchPopupRoute,
    TieMatchResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tieMatchRoute,
    ...tieMatchPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TieMatchComponent,
        TieMatchDetailComponent,
        TieMatchDialogComponent,
        TieMatchDeleteDialogComponent,
        TieMatchPopupComponent,
        TieMatchDeletePopupComponent,
    ],
    entryComponents: [
        TieMatchComponent,
        TieMatchDialogComponent,
        TieMatchPopupComponent,
        TieMatchDeleteDialogComponent,
        TieMatchDeletePopupComponent,
    ],
    providers: [
        TieMatchService,
        TieMatchPopupService,
        TieMatchResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiTieMatchModule {}
