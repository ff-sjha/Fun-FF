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
        matchDialogPage.setPointsEarnedByFranchiseInput('5');
        expect(matchDialogPage.getPointsEarnedByFranchiseInput()).toMatch('5');
        matchDialogPage.setMatchNameInput('matchName');
        expect(matchDialogPage.getMatchNameInput()).toMatch('matchName');
        matchDialogPage.stageSelectLastOption();
        matchDialogPage.setVenueInput('venue');
        expect(matchDialogPage.getVenueInput()).toMatch('venue');
        matchDialogPage.getCompletedInput().isSelected().then((selected) => {
            if (selected) {
                matchDialogPage.getCompletedInput().click();
                expect(matchDialogPage.getCompletedInput().isSelected()).toBeFalsy();
            } else {
                matchDialogPage.getCompletedInput().click();
                expect(matchDialogPage.getCompletedInput().isSelected()).toBeTruthy();
            }
        });
        matchDialogPage.tournamentSelectLastOption();
        matchDialogPage.winningFranchiseSelectLastOption();
        matchDialogPage.team1SelectLastOption();
        matchDialogPage.team2SelectLastOption();
        matchDialogPage.team3SelectLastOption();
        matchDialogPage.team4SelectLastOption();
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
    pointsEarnedByFranchiseInput = element(by.css('input#field_pointsEarnedByFranchise'));
    matchNameInput = element(by.css('input#field_matchName'));
    stageSelect = element(by.css('select#field_stage'));
    venueInput = element(by.css('input#field_venue'));
    completedInput = element(by.css('input#field_completed'));
    tournamentSelect = element(by.css('select#field_tournament'));
    winningFranchiseSelect = element(by.css('select#field_winningFranchise'));
    team1Select = element(by.css('select#field_team1'));
    team2Select = element(by.css('select#field_team2'));
    team3Select = element(by.css('select#field_team3'));
    team4Select = element(by.css('select#field_team4'));

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

    setPointsEarnedByFranchiseInput = function(pointsEarnedByFranchise) {
        this.pointsEarnedByFranchiseInput.sendKeys(pointsEarnedByFranchise);
    }

    getPointsEarnedByFranchiseInput = function() {
        return this.pointsEarnedByFranchiseInput.getAttribute('value');
    }

    setMatchNameInput = function(matchName) {
        this.matchNameInput.sendKeys(matchName);
    }

    getMatchNameInput = function() {
        return this.matchNameInput.getAttribute('value');
    }

    setStageSelect = function(stage) {
        this.stageSelect.sendKeys(stage);
    }

    getStageSelect = function() {
        return this.stageSelect.element(by.css('option:checked')).getText();
    }

    stageSelectLastOption = function() {
        this.stageSelect.all(by.tagName('option')).last().click();
    }
    setVenueInput = function(venue) {
        this.venueInput.sendKeys(venue);
    }

    getVenueInput = function() {
        return this.venueInput.getAttribute('value');
    }

    getCompletedInput = function() {
        return this.completedInput;
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

    team1SelectLastOption = function() {
        this.team1Select.all(by.tagName('option')).last().click();
    }

    team1SelectOption = function(option) {
        this.team1Select.sendKeys(option);
    }

    getTeam1Select = function() {
        return this.team1Select;
    }

    getTeam1SelectedOption = function() {
        return this.team1Select.element(by.css('option:checked')).getText();
    }

    team2SelectLastOption = function() {
        this.team2Select.all(by.tagName('option')).last().click();
    }

    team2SelectOption = function(option) {
        this.team2Select.sendKeys(option);
    }

    getTeam2Select = function() {
        return this.team2Select;
    }

    getTeam2SelectedOption = function() {
        return this.team2Select.element(by.css('option:checked')).getText();
    }

    team3SelectLastOption = function() {
        this.team3Select.all(by.tagName('option')).last().click();
    }

    team3SelectOption = function(option) {
        this.team3Select.sendKeys(option);
    }

    getTeam3Select = function() {
        return this.team3Select;
    }

    getTeam3SelectedOption = function() {
        return this.team3Select.element(by.css('option:checked')).getText();
    }

    team4SelectLastOption = function() {
        this.team4Select.all(by.tagName('option')).last().click();
    }

    team4SelectOption = function(option) {
        this.team4Select.sendKeys(option);
    }

    getTeam4Select = function() {
        return this.team4Select;
    }

    getTeam4SelectedOption = function() {
        return this.team4Select.element(by.css('option:checked')).getText();
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
