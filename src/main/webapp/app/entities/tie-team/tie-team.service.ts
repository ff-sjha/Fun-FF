import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TieTeam } from './tie-team.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TieTeamService {

    private resourceUrl = SERVER_API_URL + 'api/tie-teams';

    constructor(private http: Http) { }

    create(tieTeam: TieTeam): Observable<TieTeam> {
        const copy = this.convert(tieTeam);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tieTeam: TieTeam): Observable<TieTeam> {
        const copy = this.convert(tieTeam);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TieTeam> {
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
     * Convert a returned JSON object to TieTeam.
     */
    private convertItemFromServer(json: any): TieTeam {
        const entity: TieTeam = Object.assign(new TieTeam(), json);
        return entity;
    }

    /**
     * Convert a TieTeam to a JSON which can be sent to the server.
     */
    private convert(tieTeam: TieTeam): TieTeam {
        const copy: TieTeam = Object.assign({}, tieTeam);
        return copy;
    }
}
