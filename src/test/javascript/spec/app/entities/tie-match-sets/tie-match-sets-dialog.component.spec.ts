/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { TieMatchSetsDialogComponent } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets-dialog.component';
import { TieMatchSetsService } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets.service';
import { TieMatchSets } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets.model';
import { TieMatchService } from '../../../../../../main/webapp/app/entities/tie-match';

describe('Component Tests', () => {

    describe('TieMatchSets Management Dialog Component', () => {
        let comp: TieMatchSetsDialogComponent;
        let fixture: ComponentFixture<TieMatchSetsDialogComponent>;
        let service: TieMatchSetsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchSetsDialogComponent],
                providers: [
                    TieMatchService,
                    TieMatchSetsService
                ]
            })
            .overrideTemplate(TieMatchSetsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchSetsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchSetsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieMatchSets(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.tieMatchSets = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieMatchSetsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieMatchSets();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.tieMatchSets = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieMatchSetsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
