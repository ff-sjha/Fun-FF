import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SeasonsFranchise } from './seasons-franchise.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SeasonsFranchiseService {

    private resourceUrl = SERVER_API_URL + 'api/seasons-franchises';

    constructor(private http: Http) { }

    create(seasonsFranchise: SeasonsFranchise): Observable<SeasonsFranchise> {
        const copy = this.convert(seasonsFranchise);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(seasonsFranchise: SeasonsFranchise): Observable<SeasonsFranchise> {
        const copy = this.convert(seasonsFranchise);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SeasonsFranchise> {
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

    queryActiveSeason(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl + '/active', options)
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
     * Convert a returned JSON object to SeasonsFranchise.
     */
    private convertItemFromServer(json: any): SeasonsFranchise {
        const entity: SeasonsFranchise = Object.assign(new SeasonsFranchise(), json);
        return entity;
    }

    /**
     * Convert a SeasonsFranchise to a JSON which can be sent to the server.
     */
    private convert(seasonsFranchise: SeasonsFranchise): SeasonsFranchise {
        const copy: SeasonsFranchise = Object.assign({}, seasonsFranchise);
        return copy;
    }
}
