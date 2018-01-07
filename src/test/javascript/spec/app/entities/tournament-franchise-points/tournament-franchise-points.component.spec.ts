/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { TournamentFranchisePointsComponent } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points.component';
import { TournamentFranchisePointsService } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points.service';
import { TournamentFranchisePoints } from '../../../../../../main/webapp/app/entities/tournament-franchise-points/tournament-franchise-points.model';

describe('Component Tests', () => {

    describe('TournamentFranchisePoints Management Component', () => {
        let comp: TournamentFranchisePointsComponent;
        let fixture: ComponentFixture<TournamentFranchisePointsComponent>;
        let service: TournamentFranchisePointsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TournamentFranchisePointsComponent],
                providers: [
                    TournamentFranchisePointsService
                ]
            })
            .overrideTemplate(TournamentFranchisePointsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TournamentFranchisePointsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TournamentFranchisePointsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new TournamentFranchisePoints(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tournamentFranchisePoints[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
