import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    PlayerService,
    PlayerPopupService,
    PlayerComponent,
    PlayerDetailComponent,
    PlayerDialogComponent,
    PlayerPopupComponent,
    PlayerDeletePopupComponent,
    PlayerDeleteDialogComponent,
    playerRoute,
    playerPopupRoute,
    PlayerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...playerRoute,
    ...playerPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlayerComponent,
        PlayerDetailComponent,
        PlayerDialogComponent,
        PlayerDeleteDialogComponent,
        PlayerPopupComponent,
        PlayerDeletePopupComponent,
    ],
    entryComponents: [
        PlayerComponent,
        PlayerDialogComponent,
        PlayerPopupComponent,
        PlayerDeleteDialogComponent,
        PlayerDeletePopupComponent,
    ],
    providers: [
        PlayerService,
        PlayerPopupService,
        PlayerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiPlayerModule {}
