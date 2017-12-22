import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Tournament e2e test', () => {

    let navBarPage: NavBarPage;
    let tournamentDialogPage: TournamentDialogPage;
    let tournamentComponentsPage: TournamentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Tournaments', () => {
        navBarPage.goToEntity('tournament');
        tournamentComponentsPage = new TournamentComponentsPage();
        expect(tournamentComponentsPage.getTitle()).toMatch(/fafiApp.tournament.home.title/);

    });

    it('should load create Tournament dialog', () => {
        tournamentComponentsPage.clickOnCreateButton();
        tournamentDialogPage = new TournamentDialogPage();
        expect(tournamentDialogPage.getModalTitle()).toMatch(/fafiApp.tournament.home.createOrEditLabel/);
        tournamentDialogPage.close();
    });

    it('should create and save Tournaments', () => {
        tournamentComponentsPage.clickOnCreateButton();
        tournamentDialogPage.setNameInput('name');
        expect(tournamentDialogPage.getNameInput()).toMatch('name');
        tournamentDialogPage.setStartDateInput('2000-12-31');
        expect(tournamentDialogPage.getStartDateInput()).toMatch('2000-12-31');
        tournamentDialogPage.setEndDateInput('2000-12-31');
        expect(tournamentDialogPage.getEndDateInput()).toMatch('2000-12-31');
        tournamentDialogPage.seasonSelectLastOption();
        tournamentDialogPage.winnerSelectLastOption();
        tournamentDialogPage.save();
        expect(tournamentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TournamentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-tournament div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TournamentDialogPage {
    modalTitle = element(by.css('h4#myTournamentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));
    seasonSelect = element(by.css('select#field_season'));
    winnerSelect = element(by.css('select#field_winner'));

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

    winnerSelectLastOption = function() {
        this.winnerSelect.all(by.tagName('option')).last().click();
    }

    winnerSelectOption = function(option) {
        this.winnerSelect.sendKeys(option);
    }

    getWinnerSelect = function() {
        return this.winnerSelect;
    }

    getWinnerSelectedOption = function() {
        return this.winnerSelect.element(by.css('option:checked')).getText();
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
