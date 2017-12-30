import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('News e2e test', () => {

    let navBarPage: NavBarPage;
    let newsDialogPage: NewsDialogPage;
    let newsComponentsPage: NewsComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load News', () => {
        navBarPage.goToEntity('news');
        newsComponentsPage = new NewsComponentsPage();
        expect(newsComponentsPage.getTitle()).toMatch(/fafiApp.news.home.title/);

    });

    it('should load create News dialog', () => {
        newsComponentsPage.clickOnCreateButton();
        newsDialogPage = new NewsDialogPage();
        expect(newsDialogPage.getModalTitle()).toMatch(/fafiApp.news.home.createOrEditLabel/);
        newsDialogPage.close();
    });

    it('should create and save News', () => {
        newsComponentsPage.clickOnCreateButton();
        newsDialogPage.setTitleInput('title');
        expect(newsDialogPage.getTitleInput()).toMatch('title');
        newsDialogPage.setDetailsInput('details');
        expect(newsDialogPage.getDetailsInput()).toMatch('details');
        newsDialogPage.setActiveFromInput('2000-12-31');
        expect(newsDialogPage.getActiveFromInput()).toMatch('2000-12-31');
        newsDialogPage.setAtiveTillInput('2000-12-31');
        expect(newsDialogPage.getAtiveTillInput()).toMatch('2000-12-31');
        newsDialogPage.save();
        expect(newsDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class NewsComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-news div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class NewsDialogPage {
    modalTitle = element(by.css('h4#myNewsLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    detailsInput = element(by.css('textarea#field_details'));
    activeFromInput = element(by.css('input#field_activeFrom'));
    ativeTillInput = element(by.css('input#field_ativeTill'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    }

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    }

    setDetailsInput = function(details) {
        this.detailsInput.sendKeys(details);
    }

    getDetailsInput = function() {
        return this.detailsInput.getAttribute('value');
    }

    setActiveFromInput = function(activeFrom) {
        this.activeFromInput.sendKeys(activeFrom);
    }

    getActiveFromInput = function() {
        return this.activeFromInput.getAttribute('value');
    }

    setAtiveTillInput = function(ativeTill) {
        this.ativeTillInput.sendKeys(ativeTill);
    }

    getAtiveTillInput = function() {
        return this.ativeTillInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
