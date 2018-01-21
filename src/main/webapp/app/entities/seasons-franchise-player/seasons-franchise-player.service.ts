import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SeasonsFranchisePlayer } from './seasons-franchise-player.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SeasonsFranchisePlayerService {

    private resourceUrl = SERVER_API_URL + 'api/seasons-franchise-players';

    constructor(private http: Http) { }

    create(seasonsFranchisePlayer: SeasonsFranchisePlayer): Observable<SeasonsFranchisePlayer> {
        const copy = this.convert(seasonsFranchisePlayer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(seasonsFranchisePlayer: SeasonsFranchisePlayer): Observable<SeasonsFranchisePlayer> {
        const copy = this.convert(seasonsFranchisePlayer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SeasonsFranchisePlayer> {
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

    queryByFranchiseId(franchiseId: string, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        options.params.set('seasonsFranchiseId.equals', franchiseId);
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
     * Convert a returned JSON object to SeasonsFranchisePlayer.
     */
    private convertItemFromServer(json: any): SeasonsFranchisePlayer {
        const entity: SeasonsFranchisePlayer = Object.assign(new SeasonsFranchisePlayer(), json);
        return entity;
    }

    /**
     * Convert a SeasonsFranchisePlayer to a JSON which can be sent to the server.
     */
    private convert(seasonsFranchisePlayer: SeasonsFranchisePlayer): SeasonsFranchisePlayer {
        const copy: SeasonsFranchisePlayer = Object.assign({}, seasonsFranchisePlayer);
        return copy;
    }
}
