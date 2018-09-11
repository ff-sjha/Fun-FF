import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { VOLLEY_BALL_ROUTE, VolleyballComponent } from '../volleyball';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ VOLLEY_BALL_ROUTE ], { useHash: true })
    ],
    declarations: [
        VolleyballComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiVolleyballModule {}
