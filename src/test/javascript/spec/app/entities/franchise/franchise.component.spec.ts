/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { FranchiseComponent } from '../../../../../../main/webapp/app/entities/franchise/franchise.component';
import { FranchiseService } from '../../../../../../main/webapp/app/entities/franchise/franchise.service';
import { Franchise } from '../../../../../../main/webapp/app/entities/franchise/franchise.model';

describe('Component Tests', () => {

    describe('Franchise Management Component', () => {
        let comp: FranchiseComponent;
        let fixture: ComponentFixture<FranchiseComponent>;
        let service: FranchiseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [FranchiseComponent],
                providers: [
                    FranchiseService
                ]
            })
            .overrideTemplate(FranchiseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FranchiseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FranchiseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Franchise(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.franchises[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
