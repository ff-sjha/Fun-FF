import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SportInfo } from './sport-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SportInfoService {

    private resourceUrl = SERVER_API_URL + 'api/sport-infos';

    constructor(private http: Http) { }

    create(sportInfo: SportInfo): Observable<SportInfo> {
        const copy = this.convert(sportInfo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(sportInfo: SportInfo): Observable<SportInfo> {
        const copy = this.convert(sportInfo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SportInfo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findByGame(game: string): Observable<SportInfo> {
        return this.http.get(`${this.resourceUrl}-bygame/${game}`).map((res: Response) => {
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
     * Convert a returned JSON object to SportInfo.
     */
    private convertItemFromServer(json: any): SportInfo {
        const entity: SportInfo = Object.assign(new SportInfo(), json);
        return entity;
    }

    /**
     * Convert a SportInfo to a JSON which can be sent to the server.
     */
    private convert(sportInfo: SportInfo): SportInfo {
        const copy: SportInfo = Object.assign({}, sportInfo);
        return copy;
    }
}
