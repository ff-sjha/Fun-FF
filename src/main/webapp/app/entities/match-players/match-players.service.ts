import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MatchPlayers } from './match-players.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MatchPlayersService {

    private resourceUrl = SERVER_API_URL + 'api/match-players';

    constructor(private http: Http) { }

    create(matchPlayers: MatchPlayers): Observable<MatchPlayers> {
        const copy = this.convert(matchPlayers);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(matchPlayers: MatchPlayers): Observable<MatchPlayers> {
        const copy = this.convert(matchPlayers);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MatchPlayers> {
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
     * Convert a returned JSON object to MatchPlayers.
     */
    private convertItemFromServer(json: any): MatchPlayers {
        const entity: MatchPlayers = Object.assign(new MatchPlayers(), json);
        return entity;
    }

    /**
     * Convert a MatchPlayers to a JSON which can be sent to the server.
     */
    private convert(matchPlayers: MatchPlayers): MatchPlayers {
        const copy: MatchPlayers = Object.assign({}, matchPlayers);
        return copy;
    }
}
