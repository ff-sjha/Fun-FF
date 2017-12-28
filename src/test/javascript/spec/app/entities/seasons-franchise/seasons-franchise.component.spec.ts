/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { SeasonsFranchiseComponent } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise.component';
import { SeasonsFranchiseService } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise.service';
import { SeasonsFranchise } from '../../../../../../main/webapp/app/entities/seasons-franchise/seasons-franchise.model';

describe('Component Tests', () => {

    describe('SeasonsFranchise Management Component', () => {
        let comp: SeasonsFranchiseComponent;
        let fixture: ComponentFixture<SeasonsFranchiseComponent>;
        let service: SeasonsFranchiseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonsFranchiseComponent],
                providers: [
                    SeasonsFranchiseService
                ]
            })
            .overrideTemplate(SeasonsFranchiseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonsFranchiseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonsFranchiseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SeasonsFranchise(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.seasonsFranchises[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
