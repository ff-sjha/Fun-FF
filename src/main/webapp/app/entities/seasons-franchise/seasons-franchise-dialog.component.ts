import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SeasonsFranchise } from './seasons-franchise.model';
import { SeasonsFranchisePopupService } from './seasons-franchise-popup.service';
import { SeasonsFranchiseService } from './seasons-franchise.service';
import { Season, SeasonService } from '../season';
import { Franchise, FranchiseService } from '../franchise';
import { Player, PlayerService } from '../player';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-seasons-franchise-dialog',
    templateUrl: './seasons-franchise-dialog.component.html'
})
export class SeasonsFranchiseDialogComponent implements OnInit {

    seasonsFranchise: SeasonsFranchise;
    isSaving: boolean;

    seasons: Season[];

    franchises: Franchise[];

    players: Player[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private seasonsFranchiseService: SeasonsFranchiseService,
        private seasonService: SeasonService,
        private franchiseService: FranchiseService,
        private playerService: PlayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.seasonService.query()
            .subscribe((res: ResponseWrapper) => { this.seasons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.franchiseService.query()
            .subscribe((res: ResponseWrapper) => { this.franchises = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.playerService.query()
            .subscribe((res: ResponseWrapper) => { this.players = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.seasonsFranchise.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seasonsFranchiseService.update(this.seasonsFranchise));
        } else {
            this.subscribeToSaveResponse(
                this.seasonsFranchiseService.create(this.seasonsFranchise));
        }
    }

    private subscribeToSaveResponse(result: Observable<SeasonsFranchise>) {
        result.subscribe((res: SeasonsFranchise) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SeasonsFranchise) {
        this.eventManager.broadcast({ name: 'seasonsFranchiseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSeasonById(index: number, item: Season) {
        return item.id;
    }

    trackFranchiseById(index: number, item: Franchise) {
        return item.id;
    }

    trackPlayerById(index: number, item: Player) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-seasons-franchise-popup',
    template: ''
})
export class SeasonsFranchisePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonsFranchisePopupService: SeasonsFranchisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seasonsFranchisePopupService
                    .open(SeasonsFranchiseDialogComponent as Component, params['id']);
            } else {
                this.seasonsFranchisePopupService
                    .open(SeasonsFranchiseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
