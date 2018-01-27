/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { TieMatchPlayersComponent } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players.component';
import { TieMatchPlayersService } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players.service';
import { TieMatchPlayers } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players.model';

describe('Component Tests', () => {

    describe('TieMatchPlayers Management Component', () => {
        let comp: TieMatchPlayersComponent;
        let fixture: ComponentFixture<TieMatchPlayersComponent>;
        let service: TieMatchPlayersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchPlayersComponent],
                providers: [
                    TieMatchPlayersService
                ]
            })
            .overrideTemplate(TieMatchPlayersComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchPlayersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchPlayersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new TieMatchPlayers(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tieMatchPlayers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
