import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    MatchUmpireService,
    MatchUmpirePopupService,
    MatchUmpireComponent,
    MatchUmpireDetailComponent,
    MatchUmpireDialogComponent,
    MatchUmpirePopupComponent,
    MatchUmpireDeletePopupComponent,
    MatchUmpireDeleteDialogComponent,
    matchUmpireRoute,
    matchUmpirePopupRoute,
    MatchUmpireResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...matchUmpireRoute,
    ...matchUmpirePopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MatchUmpireComponent,
        MatchUmpireDetailComponent,
        MatchUmpireDialogComponent,
        MatchUmpireDeleteDialogComponent,
        MatchUmpirePopupComponent,
        MatchUmpireDeletePopupComponent,
    ],
    entryComponents: [
        MatchUmpireComponent,
        MatchUmpireDialogComponent,
        MatchUmpirePopupComponent,
        MatchUmpireDeleteDialogComponent,
        MatchUmpireDeletePopupComponent,
    ],
    providers: [
        MatchUmpireService,
        MatchUmpirePopupService,
        MatchUmpireResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiMatchUmpireModule {}
