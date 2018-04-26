import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { BOWLING_ROUTE, BowlingComponent } from '../bowling';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ BOWLING_ROUTE ], { useHash: true })
    ],
    declarations: [
      BowlingComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiBowlingModule {}
