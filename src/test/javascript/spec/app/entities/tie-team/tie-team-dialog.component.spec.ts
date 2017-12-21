/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { TieTeamDialogComponent } from '../../../../../../main/webapp/app/entities/tie-team/tie-team-dialog.component';
import { TieTeamService } from '../../../../../../main/webapp/app/entities/tie-team/tie-team.service';
import { TieTeam } from '../../../../../../main/webapp/app/entities/tie-team/tie-team.model';

describe('Component Tests', () => {

    describe('TieTeam Management Dialog Component', () => {
        let comp: TieTeamDialogComponent;
        let fixture: ComponentFixture<TieTeamDialogComponent>;
        let service: TieTeamService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieTeamDialogComponent],
                providers: [
                    TieTeamService
                ]
            })
            .overrideTemplate(TieTeamDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieTeamDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieTeamService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieTeam(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.tieTeam = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieTeamListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TieTeam();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.tieTeam = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'tieTeamListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
