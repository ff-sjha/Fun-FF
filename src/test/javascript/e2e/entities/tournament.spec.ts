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
        tournamentDialogPage.setStartDateInput('2000-12-31');
        expect(tournamentDialogPage.getStartDateInput()).toMatch('2000-12-31');
        tournamentDialogPage.setEndDateInput('2000-12-31');
        expect(tournamentDialogPage.getEndDateInput()).toMatch('2000-12-31');
        tournamentDialogPage.typeSelectLastOption();
        tournamentDialogPage.seasonSelectLastOption();
        tournamentDialogPage.winningFranchiseSelectLastOption();
        tournamentDialogPage.playerOfTournamentSelectLastOption();
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
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));
    typeSelect = element(by.css('select#field_type'));
    seasonSelect = element(by.css('select#field_season'));
    winningFranchiseSelect = element(by.css('select#field_winningFranchise'));
    playerOfTournamentSelect = element(by.css('select#field_playerOfTournament'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
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

    setTypeSelect = function(type) {
        this.typeSelect.sendKeys(type);
    }

    getTypeSelect = function() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    typeSelectLastOption = function() {
        this.typeSelect.all(by.tagName('option')).last().click();
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

    winningFranchiseSelectLastOption = function() {
        this.winningFranchiseSelect.all(by.tagName('option')).last().click();
    }

    winningFranchiseSelectOption = function(option) {
        this.winningFranchiseSelect.sendKeys(option);
    }

    getWinningFranchiseSelect = function() {
        return this.winningFranchiseSelect;
    }

    getWinningFranchiseSelectedOption = function() {
        return this.winningFranchiseSelect.element(by.css('option:checked')).getText();
    }

    playerOfTournamentSelectLastOption = function() {
        this.playerOfTournamentSelect.all(by.tagName('option')).last().click();
    }

    playerOfTournamentSelectOption = function(option) {
        this.playerOfTournamentSelect.sendKeys(option);
    }

    getPlayerOfTournamentSelect = function() {
        return this.playerOfTournamentSelect;
    }

    getPlayerOfTournamentSelectedOption = function() {
        return this.playerOfTournamentSelect.element(by.css('option:checked')).getText();
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
