import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SeasonsFranchisePlayer e2e test', () => {

    let navBarPage: NavBarPage;
    let seasonsFranchisePlayerDialogPage: SeasonsFranchisePlayerDialogPage;
    let seasonsFranchisePlayerComponentsPage: SeasonsFranchisePlayerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SeasonsFranchisePlayers', () => {
        navBarPage.goToEntity('seasons-franchise-player');
        seasonsFranchisePlayerComponentsPage = new SeasonsFranchisePlayerComponentsPage();
        expect(seasonsFranchisePlayerComponentsPage.getTitle()).toMatch(/fafiApp.seasonsFranchisePlayer.home.title/);

    });

    it('should load create SeasonsFranchisePlayer dialog', () => {
        seasonsFranchisePlayerComponentsPage.clickOnCreateButton();
        seasonsFranchisePlayerDialogPage = new SeasonsFranchisePlayerDialogPage();
        expect(seasonsFranchisePlayerDialogPage.getModalTitle()).toMatch(/fafiApp.seasonsFranchisePlayer.home.createOrEditLabel/);
        seasonsFranchisePlayerDialogPage.close();
    });

   /* it('should create and save SeasonsFranchisePlayers', () => {
        seasonsFranchisePlayerComponentsPage.clickOnCreateButton();
        seasonsFranchisePlayerDialogPage.setBidPriceInput('5');
        expect(seasonsFranchisePlayerDialogPage.getBidPriceInput()).toMatch('5');
        seasonsFranchisePlayerDialogPage.seasonsFranchiseSelectLastOption();
        seasonsFranchisePlayerDialogPage.playerSelectLastOption();
        seasonsFranchisePlayerDialogPage.save();
        expect(seasonsFranchisePlayerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SeasonsFranchisePlayerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-seasons-franchise-player div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SeasonsFranchisePlayerDialogPage {
    modalTitle = element(by.css('h4#mySeasonsFranchisePlayerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    bidPriceInput = element(by.css('input#field_bidPrice'));
    seasonsFranchiseSelect = element(by.css('select#field_seasonsFranchise'));
    playerSelect = element(by.css('select#field_player'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBidPriceInput = function(bidPrice) {
        this.bidPriceInput.sendKeys(bidPrice);
    }

    getBidPriceInput = function() {
        return this.bidPriceInput.getAttribute('value');
    }

    seasonsFranchiseSelectLastOption = function() {
        this.seasonsFranchiseSelect.all(by.tagName('option')).last().click();
    }

    seasonsFranchiseSelectOption = function(option) {
        this.seasonsFranchiseSelect.sendKeys(option);
    }

    getSeasonsFranchiseSelect = function() {
        return this.seasonsFranchiseSelect;
    }

    getSeasonsFranchiseSelectedOption = function() {
        return this.seasonsFranchiseSelect.element(by.css('option:checked')).getText();
    }

    playerSelectLastOption = function() {
        this.playerSelect.all(by.tagName('option')).last().click();
    }

    playerSelectOption = function(option) {
        this.playerSelect.sendKeys(option);
    }

    getPlayerSelect = function() {
        return this.playerSelect;
    }

    getPlayerSelectedOption = function() {
        return this.playerSelect.element(by.css('option:checked')).getText();
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
