/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { TieTeamComponent } from '../../../../../../main/webapp/app/entities/tie-team/tie-team.component';
import { TieTeamService } from '../../../../../../main/webapp/app/entities/tie-team/tie-team.service';
import { TieTeam } from '../../../../../../main/webapp/app/entities/tie-team/tie-team.model';

describe('Component Tests', () => {

    describe('TieTeam Management Component', () => {
        let comp: TieTeamComponent;
        let fixture: ComponentFixture<TieTeamComponent>;
        let service: TieTeamService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [TieTeamComponent],
                providers: [
                    TieTeamService
                ]
            })
            .overrideTemplate(TieTeamComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TieTeamComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TieTeamService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new TieTeam(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tieTeams[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
