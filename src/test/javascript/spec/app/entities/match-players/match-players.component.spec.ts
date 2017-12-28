/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { MatchPlayersComponent } from '../../../../../../main/webapp/app/entities/match-players/match-players.component';
import { MatchPlayersService } from '../../../../../../main/webapp/app/entities/match-players/match-players.service';
import { MatchPlayers } from '../../../../../../main/webapp/app/entities/match-players/match-players.model';

describe('Component Tests', () => {

    describe('MatchPlayers Management Component', () => {
        let comp: MatchPlayersComponent;
        let fixture: ComponentFixture<MatchPlayersComponent>;
        let service: MatchPlayersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchPlayersComponent],
                providers: [
                    MatchPlayersService
                ]
            })
            .overrideTemplate(MatchPlayersComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchPlayersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchPlayersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MatchPlayers(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.matchPlayers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
