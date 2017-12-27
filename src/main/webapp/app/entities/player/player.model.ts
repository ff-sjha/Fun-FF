import { BaseEntity } from './../../shared';

export class Player implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
    ) {
    }
}
