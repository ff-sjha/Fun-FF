import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { SportInfo } from './sport-info.model';
import { SportInfoPopupService } from './sport-info-popup.service';
import { SportInfoService } from './sport-info.service';

@Component({
    selector: 'fafi-sport-info-dialog',
    templateUrl: './sport-info-dialog.component.html'
})
export class SportInfoDialogComponent implements OnInit {

    sportInfo: SportInfo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private sportInfoService: SportInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sportInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sportInfoService.update(this.sportInfo));
        } else {
            this.subscribeToSaveResponse(
                this.sportInfoService.create(this.sportInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<SportInfo>) {
        result.subscribe((res: SportInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SportInfo) {
        this.eventManager.broadcast({ name: 'sportInfoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'fafi-sport-info-popup',
    template: ''
})
export class SportInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sportInfoPopupService: SportInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sportInfoPopupService
                    .open(SportInfoDialogComponent as Component, params['id']);
            } else {
                this.sportInfoPopupService
                    .open(SportInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
