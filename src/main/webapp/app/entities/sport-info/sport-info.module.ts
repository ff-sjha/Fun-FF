import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';
import {
    SportInfoService,
    SportInfoPopupService,
    SportInfoComponent,
    SportInfoDetailComponent,
    SportInfoDialogComponent,
    SportInfoPopupComponent,
    SportInfoDeletePopupComponent,
    SportInfoDeleteDialogComponent,
    sportInfoRoute,
    sportInfoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sportInfoRoute,
    ...sportInfoPopupRoute,
];

@NgModule({
    imports: [
        FafiSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SportInfoComponent,
        SportInfoDetailComponent,
        SportInfoDialogComponent,
        SportInfoDeleteDialogComponent,
        SportInfoPopupComponent,
        SportInfoDeletePopupComponent,
    ],
    entryComponents: [
        SportInfoComponent,
        SportInfoDialogComponent,
        SportInfoPopupComponent,
        SportInfoDeleteDialogComponent,
        SportInfoDeletePopupComponent,
    ],
    providers: [
        SportInfoService,
        SportInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiSportInfoModule {}
