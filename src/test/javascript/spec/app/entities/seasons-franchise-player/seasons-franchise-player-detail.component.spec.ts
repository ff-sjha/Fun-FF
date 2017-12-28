/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { SeasonsFranchisePlayerDetailComponent } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player-detail.component';
import { SeasonsFranchisePlayerService } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player.service';
import { SeasonsFranchisePlayer } from '../../../../../../main/webapp/app/entities/seasons-franchise-player/seasons-franchise-player.model';

describe('Component Tests', () => {

    describe('SeasonsFranchisePlayer Management Detail Component', () => {
        let comp: SeasonsFranchisePlayerDetailComponent;
        let fixture: ComponentFixture<SeasonsFranchisePlayerDetailComponent>;
        let service: SeasonsFranchisePlayerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonsFranchisePlayerDetailComponent],
                providers: [
                    SeasonsFranchisePlayerService
                ]
            })
            .overrideTemplate(SeasonsFranchisePlayerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonsFranchisePlayerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonsFranchisePlayerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SeasonsFranchisePlayer(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.seasonsFranchisePlayer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
