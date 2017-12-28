import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('MatchFranchise e2e test', () => {

    let navBarPage: NavBarPage;
    let matchFranchiseDialogPage: MatchFranchiseDialogPage;
    let matchFranchiseComponentsPage: MatchFranchiseComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MatchFranchises', () => {
        navBarPage.goToEntity('match-franchise');
        matchFranchiseComponentsPage = new MatchFranchiseComponentsPage();
        expect(matchFranchiseComponentsPage.getTitle()).toMatch(/fafiApp.matchFranchise.home.title/);

    });

    it('should load create MatchFranchise dialog', () => {
        matchFranchiseComponentsPage.clickOnCreateButton();
        matchFranchiseDialogPage = new MatchFranchiseDialogPage();
        expect(matchFranchiseDialogPage.getModalTitle()).toMatch(/fafiApp.matchFranchise.home.createOrEditLabel/);
        matchFranchiseDialogPage.close();
    });

   /* it('should create and save MatchFranchises', () => {
        matchFranchiseComponentsPage.clickOnCreateButton();
        matchFranchiseDialogPage.matchSelectLastOption();
        matchFranchiseDialogPage.seasonsFranchiseSelectLastOption();
        matchFranchiseDialogPage.save();
        expect(matchFranchiseDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MatchFranchiseComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-match-franchise div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MatchFranchiseDialogPage {
    modalTitle = element(by.css('h4#myMatchFranchiseLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    matchSelect = element(by.css('select#field_match'));
    seasonsFranchiseSelect = element(by.css('select#field_seasonsFranchise'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    matchSelectLastOption = function() {
        this.matchSelect.all(by.tagName('option')).last().click();
    }

    matchSelectOption = function(option) {
        this.matchSelect.sendKeys(option);
    }

    getMatchSelect = function() {
        return this.matchSelect;
    }

    getMatchSelectedOption = function() {
        return this.matchSelect.element(by.css('option:checked')).getText();
    }

    seasonsFranchiseSelectLastOption = function() {
        this.seasonsFranchiseSelect.all(by.tagName('option')).last().click();
    }

    seasonsFranchiseSelectOption = function(option) {
        this.seasonsFranchiseSelect.sendKeys(option);
    }

    getSeasonsFranchiseSelect = function() {
        return this.seasonsFranchiseSelect;
    }

    getSeasonsFranchiseSelectedOption = function() {
        return this.seasonsFranchiseSelect.element(by.css('option:checked')).getText();
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
