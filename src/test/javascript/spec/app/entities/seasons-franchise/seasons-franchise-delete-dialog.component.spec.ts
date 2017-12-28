/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { SeasonsFranchiseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise-delete-dialog.component';
import { SeasonsFranchiseService } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise.service';

describe('Component Tests', () => {

    describe('SeasonsFranchise Management Delete Component', () => {
        let comp: SeasonsFranchiseDeleteDialogComponent;
        let fixture: ComponentFixture<SeasonsFranchiseDeleteDialogComponent>;
        let service: SeasonsFranchiseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonsFranchiseDeleteDialogComponent],
                providers: [
                    SeasonsFranchiseService
                ]
            })
            .overrideTemplate(SeasonsFranchiseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonsFranchiseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonsFranchiseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
