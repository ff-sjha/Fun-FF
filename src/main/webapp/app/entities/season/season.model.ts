import { BaseEntity } from './../../shared';

export class Season implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: any,
        public endDate?: any,
        public active?: boolean,
        public tournaments?: BaseEntity[],
        public winnerId?: number,
        public winnerName?: string,
    ) {
        this.active = false;
    }
}
