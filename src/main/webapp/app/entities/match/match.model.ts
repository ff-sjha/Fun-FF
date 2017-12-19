import { BaseEntity } from './../../shared';

export class Match implements BaseEntity {
    constructor(
        public id?: number,
        public startDateTime?: any,
        public endDateTime?: any,
        public tournamentId?: number,
    ) {
    }
}
