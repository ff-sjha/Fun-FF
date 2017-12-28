/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { MatchPlayersDetailComponent } from '../../../../../../main/webapp/app/entities/match-players/match-players-detail.component';
import { MatchPlayersService } from '../../../../../../main/webapp/app/entities/match-players/match-players.service';
import { MatchPlayers } from '../../../../../../main/webapp/app/entities/match-players/match-players.model';

describe('Component Tests', () => {

    describe('MatchPlayers Management Detail Component', () => {
        let comp: MatchPlayersDetailComponent;
        let fixture: ComponentFixture<MatchPlayersDetailComponent>;
        let service: MatchPlayersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchPlayersDetailComponent],
                providers: [
                    MatchPlayersService
                ]
            })
            .overrideTemplate(MatchPlayersDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchPlayersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchPlayersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MatchPlayers(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.matchPlayers).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
