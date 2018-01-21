import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ResponseWrapper, createRequestOption } from '../shared';

@Injectable()
export class FranchiseInfoService {

    private resourceUrl = SERVER_API_URL + 'api/seasons-franchises';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

}
