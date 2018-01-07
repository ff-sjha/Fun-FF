/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { TournamentFranchisePointsDetailComponent } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points-detail.component';
import { TournamentFranchisePointsService } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points.service';
import { TournamentFranchisePoints } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points.model';

describe('Component Tests', () => {

    describe('TournamentFranchisePoints Management Detail Component', () => {
        let comp: TournamentFranchisePointsDetailComponent;
        let fixture: ComponentFixture<TournamentFranchisePointsDetailComponent>;
        let service: TournamentFranchisePointsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TournamentFranchisePointsDetailComponent],
                providers: [
                    TournamentFranchisePointsService
                ]
            })
            .overrideTemplate(TournamentFranchisePointsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TournamentFranchisePointsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TournamentFranchisePointsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new TournamentFranchisePoints(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tournamentFranchisePoints).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
