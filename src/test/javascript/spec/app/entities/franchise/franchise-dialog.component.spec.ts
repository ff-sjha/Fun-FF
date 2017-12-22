/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { FranchiseDialogComponent } from '../../../../../../main/webapp/app/entities/franchise/franchise-dialog.component';
import { FranchiseService } from '../../../../../../main/webapp/app/entities/franchise/franchise.service';
import { Franchise } from '../../../../../../main/webapp/app/entities/franchise/franchise.model';
import { PlayerService } from '../../../../../../main/webapp/app/entities/player';
import { SeasonService } from '../../../../../../main/webapp/app/entities/season';

describe('Component Tests', () => {

    describe('Franchise Management Dialog Component', () => {
        let comp: FranchiseDialogComponent;
        let fixture: ComponentFixture<FranchiseDialogComponent>;
        let service: FranchiseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [FranchiseDialogComponent],
                providers: [
                    PlayerService,
                    SeasonService,
                    FranchiseService
                ]
            })
            .overrideTemplate(FranchiseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FranchiseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FranchiseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Franchise(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.franchise = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'franchiseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Franchise();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.franchise = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'franchiseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
