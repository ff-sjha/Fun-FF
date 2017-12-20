import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { FOOTBALL_ROUTE, FootballComponent } from '../football';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ FOOTBALL_ROUTE ], { useHash: true })
    ],
    declarations: [
      FootballComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiFootballModule {}
