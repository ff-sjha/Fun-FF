import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TieMatchSets } from './tie-match-sets.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TieMatchSetsService {

    private resourceUrl = SERVER_API_URL + 'api/tie-match-sets';

    constructor(private http: Http) { }

    create(tieMatchSets: TieMatchSets): Observable<TieMatchSets> {
        const copy = this.convert(tieMatchSets);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tieMatchSets: TieMatchSets): Observable<TieMatchSets> {
        const copy = this.convert(tieMatchSets);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TieMatchSets> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
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
     * Convert a returned JSON object to TieMatchSets.
     */
    private convertItemFromServer(json: any): TieMatchSets {
        const entity: TieMatchSets = Object.assign(new TieMatchSets(), json);
        return entity;
    }

    /**
     * Convert a TieMatchSets to a JSON which can be sent to the server.
     */
    private convert(tieMatchSets: TieMatchSets): TieMatchSets {
        const copy: TieMatchSets = Object.assign({}, tieMatchSets);
        return copy;
    }
}
