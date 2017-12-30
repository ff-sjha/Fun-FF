import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Match } from './match.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MatchService {

    private resourceUrl = SERVER_API_URL + 'api/matches';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(match: Match): Observable<Match> {
        const copy = this.convert(match);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(match: Match): Observable<Match> {
        const copy = this.convert(match);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Match> {
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

    upcomingMatches(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl + '?upcomingMatches.equals=true', options)
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
     * Convert a returned JSON object to Match.
     */
    private convertItemFromServer(json: any): Match {
        const entity: Match = Object.assign(new Match(), json);
        entity.startDateTime = this.dateUtils
            .convertDateTimeFromServer(json.startDateTime);
        entity.endDateTime = this.dateUtils
            .convertDateTimeFromServer(json.endDateTime);
        return entity;
    }

    /**
     * Convert a Match to a JSON which can be sent to the server.
     */
    private convert(match: Match): Match {
        const copy: Match = Object.assign({}, match);

        copy.startDateTime = this.dateUtils.toDate(match.startDateTime);

        copy.endDateTime = this.dateUtils.toDate(match.endDateTime);
        return copy;
    }
}
