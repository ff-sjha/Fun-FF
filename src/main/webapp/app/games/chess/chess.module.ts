import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FafiSharedModule } from '../../shared';

import { CHESS_ROUTE, ChessComponent } from '../chess';

@NgModule({
    imports: [
      FafiSharedModule,
      RouterModule.forRoot([ CHESS_ROUTE ], { useHash: true })
    ],
    declarations: [
      ChessComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiChessModule {}
