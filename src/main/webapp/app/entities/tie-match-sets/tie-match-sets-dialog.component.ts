import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TieMatchSets } from './tie-match-sets.model';
import { TieMatchSetsPopupService } from './tie-match-sets-popup.service';
import { TieMatchSetsService } from './tie-match-sets.service';

@Component({
    selector: 'fafi-tie-match-sets-dialog',
    templateUrl: './tie-match-sets-dialog.component.html'
})
export class TieMatchSetsDialogComponent implements OnInit {

    tieMatchSets: TieMatchSets;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tieMatchSetsService: TieMatchSetsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tieMatchSets.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tieMatchSetsService.update(this.tieMatchSets));
        } else {
            this.subscribeToSaveResponse(
                this.tieMatchSetsService.create(this.tieMatchSets));
        }
    }

    private subscribeToSaveResponse(result: Observable<TieMatchSets>) {
        result.subscribe((res: TieMatchSets) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TieMatchSets) {
        this.eventManager.broadcast({ name: 'tieMatchSetsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'fafi-tie-match-sets-popup',
    template: ''
})
export class TieMatchSetsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tieMatchSetsPopupService: TieMatchSetsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tieMatchSetsPopupService
                    .open(TieMatchSetsDialogComponent as Component, params['id']);
            } else {
                this.tieMatchSetsPopupService
                    .open(TieMatchSetsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
