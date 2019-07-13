import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IGoods, Goods } from 'app/shared/model/goods.model';
import { GoodsService } from './goods.service';
import { IWarehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse';

@Component({
  selector: 'jhi-goods-update',
  templateUrl: './goods-update.component.html'
})
export class GoodsUpdateComponent implements OnInit {
  isSaving: boolean;

  warehouses: IWarehouse[];
  gmtCreateDp: any;
  gmtModifiedDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    sku: [],
    type: [],
    gmtCreate: [],
    gmtModified: [],
    deleted: [],
    warehouseId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected goodsService: GoodsService,
    protected warehouseService: WarehouseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ goods }) => {
      this.updateForm(goods);
    });
    this.warehouseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWarehouse[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWarehouse[]>) => response.body)
      )
      .subscribe((res: IWarehouse[]) => (this.warehouses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(goods: IGoods) {
    this.editForm.patchValue({
      id: goods.id,
      name: goods.name,
      sku: goods.sku,
      type: goods.type,
      gmtCreate: goods.gmtCreate,
      gmtModified: goods.gmtModified,
      deleted: goods.deleted,
      warehouseId: goods.warehouseId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const goods = this.createFromForm();
    if (goods.id !== undefined) {
      this.subscribeToSaveResponse(this.goodsService.update(goods));
    } else {
      this.subscribeToSaveResponse(this.goodsService.create(goods));
    }
  }

  private createFromForm(): IGoods {
    return {
      ...new Goods(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      sku: this.editForm.get(['sku']).value,
      type: this.editForm.get(['type']).value,
      gmtCreate: this.editForm.get(['gmtCreate']).value,
      gmtModified: this.editForm.get(['gmtModified']).value,
      deleted: this.editForm.get(['deleted']).value,
      warehouseId: this.editForm.get(['warehouseId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoods>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackWarehouseById(index: number, item: IWarehouse) {
    return item.id;
  }
}
