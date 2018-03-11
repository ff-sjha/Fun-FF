import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../shared';

import { EXPENSE_ROUTE, ExpenseComponent } from './';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ EXPENSE_ROUTE ], { useHash: true })
    ],
    declarations: [
      ExpenseComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiAppExpenseModule {}
