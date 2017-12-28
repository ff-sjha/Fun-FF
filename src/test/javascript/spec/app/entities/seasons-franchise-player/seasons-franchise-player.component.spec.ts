/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { SeasonsFranchisePlayerComponent } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player.component';
import { SeasonsFranchisePlayerService } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player.service';
import { SeasonsFranchisePlayer } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player.model';

describe('Component Tests', () => {

    describe('SeasonsFranchisePlayer Management Component', () => {
        let comp: SeasonsFranchisePlayerComponent;
        let fixture: ComponentFixture<SeasonsFranchisePlayerComponent>;
        let service: SeasonsFranchisePlayerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonsFranchisePlayerComponent],
                providers: [
                    SeasonsFranchisePlayerService
                ]
            })
            .overrideTemplate(SeasonsFranchisePlayerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonsFranchisePlayerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonsFranchisePlayerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SeasonsFranchisePlayer(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.seasonsFranchisePlayers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
