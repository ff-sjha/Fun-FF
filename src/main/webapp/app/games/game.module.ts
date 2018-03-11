import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FafiFootballModule } from './football/football.module';
import { FafiBadmintonModule } from './badminton/badminton.module';
import { FafiChessModule } from './chess/chess.module';
import { FafiLudoModule } from './ludo/ludo.module';
import { FafiTableTennisModule } from './table-tennis/table-tennis.module';
import { FafiBoxCricketModule } from './box-cricket/box-cricket.module';
import { FafiUnoModule } from './uno/uno.module';
import { FafiCarromModule } from './carrom/carrom.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FafiFootballModule,
        FafiBadmintonModule,
        FafiChessModule,
        FafiLudoModule,
        FafiTableTennisModule,
        FafiBoxCricketModule,
        FafiUnoModule,
        FafiCarromModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiGameModule {}
