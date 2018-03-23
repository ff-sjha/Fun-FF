/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { SportInfoComponent } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.component';
import { SportInfoService } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.service';
import { SportInfo } from '../../../../../../main/webapp/app/entities/sport-info/sport-info.model';

describe('Component Tests', () => {

    describe('SportInfo Management Component', () => {
        let comp: SportInfoComponent;
        let fixture: ComponentFixture<SportInfoComponent>;
        let service: SportInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SportInfoComponent],
                providers: [
                    SportInfoService
                ]
            })
            .overrideTemplate(SportInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SportInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SportInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SportInfo(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sportInfos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
