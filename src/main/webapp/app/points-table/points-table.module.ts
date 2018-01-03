import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../shared';
import {
    PointsTableService,
    PointsTableComponent,
    POINTS_TABLE_ROUTE,
} from './';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ POINTS_TABLE_ROUTE ], { useHash: true })
    ],
    declarations: [
      PointsTableComponent,
    ],
    entryComponents: [
        PointsTableComponent,
    ],
    providers: [
        PointsTableService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiAppPointsTableModule {}
