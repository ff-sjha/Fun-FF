import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SportInfo } from './sport-info.model';
import { SportInfoPopupService } from './sport-info-popup.service';
import { SportInfoService } from './sport-info.service';

@Component({
    selector: 'fafi-sport-info-delete-dialog',
    templateUrl: './sport-info-delete-dialog.component.html'
})
export class SportInfoDeleteDialogComponent {

    sportInfo: SportInfo;

    constructor(
        private sportInfoService: SportInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sportInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sportInfoListModification',
                content: 'Deleted an sportInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-sport-info-delete-popup',
    template: ''
})
export class SportInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sportInfoPopupService: SportInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sportInfoPopupService
                .open(SportInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
