import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { BOX_CRICKET_ROUTE, BoxCricketComponent } from '../box-cricket';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ BOX_CRICKET_ROUTE ], { useHash: true })
    ],
    declarations: [
      BoxCricketComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiBoxCricketModule {}
