import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BugTrackerJHipsterSharedModule } from 'app/shared';
import {
  GoodsComponent,
  GoodsDetailComponent,
  GoodsUpdateComponent,
  GoodsDeletePopupComponent,
  GoodsDeleteDialogComponent,
  goodsRoute,
  goodsPopupRoute
} from './';

const ENTITY_STATES = [...goodsRoute, ...goodsPopupRoute];

@NgModule({
  imports: [BugTrackerJHipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [GoodsComponent, GoodsDetailComponent, GoodsUpdateComponent, GoodsDeleteDialogComponent, GoodsDeletePopupComponent],
  entryComponents: [GoodsComponent, GoodsUpdateComponent, GoodsDeleteDialogComponent, GoodsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BugTrackerJHipsterGoodsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
