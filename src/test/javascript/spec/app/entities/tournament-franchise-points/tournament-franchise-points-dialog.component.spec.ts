/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { TournamentFranchisePointsDialogComponent } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points-dialog.component';
import { TournamentFranchisePointsService } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points.service';
import { TournamentFranchisePoints } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points.model';
import { TournamentService } from '../../../../../../main/webapp/app/entities/tournament';
import { FranchiseService } from '../../../../../../main/webapp/app/entities/franchise';

describe('Component Tests', () => {

    describe('TournamentFranchisePoints Management Dialog Component', () => {
        let comp: TournamentFranchisePointsDialogComponent;
        let fixture: ComponentFixture<TournamentFranchisePointsDialogComponent>;
        let service: TournamentFranchisePointsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TournamentFranchisePointsDialogComponent],
                providers: [
                    TournamentService,
                    FranchiseService,
                    TournamentFranchisePointsService
                ]
            })
            .overrideTemplate(TournamentFranchisePointsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TournamentFranchisePointsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TournamentFranchisePointsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TournamentFranchisePoints(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.tournamentFranchisePoints = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tournamentFranchisePointsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TournamentFranchisePoints();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.tournamentFranchisePoints = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tournamentFranchisePointsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
