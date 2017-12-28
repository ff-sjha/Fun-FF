import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SeasonsFranchisePlayer } from './seasons-franchise-player.model';
import { SeasonsFranchisePlayerService } from './seasons-franchise-player.service';

@Injectable()
export class SeasonsFranchisePlayerPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private seasonsFranchisePlayerService: SeasonsFranchisePlayerService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.seasonsFranchisePlayerService.find(id).subscribe((seasonsFranchisePlayer) => {
                    this.ngbModalRef = this.seasonsFranchisePlayerModalRef(component, seasonsFranchisePlayer);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.seasonsFranchisePlayerModalRef(component, new SeasonsFranchisePlayer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    seasonsFranchisePlayerModalRef(component: Component, seasonsFranchisePlayer: SeasonsFranchisePlayer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seasonsFranchisePlayer = seasonsFranchisePlayer;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
