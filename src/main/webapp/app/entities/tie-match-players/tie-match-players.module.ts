import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    TieMatchPlayersService,
    TieMatchPlayersPopupService,
    TieMatchPlayersComponent,
    TieMatchPlayersDetailComponent,
    TieMatchPlayersDialogComponent,
    TieMatchPlayersPopupComponent,
    TieMatchPlayersDeletePopupComponent,
    TieMatchPlayersDeleteDialogComponent,
    tieMatchPlayersRoute,
    tieMatchPlayersPopupRoute,
    TieMatchPlayersResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tieMatchPlayersRoute,
    ...tieMatchPlayersPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TieMatchPlayersComponent,
        TieMatchPlayersDetailComponent,
        TieMatchPlayersDialogComponent,
        TieMatchPlayersDeleteDialogComponent,
        TieMatchPlayersPopupComponent,
        TieMatchPlayersDeletePopupComponent,
    ],
    entryComponents: [
        TieMatchPlayersComponent,
        TieMatchPlayersDialogComponent,
        TieMatchPlayersPopupComponent,
        TieMatchPlayersDeleteDialogComponent,
        TieMatchPlayersDeletePopupComponent,
    ],
    providers: [
        TieMatchPlayersService,
        TieMatchPlayersPopupService,
        TieMatchPlayersResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiTieMatchPlayersModule {}
