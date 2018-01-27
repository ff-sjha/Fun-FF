import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('TieMatchSets e2e test', () => {

    let navBarPage: NavBarPage;
    let tieMatchSetsDialogPage: TieMatchSetsDialogPage;
    let tieMatchSetsComponentsPage: TieMatchSetsComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TieMatchSets', () => {
        navBarPage.goToEntity('tie-match-sets');
        tieMatchSetsComponentsPage = new TieMatchSetsComponentsPage();
        expect(tieMatchSetsComponentsPage.getTitle()).toMatch(/fafiApp.tieMatchSets.home.title/);

    });

    it('should load create TieMatchSets dialog', () => {
        tieMatchSetsComponentsPage.clickOnCreateButton();
        tieMatchSetsDialogPage = new TieMatchSetsDialogPage();
        expect(tieMatchSetsDialogPage.getModalTitle()).toMatch(/fafiApp.tieMatchSets.home.createOrEditLabel/);
        tieMatchSetsDialogPage.close();
    });

    it('should create and save TieMatchSets', () => {
        tieMatchSetsComponentsPage.clickOnCreateButton();
        tieMatchSetsDialogPage.setSetNumberInput('5');
        expect(tieMatchSetsDialogPage.getSetNumberInput()).toMatch('5');
        tieMatchSetsDialogPage.setTeam1PointsInput('5');
        expect(tieMatchSetsDialogPage.getTeam1PointsInput()).toMatch('5');
        tieMatchSetsDialogPage.setTeam2PointsInput('5');
        expect(tieMatchSetsDialogPage.getTeam2PointsInput()).toMatch('5');
        tieMatchSetsDialogPage.save();
        expect(tieMatchSetsDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TieMatchSetsComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-tie-match-sets div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TieMatchSetsDialogPage {
    modalTitle = element(by.css('h4#myTieMatchSetsLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    setNumberInput = element(by.css('input#field_setNumber'));
    team1PointsInput = element(by.css('input#field_team1Points'));
    team2PointsInput = element(by.css('input#field_team2Points'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setSetNumberInput = function(setNumber) {
        this.setNumberInput.sendKeys(setNumber);
    }

    getSetNumberInput = function() {
        return this.setNumberInput.getAttribute('value');
    }

    setTeam1PointsInput = function(team1Points) {
        this.team1PointsInput.sendKeys(team1Points);
    }

    getTeam1PointsInput = function() {
        return this.team1PointsInput.getAttribute('value');
    }

    setTeam2PointsInput = function(team2Points) {
        this.team2PointsInput.sendKeys(team2Points);
    }

    getTeam2PointsInput = function() {
        return this.team2PointsInput.getAttribute('value');
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
