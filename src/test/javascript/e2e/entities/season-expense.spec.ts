import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SeasonExpense e2e test', () => {

    let navBarPage: NavBarPage;
    let seasonExpenseDialogPage: SeasonExpenseDialogPage;
    let seasonExpenseComponentsPage: SeasonExpenseComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SeasonExpenses', () => {
        navBarPage.goToEntity('season-expense');
        seasonExpenseComponentsPage = new SeasonExpenseComponentsPage();
        expect(seasonExpenseComponentsPage.getTitle()).toMatch(/fafiApp.seasonExpense.home.title/);

    });

    it('should load create SeasonExpense dialog', () => {
        seasonExpenseComponentsPage.clickOnCreateButton();
        seasonExpenseDialogPage = new SeasonExpenseDialogPage();
        expect(seasonExpenseDialogPage.getModalTitle()).toMatch(/fafiApp.seasonExpense.home.createOrEditLabel/);
        seasonExpenseDialogPage.close();
    });

   /* it('should create and save SeasonExpenses', () => {
        seasonExpenseComponentsPage.clickOnCreateButton();
        seasonExpenseDialogPage.setIncurredDateInput('2000-12-31');
        expect(seasonExpenseDialogPage.getIncurredDateInput()).toMatch('2000-12-31');
        seasonExpenseDialogPage.setDescriptionInput('description');
        expect(seasonExpenseDialogPage.getDescriptionInput()).toMatch('description');
        seasonExpenseDialogPage.setAmountInput('5');
        expect(seasonExpenseDialogPage.getAmountInput()).toMatch('5');
        seasonExpenseDialogPage.seasonSelectLastOption();
        seasonExpenseDialogPage.save();
        expect(seasonExpenseDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SeasonExpenseComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('fafi-season-expense div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SeasonExpenseDialogPage {
    modalTitle = element(by.css('h4#mySeasonExpenseLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    incurredDateInput = element(by.css('input#field_incurredDate'));
    descriptionInput = element(by.css('input#field_description'));
    amountInput = element(by.css('input#field_amount'));
    seasonSelect = element(by.css('select#field_season'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setIncurredDateInput = function(incurredDate) {
        this.incurredDateInput.sendKeys(incurredDate);
    }

    getIncurredDateInput = function() {
        return this.incurredDateInput.getAttribute('value');
    }

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    }

    setAmountInput = function(amount) {
        this.amountInput.sendKeys(amount);
    }

    getAmountInput = function() {
        return this.amountInput.getAttribute('value');
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
