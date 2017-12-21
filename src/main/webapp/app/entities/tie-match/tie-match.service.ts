import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TieMatch } from './tie-match.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TieMatchService {

    private resourceUrl = SERVER_API_URL + 'api/tie-matches';

    constructor(private http: Http) { }

    create(tieMatch: TieMatch): Observable<TieMatch> {
        const copy = this.convert(tieMatch);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tieMatch: TieMatch): Observable<TieMatch> {
        const copy = this.convert(tieMatch);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TieMatch> {
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
     * Convert a returned JSON object to TieMatch.
     */
    private convertItemFromServer(json: any): TieMatch {
        const entity: TieMatch = Object.assign(new TieMatch(), json);
        return entity;
    }

    /**
     * Convert a TieMatch to a JSON which can be sent to the server.
     */
    private convert(tieMatch: TieMatch): TieMatch {
        const copy: TieMatch = Object.assign({}, tieMatch);
        return copy;
    }
}
