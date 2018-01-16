import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SeasonExpense } from './season-expense.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SeasonExpenseService {

    private resourceUrl = SERVER_API_URL + 'api/season-expenses';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(seasonExpense: SeasonExpense): Observable<SeasonExpense> {
        const copy = this.convert(seasonExpense);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(seasonExpense: SeasonExpense): Observable<SeasonExpense> {
        const copy = this.convert(seasonExpense);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SeasonExpense> {
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
     * Convert a returned JSON object to SeasonExpense.
     */
    private convertItemFromServer(json: any): SeasonExpense {
        const entity: SeasonExpense = Object.assign(new SeasonExpense(), json);
        entity.incurredDate = this.dateUtils
            .convertLocalDateFromServer(json.incurredDate);
        return entity;
    }

    /**
     * Convert a SeasonExpense to a JSON which can be sent to the server.
     */
    private convert(seasonExpense: SeasonExpense): SeasonExpense {
        const copy: SeasonExpense = Object.assign({}, seasonExpense);
        copy.incurredDate = this.dateUtils
            .convertLocalDateToServer(seasonExpense.incurredDate);
        return copy;
    }
}
