/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { TournamentDetailComponent } from '../../../../../../main/webapp/app/entities/tournament/tournament-detail.component';
import { TournamentService } from '../../../../../../main/webapp/app/entities/tournament/tournament.service';
import { Tournament } from '../../../../../../main/webapp/app/entities/tournament/tournament.model';

describe('Component Tests', () => {

    describe('Tournament Management Detail Component', () => {
        let comp: TournamentDetailComponent;
        let fixture: ComponentFixture<TournamentDetailComponent>;
        let service: TournamentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TournamentDetailComponent],
                providers: [
                    TournamentService
                ]
            })
            .overrideTemplate(TournamentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TournamentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TournamentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Tournament(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tournament).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
