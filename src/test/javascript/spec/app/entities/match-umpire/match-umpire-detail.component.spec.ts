/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { MatchUmpireDetailComponent } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire-detail.component';
import { MatchUmpireService } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire.service';
import { MatchUmpire } from '../../../../../../main/webapp/app/entities/match-umpire/match-umpire.model';

describe('Component Tests', () => {

    describe('MatchUmpire Management Detail Component', () => {
        let comp: MatchUmpireDetailComponent;
        let fixture: ComponentFixture<MatchUmpireDetailComponent>;
        let service: MatchUmpireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchUmpireDetailComponent],
                providers: [
                    MatchUmpireService
                ]
            })
            .overrideTemplate(MatchUmpireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchUmpireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchUmpireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MatchUmpire(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.matchUmpire).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
