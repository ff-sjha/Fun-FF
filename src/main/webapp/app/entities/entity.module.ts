import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FafiSeasonModule } from './season/season.module';
import { FafiPlayerModule } from './player/player.module';
import { FafiFranchiseModule } from './franchise/franchise.module';
import { FafiTournamentModule } from './tournament/tournament.module';
import { FafiMatchModule } from './match/match.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FafiSeasonModule,
        FafiPlayerModule,
        FafiFranchiseModule,
        FafiTournamentModule,
        FafiMatchModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiEntityModule {}
