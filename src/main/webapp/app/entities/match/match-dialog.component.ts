import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Match } from './match.model';
import { MatchPopupService } from './match-popup.service';
import { MatchService } from './match.service';
import { Tournament, TournamentService } from '../tournament';
import { Franchise, FranchiseService } from '../franchise';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-match-dialog',
    templateUrl: './match-dialog.component.html'
})
export class MatchDialogComponent implements OnInit {

    match: Match;
    isSaving: boolean;

    tournaments: Tournament[];

    franchise1s: Franchise[];

    franchise2s: Franchise[];

    winners: Franchise[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private matchService: MatchService,
        private tournamentService: TournamentService,
        private franchiseService: FranchiseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tournamentService.query()
            .subscribe((res: ResponseWrapper) => { this.tournaments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.franchiseService
            .query({filter: 'match-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.match.franchise1Id) {
                    this.franchise1s = res.json;
                } else {
                    this.franchiseService
                        .find(this.match.franchise1Id)
                        .subscribe((subRes: Franchise) => {
                            this.franchise1s = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.franchiseService
            .query({filter: 'match-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.match.franchise2Id) {
                    this.franchise2s = res.json;
                } else {
                    this.franchiseService
                        .find(this.match.franchise2Id)
                        .subscribe((subRes: Franchise) => {
                            this.franchise2s = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.franchiseService
            .query({filter: 'match-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.match.winnerId) {
                    this.winners = res.json;
                } else {
                    this.franchiseService
                        .find(this.match.winnerId)
                        .subscribe((subRes: Franchise) => {
                            this.winners = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.match.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matchService.update(this.match));
        } else {
            this.subscribeToSaveResponse(
                this.matchService.create(this.match));
        }
    }

    private subscribeToSaveResponse(result: Observable<Match>) {
        result.subscribe((res: Match) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Match) {
        this.eventManager.broadcast({ name: 'matchListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTournamentById(index: number, item: Tournament) {
        return item.id;
    }

    trackFranchiseById(index: number, item: Franchise) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-match-popup',
    template: ''
})
export class MatchPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchPopupService: MatchPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.matchPopupService
                    .open(MatchDialogComponent as Component, params['id']);
            } else {
                this.matchPopupService
                    .open(MatchDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
