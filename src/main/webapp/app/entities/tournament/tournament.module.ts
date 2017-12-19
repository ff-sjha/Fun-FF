import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    TournamentService,
    TournamentPopupService,
    TournamentComponent,
    TournamentDetailComponent,
    TournamentDialogComponent,
    TournamentPopupComponent,
    TournamentDeletePopupComponent,
    TournamentDeleteDialogComponent,
    tournamentRoute,
    tournamentPopupRoute,
    TournamentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tournamentRoute,
    ...tournamentPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TournamentComponent,
        TournamentDetailComponent,
        TournamentDialogComponent,
        TournamentDeleteDialogComponent,
        TournamentPopupComponent,
        TournamentDeletePopupComponent,
    ],
    entryComponents: [
        TournamentComponent,
        TournamentDialogComponent,
        TournamentPopupComponent,
        TournamentDeleteDialogComponent,
        TournamentDeletePopupComponent,
    ],
    providers: [
        TournamentService,
        TournamentPopupService,
        TournamentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiTournamentModule {}
