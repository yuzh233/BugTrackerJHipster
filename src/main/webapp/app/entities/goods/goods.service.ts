import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGoods } from 'app/shared/model/goods.model';

type EntityResponseType = HttpResponse<IGoods>;
type EntityArrayResponseType = HttpResponse<IGoods[]>;

@Injectable({ providedIn: 'root' })
export class GoodsService {
  public resourceUrl = SERVER_API_URL + 'api/goods';

  constructor(protected http: HttpClient) {}

  create(goods: IGoods): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goods);
    return this.http
      .post<IGoods>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(goods: IGoods): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goods);
    return this.http
      .put<IGoods>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGoods>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGoods[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(goods: IGoods): IGoods {
    const copy: IGoods = Object.assign({}, goods, {
      gmtCreate: goods.gmtCreate != null && goods.gmtCreate.isValid() ? goods.gmtCreate.format(DATE_FORMAT) : null,
      gmtModified: goods.gmtModified != null && goods.gmtModified.isValid() ? goods.gmtModified.format(DATE_FORMAT) : null
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
      res.body.forEach((goods: IGoods) => {
        goods.gmtCreate = goods.gmtCreate != null ? moment(goods.gmtCreate) : null;
        goods.gmtModified = goods.gmtModified != null ? moment(goods.gmtModified) : null;
      });
    }
    return res;
  }
}
