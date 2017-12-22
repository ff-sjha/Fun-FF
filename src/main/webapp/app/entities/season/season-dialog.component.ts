import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Season } from './season.model';
import { SeasonPopupService } from './season-popup.service';
import { SeasonService } from './season.service';
import { Franchise, FranchiseService } from '../franchise';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'fafi-season-dialog',
    templateUrl: './season-dialog.component.html'
})
export class SeasonDialogComponent implements OnInit {

    season: Season;
    isSaving: boolean;

    winners: Franchise[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private seasonService: SeasonService,
        private franchiseService: FranchiseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.franchiseService
            .query({filter: 'season-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.season.winnerId) {
                    this.winners = res.json;
                } else {
                    this.franchiseService
                        .find(this.season.winnerId)
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
        if (this.season.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seasonService.update(this.season));
        } else {
            this.subscribeToSaveResponse(
                this.seasonService.create(this.season));
        }
    }

    private subscribeToSaveResponse(result: Observable<Season>) {
        result.subscribe((res: Season) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Season) {
        this.eventManager.broadcast({ name: 'seasonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFranchiseById(index: number, item: Franchise) {
        return item.id;
    }
}

@Component({
    selector: 'fafi-season-popup',
    template: ''
})
export class SeasonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonPopupService: SeasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seasonPopupService
                    .open(SeasonDialogComponent as Component, params['id']);
            } else {
                this.seasonPopupService
                    .open(SeasonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
