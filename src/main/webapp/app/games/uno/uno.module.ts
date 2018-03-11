import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { UNO_ROUTE, UnoComponent } from '../uno';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ UNO_ROUTE ], { useHash: true })
    ],
    declarations: [
      UnoComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiUnoModule {}
