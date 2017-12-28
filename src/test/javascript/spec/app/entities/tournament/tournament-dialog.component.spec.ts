/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { TournamentDialogComponent } from '../../../../../../main/webapp/app/entities/tournament/tournament-dialog.component';
import { TournamentService } from '../../../../../../main/webapp/app/entities/tournament/tournament.service';
import { Tournament } from '../../../../../../main/webapp/app/entities/tournament/tournament.model';
import { SeasonService } from '../../../../../../main/webapp/app/entities/season';
import { SeasonsFranchiseService } from '../../../../../../main/webapp/app/entities/seasons-franchise';
import { SeasonsFranchisePlayerService } from '../../../../../../main/webapp/app/entities/seasons-franchise-player';

describe('Component Tests', () => {

    describe('Tournament Management Dialog Component', () => {
        let comp: TournamentDialogComponent;
        let fixture: ComponentFixture<TournamentDialogComponent>;
        let service: TournamentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TournamentDialogComponent],
                providers: [
                    SeasonService,
                    SeasonsFranchiseService,
                    SeasonsFranchisePlayerService,
                    TournamentService
                ]
            })
            .overrideTemplate(TournamentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TournamentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TournamentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Tournament(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.tournament = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tournamentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Tournament();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.tournament = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tournamentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
