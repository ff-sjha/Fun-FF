/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { FafiTestModule } from '../../../test.module';
import { SportInfoDetailComponent } from '../../../../../../main/webapp/app/entities/sport-info/sport-info-detail.component';
import { SportInfoService } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.service';
import { SportInfo } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.model';

describe('Component Tests', () => {

    describe('SportInfo Management Detail Component', () => {
        let comp: SportInfoDetailComponent;
        let fixture: ComponentFixture<SportInfoDetailComponent>;
        let service: SportInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SportInfoDetailComponent],
                providers: [
                    SportInfoService
                ]
            })
            .overrideTemplate(SportInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SportInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SportInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SportInfo(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sportInfo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
