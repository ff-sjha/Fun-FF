/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { MatchUmpireComponent } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire.component';
import { MatchUmpireService } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire.service';
import { MatchUmpire } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire.model';

describe('Component Tests', () => {

    describe('MatchUmpire Management Component', () => {
        let comp: MatchUmpireComponent;
        let fixture: ComponentFixture<MatchUmpireComponent>;
        let service: MatchUmpireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchUmpireComponent],
                providers: [
                    MatchUmpireService
                ]
            })
            .overrideTemplate(MatchUmpireComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchUmpireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchUmpireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MatchUmpire(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.matchUmpires[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
