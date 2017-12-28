import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MatchFranchise } from './match-franchise.model';
import { MatchFranchisePopupService } from './match-franchise-popup.service';
import { MatchFranchiseService } from './match-franchise.service';

@Component({
    selector: 'fafi-match-franchise-delete-dialog',
    templateUrl: './match-franchise-delete-dialog.component.html'
})
export class MatchFranchiseDeleteDialogComponent {

    matchFranchise: MatchFranchise;

    constructor(
        private matchFranchiseService: MatchFranchiseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matchFranchiseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'matchFranchiseListModification',
                content: 'Deleted an matchFranchise'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-match-franchise-delete-popup',
    template: ''
})
export class MatchFranchiseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private matchFranchisePopupService: MatchFranchisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.matchFranchisePopupService
                .open(MatchFranchiseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
