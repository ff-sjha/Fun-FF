import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SeasonsFranchise } from './seasons-franchise.model';
import { SeasonsFranchisePopupService } from './seasons-franchise-popup.service';
import { SeasonsFranchiseService } from './seasons-franchise.service';

@Component({
    selector: 'fafi-seasons-franchise-delete-dialog',
    templateUrl: './seasons-franchise-delete-dialog.component.html'
})
export class SeasonsFranchiseDeleteDialogComponent {

    seasonsFranchise: SeasonsFranchise;

    constructor(
        private seasonsFranchiseService: SeasonsFranchiseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seasonsFranchiseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seasonsFranchiseListModification',
                content: 'Deleted an seasonsFranchise'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-seasons-franchise-delete-popup',
    template: ''
})
export class SeasonsFranchiseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonsFranchisePopupService: SeasonsFranchisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.seasonsFranchisePopupService
                .open(SeasonsFranchiseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
