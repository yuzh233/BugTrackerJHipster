import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IWarehouse, Warehouse } from 'app/shared/model/warehouse.model';
import { WarehouseService } from './warehouse.service';

@Component({
  selector: 'jhi-warehouse-update',
  templateUrl: './warehouse-update.component.html'
})
export class WarehouseUpdateComponent implements OnInit {
  isSaving: boolean;
  gmtCreateDp: any;
  gmtModifiedDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
    stock: [],
    owner: [],
    gmtCreate: [],
    gmtModified: [],
    deleted: []
  });

  constructor(protected warehouseService: WarehouseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ warehouse }) => {
      this.updateForm(warehouse);
    });
  }

  updateForm(warehouse: IWarehouse) {
    this.editForm.patchValue({
      id: warehouse.id,
      name: warehouse.name,
      address: warehouse.address,
      stock: warehouse.stock,
      owner: warehouse.owner,
      gmtCreate: warehouse.gmtCreate,
      gmtModified: warehouse.gmtModified,
      deleted: warehouse.deleted
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const warehouse = this.createFromForm();
    if (warehouse.id !== undefined) {
      this.subscribeToSaveResponse(this.warehouseService.update(warehouse));
    } else {
      this.subscribeToSaveResponse(this.warehouseService.create(warehouse));
    }
  }

  private createFromForm(): IWarehouse {
    return {
      ...new Warehouse(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      stock: this.editForm.get(['stock']).value,
      owner: this.editForm.get(['owner']).value,
      gmtCreate: this.editForm.get(['gmtCreate']).value,
      gmtModified: this.editForm.get(['gmtModified']).value,
      deleted: this.editForm.get(['deleted']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWarehouse>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
