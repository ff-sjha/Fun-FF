import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import {Account, ITEMS_PER_PAGE, LoginModalService, Principal, ResponseWrapper} from '../shared';
import {Tournament, TournamentService} from '../entities/tournament';
import {Season, SeasonService} from '../entities/season';
import {Match, MatchService} from '../entities/match';

@Component({
  selector: 'fafi-fixture',
  templateUrl: './fixture.component.html',
  styleUrls: [
    'fixture.scss'
  ]
})
export class FixtureComponent implements OnInit {

    tournamentId: String;
    seasonId: String;

    account: Account;
    modalRef: NgbModalRef;
    seasons: Season[];
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
    private tournamentService: TournamentService,
    private matchService: MatchService,
    private seasonService: SeasonService
  ) {
      this.tournamentId = '';
      this.seasonId = '';
  }

  ngOnInit() {
      this.loadAll();
  }

  loadAll() {
      this.matchService.upcomingMatches().subscribe(
              (res: ResponseWrapper) => this.onSuccessMatch(res.json, res.headers),
              (res: ResponseWrapper) => this.onError(res.json)
      );
  }

  private onChangeSeason(season) {
      this.tournamentService.query().subscribe(
              (res: ResponseWrapper) => this.onSuccessTournament(res.json, res.headers),
              (res: ResponseWrapper) => this.onError(res.json)
      );
  }
  private onSuccessSeason(data, headers) {
      this.seasons = data;
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
