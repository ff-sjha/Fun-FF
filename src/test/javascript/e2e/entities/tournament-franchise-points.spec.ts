import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('TournamentFranchisePoints e2e test', () => {

    let navBarPage: NavBarPage;
    let tournamentFranchisePointsDialogPage: TournamentFranchisePointsDialogPage;
    let tournamentFranchisePointsComponentsPage: TournamentFranchisePointsComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TournamentFranchisePoints', () => {
        navBarPage.goToEntity('tournament-franchise-points');
        tournamentFranchisePointsComponentsPage = new TournamentFranchisePointsComponentsPage();
        expect(tournamentFranchisePointsComponentsPage.getTitle()).toMatch(/fafiApp.tournamentFranchisePoints.home.title/);

    });

    it('should load create TournamentFranchisePoints dialog', () => {
        tournamentFranchisePointsComponentsPage.clickOnCreateButton();
        tournamentFranchisePointsDialogPage = new TournamentFranchisePointsDialogPage();
        expect(tournamentFranchisePointsDialogPage.getModalTitle()).toMatch(/fafiApp.tournamentFranchisePoints.home.createOrEditLabel/);
        tournamentFranchisePointsDialogPage.close();
    });

   /* it('should create and save TournamentFranchisePoints', () => {
        tournamentFranchisePointsComponentsPage.clickOnCreateButton();
        tournamentFranchisePointsDialogPage.setPointsInput('5');
        expect(tournamentFranchisePointsDialogPage.getPointsInput()).toMatch('5');
        tournamentFranchisePointsDialogPage.tournamentSelectLastOption();
        tournamentFranchisePointsDialogPage.franchiseSelectLastOption();
        tournamentFranchisePointsDialogPage.save();
        expect(tournamentFranchisePointsDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TournamentFranchisePointsComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-tournament-franchise-points div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TournamentFranchisePointsDialogPage {
    modalTitle = element(by.css('h4#myTournamentFranchisePointsLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    pointsInput = element(by.css('input#field_points'));
    tournamentSelect = element(by.css('select#field_tournament'));
    franchiseSelect = element(by.css('select#field_franchise'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPointsInput = function(points) {
        this.pointsInput.sendKeys(points);
    }

    getPointsInput = function() {
        return this.pointsInput.getAttribute('value');
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
