import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('TieMatchPlayers e2e test', () => {

    let navBarPage: NavBarPage;
    let tieMatchPlayersDialogPage: TieMatchPlayersDialogPage;
    let tieMatchPlayersComponentsPage: TieMatchPlayersComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TieMatchPlayers', () => {
        navBarPage.goToEntity('tie-match-players');
        tieMatchPlayersComponentsPage = new TieMatchPlayersComponentsPage();
        expect(tieMatchPlayersComponentsPage.getTitle()).toMatch(/fafiApp.tieMatchPlayers.home.title/);

    });

    it('should load create TieMatchPlayers dialog', () => {
        tieMatchPlayersComponentsPage.clickOnCreateButton();
        tieMatchPlayersDialogPage = new TieMatchPlayersDialogPage();
        expect(tieMatchPlayersDialogPage.getModalTitle()).toMatch(/fafiApp.tieMatchPlayers.home.createOrEditLabel/);
        tieMatchPlayersDialogPage.close();
    });

   /* it('should create and save TieMatchPlayers', () => {
        tieMatchPlayersComponentsPage.clickOnCreateButton();
        tieMatchPlayersDialogPage.tieMatchSelectLastOption();
        tieMatchPlayersDialogPage.seasonsFranchisePlayerSelectLastOption();
        tieMatchPlayersDialogPage.save();
        expect(tieMatchPlayersDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TieMatchPlayersComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-tie-match-players div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TieMatchPlayersDialogPage {
    modalTitle = element(by.css('h4#myTieMatchPlayersLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    tieMatchSelect = element(by.css('select#field_tieMatch'));
    seasonsFranchisePlayerSelect = element(by.css('select#field_seasonsFranchisePlayer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    tieMatchSelectLastOption = function() {
        this.tieMatchSelect.all(by.tagName('option')).last().click();
    }

    tieMatchSelectOption = function(option) {
        this.tieMatchSelect.sendKeys(option);
    }

    getTieMatchSelect = function() {
        return this.tieMatchSelect;
    }

    getTieMatchSelectedOption = function() {
        return this.tieMatchSelect.element(by.css('option:checked')).getText();
    }

    seasonsFranchisePlayerSelectLastOption = function() {
        this.seasonsFranchisePlayerSelect.all(by.tagName('option')).last().click();
    }

    seasonsFranchisePlayerSelectOption = function(option) {
        this.seasonsFranchisePlayerSelect.sendKeys(option);
    }

    getSeasonsFranchisePlayerSelect = function() {
        return this.seasonsFranchisePlayerSelect;
    }

    getSeasonsFranchisePlayerSelectedOption = function() {
        return this.seasonsFranchisePlayerSelect.element(by.css('option:checked')).getText();
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
