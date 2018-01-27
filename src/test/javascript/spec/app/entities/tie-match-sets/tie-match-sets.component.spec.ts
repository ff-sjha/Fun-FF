/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { TieMatchSetsComponent } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets.component';
import { TieMatchSetsService } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets.service';
import { TieMatchSets } from '../../../../../../main/webapp/app/entities/tie-match-sets/tie-match-sets.model';

describe('Component Tests', () => {

    describe('TieMatchSets Management Component', () => {
        let comp: TieMatchSetsComponent;
        let fixture: ComponentFixture<TieMatchSetsComponent>;
        let service: TieMatchSetsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchSetsComponent],
                providers: [
                    TieMatchSetsService
                ]
            })
            .overrideTemplate(TieMatchSetsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchSetsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchSetsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new TieMatchSets(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tieMatchSets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
