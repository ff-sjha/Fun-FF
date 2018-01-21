import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../shared';
import {
    FranchiseInfoComponent,
    FranchiseTeamComponent,
    FRANCHISE_INFO_ROUTE,
} from './';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ FRANCHISE_INFO_ROUTE ], { useHash: true })
    ],
    declarations: [
        FranchiseInfoComponent, FranchiseTeamComponent
    ],
    entryComponents: [
        FranchiseInfoComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiAppFranchiseInfoModule {}
