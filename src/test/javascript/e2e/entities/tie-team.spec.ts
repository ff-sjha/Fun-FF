import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('TieTeam e2e test', () => {

    let navBarPage: NavBarPage;
    let tieTeamDialogPage: TieTeamDialogPage;
    let tieTeamComponentsPage: TieTeamComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TieTeams', () => {
        navBarPage.goToEntity('tie-team');
        tieTeamComponentsPage = new TieTeamComponentsPage();
        expect(tieTeamComponentsPage.getTitle()).toMatch(/fafiApp.tieTeam.home.title/);

    });

    it('should load create TieTeam dialog', () => {
        tieTeamComponentsPage.clickOnCreateButton();
        tieTeamDialogPage = new TieTeamDialogPage();
        expect(tieTeamDialogPage.getModalTitle()).toMatch(/fafiApp.tieTeam.home.createOrEditLabel/);
        tieTeamDialogPage.close();
    });

    it('should create and save TieTeams', () => {
        tieTeamComponentsPage.clickOnCreateButton();
        tieTeamDialogPage.setPointsInput('5');
        expect(tieTeamDialogPage.getPointsInput()).toMatch('5');
        tieTeamDialogPage.save();
        expect(tieTeamDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TieTeamComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-tie-team div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TieTeamDialogPage {
    modalTitle = element(by.css('h4#myTieTeamLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    pointsInput = element(by.css('input#field_points'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPointsInput = function(points) {
        this.pointsInput.sendKeys(points);
    }

    getPointsInput = function() {
        return this.pointsInput.getAttribute('value');
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
