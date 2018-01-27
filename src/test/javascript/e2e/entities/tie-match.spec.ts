import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('TieMatch e2e test', () => {

    let navBarPage: NavBarPage;
    let tieMatchDialogPage: TieMatchDialogPage;
    let tieMatchComponentsPage: TieMatchComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TieMatches', () => {
        navBarPage.goToEntity('tie-match');
        tieMatchComponentsPage = new TieMatchComponentsPage();
        expect(tieMatchComponentsPage.getTitle()).toMatch(/fafiApp.tieMatch.home.title/);

    });

    it('should load create TieMatch dialog', () => {
        tieMatchComponentsPage.clickOnCreateButton();
        tieMatchDialogPage = new TieMatchDialogPage();
        expect(tieMatchDialogPage.getModalTitle()).toMatch(/fafiApp.tieMatch.home.createOrEditLabel/);
        tieMatchDialogPage.close();
    });

   /* it('should create and save TieMatches', () => {
        tieMatchComponentsPage.clickOnCreateButton();
        tieMatchDialogPage.tieTypeSelectLastOption();
        tieMatchDialogPage.matchSelectLastOption();
        tieMatchDialogPage.save();
        expect(tieMatchDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TieMatchComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-tie-match div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TieMatchDialogPage {
    modalTitle = element(by.css('h4#myTieMatchLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    tieTypeSelect = element(by.css('select#field_tieType'));
    matchSelect = element(by.css('select#field_match'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTieTypeSelect = function(tieType) {
        this.tieTypeSelect.sendKeys(tieType);
    }

    getTieTypeSelect = function() {
        return this.tieTypeSelect.element(by.css('option:checked')).getText();
    }

    tieTypeSelectLastOption = function() {
        this.tieTypeSelect.all(by.tagName('option')).last().click();
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
