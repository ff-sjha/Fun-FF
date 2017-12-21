/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { TieTeamDetailComponent } from '../../../../../../main/webapp/app/entities/tie-team/tie-team-detail.component';
import { TieTeamService } from '../../../../../../main/webapp/app/entities/tie-team/tie-team.service';
import { TieTeam } from '../../../../../../main/webapp/app/entities/tie-team/tie-team.model';

describe('Component Tests', () => {

    describe('TieTeam Management Detail Component', () => {
        let comp: TieTeamDetailComponent;
        let fixture: ComponentFixture<TieTeamDetailComponent>;
        let service: TieTeamService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieTeamDetailComponent],
                providers: [
                    TieTeamService
                ]
            })
            .overrideTemplate(TieTeamDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieTeamDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieTeamService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new TieTeam(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tieTeam).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
