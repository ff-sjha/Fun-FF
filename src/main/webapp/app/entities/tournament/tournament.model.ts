import { BaseEntity } from './../../shared';

export class Tournament implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: any,
        public endDate?: any,
        public seasonId?: number,
        public seasonName?: string,
        public matches?: BaseEntity[],
        public winnerId?: number,
        public winnerName?: string,
    ) {
    }
}
