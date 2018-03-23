/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { SportInfoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sport-info/sport-info-delete-dialog.component';
import { SportInfoService } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.service';

describe('Component Tests', () => {

    describe('SportInfo Management Delete Component', () => {
        let comp: SportInfoDeleteDialogComponent;
        let fixture: ComponentFixture<SportInfoDeleteDialogComponent>;
        let service: SportInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SportInfoDeleteDialogComponent],
                providers: [
                    SportInfoService
                ]
            })
            .overrideTemplate(SportInfoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SportInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SportInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
