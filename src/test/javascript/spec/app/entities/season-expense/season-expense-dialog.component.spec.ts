/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FafiTestModule } from '../../../test.module';
import { SeasonExpenseDialogComponent } from '../../../../../../main/webapp/app/entities/season-expense/season-expense-dialog.component';
import { SeasonExpenseService } from '../../../../../../main/webapp/app/entities/season-expense/season-expense.service';
import { SeasonExpense } from '../../../../../../main/webapp/app/entities/season-expense/season-expense.model';
import { SeasonService } from '../../../../../../main/webapp/app/entities/season';

describe('Component Tests', () => {

    describe('SeasonExpense Management Dialog Component', () => {
        let comp: SeasonExpenseDialogComponent;
        let fixture: ComponentFixture<SeasonExpenseDialogComponent>;
        let service: SeasonExpenseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonExpenseDialogComponent],
                providers: [
                    SeasonService,
                    SeasonExpenseService
                ]
            })
            .overrideTemplate(SeasonExpenseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonExpenseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonExpenseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SeasonExpense(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.seasonExpense = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'seasonExpenseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SeasonExpense();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.seasonExpense = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'seasonExpenseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
