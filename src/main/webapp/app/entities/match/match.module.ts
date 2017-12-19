import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    MatchService,
    MatchPopupService,
    MatchComponent,
    MatchDetailComponent,
    MatchDialogComponent,
    MatchPopupComponent,
    MatchDeletePopupComponent,
    MatchDeleteDialogComponent,
    matchRoute,
    matchPopupRoute,
    MatchResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...matchRoute,
    ...matchPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MatchComponent,
        MatchDetailComponent,
        MatchDialogComponent,
        MatchDeleteDialogComponent,
        MatchPopupComponent,
        MatchDeletePopupComponent,
    ],
    entryComponents: [
        MatchComponent,
        MatchDialogComponent,
        MatchPopupComponent,
        MatchDeleteDialogComponent,
        MatchDeletePopupComponent,
    ],
    providers: [
        MatchService,
        MatchPopupService,
        MatchResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiMatchModule {}
