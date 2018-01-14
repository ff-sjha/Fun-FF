/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { MatchUmpireDialogComponent } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire-dialog.component';
import { MatchUmpireService } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire.service';
import { MatchUmpire } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire.model';
import { MatchService } from '../../../../../../main/webapp/app/entities/match';
import { PlayerService } from '../../../../../../main/webapp/app/entities/player';

describe('Component Tests', () => {

    describe('MatchUmpire Management Dialog Component', () => {
        let comp: MatchUmpireDialogComponent;
        let fixture: ComponentFixture<MatchUmpireDialogComponent>;
        let service: MatchUmpireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchUmpireDialogComponent],
                providers: [
                    MatchService,
                    PlayerService,
                    MatchUmpireService
                ]
            })
            .overrideTemplate(MatchUmpireDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchUmpireDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchUmpireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MatchUmpire(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.matchUmpire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'matchUmpireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MatchUmpire();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.matchUmpire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'matchUmpireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
