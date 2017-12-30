import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { News } from './news.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class NewsService {

    private resourceUrl = SERVER_API_URL + 'api/news';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(news: News): Observable<News> {
        const copy = this.convert(news);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(news: News): Observable<News> {
        const copy = this.convert(news);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<News> {
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

    queryAnyNews(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl + '/with-events', options)
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
     * Convert a returned JSON object to News.
     */
    private convertItemFromServer(json: any): News {
        const entity: News = Object.assign(new News(), json);
        entity.activeFrom = this.dateUtils
            .convertLocalDateFromServer(json.activeFrom);
        entity.ativeTill = this.dateUtils
            .convertLocalDateFromServer(json.ativeTill);
        return entity;
    }

    /**
     * Convert a News to a JSON which can be sent to the server.
     */
    private convert(news: News): News {
        const copy: News = Object.assign({}, news);
        copy.activeFrom = this.dateUtils
            .convertLocalDateToServer(news.activeFrom);
        copy.ativeTill = this.dateUtils
            .convertLocalDateToServer(news.ativeTill);
        return copy;
    }
}
