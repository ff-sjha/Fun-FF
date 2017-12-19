import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Season e2e test', () => {

    let navBarPage: NavBarPage;
    let seasonDialogPage: SeasonDialogPage;
    let seasonComponentsPage: SeasonComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Seasons', () => {
        navBarPage.goToEntity('season');
        seasonComponentsPage = new SeasonComponentsPage();
        expect(seasonComponentsPage.getTitle()).toMatch(/fafiApp.season.home.title/);

    });

    it('should load create Season dialog', () => {
        seasonComponentsPage.clickOnCreateButton();
        seasonDialogPage = new SeasonDialogPage();
        expect(seasonDialogPage.getModalTitle()).toMatch(/fafiApp.season.home.createOrEditLabel/);
        seasonDialogPage.close();
    });

    it('should create and save Seasons', () => {
        seasonComponentsPage.clickOnCreateButton();
        seasonDialogPage.setNameInput('name');
        expect(seasonDialogPage.getNameInput()).toMatch('name');
        seasonDialogPage.setStartDateInput('2000-12-31');
        expect(seasonDialogPage.getStartDateInput()).toMatch('2000-12-31');
        seasonDialogPage.setEndDateInput('2000-12-31');
        expect(seasonDialogPage.getEndDateInput()).toMatch('2000-12-31');
        seasonDialogPage.getActiveInput().isSelected().then((selected) => {
            if (selected) {
                seasonDialogPage.getActiveInput().click();
                expect(seasonDialogPage.getActiveInput().isSelected()).toBeFalsy();
            } else {
                seasonDialogPage.getActiveInput().click();
                expect(seasonDialogPage.getActiveInput().isSelected()).toBeTruthy();
            }
        });
        seasonDialogPage.setWinnerInput('winner');
        expect(seasonDialogPage.getWinnerInput()).toMatch('winner');
        seasonDialogPage.save();
        expect(seasonDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SeasonComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-season div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SeasonDialogPage {
    modalTitle = element(by.css('h4#mySeasonLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));
    activeInput = element(by.css('input#field_active'));
    winnerInput = element(by.css('input#field_winner'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setStartDateInput = function(startDate) {
        this.startDateInput.sendKeys(startDate);
    }

    getStartDateInput = function() {
        return this.startDateInput.getAttribute('value');
    }

    setEndDateInput = function(endDate) {
        this.endDateInput.sendKeys(endDate);
    }

    getEndDateInput = function() {
        return this.endDateInput.getAttribute('value');
    }

    getActiveInput = function() {
        return this.activeInput;
    }
    setWinnerInput = function(winner) {
        this.winnerInput.sendKeys(winner);
    }

    getWinnerInput = function() {
        return this.winnerInput.getAttribute('value');
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
