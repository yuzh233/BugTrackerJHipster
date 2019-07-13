/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WarehouseComponentsPage, WarehouseDeleteDialog, WarehouseUpdatePage } from './warehouse.page-object';

const expect = chai.expect;

describe('Warehouse e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let warehouseUpdatePage: WarehouseUpdatePage;
  let warehouseComponentsPage: WarehouseComponentsPage;
  let warehouseDeleteDialog: WarehouseDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Warehouses', async () => {
    await navBarPage.goToEntity('warehouse');
    warehouseComponentsPage = new WarehouseComponentsPage();
    await browser.wait(ec.visibilityOf(warehouseComponentsPage.title), 5000);
    expect(await warehouseComponentsPage.getTitle()).to.eq('bugTrackerJHipsterApp.warehouse.home.title');
  });

  it('should load create Warehouse page', async () => {
    await warehouseComponentsPage.clickOnCreateButton();
    warehouseUpdatePage = new WarehouseUpdatePage();
    expect(await warehouseUpdatePage.getPageTitle()).to.eq('bugTrackerJHipsterApp.warehouse.home.createOrEditLabel');
    await warehouseUpdatePage.cancel();
  });

  it('should create and save Warehouses', async () => {
    const nbButtonsBeforeCreate = await warehouseComponentsPage.countDeleteButtons();

    await warehouseComponentsPage.clickOnCreateButton();
    await promise.all([
      warehouseUpdatePage.setNameInput('name'),
      warehouseUpdatePage.setAddressInput('address'),
      warehouseUpdatePage.setStockInput('5'),
      warehouseUpdatePage.setOwnerInput('owner'),
      warehouseUpdatePage.setGmtCreateInput('2000-12-31'),
      warehouseUpdatePage.setGmtModifiedInput('2000-12-31'),
      warehouseUpdatePage.setDeletedInput('5')
    ]);
    expect(await warehouseUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await warehouseUpdatePage.getAddressInput()).to.eq('address', 'Expected Address value to be equals to address');
    expect(await warehouseUpdatePage.getStockInput()).to.eq('5', 'Expected stock value to be equals to 5');
    expect(await warehouseUpdatePage.getOwnerInput()).to.eq('owner', 'Expected Owner value to be equals to owner');
    expect(await warehouseUpdatePage.getGmtCreateInput()).to.eq('2000-12-31', 'Expected gmtCreate value to be equals to 2000-12-31');
    expect(await warehouseUpdatePage.getGmtModifiedInput()).to.eq('2000-12-31', 'Expected gmtModified value to be equals to 2000-12-31');
    expect(await warehouseUpdatePage.getDeletedInput()).to.eq('5', 'Expected deleted value to be equals to 5');
    await warehouseUpdatePage.save();
    expect(await warehouseUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await warehouseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Warehouse', async () => {
    const nbButtonsBeforeDelete = await warehouseComponentsPage.countDeleteButtons();
    await warehouseComponentsPage.clickOnLastDeleteButton();

    warehouseDeleteDialog = new WarehouseDeleteDialog();
    expect(await warehouseDeleteDialog.getDialogTitle()).to.eq('bugTrackerJHipsterApp.warehouse.delete.question');
    await warehouseDeleteDialog.clickOnConfirmButton();

    expect(await warehouseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
