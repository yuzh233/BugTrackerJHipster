import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class GoodsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-goods div table .btn-danger'));
  title = element.all(by.css('jhi-goods div h2#page-heading span')).first();

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

export class GoodsUpdatePage {
  pageTitle = element(by.id('jhi-goods-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  skuInput = element(by.id('field_sku'));
  typeSelect = element(by.id('field_type'));
  gmtCreateInput = element(by.id('field_gmtCreate'));
  gmtModifiedInput = element(by.id('field_gmtModified'));
  deletedInput = element(by.id('field_deleted'));
  warehouseSelect = element(by.id('field_warehouse'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setSkuInput(sku) {
    await this.skuInput.sendKeys(sku);
  }

  async getSkuInput() {
    return await this.skuInput.getAttribute('value');
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(timeout?: number) {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

  async warehouseSelectLastOption(timeout?: number) {
    await this.warehouseSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async warehouseSelectOption(option) {
    await this.warehouseSelect.sendKeys(option);
  }

  getWarehouseSelect(): ElementFinder {
    return this.warehouseSelect;
  }

  async getWarehouseSelectedOption() {
    return await this.warehouseSelect.element(by.css('option:checked')).getText();
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

export class GoodsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-goods-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-goods'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
