import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FafiSeasonModule } from './season/season.module';
import { FafiPlayerModule } from './player/player.module';
import { FafiFranchiseModule } from './franchise/franchise.module';
import { FafiTournamentModule } from './tournament/tournament.module';
import { FafiMatchModule } from './match/match.module';
import { FafiSeasonsFranchiseModule } from './seasons-franchise/seasons-franchise.module';
import { FafiSeasonsFranchisePlayerModule } from './seasons-franchise-player/seasons-franchise-player.module';
import { FafiMatchPlayersModule } from './match-players/match-players.module';
import { FafiNewsModule } from './news/news.module';
import { FafiTournamentFranchisePointsModule } from './tournament-franchise-points/tournament-franchise-points.module';
import { FafiMatchUmpireModule } from './match-umpire/match-umpire.module';
import { FafiSeasonExpenseModule } from './season-expense/season-expense.module';
import { FafiTieMatchModule } from './tie-match/tie-match.module';
import { FafiTieMatchPlayersModule } from './tie-match-players/tie-match-players.module';
import { FafiTieMatchSetsModule } from './tie-match-sets/tie-match-sets.module';
import { FafiSportInfoModule } from './sport-info/sport-info.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FafiSeasonModule,
        FafiPlayerModule,
        FafiFranchiseModule,
        FafiTournamentModule,
        FafiMatchModule,
        FafiSeasonsFranchiseModule,
        FafiSeasonsFranchisePlayerModule,
        FafiMatchPlayersModule,
        FafiNewsModule,
        FafiTournamentFranchisePointsModule,
        FafiMatchUmpireModule,
        FafiSeasonExpenseModule,
        FafiTieMatchModule,
        FafiTieMatchPlayersModule,
        FafiTieMatchSetsModule,
        FafiSportInfoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FafiEntityModule {}
