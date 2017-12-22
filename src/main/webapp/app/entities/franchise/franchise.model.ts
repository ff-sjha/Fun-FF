import { BaseEntity } from './../../shared';

export class Franchise implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public logoPath?: string,
        public players?: BaseEntity[],
        public seasonId?: number,
        public ownerId?: number,
        public iconPlayerId?: number,
    ) {
    }
}
