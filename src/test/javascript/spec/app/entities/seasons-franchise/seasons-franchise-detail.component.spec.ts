/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { SeasonsFranchiseDetailComponent } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise-detail.component';
import { SeasonsFranchiseService } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise.service';
import { SeasonsFranchise } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise.model';

describe('Component Tests', () => {

    describe('SeasonsFranchise Management Detail Component', () => {
        let comp: SeasonsFranchiseDetailComponent;
        let fixture: ComponentFixture<SeasonsFranchiseDetailComponent>;
        let service: SeasonsFranchiseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonsFranchiseDetailComponent],
                providers: [
                    SeasonsFranchiseService
                ]
            })
            .overrideTemplate(SeasonsFranchiseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonsFranchiseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonsFranchiseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SeasonsFranchise(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.seasonsFranchise).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
