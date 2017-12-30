import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import {Account, ITEMS_PER_PAGE, LoginModalService, Principal, ResponseWrapper} from '../shared';
import {News, NewsService} from '../entities/news';
import {Tournament, TournamentService} from '../entities/tournament';
import {Match, MatchService} from '../entities/match';

@Component({
    selector: 'fafi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    newslist: News[];
    tournaments: Tournament[];
    matches: Match[];
    totalItems: any;
    queryCount: any;
    links: any;
    error: any;
    success: any;
    routeData: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private router: Router,
        private newsService: NewsService,
        private tournamentService: TournamentService,
        private matchService: MatchService
    ) {
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        /*this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });*/
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.loadAll();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    loadAll() {
        this.page = 0;
        this.newsService.queryAnyNews().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.tournamentService.queryActive().subscribe(
                (res: ResponseWrapper) => this.onSuccessTournament(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)
        );

        this.matchService.upcomingMatches().subscribe(
                (res: ResponseWrapper) => this.onSuccessMatch(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    sort() {
        const result = [];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
    trackId(index: number, item: News) {
        return item.id;
    }
    private onSuccess(data, headers) {
        // this.page = pagingParams.page;
        this.newslist = data;
    }
    private onSuccessTournament(data, headers) {
        this.tournaments = data;
    }
    private onSuccessMatch(data, headers) {
        this.matches = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
