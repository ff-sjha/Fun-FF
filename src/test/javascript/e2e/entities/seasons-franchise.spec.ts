import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SeasonsFranchise e2e test', () => {

    let navBarPage: NavBarPage;
    let seasonsFranchiseDialogPage: SeasonsFranchiseDialogPage;
    let seasonsFranchiseComponentsPage: SeasonsFranchiseComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SeasonsFranchises', () => {
        navBarPage.goToEntity('seasons-franchise');
        seasonsFranchiseComponentsPage = new SeasonsFranchiseComponentsPage();
        expect(seasonsFranchiseComponentsPage.getTitle()).toMatch(/fafiApp.seasonsFranchise.home.title/);

    });

    it('should load create SeasonsFranchise dialog', () => {
        seasonsFranchiseComponentsPage.clickOnCreateButton();
        seasonsFranchiseDialogPage = new SeasonsFranchiseDialogPage();
        expect(seasonsFranchiseDialogPage.getModalTitle()).toMatch(/fafiApp.seasonsFranchise.home.createOrEditLabel/);
        seasonsFranchiseDialogPage.close();
    });

   /* it('should create and save SeasonsFranchises', () => {
        seasonsFranchiseComponentsPage.clickOnCreateButton();
        seasonsFranchiseDialogPage.seasonSelectLastOption();
        seasonsFranchiseDialogPage.franchiseSelectLastOption();
        seasonsFranchiseDialogPage.ownerSelectLastOption();
        seasonsFranchiseDialogPage.iconPlayerSelectLastOption();
        seasonsFranchiseDialogPage.save();
        expect(seasonsFranchiseDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SeasonsFranchiseComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-seasons-franchise div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SeasonsFranchiseDialogPage {
    modalTitle = element(by.css('h4#mySeasonsFranchiseLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    seasonSelect = element(by.css('select#field_season'));
    franchiseSelect = element(by.css('select#field_franchise'));
    ownerSelect = element(by.css('select#field_owner'));
    iconPlayerSelect = element(by.css('select#field_iconPlayer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    seasonSelectLastOption = function() {
        this.seasonSelect.all(by.tagName('option')).last().click();
    }

    seasonSelectOption = function(option) {
        this.seasonSelect.sendKeys(option);
    }

    getSeasonSelect = function() {
        return this.seasonSelect;
    }

    getSeasonSelectedOption = function() {
        return this.seasonSelect.element(by.css('option:checked')).getText();
    }

    franchiseSelectLastOption = function() {
        this.franchiseSelect.all(by.tagName('option')).last().click();
    }

    franchiseSelectOption = function(option) {
        this.franchiseSelect.sendKeys(option);
    }

    getFranchiseSelect = function() {
        return this.franchiseSelect;
    }

    getFranchiseSelectedOption = function() {
        return this.franchiseSelect.element(by.css('option:checked')).getText();
    }

    ownerSelectLastOption = function() {
        this.ownerSelect.all(by.tagName('option')).last().click();
    }

    ownerSelectOption = function(option) {
        this.ownerSelect.sendKeys(option);
    }

    getOwnerSelect = function() {
        return this.ownerSelect;
    }

    getOwnerSelectedOption = function() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
    }

    iconPlayerSelectLastOption = function() {
        this.iconPlayerSelect.all(by.tagName('option')).last().click();
    }

    iconPlayerSelectOption = function(option) {
        this.iconPlayerSelect.sendKeys(option);
    }

    getIconPlayerSelect = function() {
        return this.iconPlayerSelect;
    }

    getIconPlayerSelectedOption = function() {
        return this.iconPlayerSelect.element(by.css('option:checked')).getText();
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
