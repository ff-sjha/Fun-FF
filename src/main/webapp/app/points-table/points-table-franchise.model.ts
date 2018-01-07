import { BaseEntity } from './../shared';

export class PointsTableFanchise implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public totalPoints?: number,
    ) {
    }
}