import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('MatchUmpire e2e test', () => {

    let navBarPage: NavBarPage;
    let matchUmpireDialogPage: MatchUmpireDialogPage;
    let matchUmpireComponentsPage: MatchUmpireComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MatchUmpires', () => {
        navBarPage.goToEntity('match-umpire');
        matchUmpireComponentsPage = new MatchUmpireComponentsPage();
        expect(matchUmpireComponentsPage.getTitle()).toMatch(/fafiApp.matchUmpire.home.title/);

    });

    it('should load create MatchUmpire dialog', () => {
        matchUmpireComponentsPage.clickOnCreateButton();
        matchUmpireDialogPage = new MatchUmpireDialogPage();
        expect(matchUmpireDialogPage.getModalTitle()).toMatch(/fafiApp.matchUmpire.home.createOrEditLabel/);
        matchUmpireDialogPage.close();
    });

   /* it('should create and save MatchUmpires', () => {
        matchUmpireComponentsPage.clickOnCreateButton();
        matchUmpireDialogPage.matchSelectLastOption();
        matchUmpireDialogPage.umpireSelectLastOption();
        matchUmpireDialogPage.save();
        expect(matchUmpireDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MatchUmpireComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-match-umpire div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MatchUmpireDialogPage {
    modalTitle = element(by.css('h4#myMatchUmpireLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    matchSelect = element(by.css('select#field_match'));
    umpireSelect = element(by.css('select#field_umpire'));

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

    umpireSelectLastOption = function() {
        this.umpireSelect.all(by.tagName('option')).last().click();
    }

    umpireSelectOption = function(option) {
        this.umpireSelect.sendKeys(option);
    }

    getUmpireSelect = function() {
        return this.umpireSelect;
    }

    getUmpireSelectedOption = function() {
        return this.umpireSelect.element(by.css('option:checked')).getText();
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
