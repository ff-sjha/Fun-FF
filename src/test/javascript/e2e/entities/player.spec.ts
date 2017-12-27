import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Player e2e test', () => {

    let navBarPage: NavBarPage;
    let playerDialogPage: PlayerDialogPage;
    let playerComponentsPage: PlayerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Players', () => {
        navBarPage.goToEntity('player');
        playerComponentsPage = new PlayerComponentsPage();
        expect(playerComponentsPage.getTitle()).toMatch(/fafiApp.player.home.title/);

    });

    it('should load create Player dialog', () => {
        playerComponentsPage.clickOnCreateButton();
        playerDialogPage = new PlayerDialogPage();
        expect(playerDialogPage.getModalTitle()).toMatch(/fafiApp.player.home.createOrEditLabel/);
        playerDialogPage.close();
    });

    it('should create and save Players', () => {
        playerComponentsPage.clickOnCreateButton();
        playerDialogPage.setFirstNameInput('firstName');
        expect(playerDialogPage.getFirstNameInput()).toMatch('firstName');
        playerDialogPage.setLastNameInput('lastName');
        expect(playerDialogPage.getLastNameInput()).toMatch('lastName');
        playerDialogPage.setEmailInput('email');
        expect(playerDialogPage.getEmailInput()).toMatch('email');
        playerDialogPage.getActiveInput().isSelected().then((selected) => {
            if (selected) {
                playerDialogPage.getActiveInput().click();
                expect(playerDialogPage.getActiveInput().isSelected()).toBeFalsy();
            } else {
                playerDialogPage.getActiveInput().click();
                expect(playerDialogPage.getActiveInput().isSelected()).toBeTruthy();
            }
        });
        playerDialogPage.save();
        expect(playerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PlayerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-player div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PlayerDialogPage {
    modalTitle = element(by.css('h4#myPlayerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstNameInput = element(by.css('input#field_firstName'));
    lastNameInput = element(by.css('input#field_lastName'));
    emailInput = element(by.css('input#field_email'));
    activeInput = element(by.css('input#field_active'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFirstNameInput = function(firstName) {
        this.firstNameInput.sendKeys(firstName);
    }

    getFirstNameInput = function() {
        return this.firstNameInput.getAttribute('value');
    }

    setLastNameInput = function(lastName) {
        this.lastNameInput.sendKeys(lastName);
    }

    getLastNameInput = function() {
        return this.lastNameInput.getAttribute('value');
    }

    setEmailInput = function(email) {
        this.emailInput.sendKeys(email);
    }

    getEmailInput = function() {
        return this.emailInput.getAttribute('value');
    }

    getActiveInput = function() {
        return this.activeInput;
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
