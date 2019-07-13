import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class WarehouseComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-warehouse div table .btn-danger'));
  title = element.all(by.css('jhi-warehouse div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class WarehouseUpdatePage {
  pageTitle = element(by.id('jhi-warehouse-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  addressInput = element(by.id('field_address'));
  stockInput = element(by.id('field_stock'));
  ownerInput = element(by.id('field_owner'));
  gmtCreateInput = element(by.id('field_gmtCreate'));
  gmtModifiedInput = element(by.id('field_gmtModified'));
  deletedInput = element(by.id('field_deleted'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return await this.addressInput.getAttribute('value');
  }

  async setStockInput(stock) {
    await this.stockInput.sendKeys(stock);
  }

  async getStockInput() {
    return await this.stockInput.getAttribute('value');
  }

  async setOwnerInput(owner) {
    await this.ownerInput.sendKeys(owner);
  }

  async getOwnerInput() {
    return await this.ownerInput.getAttribute('value');
  }

  async setGmtCreateInput(gmtCreate) {
    await this.gmtCreateInput.sendKeys(gmtCreate);
  }

  async getGmtCreateInput() {
    return await this.gmtCreateInput.getAttribute('value');
  }

  async setGmtModifiedInput(gmtModified) {
    await this.gmtModifiedInput.sendKeys(gmtModified);
  }

  async getGmtModifiedInput() {
    return await this.gmtModifiedInput.getAttribute('value');
  }

  async setDeletedInput(deleted) {
    await this.deletedInput.sendKeys(deleted);
  }

  async getDeletedInput() {
    return await this.deletedInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class WarehouseDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-warehouse-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-warehouse'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
