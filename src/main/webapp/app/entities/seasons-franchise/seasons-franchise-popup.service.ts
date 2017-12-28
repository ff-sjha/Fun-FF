import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SeasonsFranchise } from './seasons-franchise.model';
import { SeasonsFranchiseService } from './seasons-franchise.service';

@Injectable()
export class SeasonsFranchisePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private seasonsFranchiseService: SeasonsFranchiseService

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
                this.seasonsFranchiseService.find(id).subscribe((seasonsFranchise) => {
                    this.ngbModalRef = this.seasonsFranchiseModalRef(component, seasonsFranchise);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.seasonsFranchiseModalRef(component, new SeasonsFranchise());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    seasonsFranchiseModalRef(component: Component, seasonsFranchise: SeasonsFranchise): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seasonsFranchise = seasonsFranchise;
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
