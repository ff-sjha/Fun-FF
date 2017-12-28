/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { MatchFranchiseComponent } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise.component';
import { MatchFranchiseService } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise.service';
import { MatchFranchise } from '../../../../../../main/webapp/app/entities/match-franchise/match-franchise.model';

describe('Component Tests', () => {

    describe('MatchFranchise Management Component', () => {
        let comp: MatchFranchiseComponent;
        let fixture: ComponentFixture<MatchFranchiseComponent>;
        let service: MatchFranchiseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [MatchFranchiseComponent],
                providers: [
                    MatchFranchiseService
                ]
            })
            .overrideTemplate(MatchFranchiseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MatchFranchiseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchFranchiseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MatchFranchise(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.matchFranchises[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
