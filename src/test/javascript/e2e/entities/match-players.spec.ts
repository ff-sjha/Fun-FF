import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('MatchPlayers e2e test', () => {

    let navBarPage: NavBarPage;
    let matchPlayersDialogPage: MatchPlayersDialogPage;
    let matchPlayersComponentsPage: MatchPlayersComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MatchPlayers', () => {
        navBarPage.goToEntity('match-players');
        matchPlayersComponentsPage = new MatchPlayersComponentsPage();
        expect(matchPlayersComponentsPage.getTitle()).toMatch(/fafiApp.matchPlayers.home.title/);

    });

    it('should load create MatchPlayers dialog', () => {
        matchPlayersComponentsPage.clickOnCreateButton();
        matchPlayersDialogPage = new MatchPlayersDialogPage();
        expect(matchPlayersDialogPage.getModalTitle()).toMatch(/fafiApp.matchPlayers.home.createOrEditLabel/);
        matchPlayersDialogPage.close();
    });

   /* it('should create and save MatchPlayers', () => {
        matchPlayersComponentsPage.clickOnCreateButton();
        matchPlayersDialogPage.getPlayerOfTheMatchInput().isSelected().then((selected) => {
            if (selected) {
                matchPlayersDialogPage.getPlayerOfTheMatchInput().click();
                expect(matchPlayersDialogPage.getPlayerOfTheMatchInput().isSelected()).toBeFalsy();
            } else {
                matchPlayersDialogPage.getPlayerOfTheMatchInput().click();
                expect(matchPlayersDialogPage.getPlayerOfTheMatchInput().isSelected()).toBeTruthy();
            }
        });
        matchPlayersDialogPage.setPlayerPointsEarnedInput('5');
        expect(matchPlayersDialogPage.getPlayerPointsEarnedInput()).toMatch('5');
        matchPlayersDialogPage.matchSelectLastOption();
        matchPlayersDialogPage.seasonsFranchisePlayerSelectLastOption();
        matchPlayersDialogPage.save();
        expect(matchPlayersDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MatchPlayersComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-match-players div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MatchPlayersDialogPage {
    modalTitle = element(by.css('h4#myMatchPlayersLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    playerOfTheMatchInput = element(by.css('input#field_playerOfTheMatch'));
    playerPointsEarnedInput = element(by.css('input#field_playerPointsEarned'));
    matchSelect = element(by.css('select#field_match'));
    seasonsFranchisePlayerSelect = element(by.css('select#field_seasonsFranchisePlayer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    getPlayerOfTheMatchInput = function() {
        return this.playerOfTheMatchInput;
    }
    setPlayerPointsEarnedInput = function(playerPointsEarned) {
        this.playerPointsEarnedInput.sendKeys(playerPointsEarned);
    }

    getPlayerPointsEarnedInput = function() {
        return this.playerPointsEarnedInput.getAttribute('value');
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
