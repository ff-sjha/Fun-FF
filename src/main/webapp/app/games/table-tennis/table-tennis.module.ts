import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { TABLE_TENNIS_ROUTE, TableTennisComponent } from '../table-tennis';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ TABLE_TENNIS_ROUTE ], { useHash: true })
    ],
    declarations: [
      TableTennisComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiTableTennisModule {}
