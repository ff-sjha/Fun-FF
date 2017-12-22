import { BaseEntity } from './../../shared';
import {Player} from '../player';

export class TieTeam implements BaseEntity {
    constructor(
        public id?: number,
        public points?: number,
        public tiePlayers?: Player[],
    ) {
    }
}
