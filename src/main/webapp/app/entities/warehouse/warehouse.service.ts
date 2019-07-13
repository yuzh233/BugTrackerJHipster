import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWarehouse } from 'app/shared/model/warehouse.model';

type EntityResponseType = HttpResponse<IWarehouse>;
type EntityArrayResponseType = HttpResponse<IWarehouse[]>;

@Injectable({ providedIn: 'root' })
export class WarehouseService {
  public resourceUrl = SERVER_API_URL + 'api/warehouses';

  constructor(protected http: HttpClient) {}

  create(warehouse: IWarehouse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(warehouse);
    return this.http
      .post<IWarehouse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(warehouse: IWarehouse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(warehouse);
    return this.http
      .put<IWarehouse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWarehouse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWarehouse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(warehouse: IWarehouse): IWarehouse {
    const copy: IWarehouse = Object.assign({}, warehouse, {
      gmtCreate: warehouse.gmtCreate != null && warehouse.gmtCreate.isValid() ? warehouse.gmtCreate.format(DATE_FORMAT) : null,
      gmtModified: warehouse.gmtModified != null && warehouse.gmtModified.isValid() ? warehouse.gmtModified.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.gmtCreate = res.body.gmtCreate != null ? moment(res.body.gmtCreate) : null;
      res.body.gmtModified = res.body.gmtModified != null ? moment(res.body.gmtModified) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((warehouse: IWarehouse) => {
        warehouse.gmtCreate = warehouse.gmtCreate != null ? moment(warehouse.gmtCreate) : null;
        warehouse.gmtModified = warehouse.gmtModified != null ? moment(warehouse.gmtModified) : null;
      });
    }
    return res;
  }
}
