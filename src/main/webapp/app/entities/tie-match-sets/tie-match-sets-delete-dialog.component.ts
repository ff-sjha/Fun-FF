import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TieMatchSets } from './tie-match-sets.model';
import { TieMatchSetsPopupService } from './tie-match-sets-popup.service';
import { TieMatchSetsService } from './tie-match-sets.service';

@Component({
    selector: 'fafi-tie-match-sets-delete-dialog',
    templateUrl: './tie-match-sets-delete-dialog.component.html'
})
export class TieMatchSetsDeleteDialogComponent {

    tieMatchSets: TieMatchSets;

    constructor(
        private tieMatchSetsService: TieMatchSetsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tieMatchSetsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tieMatchSetsListModification',
                content: 'Deleted an tieMatchSets'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-tie-match-sets-delete-popup',
    template: ''
})
export class TieMatchSetsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tieMatchSetsPopupService: TieMatchSetsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tieMatchSetsPopupService
                .open(TieMatchSetsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
