import { BaseEntity } from './../shared';

export class PointsTablePlayer implements BaseEntity {
    constructor(
        public id?: number,
        public totalPoints?: number,
        public firstName?: string,
        public lastName?: string,
        public totalMatches?: number,
    ) {
    }
}
