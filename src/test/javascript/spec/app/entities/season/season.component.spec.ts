/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { FafiTestModule } from '../../../test.module';
import { SeasonComponent } from '../../../../../../main/webapp/app/entities/season/season.component';
import { SeasonService } from '../../../../../../main/webapp/app/entities/season/season.service';
import { Season } from '../../../../../../main/webapp/app/entities/season/season.model';

describe('Component Tests', () => {

    describe('Season Management Component', () => {
        let comp: SeasonComponent;
        let fixture: ComponentFixture<SeasonComponent>;
        let service: SeasonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FafiTestModule],
                declarations: [SeasonComponent],
                providers: [
                    SeasonService
                ]
            })
            .overrideTemplate(SeasonComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Season(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.seasons[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
