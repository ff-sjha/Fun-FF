import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    TieTeamService,
    TieTeamPopupService,
    TieTeamComponent,
    TieTeamDetailComponent,
    TieTeamDialogComponent,
    TieTeamPopupComponent,
    TieTeamDeletePopupComponent,
    TieTeamDeleteDialogComponent,
    tieTeamRoute,
    tieTeamPopupRoute,
    TieTeamResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tieTeamRoute,
    ...tieTeamPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TieTeamComponent,
        TieTeamDetailComponent,
        TieTeamDialogComponent,
        TieTeamDeleteDialogComponent,
        TieTeamPopupComponent,
        TieTeamDeletePopupComponent,
    ],
    entryComponents: [
        TieTeamComponent,
        TieTeamDialogComponent,
        TieTeamPopupComponent,
        TieTeamDeleteDialogComponent,
        TieTeamDeletePopupComponent,
    ],
    providers: [
        TieTeamService,
        TieTeamPopupService,
        TieTeamResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiTieTeamModule {}
