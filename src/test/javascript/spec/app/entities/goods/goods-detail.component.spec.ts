/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BugTrackerJHipsterTestModule } from '../../../test.module';
import { GoodsDetailComponent } from 'app/entities/goods/goods-detail.component';
import { Goods } from 'app/shared/model/goods.model';

describe('Component Tests', () => {
  describe('Goods Management Detail Component', () => {
    let comp: GoodsDetailComponent;
    let fixture: ComponentFixture<GoodsDetailComponent>;
    const route = ({ data: of({ goods: new Goods(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BugTrackerJHipsterTestModule],
        declarations: [GoodsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GoodsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoodsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goods).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
