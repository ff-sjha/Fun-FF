import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    TournamentFranchisePointsService,
    TournamentFranchisePointsPopupService,
    TournamentFranchisePointsComponent,
    TournamentFranchisePointsDetailComponent,
    TournamentFranchisePointsDialogComponent,
    TournamentFranchisePointsPopupComponent,
    TournamentFranchisePointsDeletePopupComponent,
    TournamentFranchisePointsDeleteDialogComponent,
    tournamentFranchisePointsRoute,
    tournamentFranchisePointsPopupRoute,
    TournamentFranchisePointsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tournamentFranchisePointsRoute,
    ...tournamentFranchisePointsPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TournamentFranchisePointsComponent,
        TournamentFranchisePointsDetailComponent,
        TournamentFranchisePointsDialogComponent,
        TournamentFranchisePointsDeleteDialogComponent,
        TournamentFranchisePointsPopupComponent,
        TournamentFranchisePointsDeletePopupComponent,
    ],
    entryComponents: [
        TournamentFranchisePointsComponent,
        TournamentFranchisePointsDialogComponent,
        TournamentFranchisePointsPopupComponent,
        TournamentFranchisePointsDeleteDialogComponent,
        TournamentFranchisePointsDeletePopupComponent,
    ],
    providers: [
        TournamentFranchisePointsService,
        TournamentFranchisePointsPopupService,
        TournamentFranchisePointsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiTournamentFranchisePointsModule {}
