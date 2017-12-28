import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MatchFranchise } from './match-franchise.model';
import { MatchFranchisePopupService } from './match-franchise-popup.service';
import { MatchFranchiseService } from './match-franchise.service';
import { Match, MatchService } from '../match';
import { SeasonsFranchise, SeasonsFranchiseService } from '../seasons-franchise';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-match-franchise-dialog',
    templateUrl: './match-franchise-dialog.component.html'
})
export class MatchFranchiseDialogComponent implements OnInit {

    matchFranchise: MatchFranchise;
    isSaving: boolean;

    matches: Match[];

    seasonsfranchises: SeasonsFranchise[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private matchFranchiseService: MatchFranchiseService,
        private matchService: MatchService,
        private seasonsFranchiseService: SeasonsFranchiseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.matchService.query()
            .subscribe((res: ResponseWrapper) => { this.matches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.seasonsFranchiseService.query()
            .subscribe((res: ResponseWrapper) => { this.seasonsfranchises = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.matchFranchise.id !== undefined) {
            this.subscribeToSaveResponse(
                this.matchFranchiseService.update(this.matchFranchise));
        } else {
            this.subscribeToSaveResponse(
                this.matchFranchiseService.create(this.matchFranchise));
        }
    }

    private subscribeToSaveResponse(result: Observable<MatchFranchise>) {
        result.subscribe((res: MatchFranchise) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MatchFranchise) {
        this.eventManager.broadcast({ name: 'matchFranchiseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMatchById(index: number, item: Match) {
        return item.id;
    }

    trackSeasonsFranchiseById(index: number, item: SeasonsFranchise) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-match-franchise-popup',
    template: ''
})
export class MatchFranchisePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchFranchisePopupService: MatchFranchisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.matchFranchisePopupService
                    .open(MatchFranchiseDialogComponent as Component, params['id']);
            } else {
                this.matchFranchisePopupService
                    .open(MatchFranchiseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
