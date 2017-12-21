/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { TieMatchDetailComponent } from '../../../../../../main/webapp/app/entities/tie-match/tie-match-detail.component';
import { TieMatchService } from '../../../../../../main/webapp/app/entities/tie-match/tie-match.service';
import { TieMatch } from '../../../../../../main/webapp/app/entities/tie-match/tie-match.model';

describe('Component Tests', () => {

    describe('TieMatch Management Detail Component', () => {
        let comp: TieMatchDetailComponent;
        let fixture: ComponentFixture<TieMatchDetailComponent>;
        let service: TieMatchService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchDetailComponent],
                providers: [
                    TieMatchService
                ]
            })
            .overrideTemplate(TieMatchDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new TieMatch(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tieMatch).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
