import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TieMatch } from './tie-match.model';
import { TieMatchPopupService } from './tie-match-popup.service';
import { TieMatchService } from './tie-match.service';

@Component({
    selector: 'fafi-tie-match-delete-dialog',
    templateUrl: './tie-match-delete-dialog.component.html'
})
export class TieMatchDeleteDialogComponent {

    tieMatch: TieMatch;

    constructor(
        private tieMatchService: TieMatchService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tieMatchService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tieMatchListModification',
                content: 'Deleted an tieMatch'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-tie-match-delete-popup',
    template: ''
})
export class TieMatchDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tieMatchPopupService: TieMatchPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tieMatchPopupService
                .open(TieMatchDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
