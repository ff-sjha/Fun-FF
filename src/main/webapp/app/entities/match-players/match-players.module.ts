import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    MatchPlayersService,
    MatchPlayersPopupService,
    MatchPlayersComponent,
    MatchPlayersDetailComponent,
    MatchPlayersDialogComponent,
    MatchPlayersPopupComponent,
    MatchPlayersDeletePopupComponent,
    MatchPlayersDeleteDialogComponent,
    matchPlayersRoute,
    matchPlayersPopupRoute,
    MatchPlayersResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...matchPlayersRoute,
    ...matchPlayersPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MatchPlayersComponent,
        MatchPlayersDetailComponent,
        MatchPlayersDialogComponent,
        MatchPlayersDeleteDialogComponent,
        MatchPlayersPopupComponent,
        MatchPlayersDeletePopupComponent,
    ],
    entryComponents: [
        MatchPlayersComponent,
        MatchPlayersDialogComponent,
        MatchPlayersPopupComponent,
        MatchPlayersDeleteDialogComponent,
        MatchPlayersDeletePopupComponent,
    ],
    providers: [
        MatchPlayersService,
        MatchPlayersPopupService,
        MatchPlayersResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiMatchPlayersModule {}
