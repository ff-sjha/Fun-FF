import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { BOLLEY_BALL_ROUTE, VolleyBallComponent } from '../volleyball';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ BOLLEY_BALL_ROUTE ], { useHash: true })
    ],
    declarations: [
        VolleyBallComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiVolleyBallModule {}
