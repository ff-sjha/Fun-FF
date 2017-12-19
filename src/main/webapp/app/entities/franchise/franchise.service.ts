import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Franchise } from './franchise.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FranchiseService {

    private resourceUrl = SERVER_API_URL + 'api/franchises';

    constructor(private http: Http) { }

    create(franchise: Franchise): Observable<Franchise> {
        const copy = this.convert(franchise);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(franchise: Franchise): Observable<Franchise> {
        const copy = this.convert(franchise);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Franchise> {
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
     * Convert a returned JSON object to Franchise.
     */
    private convertItemFromServer(json: any): Franchise {
        const entity: Franchise = Object.assign(new Franchise(), json);
        return entity;
    }

    /**
     * Convert a Franchise to a JSON which can be sent to the server.
     */
    private convert(franchise: Franchise): Franchise {
        const copy: Franchise = Object.assign({}, franchise);
        return copy;
    }
}
