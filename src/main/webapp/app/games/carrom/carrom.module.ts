import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { CARROM_ROUTE, CarromComponent } from '../carrom';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ CARROM_ROUTE ], { useHash: true })
    ],
    declarations: [
      CarromComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiCarromModule {}
