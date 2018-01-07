import { BaseEntity } from './../../shared';

export class TournamentFranchisePoints implements BaseEntity {
    constructor(
        public id?: number,
        public points?: number,
        public tournamentId?: number,
        public franchiseId?: number,
    ) {
    }
}
