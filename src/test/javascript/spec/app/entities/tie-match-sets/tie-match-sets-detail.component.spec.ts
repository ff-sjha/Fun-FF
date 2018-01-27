/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { TieMatchSetsDetailComponent } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets-detail.component';
import { TieMatchSetsService } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets.service';
import { TieMatchSets } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets.model';

describe('Component Tests', () => {

    describe('TieMatchSets Management Detail Component', () => {
        let comp: TieMatchSetsDetailComponent;
        let fixture: ComponentFixture<TieMatchSetsDetailComponent>;
        let service: TieMatchSetsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchSetsDetailComponent],
                providers: [
                    TieMatchSetsService
                ]
            })
            .overrideTemplate(TieMatchSetsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchSetsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchSetsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new TieMatchSets(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tieMatchSets).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
