/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { SeasonExpenseComponent } from '../../../../../../main/webapp/app/entities/season-expense/season-expense.component';
import { SeasonExpenseService } from '../../../../../../main/webapp/app/entities/season-expense/season-expense.service';
import { SeasonExpense } from '../../../../../../main/webapp/app/entities/season-expense/season-expense.model';

describe('Component Tests', () => {

    describe('SeasonExpense Management Component', () => {
        let comp: SeasonExpenseComponent;
        let fixture: ComponentFixture<SeasonExpenseComponent>;
        let service: SeasonExpenseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonExpenseComponent],
                providers: [
                    SeasonExpenseService
                ]
            })
            .overrideTemplate(SeasonExpenseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonExpenseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonExpenseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SeasonExpense(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.seasonExpenses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
