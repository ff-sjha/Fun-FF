/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { TieMatchPlayersDialogComponent } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players-dialog.component';
import { TieMatchPlayersService } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players.service';
import { TieMatchPlayers } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players.model';
import { TieMatchService } from '../../../../../../main/webapp/app/entities/tie-match';
import { SeasonsFranchisePlayerService } from '../../../../../../main/webapp/app/entities/seasons-franchise-player';

describe('Component Tests', () => {

    describe('TieMatchPlayers Management Dialog Component', () => {
        let comp: TieMatchPlayersDialogComponent;
        let fixture: ComponentFixture<TieMatchPlayersDialogComponent>;
        let service: TieMatchPlayersService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchPlayersDialogComponent],
                providers: [
                    TieMatchService,
                    SeasonsFranchisePlayerService,
                    TieMatchPlayersService
                ]
            })
            .overrideTemplate(TieMatchPlayersDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchPlayersDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchPlayersService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieMatchPlayers(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.tieMatchPlayers = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieMatchPlayersListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieMatchPlayers();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.tieMatchPlayers = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieMatchPlayersListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
