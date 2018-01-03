import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PointsTablePlayer } from './points-table-player.model';
import { ResponseWrapper, createRequestOption } from '../shared';

@Injectable()
export class PointsTableService {

    private resourceUrl = SERVER_API_URL + 'api/points-table';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to PointsTablePlayer.
     */
    private convertItemFromServer(json: any): PointsTablePlayer {
        const entity: PointsTablePlayer = Object.assign(new PointsTablePlayer(), json);
        return entity;
    }
}
