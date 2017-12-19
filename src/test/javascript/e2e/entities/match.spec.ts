import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Match e2e test', () => {

    let navBarPage: NavBarPage;
    let matchDialogPage: MatchDialogPage;
    let matchComponentsPage: MatchComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Matches', () => {
        navBarPage.goToEntity('match');
        matchComponentsPage = new MatchComponentsPage();
        expect(matchComponentsPage.getTitle()).toMatch(/fafiApp.match.home.title/);

    });

    it('should load create Match dialog', () => {
        matchComponentsPage.clickOnCreateButton();
        matchDialogPage = new MatchDialogPage();
        expect(matchDialogPage.getModalTitle()).toMatch(/fafiApp.match.home.createOrEditLabel/);
        matchDialogPage.close();
    });

    it('should create and save Matches', () => {
        matchComponentsPage.clickOnCreateButton();
        matchDialogPage.setStartDateTimeInput(12310020012301);
        expect(matchDialogPage.getStartDateTimeInput()).toMatch('2001-12-31T02:30');
        matchDialogPage.setEndDateTimeInput(12310020012301);
        expect(matchDialogPage.getEndDateTimeInput()).toMatch('2001-12-31T02:30');
        matchDialogPage.tournamentSelectLastOption();
        matchDialogPage.save();
        expect(matchDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MatchComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-match div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MatchDialogPage {
    modalTitle = element(by.css('h4#myMatchLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    startDateTimeInput = element(by.css('input#field_startDateTime'));
    endDateTimeInput = element(by.css('input#field_endDateTime'));
    tournamentSelect = element(by.css('select#field_tournament'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setStartDateTimeInput = function(startDateTime) {
        this.startDateTimeInput.sendKeys(startDateTime);
    }

    getStartDateTimeInput = function() {
        return this.startDateTimeInput.getAttribute('value');
    }

    setEndDateTimeInput = function(endDateTime) {
        this.endDateTimeInput.sendKeys(endDateTime);
    }

    getEndDateTimeInput = function() {
        return this.endDateTimeInput.getAttribute('value');
    }

    tournamentSelectLastOption = function() {
        this.tournamentSelect.all(by.tagName('option')).last().click();
    }

    tournamentSelectOption = function(option) {
        this.tournamentSelect.sendKeys(option);
    }

    getTournamentSelect = function() {
        return this.tournamentSelect;
    }

    getTournamentSelectedOption = function() {
        return this.tournamentSelect.element(by.css('option:checked')).getText();
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
