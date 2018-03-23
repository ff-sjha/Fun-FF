/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { SportInfoDialogComponent } from '../../../../../../main/webapp/app/entities/sport-info/sport-info-dialog.component';
import { SportInfoService } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.service';
import { SportInfo } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.model';

describe('Component Tests', () => {

    describe('SportInfo Management Dialog Component', () => {
        let comp: SportInfoDialogComponent;
        let fixture: ComponentFixture<SportInfoDialogComponent>;
        let service: SportInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SportInfoDialogComponent],
                providers: [
                    SportInfoService
                ]
            })
            .overrideTemplate(SportInfoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SportInfoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SportInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SportInfo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sportInfo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sportInfoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SportInfo();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sportInfo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sportInfoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
