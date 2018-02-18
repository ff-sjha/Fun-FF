/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { TieMatchDialogComponent } from '../../../../../../main/webapp/app/entities/tie-match/tie-match-dialog.component';
import { TieMatchService } from '../../../../../../main/webapp/app/entities/tie-match/tie-match.service';
import { TieMatch } from '../../../../../../main/webapp/app/entities/tie-match/tie-match.model';
import { MatchService } from '../../../../../../main/webapp/app/entities/match';
import { SeasonsFranchisePlayerService } from '../../../../../../main/webapp/app/entities/seasons-franchise-player';

describe('Component Tests', () => {

    describe('TieMatch Management Dialog Component', () => {
        let comp: TieMatchDialogComponent;
        let fixture: ComponentFixture<TieMatchDialogComponent>;
        let service: TieMatchService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchDialogComponent],
                providers: [
                    MatchService,
                    SeasonsFranchisePlayerService,
                    TieMatchService
                ]
            })
            .overrideTemplate(TieMatchDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieMatch(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.tieMatch = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieMatchListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieMatch();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.tieMatch = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieMatchListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
