import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MatchUmpire } from './match-umpire.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MatchUmpireService {

    private resourceUrl = SERVER_API_URL + 'api/match-umpires';

    constructor(private http: Http) { }

    create(matchUmpire: MatchUmpire): Observable<MatchUmpire> {
        const copy = this.convert(matchUmpire);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(matchUmpire: MatchUmpire): Observable<MatchUmpire> {
        const copy = this.convert(matchUmpire);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MatchUmpire> {
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
     * Convert a returned JSON object to MatchUmpire.
     */
    private convertItemFromServer(json: any): MatchUmpire {
        const entity: MatchUmpire = Object.assign(new MatchUmpire(), json);
        return entity;
    }

    /**
     * Convert a MatchUmpire to a JSON which can be sent to the server.
     */
    private convert(matchUmpire: MatchUmpire): MatchUmpire {
        const copy: MatchUmpire = Object.assign({}, matchUmpire);
        return copy;
    }
}
