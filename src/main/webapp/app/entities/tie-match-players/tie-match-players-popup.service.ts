import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TieMatchPlayers } from './tie-match-players.model';
import { TieMatchPlayersService } from './tie-match-players.service';

@Injectable()
export class TieMatchPlayersPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tieMatchPlayersService: TieMatchPlayersService

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
                this.tieMatchPlayersService.find(id).subscribe((tieMatchPlayers) => {
                    this.ngbModalRef = this.tieMatchPlayersModalRef(component, tieMatchPlayers);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tieMatchPlayersModalRef(component, new TieMatchPlayers());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tieMatchPlayersModalRef(component: Component, tieMatchPlayers: TieMatchPlayers): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tieMatchPlayers = tieMatchPlayers;
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
