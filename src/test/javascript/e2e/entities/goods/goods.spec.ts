/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GoodsComponentsPage, GoodsDeleteDialog, GoodsUpdatePage } from './goods.page-object';

const expect = chai.expect;

describe('Goods e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let goodsUpdatePage: GoodsUpdatePage;
  let goodsComponentsPage: GoodsComponentsPage;
  let goodsDeleteDialog: GoodsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Goods', async () => {
    await navBarPage.goToEntity('goods');
    goodsComponentsPage = new GoodsComponentsPage();
    await browser.wait(ec.visibilityOf(goodsComponentsPage.title), 5000);
    expect(await goodsComponentsPage.getTitle()).to.eq('bugTrackerJHipsterApp.goods.home.title');
  });

  it('should load create Goods page', async () => {
    await goodsComponentsPage.clickOnCreateButton();
    goodsUpdatePage = new GoodsUpdatePage();
    expect(await goodsUpdatePage.getPageTitle()).to.eq('bugTrackerJHipsterApp.goods.home.createOrEditLabel');
    await goodsUpdatePage.cancel();
  });

  it('should create and save Goods', async () => {
    const nbButtonsBeforeCreate = await goodsComponentsPage.countDeleteButtons();

    await goodsComponentsPage.clickOnCreateButton();
    await promise.all([
      goodsUpdatePage.setNameInput('name'),
      goodsUpdatePage.setSkuInput('5'),
      goodsUpdatePage.typeSelectLastOption(),
      goodsUpdatePage.setGmtCreateInput('2000-12-31'),
      goodsUpdatePage.setGmtModifiedInput('2000-12-31'),
      goodsUpdatePage.setDeletedInput('5'),
      goodsUpdatePage.warehouseSelectLastOption()
    ]);
    expect(await goodsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await goodsUpdatePage.getSkuInput()).to.eq('5', 'Expected sku value to be equals to 5');
    expect(await goodsUpdatePage.getGmtCreateInput()).to.eq('2000-12-31', 'Expected gmtCreate value to be equals to 2000-12-31');
    expect(await goodsUpdatePage.getGmtModifiedInput()).to.eq('2000-12-31', 'Expected gmtModified value to be equals to 2000-12-31');
    expect(await goodsUpdatePage.getDeletedInput()).to.eq('5', 'Expected deleted value to be equals to 5');
    await goodsUpdatePage.save();
    expect(await goodsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await goodsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Goods', async () => {
    const nbButtonsBeforeDelete = await goodsComponentsPage.countDeleteButtons();
    await goodsComponentsPage.clickOnLastDeleteButton();

    goodsDeleteDialog = new GoodsDeleteDialog();
    expect(await goodsDeleteDialog.getDialogTitle()).to.eq('bugTrackerJHipsterApp.goods.delete.question');
    await goodsDeleteDialog.clickOnConfirmButton();

    expect(await goodsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
