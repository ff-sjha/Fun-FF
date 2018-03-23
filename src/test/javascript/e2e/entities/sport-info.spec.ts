import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('SportInfo e2e test', () => {

    let navBarPage: NavBarPage;
    let sportInfoDialogPage: SportInfoDialogPage;
    let sportInfoComponentsPage: SportInfoComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SportInfos', () => {
        navBarPage.goToEntity('sport-info');
        sportInfoComponentsPage = new SportInfoComponentsPage();
        expect(sportInfoComponentsPage.getTitle()).toMatch(/fafiApp.sportInfo.home.title/);

    });

    it('should load create SportInfo dialog', () => {
        sportInfoComponentsPage.clickOnCreateButton();
        sportInfoDialogPage = new SportInfoDialogPage();
        expect(sportInfoDialogPage.getModalTitle()).toMatch(/fafiApp.sportInfo.home.createOrEditLabel/);
        sportInfoDialogPage.close();
    });

    it('should create and save SportInfos', () => {
        sportInfoComponentsPage.clickOnCreateButton();
        sportInfoDialogPage.gameSelectLastOption();
        sportInfoDialogPage.setRulesInput('rules');
        expect(sportInfoDialogPage.getRulesInput()).toMatch('rules');
        sportInfoDialogPage.setScoring_systemInput('scoring_system');
        expect(sportInfoDialogPage.getScoring_systemInput()).toMatch('scoring_system');
        sportInfoDialogPage.setPoints_systemInput('points_system');
        expect(sportInfoDialogPage.getPoints_systemInput()).toMatch('points_system');
        sportInfoDialogPage.setMatch_systemInput('match_system');
        expect(sportInfoDialogPage.getMatch_systemInput()).toMatch('match_system');
        sportInfoDialogPage.save();
        expect(sportInfoDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SportInfoComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-sport-info div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SportInfoDialogPage {
    modalTitle = element(by.css('h4#mySportInfoLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    gameSelect = element(by.css('select#field_game'));
    rulesInput = element(by.css('textarea#field_rules'));
    scoring_systemInput = element(by.css('textarea#field_scoring_system'));
    points_systemInput = element(by.css('textarea#field_points_system'));
    match_systemInput = element(by.css('textarea#field_match_system'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setGameSelect = function(game) {
        this.gameSelect.sendKeys(game);
    }

    getGameSelect = function() {
        return this.gameSelect.element(by.css('option:checked')).getText();
    }

    gameSelectLastOption = function() {
        this.gameSelect.all(by.tagName('option')).last().click();
    }
    setRulesInput = function(rules) {
        this.rulesInput.sendKeys(rules);
    }

    getRulesInput = function() {
        return this.rulesInput.getAttribute('value');
    }

    setScoring_systemInput = function(scoring_system) {
        this.scoring_systemInput.sendKeys(scoring_system);
    }

    getScoring_systemInput = function() {
        return this.scoring_systemInput.getAttribute('value');
    }

    setPoints_systemInput = function(points_system) {
        this.points_systemInput.sendKeys(points_system);
    }

    getPoints_systemInput = function() {
        return this.points_systemInput.getAttribute('value');
    }

    setMatch_systemInput = function(match_system) {
        this.match_systemInput.sendKeys(match_system);
    }

    getMatch_systemInput = function() {
        return this.match_systemInput.getAttribute('value');
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
