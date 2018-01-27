/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { TieMatchPlayersDetailComponent } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players-detail.component';
import { TieMatchPlayersService } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players.service';
import { TieMatchPlayers } from '../../../../../../main/webapp/app/entities/tie-match-players/tie-match-players.model';

describe('Component Tests', () => {

    describe('TieMatchPlayers Management Detail Component', () => {
        let comp: TieMatchPlayersDetailComponent;
        let fixture: ComponentFixture<TieMatchPlayersDetailComponent>;
        let service: TieMatchPlayersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieMatchPlayersDetailComponent],
                providers: [
                    TieMatchPlayersService
                ]
            })
            .overrideTemplate(TieMatchPlayersDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieMatchPlayersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieMatchPlayersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new TieMatchPlayers(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tieMatchPlayers).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
