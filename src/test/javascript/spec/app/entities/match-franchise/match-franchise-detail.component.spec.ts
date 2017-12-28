/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { MatchFranchiseDetailComponent } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise-detail.component';
import { MatchFranchiseService } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise.service';
import { MatchFranchise } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise.model';

describe('Component Tests', () => {

    describe('MatchFranchise Management Detail Component', () => {
        let comp: MatchFranchiseDetailComponent;
        let fixture: ComponentFixture<MatchFranchiseDetailComponent>;
        let service: MatchFranchiseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchFranchiseDetailComponent],
                providers: [
                    MatchFranchiseService
                ]
            })
            .overrideTemplate(MatchFranchiseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchFranchiseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchFranchiseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MatchFranchise(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.matchFranchise).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
