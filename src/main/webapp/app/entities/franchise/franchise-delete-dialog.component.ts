import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Franchise } from './franchise.model';
import { FranchisePopupService } from './franchise-popup.service';
import { FranchiseService } from './franchise.service';

@Component({
    selector: 'fafi-franchise-delete-dialog',
    templateUrl: './franchise-delete-dialog.component.html'
})
export class FranchiseDeleteDialogComponent {

    franchise: Franchise;

    constructor(
        private franchiseService: FranchiseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.franchiseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'franchiseListModification',
                content: 'Deleted an franchise'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'fafi-franchise-delete-popup',
    template: ''
})
export class FranchiseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private franchisePopupService: FranchisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.franchisePopupService
                .open(FranchiseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
