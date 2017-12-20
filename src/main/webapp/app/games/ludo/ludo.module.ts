import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { LUDO_ROUTE, LudoComponent } from '../ludo';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ LUDO_ROUTE ], { useHash: true })
    ],
    declarations: [
      LudoComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiLudoModule {}
