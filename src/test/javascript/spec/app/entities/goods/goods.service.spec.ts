/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GoodsService } from 'app/entities/goods/goods.service';
import { IGoods, Goods, GoodsType } from 'app/shared/model/goods.model';

describe('Service Tests', () => {
  describe('Goods Service', () => {
    let injector: TestBed;
    let service: GoodsService;
    let httpMock: HttpTestingController;
    let elemDefault: IGoods;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GoodsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Goods(0, 'AAAAAAA', 0, GoodsType.FINE, currentDate, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            gmtCreate: currentDate.format(DATE_FORMAT),
            gmtModified: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Goods', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            gmtCreate: currentDate.format(DATE_FORMAT),
            gmtModified: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            gmtCreate: currentDate,
            gmtModified: currentDate
          },
          returnedFromService
        );
        service
          .create(new Goods(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Goods', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            sku: 1,
            type: 'BBBBBB',
            gmtCreate: currentDate.format(DATE_FORMAT),
            gmtModified: currentDate.format(DATE_FORMAT),
            deleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            gmtCreate: currentDate,
            gmtModified: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Goods', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            sku: 1,
            type: 'BBBBBB',
            gmtCreate: currentDate.format(DATE_FORMAT),
            gmtModified: currentDate.format(DATE_FORMAT),
            deleted: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            gmtCreate: currentDate,
            gmtModified: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Goods', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
