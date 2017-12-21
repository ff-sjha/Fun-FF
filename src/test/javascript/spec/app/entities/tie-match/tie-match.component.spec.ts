/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { TieMatchComponent } from '../../../../../../main/webapp/app/entities/tie-match/tie-match.component';
import { TieMatchService } from '../../../../../../main/webapp/app/entities/tie-match/tie-match.service';
import { TieMatch } from '../../../../../../main/webapp/app/entities/tie-match/tie-match.model';

describe('Component Tests', () => {

    describe('TieMatch Management Component', () => {
        let comp: TieMatchComponent;
        let fixture: ComponentFixture<TieMatchComponent>;
        let service: TieMatchService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchComponent],
                providers: [
                    TieMatchService
                ]
            })
            .overrideTemplate(TieMatchComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new TieMatch(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tieMatches[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
