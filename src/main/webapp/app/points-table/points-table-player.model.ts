import { BaseEntity } from './../shared';

export class PointsTablePlayer implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public totalPoints?: number,
        public totalMatches?: number,
    ) {
    }
}
