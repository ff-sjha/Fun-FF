import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Franchise e2e test', () => {

    let navBarPage: NavBarPage;
    let franchiseDialogPage: FranchiseDialogPage;
    let franchiseComponentsPage: FranchiseComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Franchises', () => {
        navBarPage.goToEntity('franchise');
        franchiseComponentsPage = new FranchiseComponentsPage();
        expect(franchiseComponentsPage.getTitle()).toMatch(/fafiApp.franchise.home.title/);

    });

    it('should load create Franchise dialog', () => {
        franchiseComponentsPage.clickOnCreateButton();
        franchiseDialogPage = new FranchiseDialogPage();
        expect(franchiseDialogPage.getModalTitle()).toMatch(/fafiApp.franchise.home.createOrEditLabel/);
        franchiseDialogPage.close();
    });

    it('should create and save Franchises', () => {
        franchiseComponentsPage.clickOnCreateButton();
        franchiseDialogPage.setNameInput('name');
        expect(franchiseDialogPage.getNameInput()).toMatch('name');
        franchiseDialogPage.setLogoPathInput('logoPath');
        expect(franchiseDialogPage.getLogoPathInput()).toMatch('logoPath');
        franchiseDialogPage.seasonSelectLastOption();
        franchiseDialogPage.ownerSelectLastOption();
        franchiseDialogPage.iconPlayerSelectLastOption();
        franchiseDialogPage.save();
        expect(franchiseDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FranchiseComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-franchise div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FranchiseDialogPage {
    modalTitle = element(by.css('h4#myFranchiseLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    logoPathInput = element(by.css('input#field_logoPath'));
    seasonSelect = element(by.css('select#field_season'));
    ownerSelect = element(by.css('select#field_owner'));
    iconPlayerSelect = element(by.css('select#field_iconPlayer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setLogoPathInput = function(logoPath) {
        this.logoPathInput.sendKeys(logoPath);
    }

    getLogoPathInput = function() {
        return this.logoPathInput.getAttribute('value');
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
