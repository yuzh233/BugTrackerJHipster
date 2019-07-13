/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BugTrackerJHipsterTestModule } from '../../../test.module';
import { GoodsUpdateComponent } from 'app/entities/goods/goods-update.component';
import { GoodsService } from 'app/entities/goods/goods.service';
import { Goods } from 'app/shared/model/goods.model';

describe('Component Tests', () => {
  describe('Goods Management Update Component', () => {
    let comp: GoodsUpdateComponent;
    let fixture: ComponentFixture<GoodsUpdateComponent>;
    let service: GoodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BugTrackerJHipsterTestModule],
        declarations: [GoodsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GoodsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoodsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoodsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Goods(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Goods();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
