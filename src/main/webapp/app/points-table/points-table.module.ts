import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../shared';

import { POINTS_TABLE_ROUTE, PointsTableComponent } from './';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ POINTS_TABLE_ROUTE ], { useHash: true })
    ],
    declarations: [
      PointsTableComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiAppPointsTableModule {}
