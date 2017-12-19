/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { FranchiseDetailComponent } from '../../../../../../main/webapp/app/entities/franchise/franchise-detail.component';
import { FranchiseService } from '../../../../../../main/webapp/app/entities/franchise/franchise.service';
import { Franchise } from '../../../../../../main/webapp/app/entities/franchise/franchise.model';

describe('Component Tests', () => {

    describe('Franchise Management Detail Component', () => {
        let comp: FranchiseDetailComponent;
        let fixture: ComponentFixture<FranchiseDetailComponent>;
        let service: FranchiseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [FranchiseDetailComponent],
                providers: [
                    FranchiseService
                ]
            })
            .overrideTemplate(FranchiseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FranchiseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FranchiseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Franchise(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.franchise).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
