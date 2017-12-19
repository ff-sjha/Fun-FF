import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Franchise } from './franchise.model';
import { FranchisePopupService } from './franchise-popup.service';
import { FranchiseService } from './franchise.service';

@Component({
    selector: 'fafi-franchise-dialog',
    templateUrl: './franchise-dialog.component.html'
})
export class FranchiseDialogComponent implements OnInit {

    franchise: Franchise;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private franchiseService: FranchiseService,
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
        if (this.franchise.id !== undefined) {
            this.subscribeToSaveResponse(
                this.franchiseService.update(this.franchise));
        } else {
            this.subscribeToSaveResponse(
                this.franchiseService.create(this.franchise));
        }
    }

    private subscribeToSaveResponse(result: Observable<Franchise>) {
        result.subscribe((res: Franchise) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Franchise) {
        this.eventManager.broadcast({ name: 'franchiseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'fafi-franchise-popup',
    template: ''
})
export class FranchisePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private franchisePopupService: FranchisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.franchisePopupService
                    .open(FranchiseDialogComponent as Component, params['id']);
            } else {
                this.franchisePopupService
                    .open(FranchiseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
