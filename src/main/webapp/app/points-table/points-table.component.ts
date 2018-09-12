import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../shared';

import { PointsTablePlayer } from './points-table-player.model';
import { PointsTableFanchise } from './points-table-franchise.model';
import { PointsTableService } from './points-table.service';
import { Tournament, TournamentService } from '../entities/tournament';
import { Match, MatchService } from '../entities/match';

@Component({
  selector: 'fafi-points-table',
  templateUrl: './points-table.component.html',
  styleUrls: [
    'points-table.scss'
  ]
})

export class PointsTableComponent implements OnInit, OnDestroy {

    pointsTablePlayer: PointsTablePlayer[];
    pointsTableFranchise: PointsTableFanchise[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    tournaments: Tournament[];
    tournamentId: number;
    matches: Match[];
    matchId: number;

  constructor(
        private pointsTableService: PointsTableService,
        private tournamentService: TournamentService,
        private matchService: MatchService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
  ) {
    this.pointsTablePlayer = [];
    this.pointsTableFranchise = [];
  }

    loadAll() {

       this.tournamentService.query()
            .subscribe((res: ResponseWrapper) => { this.tournaments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

       this.loadPointsInternal();
    }

    ngOnInit() {
        this.loadAll();
    }

    ngOnDestroy() {
    }

    private loadPointsInternal() {
        this.pointsTablePlayer = [];
        this.pointsTableFranchise = [];

        const criteria = [ {
           key: 'tournamentId.equals',
           value : this.tournamentId
        } , {
           key: 'matchId.equals',
           value : this.matchId
        } ];

        this.pointsTableService.queryPlayerPoints({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            filter: criteria
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccessPlayer(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.pointsTableService.queryFranchisePoints({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            filter: criteria
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccessFanchise(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

    private onSuccessPlayer(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.pointsTablePlayer.push(data[i]);
        }
    }

    private onSuccessFanchise(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.pointsTableFranchise.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTournamentById(index: number, item: Tournament) {
        return item.id;
    }

    trackMatchById(index: number, item: Match) {
        return item.id;
    }

    loadPoints(matchIdArg: any) {
        this.matchId = matchIdArg;
        this.loadPointsInternal();
    }

    loadMatches(tournamentIdArg: any) {
        this.tournamentId = tournamentIdArg;
        delete this.matchId;
        this.matches = [];
        const criteria = [ {
           key: 'tournamentId.equals',
           value : this.tournamentId
        }, {
           key: 'completed.equals',
           value : 'true'
        } ];
        this.matchService.query({
            page: this.page,
            size: this.itemsPerPage,
            filter: criteria
        }).subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.loadPointsInternal();
    }

   sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
}
