import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BugTrackerJHipsterSharedModule } from 'app/shared';
import {
  WarehouseComponent,
  WarehouseDetailComponent,
  WarehouseUpdateComponent,
  WarehouseDeletePopupComponent,
  WarehouseDeleteDialogComponent,
  warehouseRoute,
  warehousePopupRoute
} from './';

const ENTITY_STATES = [...warehouseRoute, ...warehousePopupRoute];

@NgModule({
  imports: [BugTrackerJHipsterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    WarehouseComponent,
    WarehouseDetailComponent,
    WarehouseUpdateComponent,
    WarehouseDeleteDialogComponent,
    WarehouseDeletePopupComponent
  ],
  entryComponents: [WarehouseComponent, WarehouseUpdateComponent, WarehouseDeleteDialogComponent, WarehouseDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BugTrackerJHipsterWarehouseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
