import { BaseEntity } from './../../shared';

export class MatchPlayers implements BaseEntity {
    constructor(
        public id?: number,
        public playerOfTheMatch?: boolean,
        public playerPointsEarned?: number,
        public matchId?: number,
        public seasonsFranchisePlayerId?: number,
    ) {
        this.playerOfTheMatch = false;
    }
}
