import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../shared';

import { FIXTURE_ROUTE, FixtureComponent } from './';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ FIXTURE_ROUTE ], { useHash: true })
    ],
    declarations: [
      FixtureComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiAppFixtureModule {}
