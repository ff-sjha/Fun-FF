/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { SeasonsFranchisePlayerDialogComponent } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player-dialog.component';
import { SeasonsFranchisePlayerService } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player.service';
import { SeasonsFranchisePlayer } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player.model';
import { SeasonsFranchiseService } from '../../../../../../main/webapp/app/entities/seasons-franchise';
import { PlayerService } from '../../../../../../main/webapp/app/entities/player';

describe('Component Tests', () => {

    describe('SeasonsFranchisePlayer Management Dialog Component', () => {
        let comp: SeasonsFranchisePlayerDialogComponent;
        let fixture: ComponentFixture<SeasonsFranchisePlayerDialogComponent>;
        let service: SeasonsFranchisePlayerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonsFranchisePlayerDialogComponent],
                providers: [
                    SeasonsFranchiseService,
                    PlayerService,
                    SeasonsFranchisePlayerService
                ]
            })
            .overrideTemplate(SeasonsFranchisePlayerDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonsFranchisePlayerDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonsFranchisePlayerService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SeasonsFranchisePlayer(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.seasonsFranchisePlayer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'seasonsFranchisePlayerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SeasonsFranchisePlayer();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.seasonsFranchisePlayer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'seasonsFranchisePlayerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
