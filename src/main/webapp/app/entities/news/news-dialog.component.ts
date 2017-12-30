import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { News } from './news.model';
import { NewsPopupService } from './news-popup.service';
import { NewsService } from './news.service';

@Component({
    selector: 'fafi-news-dialog',
    templateUrl: './news-dialog.component.html'
})
export class NewsDialogComponent implements OnInit {

    news: News;
    isSaving: boolean;
    activeFromDp: any;
    ativeTillDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private newsService: NewsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.news.id !== undefined) {
            this.subscribeToSaveResponse(
                this.newsService.update(this.news));
        } else {
            this.subscribeToSaveResponse(
                this.newsService.create(this.news));
        }
    }

    private subscribeToSaveResponse(result: Observable<News>) {
        result.subscribe((res: News) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: News) {
        this.eventManager.broadcast({ name: 'newsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'fafi-news-popup',
    template: ''
})
export class NewsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private newsPopupService: NewsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.newsPopupService
                    .open(NewsDialogComponent as Component, params['id']);
            } else {
                this.newsPopupService
                    .open(NewsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
