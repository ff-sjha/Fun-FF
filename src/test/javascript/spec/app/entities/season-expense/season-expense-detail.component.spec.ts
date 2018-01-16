/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { SeasonExpenseDetailComponent } from '../../../../../../main/webapp/app/entities/season-expense/season-expense-detail.component';
import { SeasonExpenseService } from '../../../../../../main/webapp/app/entities/season-expense/season-expense.service';
import { SeasonExpense } from '../../../../../../main/webapp/app/entities/season-expense/season-expense.model';

describe('Component Tests', () => {

    describe('SeasonExpense Management Detail Component', () => {
        let comp: SeasonExpenseDetailComponent;
        let fixture: ComponentFixture<SeasonExpenseDetailComponent>;
        let service: SeasonExpenseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonExpenseDetailComponent],
                providers: [
                    SeasonExpenseService
                ]
            })
            .overrideTemplate(SeasonExpenseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonExpenseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonExpenseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SeasonExpense(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.seasonExpense).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
