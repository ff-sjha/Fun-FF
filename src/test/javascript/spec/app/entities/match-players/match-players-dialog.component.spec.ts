/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { MatchPlayersDialogComponent } from '../../../../../../main/webapp/app/entities/match-players/match-players-dialog.component';
import { MatchPlayersService } from '../../../../../../main/webapp/app/entities/match-players/match-players.service';
import { MatchPlayers } from '../../../../../../main/webapp/app/entities/match-players/match-players.model';
import { MatchService } from '../../../../../../main/webapp/app/entities/match';
import { SeasonsFranchisePlayerService } from '../../../../../../main/webapp/app/entities/seasons-franchise-player';

describe('Component Tests', () => {

    describe('MatchPlayers Management Dialog Component', () => {
        let comp: MatchPlayersDialogComponent;
        let fixture: ComponentFixture<MatchPlayersDialogComponent>;
        let service: MatchPlayersService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchPlayersDialogComponent],
                providers: [
                    MatchService,
                    SeasonsFranchisePlayerService,
                    MatchPlayersService
                ]
            })
            .overrideTemplate(MatchPlayersDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchPlayersDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchPlayersService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MatchPlayers(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.matchPlayers = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'matchPlayersListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MatchPlayers();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.matchPlayers = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'matchPlayersListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
