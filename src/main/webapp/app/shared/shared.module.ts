import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BugTrackerJHipsterSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BugTrackerJHipsterSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BugTrackerJHipsterSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BugTrackerJHipsterSharedModule {
  static forRoot() {
    return {
      ngModule: BugTrackerJHipsterSharedModule
    };
  }
}
