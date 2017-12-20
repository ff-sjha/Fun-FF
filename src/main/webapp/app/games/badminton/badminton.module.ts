import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { BADMINTON_ROUTE, BadmintonComponent } from '../badminton';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ BADMINTON_ROUTE ], { useHash: true })
    ],
    declarations: [
      BadmintonComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiBadmintonModule {}
