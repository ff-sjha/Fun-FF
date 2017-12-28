/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { MatchFranchiseDialogComponent } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise-dialog.component';
import { MatchFranchiseService } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise.service';
import { MatchFranchise } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise.model';
import { MatchService } from '../../../../../../main/webapp/app/entities/match';
import { SeasonsFranchiseService } from '../../../../../../main/webapp/app/entities/seasons-franchise';

describe('Component Tests', () => {

    describe('MatchFranchise Management Dialog Component', () => {
        let comp: MatchFranchiseDialogComponent;
        let fixture: ComponentFixture<MatchFranchiseDialogComponent>;
        let service: MatchFranchiseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchFranchiseDialogComponent],
                providers: [
                    MatchService,
                    SeasonsFranchiseService,
                    MatchFranchiseService
                ]
            })
            .overrideTemplate(MatchFranchiseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchFranchiseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchFranchiseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MatchFranchise(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.matchFranchise = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'matchFranchiseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MatchFranchise();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.matchFranchise = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'matchFranchiseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
