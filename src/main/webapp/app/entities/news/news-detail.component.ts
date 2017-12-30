import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { News } from './news.model';
import { NewsService } from './news.service';

@Component({
    selector: 'fafi-news-detail',
    templateUrl: './news-detail.component.html'
})
export class NewsDetailComponent implements OnInit, OnDestroy {

    news: News;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private newsService: NewsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNews();
    }

    load(id) {
        this.newsService.find(id).subscribe((news) => {
            this.news = news;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNews() {
        this.eventSubscriber = this.eventManager.subscribe(
            'newsListModification',
            (response) => this.load(this.news.id)
        );
    }
}
