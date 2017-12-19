/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { TournamentComponent } from '../../../../../../main/webapp/app/entities/tournament/tournament.component';
import { TournamentService } from '../../../../../../main/webapp/app/entities/tournament/tournament.service';
import { Tournament } from '../../../../../../main/webapp/app/entities/tournament/tournament.model';

describe('Component Tests', () => {

    describe('Tournament Management Component', () => {
        let comp: TournamentComponent;
        let fixture: ComponentFixture<TournamentComponent>;
        let service: TournamentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TournamentComponent],
                providers: [
                    TournamentService
                ]
            })
            .overrideTemplate(TournamentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TournamentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TournamentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Tournament(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tournaments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
